package com.boot2;

import java.util.*;
import java.util.stream.Collectors;

public class WordProcess {

    public static void main(String[] args) {

        String processString  = "java python java python basic fortran";
        List<String> processStringList = Arrays.stream(processString.split(" ")).collect(Collectors.toList());
        processString(processStringList);
    }

    private static void processString(List<String> processStringList){

        Map<String, Long> wordCountMap = processStringList.stream()
                           .collect(Collectors.groupingBy(c->c,Collectors.counting()));

        System.out.println("wordCountMap " + wordCountMap );

        Map<String, Long>  sortedWordMap = new TreeMap<>(wordCountMap);

        System.out.println("sortedWordMap " + sortedWordMap );

        Map<String, Long>  sortedWordMapByValue = sortedWordMap.entrySet().stream()
                          .sorted(Map.Entry.comparingByValue())
                          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2)-> e1,LinkedHashMap::new));
        Map<String, Long>  sortedTreeWordMapVal = new TreeMap<>(sortedWordMapByValue);
        System.out.println("sortedTreeWordMapVal " + sortedTreeWordMapVal );





    }
}
