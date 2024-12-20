package Day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class solution {
    public static char[][] smallMap;
    public static char[] directions;
    public static int rows = 0;
    public static int cols = 0;
    public static int xStart = 0;
    public static int yStart = 0;

    public static char[][] bigMap;
    public static int bigRows = 0;
    public static int bigCols = 0;
    public static int bigXstart = 0;
    public static int bigYstart = 0;

    public static void main(String[] args) {
        try {
            getDimensions();
            smallMap = new char[rows][cols];
            reader();
            getStartingPosition();
            
            // moveAll();
            // int solution1 = solver1();
            // System.out.println("Solution part 1: " + solution1);
            
            bigRows = rows;
            bigCols = cols*2;
            bigMap = new char[bigRows][bigCols];

            populateBigMap();
            moveAllBig();
            int solution2 = solver2();
            System.out.println("Solution part 2: " + solution2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getDimensions() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day15//input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            if(line.length() == 0){
                break;
            }
            rows++;
            cols = line.length();
        }

        inputScanner.close();
        
    }

    public static void reader() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day15//input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        int i = 0;
        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            if (line.length() == 0){
                break;
            }
            smallMap[i] = line.toCharArray();
            i++;
        }

        String directionsString = "";

        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            directionsString += line;
        }

        directions = directionsString.toCharArray();

        inputScanner.close();
    }

    public static void getStartingPosition(){
        for (int i = 0; i < rows; i++){
            boolean found = false;
            for (int j = 0; j < cols; j++){
                if (smallMap[i][j] == '@'){
                    xStart = i;
                    yStart = j;
                    found = true;
                    break;
                }
            }
            if (found){
                break;
            }
        }
    }

    public static boolean wallConnected(int x, int y, char direction){
        if (direction == '<'){
            char current = smallMap[y][x];
            while (current != '#'){
                if (current == '.'){
                    return false;
                }
                x--;
                current = smallMap[y][x];
            }
            return true;
        }

        if (direction == '^'){
            char current = smallMap[y][x];
            while (current != '#'){
                if (current == '.'){
                    return false;
                }
                y--;
                current = smallMap[y][x];
            }
            return true;
        }

        if (direction == '>'){
            char current = smallMap[y][x];
            while (current != '#'){
                if (current == '.'){
                    return false;
                }
                x++;
                current = smallMap[y][x];
            }
            return true;
        }

        if (direction == 'v'){
            char current = smallMap[y][x];
            while (current != '#'){
                if (current == '.'){
                    return false;
                }
                y++;
                current = smallMap[y][x];
            }
            return true;
        }
  
        return false;
    }

    public static void printSmallMap(){
        System.out.println();
        for (int i = 0; i < rows; i++){
            System.out.println(smallMap[i]);
        }
        System.out.println();
    }

    public static void moveLeft(int x, int y){
        if (wallConnected(x, y, '<')){
            return;
        }

        xStart = x-1;
        yStart = y;

        char current = smallMap[y][x];
        char next = smallMap[y][x-1];
        smallMap[y][x] = '.';

        while (next != '.'){
            char tmp = next;
            smallMap[y][x-1] = current;
            current = tmp;
            x--;
            next = smallMap[y][x-1];
        }

        smallMap[y][x-1] = current;
    }

    public static void moveRight(int x, int y){
        if (wallConnected(x, y, '>')){
            return;
        }

        xStart = x+1;
        yStart = y;

        char current = smallMap[y][x];
        char next = smallMap[y][x+1];
        smallMap[y][x] = '.';

        while (next != '.'){
            char tmp = next;
            smallMap[y][x+1] = current;
            current = tmp;
            x++;
            next = smallMap[y][x+1];
        }

        smallMap[y][x+1] = current;
    }

    public static void moveUp(int x, int y){
        if (wallConnected(x, y, '^')){
            return;
        }

        xStart = x;
        yStart = y-1;

        char current = smallMap[y][x];
        char next = smallMap[y-1][x];
        smallMap[y][x] = '.';

        while (next != '.'){
            char tmp = next;
            smallMap[y-1][x] = current;
            current = tmp;
            y--;
            next = smallMap[y-1][x];
        }

        smallMap[y-1][x] = current;
    }

    public static void moveDown(int x, int y){
        if (wallConnected(x, y, 'v')){
            return;
        }

        xStart = x;
        yStart = y+1;

        char current = smallMap[y][x];
        char next = smallMap[y+1][x];
        smallMap[y][x] = '.';

        while (next != '.'){
            char tmp = next;
            smallMap[y+1][x] = current;
            current = tmp;
            y++;
            next = smallMap[y+1][x];
        }

        smallMap[y+1][x] = current;
    }

    public static void move(char direction){
        if (direction == '<'){
            moveLeft(xStart, yStart);
        }

        if (direction == '^'){
            moveUp(xStart, yStart);
        }

        if (direction == '>'){
            moveRight(xStart, yStart);
        }

        if (direction == 'v'){
            moveDown(xStart, yStart);
        }
    }

    public static void moveAll(){
        for (int i = 0; i < directions.length; i++){
            move(directions[i]);
        }
    }

    public static int solver1(){
        int totalSum = 0;

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (smallMap[i][j] == 'O'){
                    totalSum += i*100 + j;   
                }
            }
        }


        return totalSum;
    }
   
    // ====================================================================================================================================================================================
    //
    //  PART 2
    //
    // ====================================================================================================================================================================================
   
    public static void printBigMap(){
        System.out.println();
        for (int i = 0; i < bigRows; i++){
            System.out.println(bigMap[i]);
        }
        System.out.println();
    }

    public static void populateBigMap(){
        for (int i = 0; i < rows; i++){
            int k = 0;
            for (int j = 0; j < cols; j++){
                char current = smallMap[i][j];
                if (current == '@'){
                    bigMap[i][k] = '@';
                    bigXstart = k;
                    bigYstart = i;
                    bigMap[i][k+1] = '.';
                } else if (current == 'O'){
                    bigMap[i][k] = '[';
                    bigMap[i][k+1] = ']';
                } else {
                    bigMap[i][k] = current;
                    bigMap[i][k+1] = current;
                }

                k += 2;

            }
        }

    }

    public static boolean wallConnectedBig(int x, int y, char direction){
        if (direction == '<'){
            char current = bigMap[y][x];
            while (current != '#'){
                if (current == '.'){
                    return false;
                }
                x--;
                current = bigMap[y][x];
            }
            return true;
        }

        if (direction == '^'){
            return recWallCheckerUp(x, y);
        }

        if (direction == '>'){
            char current = bigMap[y][x];
            while (current != '#'){
                if (current == '.'){
                    return false;
                }
                x++;
                current = bigMap[y][x];
            }
            return true;
        }

        if (direction == 'v'){
            return recWallCheckerDown(x, y);
        }
  
        return false;
    }

    public static boolean recWallCheckerUp(int x, int y){
        
        char current = bigMap[y][x];
        if (current == '@'){
            return recWallCheckerUp(x, y-1);
        } else if (current == '#'){
            return true;
        } else if (current == '.'){
            return false;
        } else if (current == '['){
            return recWallCheckerUp(x, y-1) || recWallCheckerUp(x+1, y-1);
        } else if (current == ']'){
            return recWallCheckerUp(x, y-1) || recWallCheckerUp(x-1, y-1);
        } else {
            return false;
        }

    }

    public static boolean recWallCheckerDown(int x, int y){
        
        char current = bigMap[y][x];
        if (current == '@'){
            return recWallCheckerDown(x, y+1);
        } else if (current == '#'){
            return true;
        } else if (current == '.'){
            return false;
        } else if (current == '['){
            return recWallCheckerDown(x, y+1) || recWallCheckerDown(x+1, y+1);
        } else if (current == ']'){
            return recWallCheckerDown(x, y+1) || recWallCheckerDown(x-1, y+1);
        } else {
            return false;
        }

    }

    public static void moveLeftBig(int x, int y){
        if (wallConnectedBig(x, y, '<')){
            return;
        }

        bigXstart = x-1;
        bigYstart = y;

        char current = bigMap[y][x];
        char next = bigMap[y][x-1];
        bigMap[y][x] = '.';

        while (next != '.'){
            char tmp = next;
            bigMap[y][x-1] = current;
            current = tmp;
            x--;
            next = bigMap[y][x-1];
        }

        bigMap[y][x-1] = current;
    }

    public static void moveRightBig(int x, int y){
        if (wallConnectedBig(x, y, '>')){
            return;
        }

        bigXstart = x+1;
        bigYstart = y;

        char current = bigMap[y][x];
        char next = bigMap[y][x+1];
        bigMap[y][x] = '.';

        while (next != '.'){
            char tmp = next;
            bigMap[y][x+1] = current;
            current = tmp;
            x++;
            next = bigMap[y][x+1];
        }

        bigMap[y][x+1] = current;
    }

    public static void moveUpBig(int x, int y){

        char current = bigMap[y][x];

        if (current == '@'){
            moveUpBig(x, y-1);
            bigMap[y][x] = '.';
            bigMap[y-1][x] = current;
            bigXstart = x;
            bigYstart = y-1;
        } else if (current == '['){
            moveUpBig(x, y-1);
            moveUpBig(x+1, y-1);
            bigMap[y][x] = '.';
            bigMap[y][x+1] = '.';
            bigMap[y-1][x] = current;
            bigMap[y-1][x+1] = ']';
        } else if (current == ']'){
            moveUpBig(x, y-1);
            moveUpBig(x-1, y-1);
            bigMap[y][x] = '.';
            bigMap[y][x-1] = '.';
            bigMap[y-1][x] = current;
            bigMap[y-1][x-1] = '[';
        } else if (current == '.') {
            return;
        } else {
            System.out.println("SHOULD NOT HAPPEN");
            return;
        }
    }

    public static void moveDownBig(int x, int y){

        char current = bigMap[y][x];

        if (current == '@'){
            moveDownBig(x, y+1);
            bigMap[y][x] = '.';
            bigMap[y+1][x] = current;
            bigXstart = x;
            bigYstart = y+1;
        } else if (current == '['){
            moveDownBig(x, y+1);
            moveDownBig(x+1, y+1);
            bigMap[y][x] = '.';
            bigMap[y][x+1] = '.';
            bigMap[y+1][x] = current;
            bigMap[y+1][x+1] = ']';
        } else if (current == ']'){
            moveDownBig(x, y+1);
            moveDownBig(x-1, y+1);
            bigMap[y][x] = '.';
            bigMap[y][x-1] = '.';
            bigMap[y+1][x] = current;
            bigMap[y+1][x-1] = '[';
        } else if (current == '.') {
            return;
        } else {
            System.out.println("SHOULD NOT HAPPEN");
            return;
        }
    }

    public static void moveBig(char direction){
        if (direction == '<'){
            moveLeftBig(bigXstart, bigYstart);
        }

        if (direction == '^'){
            if (wallConnectedBig(bigXstart, bigYstart, '^')){
                return;
            }
            moveUpBig(bigXstart, bigYstart);
        }

        if (direction == '>'){
            moveRightBig(bigXstart, bigYstart);
        }

        if (direction == 'v'){
            if (wallConnectedBig(bigXstart, bigYstart, 'v')){
                return;
            }
            moveDownBig(bigXstart, bigYstart);
        }
    }

    public static void moveAllBig(){
        for (int i = 0; i < directions.length; i++){
            moveBig(directions[i]);          
        }
    }

    public static boolean checkBigMap(){
        for (int i = 0; i < bigRows; i++){
            for (int j = 0; j < bigCols; j++){
                if (bigMap[i][j] == '[' && bigMap[i][j+1] != ']'){
                    return false;
                } else if (bigMap[i][j] == ']' && bigMap[i][j-1] != '['){
                    return false;
                }
            }   
        }
        return true;
    }

    public static int solver2(){
        int totalSum = 0;

        for (int i = 0; i < bigRows; i++){
            for (int j = 0; j < bigCols; j++){
                if (bigMap[i][j] == '['){
                    totalSum += i*100 + j;
                    j++;   
                }
            }
        }


        return totalSum;
    }

}
