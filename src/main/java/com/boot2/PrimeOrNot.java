package com.boot2;

import java.util.Arrays;
import java.util.List;

public class PrimeOrNot {

    public static void main(String[] args) {

        int inputVal = 25;
        
        String inStr = "Hello World";
        String[] charArray =  inStr.split(" ");
        List charList = Arrays.asList(charArray);
//        charList.stream().distinct().collect(Collectors.toMap(c->c, Collections.frequency(charList,c)));

//        charList.stream().distinct().collect(Collectors.toMap();
        
//        after 20 % discount bike cost is 160  x * (20/100) = y 
    }

   private boolean primeNumberOrNot(int inpuTVal ){

       if((inpuTVal % 2 > 1)  ){
           return true;
       }
        if((inpuTVal / inpuTVal ==1) &&  (inpuTVal / 1 ==inpuTVal) ){
            return true;
        }

        return false;
   }

}
