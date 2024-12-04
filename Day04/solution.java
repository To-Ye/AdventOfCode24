package Day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        try {
            int[] lengths = getLengths();
            int numberOfLines = lengths[0];
            int lineLength = lengths[1];

            char[][] inputGrid = reader(numberOfLines, lineLength);
            int result1 = solution1(inputGrid, numberOfLines, lineLength);
            System.out.println("Result part 1: " + result1);

            int result2 = solution2(inputGrid, numberOfLines, lineLength);
            System.out.println("Result part 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getLengths() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24/Day04/input.txt");
        Scanner fileScanner = new Scanner(inputFile);
        int numberOfLines = 0;
        int lineLength = 0;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            numberOfLines++;
            lineLength = line.length();
        }

        fileScanner.close();

        return new int[]{numberOfLines, lineLength};
    }

    public static char[][] reader(int numberOfLines, int lineLength) throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24/Day04/input.txt");
        Scanner fileScanner = new Scanner(inputFile);
        
        char[][] inputGrid = new char[numberOfLines][lineLength];

        int i = 0;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            inputGrid[i] = line.toCharArray();
            i++;
        }

        fileScanner.close();

        return inputGrid;
    }

    public static int solution1(char[][] inputGrid, int numberOfLines, int lineLength) {
        int count = 0;

        for (int i = 0; i < numberOfLines; i++){
            for (int j = 0; j < lineLength; j++){
                char currentChar = inputGrid[i][j];
                boolean canGoVerticalFront = i < numberOfLines - 3;
                boolean canGoVerticalBack = i > 2;
                boolean canGoHorizontalFront = j < lineLength - 3;
                boolean canGoHorizontalBack = j > 2;

                if (currentChar == 'X'){
                    
                    if (canGoVerticalFront && checkVerticalFront(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoVerticalBack && checkVerticalBack(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoHorizontalFront && checkHorizontalFront(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoHorizontalBack && checkHorizontalBack(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoVerticalFront && canGoHorizontalFront && checkDiagonalRightDown(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoVerticalBack && canGoHorizontalFront && checkDiagonalRightUp(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoVerticalFront && canGoHorizontalBack && checkDiagonalLeftDown(inputGrid, i, j)){
                        count++;
                    }

                    if (canGoVerticalBack && canGoHorizontalBack && checkDiagonalLeftUp(inputGrid, i, j)){
                        count++;
                    }

                }
            }
        }

        return count;
    }

    public static int solution2(char[][] inputGrid, int numberOfLines, int lineLength) {
        int count = 0;
        
        for(int i = 1; i < numberOfLines-1; i++){
            for(int j = 1; j < lineLength-1; j++){
                char currentChar = inputGrid[i][j];
                char upRight = inputGrid[i-1][j+1];
                char upLeft = inputGrid[i-1][j-1];
                char downRight = inputGrid[i+1][j+1];
                char downLeft = inputGrid[i+1][j-1];

                if (currentChar == 'A'){
                    if (upRight == downLeft || upLeft == downRight){
                        continue;
                    }

                    if (upRight != 'M' && upRight != 'S'){
                        continue;
                    }
                    
                    if (downRight != 'M' && downRight != 'S'){
                        continue;
                    }

                    if (upLeft != 'M' && upLeft != 'S'){
                        continue;
                    }

                    if (downLeft != 'M' && downLeft != 'S'){
                        continue;
                    }

                    count++;

                }

            }
        }

        return count;
    }

    public static boolean checkVerticalFront(char[][] inputGrid, int i, int j){
        char m = inputGrid[i+1][j];
        char a = inputGrid[i+2][j];
        char s = inputGrid[i+3][j];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkVerticalBack(char[][] inputGrid, int i, int j){
        char m = inputGrid[i-1][j];
        char a = inputGrid[i-2][j];
        char s = inputGrid[i-3][j];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkHorizontalFront(char[][] inputGrid, int i, int j){
        char m = inputGrid[i][j+1];
        char a = inputGrid[i][j+2];
        char s = inputGrid[i][j+3];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkHorizontalBack(char[][] inputGrid, int i, int j){
        char m = inputGrid[i][j-1];
        char a = inputGrid[i][j-2];
        char s = inputGrid[i][j-3];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkDiagonalRightUp(char[][] inputGrid, int i, int j){
        char m = inputGrid[i-1][j+1];
        char a = inputGrid[i-2][j+2];
        char s = inputGrid[i-3][j+3];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkDiagonalRightDown(char[][] inputGrid, int i, int j){
        char m = inputGrid[i+1][j+1];
        char a = inputGrid[i+2][j+2];
        char s = inputGrid[i+3][j+3];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkDiagonalLeftUp(char[][] inputGrid, int i, int j){
        char m = inputGrid[i-1][j-1];
        char a = inputGrid[i-2][j-2];
        char s = inputGrid[i-3][j-3];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    public static boolean checkDiagonalLeftDown(char[][] inputGrid, int i, int j){
        char m = inputGrid[i+1][j-1];
        char a = inputGrid[i+2][j-2];
        char s = inputGrid[i+3][j-3];
        
        if (m == 'M' && a == 'A' && s == 'S'){
            return true;
        }

        return false;
    }

    


    


}
