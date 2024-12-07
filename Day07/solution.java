package Day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class solution {
    public static void main(String[] args) {
        try {
            HashMap<BigInteger, ArrayList<BigInteger>> equations = reader();

            BigInteger result1 = solver1(equations);
            System.out.println("Solution part 1: " + result1);
            
            BigInteger result2 = solver2(equations);
            System.out.println("Solution part 2: " + result2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<BigInteger, ArrayList<BigInteger>> reader() throws FileNotFoundException {
        File inputFile =  new File("AdventOfCode24//Day07//input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        HashMap<BigInteger, ArrayList<BigInteger>> equations = new HashMap<>();
        
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            String[] firstSplit = line.split(":");
            BigInteger equationResult = new BigInteger(firstSplit[0]);
            String[] secondSplit = firstSplit[1].split(" ");

            int secondSplitLength = secondSplit.length;
            ArrayList<BigInteger> equationElements = new ArrayList<>();

            for (int i = 1; i < secondSplitLength; i++){
                equationElements.add(new BigInteger(secondSplit[i]));
            }

            equations.put(equationResult, equationElements);          
            
        }

        inputScanner.close();

        return equations;
        
    }

    public static BigInteger solver1(HashMap<BigInteger, ArrayList<BigInteger>> equations) {
        BigInteger sum = BigInteger.ZERO;

        for (BigInteger key : equations.keySet()) {
            ArrayList<BigInteger> equationElements = equations.get(key);
            int numberOfElements = equationElements.size();
            boolean valid = recursiveSolver1(key, equationElements, numberOfElements, equationElements.get(0), 1);

            if (valid) {
                sum = sum.add(key);
            }
        }

        return sum;
    }

    public static boolean recursiveSolver1(BigInteger targetSolution, ArrayList<BigInteger> equationElements, int numberOfElements, BigInteger currentSum, int currentIndex) {
        
        if (currentIndex == numberOfElements) {
            return currentSum.equals(targetSolution);
        }

        BigInteger currentElement = equationElements.get(currentIndex);
        

        boolean multiplication = recursiveSolver1(targetSolution, equationElements, numberOfElements, currentSum.multiply(currentElement), currentIndex+1);
        boolean addition = recursiveSolver1(targetSolution, equationElements, numberOfElements, currentSum.add(currentElement), currentIndex+1);

        return multiplication || addition;
    }

    public static BigInteger solver2(HashMap<BigInteger, ArrayList<BigInteger>> equations) {
        BigInteger sum = BigInteger.ZERO;

        for (BigInteger key : equations.keySet()) {
            ArrayList<BigInteger> equationElements = equations.get(key);
            int numberOfElements = equationElements.size();
            boolean valid = recursiveSolver2(key, equationElements, numberOfElements, equationElements.get(0), 1);

            if (valid) {
                sum = sum.add(key);
            }
        }

        return sum;
    }

    public static boolean recursiveSolver2(BigInteger targetSolution, ArrayList<BigInteger> equationElements, int numberOfElements, BigInteger currentSum, int currentIndex) {
        
        if (currentIndex == numberOfElements) {
            return currentSum.equals(targetSolution);
        }

        BigInteger currentElement = equationElements.get(currentIndex);
        

        boolean multiplication = recursiveSolver2(targetSolution, equationElements, numberOfElements, currentSum.multiply(currentElement), currentIndex+1);
        boolean addition = recursiveSolver2(targetSolution, equationElements, numberOfElements, currentSum.add(currentElement), currentIndex+1);
        boolean concatenation = recursiveSolver2(targetSolution, equationElements, numberOfElements, (new BigInteger(currentSum.toString()+currentElement.toString())), currentIndex+1);

        return multiplication || addition || concatenation;
    }
}