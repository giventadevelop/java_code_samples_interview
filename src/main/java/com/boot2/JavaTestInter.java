package com.boot2;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class JavaTestInter {

    public static <K, V extends Comparable<? super V>> void main(String[] args) {


        List<String> duplicatesList = new ArrayList<String>();
        duplicatesList.add("Mango");
        duplicatesList.add("Banana");
        duplicatesList.add("Mango");
        duplicatesList.add("Apple");
        duplicatesList.add("Banana");
        duplicatesList.add("Orange");

        // print items that are duplicated  and usage of Collections.frequency example
        Set<String> duplicatesSet =  duplicatesList.stream()
                                     .filter(name -> Collections.frequency (duplicatesList, name) > 1)
                                     .collect (Collectors.toSet());
        System.out.println("duplicated Items : "+duplicatesSet);

        // print distinct items in the list
        System.out.println("distinct items in the list : ");
        duplicatesList.stream().distinct().forEach(System.out::println);

        // print count of items in a list from a map
        Map<String, Long> collect1 = duplicatesList.stream()
                                    .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println("Count of items in a list from a map : "+ collect1 );


        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Value1");
        map.put(2, "Value2");
        map.put(1, "Value3");

        System.out.println(map);

        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put("a", 3);
        map2.put("b", 2);
        map2.put("c", 1);
        System.out.println("Before Sorting");

        // sort a HashMap by value  and return the LinkedHashMap
        LinkedHashMap<String, Integer> sortedMap = map2.entrySet().stream().sorted((el, e2) -> {
            return el.getValue() - e2.getValue();
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (el, e2) -> el, LinkedHashMap::new));

        System.out.println("sorted HashMap by value " +  sortedMap);

        //  sort the above in the reverse order
        Map<String, Integer> sortMapByValueDescending = sortMapByValueDescending(sortedMap);
        System.out.println("sortMapByValueDescending by value Descending " +  sortMapByValueDescending);
        // another mode of reverse sorting a hashmap  using TreeMap

        SortedMap<String, Integer> treeMapReverseSortedKeys = new TreeMap<>(Comparator.reverseOrder());
        treeMapReverseSortedKeys.put("one", 1);
        treeMapReverseSortedKeys.put("three", 3);
        treeMapReverseSortedKeys.put("five", 5);
        treeMapReverseSortedKeys.put("two", 2);
        treeMapReverseSortedKeys.put("four", 4);

        System.out.println("sorted treeMapReverseSorted by Keys reverse order   " +  treeMapReverseSortedKeys);

       /* assertEquals(5, treeMap.size());
        final Iterator<String> iterator = treeMap.keySet().iterator();
        assertEquals("two", iterator.next());
        assertEquals("three", iterator.next());
        assertEquals("one", iterator.next());
        assertEquals("four", iterator.next());
        assertEquals("five", iterator.next());*/


/*

        LinkedHashMap<String, Integer> reverseSortedMap =  map2.entrySet()
                .stream()
                .sorted((Comparator<? super Map.Entry<String, Integer>>) Map.Entry.<K,V>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("Reverse Sorted HashMap by value " +  reverseSortedMap);
*/

        // Count of each characters in a string stored in a Map
        String hello = "hello";
        Map<Character, Long> collect = hello.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        System.out.println("Count of each characters of a string stored in a Map : " + collect);

        List<Employee> employees = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int deptId = random.nextInt(1000) + 1;
            // Generate a random department id between 1 and 1000
            double salary = 50000 + random.nextDouble() * 50000;
            // Generate a random salary between 50000 and 100000
            employees.add(new Employee("John", "Doe",deptId, salary));
            employees.add(new Employee("Joe", "Smith",deptId, salary));
        }
        Optional<Employee> employeeOptional = Optional.ofNullable(null);
        // below NoSuchElementException: No value present
//        System.out.println("employeeOptional "+ employeeOptional.get().getSalary());
        // prints employeeOptional Optional.empty if null with ofNullable
        System.out.println("employeeOptional "+ employeeOptional);

//        List<String>  employees.stream().flatMap(emp -> emp.getPhoneList()).collect(Collectors.toList());
        /*List<String> empNames= employees.stream().map(emp -> emp.getFirstName()+ " "+ emp.getLastName()).collect(Collectors.toList());

        Set uniqeAddresses = new HashSet<String>();

        employees.stream().map(emp -> ).collect(Collectors.toList());

        Employee EmployeeId, Name, ManagerId, Subordinates 1, John, 5, [] 2, Jane, 5, [] 3, Mary, 2, [] 4, Dany, null, [] 5, Boss, null, []*/




        // Max Min from a stream
        Optional<Employee> emp1= employees.stream().max(Comparator.comparing (Employee::getSalary));
        Optional<Employee> emp2 = employees.stream().min(Comparator.comparing (Employee::getSalary));
        System.out.println("Max Salary "+ emp1.get().getSalary());
        System.out.println("Min Salary "+ emp2.get().getSalary());

        // get the topSalaryEmployee  group by DeptId
        Map<Integer, Optional<Employee>> topSalaryEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptId,
                        Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)))));

        Map<Integer, Optional<Employee>> topSalaryEmployees2 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptId,
                        Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)))));

        // get the top salary from Optional  from a sorted map

        Map<Integer, Double> topSalaryEmployeeSalary =  topSalaryEmployees.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue().get().getSalary()));

        // different ways of collecting data in the value field of the map
        Map<Integer, java.util.Optional<Employee>> mapMaxSalByDept = employees.stream()
                .collect(Collectors.groupingBy(
                Employee::getDeptId, Collectors.reducing (BinaryOperator.maxBy(Comparator.comparing (Employee::getSalary)))));
        Map<Integer,Double> mapMaxSalByDeptDble = employees.stream().
                collect(Collectors.groupingBy
                (Employee::getDeptId,
                  Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),emp -> emp.get().getSalary())));
        // printing each entry value of the map's entry set  with dept_id and highest salary
        System.out.println(" printing each entry value of the map's entry set  with dept_id and highest salary");
        mapMaxSalByDeptDble.entrySet().forEach(System.out::println);

        // other entry set print examples

        /*charType.forEach(
                (key, value)
                        -> System.out.println(key + " = " + value));

        // Iterating every set of entry in the HashMap, and
        // printing all elements of it
        intType.entrySet().stream().forEach(
                input
                        -> System.out.println(input.getKey() + " : "
                        + input.getValue()));
        */
        Map<Integer,Employee> mapMaxSalByDeptEmpl= employees.stream().
                collect(Collectors.groupingBy
                        (Employee::getDeptId,
                           Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),emp -> emp.get())));


                                /* Map<String, Employee map1 empList.stream().
                                        collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen (Collectors.maxBy(Comparat
                                                map1.entrySet().forEach(System.out::println);
*/
/*
        Map<Integer, Double> result = topSalaryEmployeeSalary.entrySet().stream()
                .sorted (Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());
            //    .get(1);*/


        ///  remove duplicate words in a string
        String str1 = "Good bye bye world world";
