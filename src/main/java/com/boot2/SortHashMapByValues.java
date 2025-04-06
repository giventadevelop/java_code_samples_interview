package com.boot2;

import java.util.*;
import java.util.stream.Collectors;

public class SortHashMapByValues
{
        public static void main(String[] args)
        {
            //Define one HashMap called idNameMap

            Map<Integer, String> idNameMap = new HashMap<Integer, String>();

            //Insert Id-Name pairs into idNameMap

            idNameMap.put(111, "Lisa");
            idNameMap.put(222, "Narayan");
            idNameMap.put(333, "Xiangh");
            idNameMap.put(444, "Arunkumar");
            idNameMap.put(555, "Jyous");
            idNameMap.put(666, "Klusener");     

            //Java 8 sorting using Entry.comparingByValue()

            Map<Integer, String> sortedIdNameMap
                    = idNameMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1,  e2) -> e1, LinkedHashMap::new));


            //Print idNameMap before and after sorting

            System.out.println("Before Sorting : ");

            System.out.println(idNameMap);

            System.out.println("After Sorting : ");

            System.out.println(sortedIdNameMap);

            /*To sort the same HashMap in the reverse order,
             use Collections.reverseOrder() or Comparator.reverseOrder()
              as shown in the below program.*/
            sortedIdNameMap
                    = idNameMap.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))

                    // OR
                    // .sorted(Entry.comparingByValue(Comparator.reverseOrder()))

                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1,  e2) -> e1, LinkedHashMap::new));


            //Print idNameMap before and after sorting

            System.out.println("Before Sorting : ");

            System.out.println(idNameMap);

            System.out.println("After Sorting : ");

            System.out.println(sortedIdNameMap);

            /*The following program sorts HashMap in increasing order of length
            of values using Java 8 comparingByValue() by passing customized Comparator.*/
            sortedIdNameMap
                    = idNameMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue((o1, o2) -> o1.length() - o2.length()))
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue, (e1,  e2) -> e1, LinkedHashMap::new));

            //Print idNameMap before and after sorting

            System.out.println("Before Sorting : ");

            System.out.println(idNameMap);

            System.out.println("After Sorting : ");

            System.out.println(sortedIdNameMap);

            /*To sort the same HashMap in decreasing order of length of values,
            use either Collections.reverseOrder() or else return o2.length() - o1.length()
            instead of o1.length() - o2.length() as in the below program.*/
            sortedIdNameMap
                    = idNameMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue((o1, o2) -> o2.length() - o1.length()))

                    //OR
                    //.sorted(Collections
                    // .reverseOrder(Entry.comparingByValue((o1, o2) -> o1.length() - o2.length())))

                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue, (e1,  e2) -> e1, LinkedHashMap::new));

            //Print idNameMap before and after sorting

            System.out.println("Before Sorting : ");

            System.out.println(idNameMap);

            System.out.println("After Sorting : ");

            System.out.println(sortedIdNameMap);
        }
    }
