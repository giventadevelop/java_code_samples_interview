package com.boot2;

import java.util.*;
import java.util.stream.Collectors;

public class SortHashMapByKeys
{
    public static void main(String[] args) 
    {
        //Sorting HashMap according to natural order of keys
        // using TreeMap without Comparator :
        Map<String, Integer> studentMap = new HashMap<String, Integer>();
         
        studentMap.put("Jyous", 87);
        studentMap.put("Klusener", 82);
        studentMap.put("Xiangh", 91);
        studentMap.put("Lisa", 89);
        studentMap.put("Narayan", 95);
        studentMap.put("Arunkumar", 86);
                 
        Map<String, Integer> sortedStudentMap = new TreeMap<>(studentMap);
                 
        System.out.println("Before Sorting : ");
         
        System.out.println(studentMap);
         
        System.out.println("After Sorting : ");
         
        System.out.println(sortedStudentMap);

        /*Sorting HashMap in natural reverse order of keys using
         TreeMap with Comparator :
        The following program sorts HashMap in natural reverse
         order of keys by passing Collections.reverseOrder() to TreeMap.*/
        sortedStudentMap = new TreeMap<>(Collections.reverseOrder());

        sortedStudentMap.putAll(studentMap);

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);

      /*  Sorting HashMap by keys using TreeMap with customized Comparator :

    In the below example, HashMap with strings as keys is sorted in
     increasing order of length of keys by passing customized
      Comparator to TreeMap.*/
        sortedStudentMap = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o1.length() - o2.length();
            }
        });

        sortedStudentMap.putAll(studentMap);

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);

        /*To sort the same HashMap in decreasing
        order of length of keys, use either Collections.reverseOrder()
         or else return o2.length() - o1.length() instead of o1.length() - o2.length()
          as in the below program.*/

        sortedStudentMap = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o2.length() - o1.length();
            }
        });

//      OR

//      Map<String, Integer> sortedStudentMap =
//      new TreeMap<>(Collections.reverseOrder(new Comparator<String>()
//      {
//          @Override
//          public int compare(String o1, String o2)
//          {
//              return o1.length() - o2.length();
//          }
//      }));

        sortedStudentMap.putAll(studentMap);

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);

        /*From Java 8, two new methods are introduced into Map.Entry class to facilitate
         the sorting of Map objects by keys and by values.
         They are – comparingByKey() and comparingByValue().
          In this post, we will just focus on comparingByKey() method.
            Below image describes steps involved in sorting HashMap by
             keys using Java 8 comparingByKey().*/

        sortedStudentMap
                = studentMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);

        /*To sort the same HashMap in the reverse order of keys, use either
         Collections.reverseOrder() or Java 8 Comparator.reverseOrder().*/
        sortedStudentMap
                = studentMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))

                //  OR
                // .sorted(Entry.comparingByKey(Comparator.reverseOrder()))

                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);

        /*The below program uses Java 8 comparingByKey() method with customized
         Comparator to sort HashMap in increasing order of length of keys.*/

        sortedStudentMap
                = studentMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey((o1, o2) -> o1.length() - o2.length()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);

        /*To sort the same HashMap in decreasing order of length of keys,
         use either Collections.reverseOrder()
         or else return o2.length() - o1.length() instead of o1.length() - o2.length(
          as in the below program.*/
        sortedStudentMap
                = studentMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey((o1, o2) -> o2.length() - o1.length()))

                //  OR
                //  .sorted(Collections
                //  .reverseOrder(Entry.comparingByKey((o1, o2) -> o1.length() - o2.length())))

                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Before Sorting : ");

        System.out.println(studentMap);

        System.out.println("After Sorting : ");

        System.out.println(sortedStudentMap);
    }
    }
