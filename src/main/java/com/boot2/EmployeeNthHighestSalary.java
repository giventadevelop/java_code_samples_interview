package com.boot2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interview scratchpad: highest salary in the HR department (Java + SQL),
 * plus Kafka notes on idempotency and “one message → one consumer.”
 *
 * <h2>1. Intent</h2>
 * Find the employee (or salary) with the <b>highest salary</b> in department
 * <b>HR</b>, and also the <b>3rd largest</b> (Nth distinct) salary. Below: working
 * Java streams, a Map by employee id, and correct SQL
 * (the original {@code row_id = 4} filter was wrong — that is not “highest”).
 *
 * <p><b>Salary type:</b> {@link Employee} uses {@link BigDecimal}. Compare with
 * {@code compareTo} (not {@code ==}), sort with {@code Comparator.naturalOrder()},
 * and build values from strings ({@code new BigDecimal("90000.00")}) when possible.
 * Maps cleanly to SQL {@code DECIMAL}/{@code NUMERIC}.</p>
 *
 * <h2>2. Correct SQL answers</h2>
 *
 * <p>Assume tables roughly like:</p>
 * <pre>{@code
 * employee(id, first_name, last_name, dept_id, salary DECIMAL)
 * department(id, name)   -- e.g. name = 'HR'
 * }</pre>
 *
 * <h3>A) Only the max salary number in HR</h3>
 * <pre>{@code
 * SELECT MAX(e.salary) AS highest_hr_salary
 * FROM employee e
 * JOIN department d ON d.id = e.dept_id
 * WHERE d.name = 'HR';
 * }</pre>
 *
 * <h3>B) Full employee row(s) with that highest salary in HR</h3>
 * <pre>{@code
 * SELECT e.*
 * FROM employee e
 * JOIN department d ON d.id = e.dept_id
 * WHERE d.name = 'HR'
 *   AND e.salary = (
 *         SELECT MAX(e2.salary)
 *         FROM employee e2
 *         JOIN department d2 ON d2.id = e2.dept_id
 *         WHERE d2.name = 'HR'
 *       );
 * }</pre>
 *
 * <h3>C) Same idea with ORDER BY + LIMIT (PostgreSQL / MySQL)</h3>
 * <pre>{@code
 * SELECT e.*
 * FROM employee e
 * JOIN department d ON d.id = e.dept_id
 * WHERE d.name = 'HR'
 * ORDER BY e.salary DESC
 * LIMIT 1;
 * }</pre>
 *
 * <h3>D) If {@code dept_id} is already the string {@code 'HR'} (no join)</h3>
 * <pre>{@code
 * SELECT MAX(salary) FROM employee WHERE dept_id = 'HR';
 * -- or
 * SELECT * FROM employee WHERE dept_id = 'HR' ORDER BY salary DESC LIMIT 1;
 * }</pre>
 *
 * <h3>E) 3rd largest salary in HR (distinct ranks)</h3>
 * <pre>{@code
 * SELECT salary
 * FROM (
 *   SELECT e.salary,
 *          DENSE_RANK() OVER (ORDER BY e.salary DESC) AS rnk
 *   FROM employee e
 *   JOIN department d ON d.id = e.dept_id
 *   WHERE d.name = 'HR'
 * ) t
 * WHERE rnk = 3;
 *
 * SELECT DISTINCT e.salary
 * FROM employee e
 * JOIN department d ON d.id = e.dept_id
 * WHERE d.name = 'HR'
 * ORDER BY e.salary DESC
 * OFFSET 2 LIMIT 1;
 * }</pre>
 *
 * <p><b>Why the sketch SQL failed:</b>
 * {@code WHERE ... AND row_id = 4 ORDER BY salary DESC} picks an arbitrary row id,
 * not the maximum salary. Use {@code MAX}, a subquery, window functions, or
 * {@code ORDER BY salary DESC LIMIT 1}.</p>
 *
 * <h2>3. Kafka — idempotent produce &amp; one consumer per message</h2>
 *
 * <h3>Idempotent producer (no duplicate writes on retry)</h3>
 * <ul>
 *   <li>Set {@code enable.idempotence=true} on the producer (Kafka also implies
 *       {@code acks=all} and limits in-flight requests).</li>
 *   <li>Broker assigns a Producer ID (PID); each message gets a sequence number
 *       per partition. Retries with the same PID+seq are ignored → <b>exactly-once
 *       produce to a partition</b> for that producer session (no dup records from retries).</li>
 *   <li>For consume-transform-produce across topics, use <b>transactions</b>
 *       ({@code transactional.id}) so offset commit + output writes succeed or fail together.</li>
 * </ul>
 *
 * <h3>How we ensure one message is not processed by two consumers (same work)</h3>
 * <ul>
 *   <li><b>Consumer group:</b> consumers that share the same {@code group.id} form a group.
 *       Kafka assigns each <em>partition</em> to <strong>at most one</strong> consumer in that group.</li>
 *   <li>A given record lives in one partition at one offset → only the consumer that owns
 *       that partition reads it for that group. Two members of the <em>same</em> group
 *       will not both process the same partition’s messages concurrently.</li>
 *   <li>If you start a <em>second</em> group with a different {@code group.id}, that group
 *       gets its <em>own</em> copy of the stream (pub-sub). That is intentional fan-out,
 *       not double-processing within one app.</li>
 *   <li><b>At-least-once vs exactly-once consume:</b> default delivery is at-least-once
 *       (crash after process but before commit → redelivery). Make the <em>handler</em>
 *       idempotent (dedupe table on message key/id, upserts, unique DB constraints) or use
 *       EOS transactions so “process + commit offset” is atomic.</li>
 * </ul>
 *
 * <p><b>Interview one-liner:</b> Idempotent producer stops duplicate <em>writes</em> on retry;
 * consumer groups stop two workers in the <em>same group</em> from reading the same partition;
 * idempotent consumers / transactions stop double <em>side effects</em> on redelivery.</p>
 */
