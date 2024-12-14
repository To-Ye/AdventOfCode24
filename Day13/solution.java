package Day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    public static void main(String[] args) {
        
        try {
            ArrayList<ClawMachine> clawMachines = reader();

            BigInteger result1 = solver1(clawMachines);
            System.out.println("Solution part 1: " + result1);

            ArrayList<ClawMachine> clawMachines2 = adjustPrizeDistance(clawMachines);

            BigInteger result2 = solver1(clawMachines2);
            System.out.println("Solution part 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<ClawMachine> reader() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day13//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        
        ArrayList<ClawMachine> clawMachines = new ArrayList<ClawMachine>();
        ArrayList<Long> inputs = new ArrayList<>();

        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()){
                inputs.add(Long.parseLong(matcher.group()));
            }

            lineScanner.close();
        }

        inputScanner.close();

        
        int inputSize = inputs.size();

        for(int i = 0; i < inputSize; i+=6){
            Button a = new Button(inputs.get(i), inputs.get(i+1));
            Button b = new Button(inputs.get(i+2), inputs.get(i+3));
            long prizeLocationX = inputs.get(i+4);
            long prizeLocationY = inputs.get(i+5);

            ClawMachine clawMachine = new ClawMachine(a, b, prizeLocationX, prizeLocationY);
            clawMachines.add(clawMachine);
        }


        return clawMachines;
    }



    public static long[] solveLinearEquation(ClawMachine clawMachine) {
        BigDecimal a00 = new BigDecimal(clawMachine.a.xIncreaseRate);
        BigDecimal a01 = new BigDecimal(clawMachine.b.xIncreaseRate);
        BigDecimal a10 = new BigDecimal(clawMachine.a.yIncreaseRate);
        BigDecimal a11 = new BigDecimal(clawMachine.b.yIncreaseRate);
        BigDecimal b0 = new BigDecimal(clawMachine.prizeLocationX);
        BigDecimal b1 = new BigDecimal(clawMachine.prizeLocationY);

        BigDecimal det = (a00.multiply(a11)).subtract(a01.multiply(a10));

        if (det.equals(BigDecimal.ZERO)) {
            return null;
        }

        // Use scale and rounding mode to prevent non-terminating decimal expansion errors
        int scale = 25; // Adjust as necessary
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal x = ((b0.multiply(a11)).subtract(b1.multiply(a01))).divide(det, scale, roundingMode);
        BigDecimal y = ((a00.multiply(b1)).subtract(a10.multiply(b0))).divide(det, scale, roundingMode);

        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0 && x.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0 && y.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return new long[]{x.longValue(), y.longValue()};
        }

        return null;
    }


    public static BigInteger solver1(ArrayList<ClawMachine> clawMachines){
        BigInteger sum = BigInteger.ZERO;

        for(ClawMachine clawMachine : clawMachines){
            long[] eqSolution = solveLinearEquation(clawMachine);

            if(eqSolution != null){
                BigInteger pressesA = BigInteger.valueOf(eqSolution[0]);
                BigInteger pressesB = BigInteger.valueOf(eqSolution[1]);
                sum = sum.add(pressesA.multiply(new BigInteger("3"))).add(pressesB);
            }
        }

        return sum;
    }

    public static ArrayList<ClawMachine> adjustPrizeDistance(ArrayList<ClawMachine> oldClawMachines){
        ArrayList<ClawMachine> newClawMachines = new ArrayList<>();

        for(ClawMachine oldClawMachine : oldClawMachines){
            long newPriceX = oldClawMachine.prizeLocationX + 10000000000000L;
            long newPriceY = oldClawMachine.prizeLocationY + 10000000000000L;

            ClawMachine newClawMachine = new ClawMachine(oldClawMachine.a, oldClawMachine.b, newPriceX, newPriceY);
            newClawMachines.add(newClawMachine);
        }

        return newClawMachines;
    }
}

class ClawMachine {
    Button a;
    Button b;
    long prizeLocationX;
    long prizeLocationY;

    public ClawMachine(Button a, Button b, long prizeLocationX, long prizeLocationY){
        this.a = a;
        this.b = b;
        this.prizeLocationX = prizeLocationX;
        this.prizeLocationY = prizeLocationY;
    }

    @Override
    public String toString(){
        return a.toString() + "\n" + b.toString() + "\n" + "Prize: X=" + prizeLocationX + ", Y=" + prizeLocationY;
    }
}

class Button {
    long xIncreaseRate;
    long yIncreaseRate;

    public Button(long xIncreaseRate, long yIncreaseRate){
        this.xIncreaseRate = xIncreaseRate;
        this.yIncreaseRate = yIncreaseRate;
    }

    @Override
    public String toString(){
        return "Button A: X+" + xIncreaseRate + ", Y+" + yIncreaseRate;
    }
}

