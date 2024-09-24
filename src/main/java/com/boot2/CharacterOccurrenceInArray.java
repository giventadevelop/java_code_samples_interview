package com.boot2;

import java.util.Scanner;

public class CharacterOccurrenceInArray {

    public static int countOccurrences(char target, String[] array) {
        int count = 0;
        for (String str : array) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == target) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input array of strings
        System.out.print("Enter the number of strings in the array: ");
        int numStrings = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String[] array = new String[numStrings];
        for (int i = 0; i < numStrings; i++) {
            System.out.print("Enter string " + (i + 1) + ": ");
            array[i] = scanner.nextLine();
        }

        // Input character to find occurrence
        System.out.print("Enter the character to find occurrence: ");
        char target = scanner.nextLine().charAt(0);

        // Find and display occurrence
        int occurrence = countOccurrences(target, array);
        System.out.println("The character '" + target + "' occurs " + occurrence + " times in the array of strings.");
        
        scanner.close();
    }
}
