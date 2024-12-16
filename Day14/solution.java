package Day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    public static void main(String[] args) {
        try {
            ArrayList<Robot> robots = reader();
            int rows = 103;
            int columns = 101;
            int time = 100;

            // long solution1 = solver1(robots, rows, columns, time);
            // System.out.println("Solution part 1: " + solution1);

            long solution2 = solver2(robots, rows, columns);
            System.out.println("Solution part 2: " + solution2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Robot> reader() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day14//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        String regex = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)";
        Pattern pattern = Pattern.compile(regex);
        ArrayList<Robot> robots = new ArrayList<Robot>();
        
        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()){
                int pX = Integer.parseInt(matcher.group(1));
                int pY = Integer.parseInt(matcher.group(2));
                int vX = Integer.parseInt(matcher.group(3));
                int vY = Integer.parseInt(matcher.group(4));
                
                Robot currentRobot = new Robot(pX, pY, vX, vY);
                robots.add(currentRobot);
            }
        }

        inputScanner.close();
        return robots;
    }

    public static int[] predictPosition(Robot robot, int time, int width, int height){
        int[] position = new int[2];

        position[0] = (((robot.xPosition + robot.xVelocity * time) % width) + width) % width;
        position[1] = (((robot.yPosition + robot.yVelocity * time) % height) + height) % height;
        return position;
    }

    public static long solver1(ArrayList<Robot> robots, int rows, int columns, int time){
        long safetyFactor = 0;
        long[][] positions = new long[rows][columns];
            
        for (Robot robot : robots) {
            int[] newPosition = predictPosition(robot, time, columns, rows);
            positions[newPosition[1]][newPosition[0]] += 1;
        }

        long firstQuadrant = 0;
        for (int i = 0; i < rows/2; i++) {
            for (int j = 0; j < columns/2; j++) {
                firstQuadrant += positions[i][j];
            }
        }

        long secondQuadrant = 0;
        for (int i = 0; i < rows/2; i++) {
            for (int j = columns/2+1; j < columns; j++) {
                secondQuadrant += positions[i][j];
            }
        }

        long thirdQuadrant = 0;
        for (int i = rows/2+1; i < rows; i++) {
            for (int j = 0; j < columns/2; j++) {
                thirdQuadrant += positions[i][j];
            }
        }

        long fourthQuadrant = 0;
        for (int i = rows/2+1; i < rows; i++) {
            for (int j = columns/2+1; j < columns; j++) {
                fourthQuadrant += positions[i][j];
            }
        }

        safetyFactor = firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant;

        return safetyFactor;
    }

    public static void printMap(long[][] positions, int rows, int columns){
        for (int i = 0; i < rows; i++) { 
            for (int j = 0; j < columns; j++) {
                if (positions[i][j] > 0){
                    System.out.print(positions[i][j]);
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public static boolean checkMap(long[][] positions, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            int count = 0;
            int maxRow = 0;
            boolean previous = false; 
            for (int j = 0; j < columns; j++) {
                if (positions[i][j] == 1 && !previous){
                    previous = true;
                    count = 0;
                } if (previous && positions[i][j] == 1){
                    count++;
                } else {
                    maxRow = Math.max(maxRow, count);
                    count = 0;
                    previous = false;
                }
            }
            if (maxRow > 20){
                return true;
            }
        }
        return false;
    }

    public static int solver2(ArrayList<Robot> robots, int rows, int columns){
        boolean found = false;
        int time = 1;
        long[][] positions = new long[rows][columns];

        while(!found){
            for (Robot robot : robots) {
                int[] newPosition = predictPosition(robot, time, columns, rows);
                positions[newPosition[1]][newPosition[0]] += 1;
            }

            if (checkMap(positions, rows, columns)){
                found = true;
                printMap(positions, rows, columns);
            } else {
                time++;
            }
            
            positions = new long[rows][columns];
        }

        

        return time;
    }
}

class Robot {
    int xPosition;
    int yPosition;
    int xVelocity;
    int yVelocity;

    public Robot(int pX, int pY, int vX, int vY){
        this.xPosition = pX;
        this.yPosition = pY;
        this.xVelocity = vX;
        this.yVelocity = vY;
    }

    @Override
    public String toString() {
        return "p=" + xPosition + "," + yPosition + " v=" + xVelocity + "," + yVelocity;
    }
}
