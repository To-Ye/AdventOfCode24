package Day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    public static void main(String[] args) {
        try {
            HashMap<Integer,ArrayList<Integer>> rules = readerRules();
            ArrayList<ArrayList<Integer>> updates = readerUpdates();

            int result1 = solver1(rules, updates);
            System.out.println("Solution part 1: " + result1);

            int result2 = solver2(rules, updates);
            System.out.println("Solution part 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer,ArrayList<Integer>> readerRules() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day05//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        HashMap<Integer,ArrayList<Integer>> rules = new HashMap<>();

        String regex = "\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        String line = "xx";

        // extracts the rules from the first part of the input
        while(!line.equals("") && inputScanner.hasNextLine()){
            line = inputScanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            int[] rule = new int[2];

            int i = 0;
            while(matcher.find()){
                rule[i] = Integer.parseInt(matcher.group());
                i++;
            }

            int firstNmbr = rule[0];
            int secondNmbr = rule[1];

            if(rules.containsKey(firstNmbr)){
                rules.get(firstNmbr).add(secondNmbr);
            } else {
                rules.put(firstNmbr, new ArrayList<Integer>());
                rules.get(firstNmbr).add(secondNmbr);
            }
            
        }

        inputScanner.close();

        return rules;
    }

    public static ArrayList<ArrayList<Integer>> readerUpdates() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day05//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        ArrayList<ArrayList<Integer>> updates = new ArrayList<>();

        String regex = "\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        String line = "xx";

        // skips the rules until it gets to the updates of the input
        while(!line.equals("") && inputScanner.hasNextLine()){
            line = inputScanner.nextLine();            
        }
        
        // extracts the updated from the second part of the input
        while(inputScanner.hasNextLine()){
            line = inputScanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            ArrayList<Integer> currentUpdate = new ArrayList<>();

            while(matcher.find()){
                currentUpdate.add(Integer.parseInt(matcher.group()));
            }

            updates.add(currentUpdate);
        }

        inputScanner.close();

        return updates;

    }

    public static int solver1(HashMap<Integer, ArrayList<Integer>> rules, ArrayList<ArrayList<Integer>> updates) {
        int middleNumbersSum = 0;
        
        for (ArrayList<Integer> update : updates) {
            ArrayList<Integer> copyUpdate = new ArrayList<>();
            copyUpdate.addAll(update);

            int updateLength = update.size();
            
            boolean validUpdate = true;

            while(!copyUpdate.isEmpty()){
                int currentNmbr = copyUpdate.removeLast();

                if (!rules.containsKey(currentNmbr)){
                    continue;
                }

                ArrayList<Integer> currentRule = rules.get(currentNmbr);
                
                int copyUpdateLength = copyUpdate.size();
                
                for(int j = 0; j < copyUpdateLength; j++){
                    if (currentRule.contains(copyUpdate.get(j))){
                        validUpdate = false;
                        break;
                    }
                } 
                
            }

            if (validUpdate) {
                int middleNumber = update.get((updateLength-1)/2);
                middleNumbersSum += middleNumber;
            }
            
        }
        
        return middleNumbersSum;
    }

    public static int solver2(HashMap<Integer, ArrayList<Integer>> rules, ArrayList<ArrayList<Integer>> updates) {
        int middleNumbersSum = 0;
        
        for (ArrayList<Integer> update : updates) {
            ArrayList<Integer> copyUpdate = new ArrayList<>();
            copyUpdate.addAll(update);

            int updateLength = update.size();
            
            boolean validUpdate = true;

            while(!copyUpdate.isEmpty()){
                int currentNmbr = copyUpdate.removeLast();

                if (!rules.containsKey(currentNmbr)){
                    continue;
                }

                ArrayList<Integer> currentRule = rules.get(currentNmbr);
                
                int copyUpdateLength = copyUpdate.size();
                
                for(int j = 0; j < copyUpdateLength; j++){
                    if (currentRule.contains(copyUpdate.get(j))){
                        validUpdate = false;
                        break;
                    }
                } 
                
            }

            if (!validUpdate) {
                ArrayList<Integer> sortedUpdate = sortUpdate(rules, update);
                int middleNumber = sortedUpdate.get((sortedUpdate.size()-1)/2);
                middleNumbersSum += middleNumber;
            }
            
        }
        
        return middleNumbersSum;
    }

    public static ArrayList<Integer> sortUpdate(HashMap<Integer, ArrayList<Integer>> rules, ArrayList<Integer> update){
        ArrayList<Integer> sortedUpdate = new ArrayList<>();

        for (int updateElement : update) {
            boolean added = false;
            for (int sortedElement : sortedUpdate) {
                if (!rules.containsKey(sortedElement) || !rules.get(sortedElement).contains(updateElement)){
                    sortedUpdate.add(sortedUpdate.indexOf(sortedElement), updateElement);
                    added = true;
                    break;
                }
            }
            if (!added){
                sortedUpdate.add(updateElement);
            }
        }

        return sortedUpdate;
    }


}