public class EmployeeNthHighestSalary {

    /** Sample convention in this class: deptId {@code 2} means HR. */
    public static final int HR_DEPT_ID = 2;

    public static void main(String[] args) {
        List<Employee> employeeList = sampleEmployees();

        Optional<Employee> highestInHr = findHighestSalaryInHr(employeeList);
        highestInHr.ifPresentOrElse(
                e -> System.out.println("Highest HR salary: " + e),
                () -> System.out.println("No HR employees found")
        );

        findNthHighestSalary(employeeList, 3).ifPresentOrElse(
                s -> System.out.println("3rd largest salary (all depts): " + s),
                () -> System.out.println("Fewer than 3 distinct salaries company-wide")
        );
        findNthHighestSalaryInHr(employeeList, 3).ifPresentOrElse(
                s -> System.out.println("3rd largest salary (HR): " + s),
                () -> System.out.println("Fewer than 3 distinct HR salaries")
        );
        findEmployeesWithNthHighestSalaryInHr(employeeList, 3)
                .forEach(e -> System.out.println("  HR employee at 3rd rank: " + e));

        Map<Integer, Employee> byId = indexBySyntheticId(employeeList);
        System.out.println("Employees indexed: " + byId.size());
    }

    /**
     * Returns the HR employee with the maximum salary (one of them if ties).
     * Uses {@link Comparator#comparing(java.util.function.Function)} on {@link BigDecimal}.
     */
    public static Optional<Employee> findHighestSalaryInHr(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getDeptId() == HR_DEPT_ID)
                .max(Comparator.comparing(Employee::getSalary));
    }

    /** Sort descending then take first (clear but O(n log n)). */
    public static Optional<Employee> findHighestSalaryInHrBySort(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getDeptId() == HR_DEPT_ID)
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst();
    }

    /** 3rd largest distinct salary company-wide. */
    public static Optional<BigDecimal> find3rdHighestSalary(List<Employee> employees) {
        return findNthHighestSalary(employees, 3);
    }

    /**
     * Nth largest distinct salary company-wide (1 = highest).
     * Distinct + descending sort ≈ SQL {@code DENSE_RANK}.
     */
    public static Optional<BigDecimal> findNthHighestSalary(List<Employee> employees, int n) {
        if (n < 1) {
            return Optional.empty();
        }
        return employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(n - 1L)
                .findFirst();
    }

    /** Nth largest distinct salary within HR only. */
    public static Optional<BigDecimal> findNthHighestSalaryInHr(List<Employee> employees, int n) {
        if (n < 1) {
            return Optional.empty();
        }
        return employees.stream()
                .filter(e -> e.getDeptId() == HR_DEPT_ID)
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(n - 1L)
                .findFirst();
    }

    /** All HR employees at the Nth distinct HR salary rank. */
    public static List<Employee> findEmployeesWithNthHighestSalaryInHr(List<Employee> employees, int n) {
        Optional<BigDecimal> nth = findNthHighestSalaryInHr(employees, n);
        if (nth.isEmpty()) {
            return List.of();
        }
        BigDecimal target = nth.get();
        return employees.stream()
                .filter(e -> e.getDeptId() == HR_DEPT_ID)
                .filter(e -> e.getSalary().compareTo(target) == 0)
                .toList();
    }

    /** Demo data: Engineering=1, HR=2, Finance=3. */
    private static List<Employee> sampleEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Ann", "Lee", 1, bd("90000.00")));
        employeeList.add(new Employee("Bob", "Ng", 2, bd("85000.00")));
        employeeList.add(new Employee("Cara", "Diaz", 2, bd("110000.00")));
        employeeList.add(new Employee("Dan", "Cho", 2, bd("95000.00")));
        employeeList.add(new Employee("Eve", "Xu", 3, bd("120000.00")));
        return employeeList;
    }

    private static BigDecimal bd(String value) {
        return new BigDecimal(value);
    }

    private static Map<Integer, Employee> indexBySyntheticId(List<Employee> employees) {
        Map<Integer, Employee> map = new HashMap<>();
        for (int i = 0; i < employees.size(); i++) {
            map.put(i + 1, employees.get(i));
        }
        return map;
    }
}
