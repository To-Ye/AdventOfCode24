package Day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    public static void main(String[] args) {
        try {
            ArrayList<String> matches1 = reader1();
            int result1 = solution1(matches1);
            System.out.println("Soulution Part 1: " + result1);
            
            ArrayList<String> matches2 = reader2();
            int result2 = solution2(matches2);
            System.out.println("Soulution Part 2: " + result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> reader1() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24/Day03/input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        ArrayList<String> matches = new ArrayList<>();

        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        Pattern pattern = Pattern.compile(regex);

        while (inputScanner.hasNextLine()) {
            Matcher matcher = pattern.matcher(inputScanner.nextLine());
            
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        }

        inputScanner.close();

        return matches;
    }

    public static int solution1(ArrayList<String> matches) {
        int sum = 0;

        String regex = "\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);

        for (String match : matches) {
            int currentMul = 1;

            Matcher matcher = pattern.matcher(match);

            while (matcher.find()) {
                currentMul *= Integer.parseInt(matcher.group());
            }

            sum += currentMul;
        }

        return sum;
    }

    public static ArrayList<String> reader2() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24/Day03/input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        ArrayList<String> matches = new ArrayList<>();

        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);

        while (inputScanner.hasNextLine()) {
            Matcher matcher = pattern.matcher(inputScanner.nextLine());
            
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        }

        inputScanner.close();

        return matches;
    }

    public static int solution2(ArrayList<String> matches) {
        int sum = 0;
        boolean doFlag = true;

        String regex = "\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);

        for (String match : matches) {
            
            if (match.equals("do()")) {
                doFlag = true;
                continue;
            }
            
            if (match.equals("don't()")) {
                doFlag = false;
                continue;
            }

            if (!doFlag) {
                continue;
            }
            
            int currentMul = 1;

            Matcher matcher = pattern.matcher(match);

            while (matcher.find()) {
                currentMul *= Integer.parseInt(matcher.group());
            }

            sum += currentMul;
        }

        return sum;
    }
}
