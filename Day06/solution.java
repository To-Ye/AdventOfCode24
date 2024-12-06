package Day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class solution {
        public static void main(String[] args) {
            try {
                int[] dimensionsAndStart = getDimensions();
                int numberOfLines = dimensionsAndStart[0];
                int lineLength = dimensionsAndStart[1];
                int startX = dimensionsAndStart[2];
                int startY = dimensionsAndStart[3];

                char[][] startingMap = reader(numberOfLines, lineLength);
                
                int result1 = solver1(startingMap, numberOfLines, lineLength, startX, startY);
                System.out.println("Solution part 1: " + result1);

                int result2 = solver2(startingMap, numberOfLines, lineLength, startX, startY);
                System.out.println("Solution part 2: " + result2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static char[][] reader(int numberOfLines, int lineLength) throws FileNotFoundException {
            File inputFile = new File("AdventOfCode24//Day06//input.txt");
            Scanner inputScanner = new Scanner(inputFile);
            char[][] startingMap = new char[numberOfLines][lineLength];

            int i = 0;
            while(inputScanner.hasNextLine()){
                String line = inputScanner.nextLine();
                startingMap[i] = line.toCharArray();
                i++;
            }

            // for(int j = 0; j < numberOfLines; j++){
            //     for(int k = 0; k < lineLength; k++){
            //         System.out.print(startingMap[j][k]);
            //     }
            //     System.out.println();
            // }


            inputScanner.close();
            return startingMap;
        }

        public static int[] getDimensions() throws FileNotFoundException {
            int[] dimensions = new int[4];
            int numberOfLines = 0;
            int lineLength = 0;

            File inputFile = new File("AdventOfCode24//Day06//input.txt");
            Scanner inputScanner = new Scanner(inputFile);

            while(inputScanner.hasNextLine()){
                String line = inputScanner.nextLine();
                int startX = line.indexOf('^');
                if (startX != -1){
                    dimensions[2] = startX;
                    dimensions[3] = numberOfLines;
                }
                numberOfLines++;
                lineLength = line.length();
            }

            dimensions[0] = numberOfLines;
            dimensions[1] = lineLength;

            inputScanner.close();
            return dimensions;
        }

        public static int solver1(char[][] startingMap, int numberOfLines, int lineLength, int startX, int startY){
            int count = 0;
            HashSet<String> visitedPositions = new HashSet<>();
            String movingDirection = "up";
            
            int currentX = startX;
            int currentY = startY;

            while(0 <= currentX && currentX < lineLength && 0 <= currentY && currentY < numberOfLines ) {
                String currentPosition = currentX + " | " + currentY;
                visitedPositions.add(currentPosition);

                int[] nextPositions = getNextPosition(movingDirection, currentX, currentY);
                int nextX = nextPositions[0];
                int nextY = nextPositions[1];

                while(0 <= nextX && nextX < lineLength && 0 <= nextY && nextY < numberOfLines && startingMap[nextY][nextX] == '#'){
                    movingDirection = getNewDirection(movingDirection);
                    nextPositions = getNextPosition(movingDirection, currentX, currentY);
                    nextX = nextPositions[0];
                    nextY = nextPositions[1];
                }
                
                currentX = nextX;
                currentY = nextY;
            }

            count = visitedPositions.size();
            return count;
        }

        public static int[] getNextPosition(String movingDirection, int currentX, int currentY){
            int[] nextPosition = new int[2];

            if(movingDirection.equals("up")){
                nextPosition[0] = currentX;
                nextPosition[1] = currentY-1;
            }

            if(movingDirection.equals("down")){
                nextPosition[0] = currentX;
                nextPosition[1] = currentY+1;
            }

            if(movingDirection.equals("right")){
                nextPosition[0] = currentX+1;
                nextPosition[1] = currentY;
            }

            if(movingDirection.equals("left")){
                nextPosition[0] = currentX-1;
                nextPosition[1] = currentY;
            }

            return nextPosition;
        }

        public static String getNewDirection(String movingDirection){

            String newDirection = "";

            if(movingDirection.equals("up")){
                newDirection = "right";
            } else if(movingDirection.equals("right")){
                newDirection = "down";
            } else if(movingDirection.equals("down")){
                newDirection = "left";
            } else {
                newDirection = "up";
            }

            return newDirection;
        }

        public static boolean foundLoop(char[][] startingMap, int numberOfLines, int lineLength, int startX, int startY){
            boolean foundLoop = false;
            HashSet<String> visitedPositions = new HashSet<>();
            String movingDirection = "up";
            
            int currentX = startX;
            int currentY = startY;

            int maxSteps = numberOfLines * lineLength;
            int steps = 0;

            while(0 <= currentX && currentX < lineLength && 0 <= currentY && currentY < numberOfLines) {

                if (steps > maxSteps){
                    return true;
                }

                String currentPosition = currentX + " | " + currentY;

                visitedPositions.add(currentPosition);

                int[] nextPositions = getNextPosition(movingDirection, currentX, currentY);
                int nextX = nextPositions[0];
                int nextY = nextPositions[1];

                while(0 <= nextX && nextX < lineLength && 0 <= nextY && nextY < numberOfLines && startingMap[nextY][nextX] == '#'){
                    movingDirection = getNewDirection(movingDirection);
                    nextPositions = getNextPosition(movingDirection, currentX, currentY);
                    nextX = nextPositions[0];
                    nextY = nextPositions[1];
                }
                
                currentX = nextX;
                currentY = nextY;

                steps++;
            }

            return foundLoop;
        }

        public static int solver2(char[][] startingMap, int numberOfLines, int lineLength, int startX, int startY){
            int count = 0;

            for(int i = 0; i < numberOfLines; i++){
                for(int j = 0; j < lineLength; j++){
                    if(startingMap[i][j] == '#'){
                        continue;
                    } else {
                        startingMap[i][j] = '#';
                        
                        if(foundLoop(startingMap, numberOfLines, lineLength, startX, startY)){
                            count++;
                        }

                        startingMap[i][j] = '.';
                    }
                }
            }

            return count;
        }

}

