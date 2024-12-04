package Day01;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class solution {
    public static void main(String[] args) { 
        try {
            ArrayList<Integer>[] input_Lists = reader();
            ArrayList<Integer> left_column = input_Lists[0];
            ArrayList<Integer> right_column = input_Lists[1];

            int result1 = solve1(left_column, right_column);
            int result2 = solve2(left_column, right_column);

            System.out.println("Solution for the first star: " + result1);
            System.out.println("Solution for the second star: " + result2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }      
    }

    public static ArrayList<Integer>[] reader() throws FileNotFoundException {
        String src = "AdventOfCode24/Day01/";
        String fileName = "input.txt";
        File file = new File(src + fileName);
        Scanner sc = new Scanner(file);

        ArrayList<Integer>[] input_Lists = new ArrayList[2];
        input_Lists[0] = new ArrayList<Integer>();
        input_Lists[1] = new ArrayList<Integer>();
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner lineScanner = new Scanner(line);
            while(lineScanner.hasNext()) {
                input_Lists[0].add(lineScanner.nextInt());
                input_Lists[1].add(lineScanner.nextInt());
            }
            lineScanner.close();
        }

        sc.close();

        return input_Lists;

    }
    
    public static int solve1(ArrayList<Integer> left_column, ArrayList<Integer> right_column) {
        left_column.sort(null);
        right_column.sort(null);

        int list_size = left_column.size();
        int sum = 0;

        for (int i = 0; i < list_size; i++) {
            int left_element = left_column.get(i);
            int right_element = right_column.get(i);
            sum += Math.abs(left_element - right_element);
        }

        return sum;
        
    }

    public static int solve2(ArrayList<Integer> left_column, ArrayList<Integer> right_column) {
        left_column.sort(null);
        right_column.sort(null);

        int list_size = left_column.size();
        int sum = 0;

        for (int i = 0; i < list_size; i++) {
            int left_element = left_column.get(i);
            int j = 0;
            
            int count = 0;
            int right_element = Integer.MIN_VALUE;

            while(j < list_size && right_element <= left_element) {
                right_element = right_column.get(j);
                if (right_element == left_element) {
                    count++;
                }
                j++;
            }
            
            sum += count * left_element;
        }

        return sum;
        
    }
    
}
