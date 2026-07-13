package com.boot2;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeTest2 {
    /*Please find the below the coding questions asked to candidate:
    Question 1: You are given a list of Employee objects, each containing a name and a salary. Write a program using Java Stream API.
            Employee("Alice", 60000),
    Employee("Bob", 45000),
    Employee("Charlie", 70000)
    Filter out employees whose salary is greater than 50,000, and
    Collect and return the names of these high-earning employees in a list.
    Output to be obtained: [Alice, Charlie]*/

    public static void main(String[] args) {

        List<Employee> empList = Arrays.asList(new Employee("Alice", 60000),new  Employee("Bob", 45000),
                new Employee("Charlie", 70000) );
        Set<String> earnList =  empList.stream().filter(emp-> emp.getSalary()>50000)
                .map(emp->emp.getName())
                .collect(Collectors.toSet());

        System.out.println("High Earners : "+  earnList);

        int x=-4;
        System.out.println(x>>1);
        int y=4;
        System.out.println(y>>1);

    }
    static class Employee{
        private String name;
        private int salary;

        Employee(String name, int salary) {
           this.name=name;
           this.salary=salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }
    }


}
