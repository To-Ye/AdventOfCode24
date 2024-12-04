package AdventOfCode24.Day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        try {
            ArrayList<ArrayList<Integer>> reports = reader();
            int solution1 = solve1(reports);
            System.out.println("Solution for the first part 2: " + solution1);
            int solution2 = solve2(reports);
            System.out.println("Solution for part 1: " + solution2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<Integer>> reader() throws FileNotFoundException {
        File File = new File("AdventOfCode24/Day02/input.txt");
        Scanner inputScanner = new Scanner(File);

        ArrayList<ArrayList<Integer>> reports = new ArrayList<>();

        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            ArrayList<Integer> currentLevel = new ArrayList<>();

            while (lineScanner.hasNext()) {
                currentLevel.add(lineScanner.nextInt());
            }

            reports.add(currentLevel);
            lineScanner.close();

        }

        inputScanner.close();
        return reports;
    }

    public static int solve1(ArrayList<ArrayList<Integer>> reports) {
        int count = 0;

        for (ArrayList<Integer> level : reports) {
            if (isSafeLevel1(level)) {
                count++;
            }
        }

        return count;
    }

    public static boolean isSafeLevel1(ArrayList<Integer> level) {
        
        int levelSize = level.size();
        boolean increasing = false;

        if (levelSize == 1){
            return true;
        }

        int currentLevel = level.get(0);
        int nextLevel = level.get(1);

        if (currentLevel == nextLevel){
            return false;
        }

        if (currentLevel < nextLevel) {
            if (nextLevel - currentLevel > 3) {
                return false;
            }
            increasing = true;
        } else {
            if (currentLevel - nextLevel > 3) {
                return false;
            }
        }

        
        for (int i = 1; i < levelSize-1; i++) {

            currentLevel = level.get(i);
            nextLevel = level.get(i+1);

            if (currentLevel == nextLevel) {
                return false;
            }
            
            if (increasing) {

                if (currentLevel > nextLevel) {
                    return false;
                }

                if (nextLevel - currentLevel > 3) {
                    return false;
                }

            } else {
                if (currentLevel < nextLevel) {
                    return false;
                }

                if (currentLevel - nextLevel > 3) {
                    return false;
                }

            }

        }

        return true;
    }

    public static int solve2(ArrayList<ArrayList<Integer>> reports) {
        int count = 0;

        for (ArrayList<Integer> level : reports) {
            if (isSafeLevel1(level)) {
                count++;
            } else {
                int levelSize = level.size();
                for (int i = 0; i < levelSize; i++) {
                    ArrayList<Integer> newLevel = new ArrayList<>(level);
                    newLevel.remove(i);
                    if (isSafeLevel1(newLevel)) {
                        count++;
                        break;
                    }
                }
            }
        }

        return count;
    }


}
