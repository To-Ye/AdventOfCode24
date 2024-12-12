package Day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        try {
            ArrayList<Long> input = reader();

            Map<Long, Long> inputCounts = new HashMap<>();
            
            for (long number : input) {
                inputCounts.put(number, 1L);
            }

            long result1 = optimizedSolver(inputCounts, 25);
            System.out.println("Solution part 1: " + result1);

            long result2 = optimizedSolver(inputCounts, 75);
            System.out.println("Solution part 2: " + result2);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Long> reader() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day11//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        ArrayList<Long> input = new ArrayList<Long>();


        while(inputScanner.hasNextLong()){
            long currentLong = inputScanner.nextLong();

            input.add(currentLong);
        }

        inputScanner.close();
        return input;
        
    }

    public static boolean hasEvenNumberOfDigits(long number) {
        return String.valueOf(number).length() % 2 == 0;
    }
    
    public static long optimizedSolver(Map<Long, Long> inputCounts, int blinks) {
        for (int i = 0; i < blinks; i++) {
            Map<Long, Long> newCounts = new HashMap<>();

            for (Map.Entry<Long, Long> entry : inputCounts.entrySet()) {
                long number = entry.getKey();
                long count = entry.getValue();

                if (number == 0) {
                    newCounts.put(1L, newCounts.getOrDefault(1L, 0L) + count);
                } else if (hasEvenNumberOfDigits(number)) {
                    long divisor = (long) Math.pow(10, String.valueOf(number).length() / 2);
                    long left = number / divisor;
                    long right = number % divisor;

                    newCounts.put(left, newCounts.getOrDefault(left, 0L) + count);
                    newCounts.put(right, newCounts.getOrDefault(right, 0L) + count);
                } else {
                    long newNumber = number * 2024;
                    newCounts.put(newNumber, newCounts.getOrDefault(newNumber, 0L) + count);
                }
            }

            inputCounts = newCounts; 
        }

        return inputCounts.values().stream().mapToLong(Long::longValue).sum();
    }

    
}