//        String[] words = str1.split("\\s+");
        // or use below
        String[] words = str1.split(" ");

//        String duplicateWordsRemoved = Arrays.asList(str1.split("\\s+")).stream().distinct()
//                .collect(Collectors.joining(" "));

        String duplicateWordsRemoved = Arrays.asList(str1.split(" ")).stream().distinct()
                .collect(Collectors.joining(" "));

        // Using Java 8 stream and distinct to remove duplicates
        String result = Arrays.stream(words)
                .distinct()
                .collect(Collectors.joining(" "));
        System.out.println("Duplicate Words Removed  "+ duplicateWordsRemoved);


    String str="Reverse each word of a string using Java 8 streams";
        String reverseString= stream(str.split(" "))
                .map(word ->new StringBuffer(word).reverse()) .collect(Collectors.joining(" "));
        System.out.println(str);

        System.out.println(reverseString);

        List<List<Integer>> listOfListofInts = numberCategories();

        System.out.println("The Structure before applying flatMap flattening is : " +
                listOfListofInts);
        // Using flatMap for transformating and flattening.
        List<Integer> listofInts  = listOfListofInts.stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
        System.out.println("The Structure after applying flatMap to listOfListofInts : " +
                listofInts);

      //  For nested arrays
      // Flatmap  Merging Arrays into a Single List
        String[][] dataArray = new String[][]{{"a", "b"},
                {"c", "d"}, {"e", "f"}, {"g", "h"}};
        List<String> listOfAllChars = Arrays.stream(dataArray)
                .flatMap(x -> Arrays.stream(x))
                .collect(Collectors.toList());
        System.out.println("  Flatmapped  Merged Arrays into a Single List" +listOfAllChars);



    }

        private  static List<List<Integer>> numberCategories() {
            List<Integer> numbers = new ArrayList<>();
            // Add some sample numbers to the list
            for (int i = 1; i <= 8; i++) {
                numbers.add(i);
            }

            List<List<Integer>> numberCategories = new ArrayList<>();

            List<Integer> primeNumbers = new ArrayList<>();
            List<Integer> oddNumbers = new ArrayList<>();
            List<Integer> evenNumbers = new ArrayList<>();


            for (int number : numbers) {
                if (isPrime(number)) {
                    primeNumbers.add(number);
                }
                if (number % 2 == 0) {
                    evenNumbers.add(number);
                } else {
                    oddNumbers.add(number);
                }
            }

            numberCategories.add( primeNumbers );
            numberCategories.add( oddNumbers );
            numberCategories.add( evenNumbers );

            System.out.println("Prime Numbers: " + primeNumbers);
            System.out.println("Odd Numbers: " + oddNumbers);
            System.out.println("Even Numbers: " + evenNumbers);
            return numberCategories;
        }

        private static boolean isPrime(int number) {
            if (number <= 1) {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }

   /*
    https://www.baeldung.com/java-sort-map-descending
   To sort a Map in descending order, we can use a custom Comparator that reverses
    the natural order of the values. Here’s an example of how to achieve this:

    In this example, we define a method sortMapByValueDescending method that takes an
    input map and creates a custom Comparator to compare Map.
    Entry objects based on their values in descending order and initializes a new LinkedHashMap
     to hold the sorted entries.
    */

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueDescending(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<K, V>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
