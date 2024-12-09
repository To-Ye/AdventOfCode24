package Day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        try {
            int[] dimensions = getDimensions();
            int rows = dimensions[0];
            int cols = dimensions[1];          
            char[][] frequencyMap = reader(rows, cols);

            int solution1 = solver1(frequencyMap, rows, cols);
            System.out.println("Solution part 1: " + solution1);
            
            int solution2 = solver2(frequencyMap, rows, cols);
            System.out.println("Solution part 2: " + solution2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static char[][] reader(int rows, int cols) throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day08//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        char[][] frequencyMap = new char[rows][cols];
        
        int i = 0;
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            frequencyMap[i] = line.toCharArray();
            i++;
        }

        inputScanner.close();
        return frequencyMap;
    }

    public static int[] getDimensions() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day08//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        int[] dimensions = new int[2];
        int rows = 0;
        int cols = 0;
        
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            rows++;
            cols = line.length();
        }

        inputScanner.close();
        dimensions[0] = rows;
        dimensions[1] = cols;
        return dimensions;
    }

    public static ArrayList<Point> getAntennas(char[][] frequencyMap, int rows, int cols) {

        ArrayList<Point> antennas = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char currentFrequency = frequencyMap[i][j];
                Point currPoint = null;

                if(currentFrequency != '#' && currentFrequency != '.') {
                    currPoint = new Point(i, j, currentFrequency);
                    antennas.add(currPoint);
                }
            }
        }

        return antennas;
    }

    public static ArrayList<PointPair> getPossiblePairs(ArrayList<Point> antennas) {
        ArrayList<PointPair> possiblePairs = new ArrayList<>();
        int antennasSize = antennas.size();

        for (int i = 0; i < antennasSize; i++) {
            Point antenna1 = antennas.get(i);
            char frequency1 = antenna1.frequency;
            for (int j = i+1; j < antennasSize; j++) {
                Point antenna2 = antennas.get(j);
                char frequency2 = antenna2.frequency;
                if(frequency1 == frequency2) {
                    PointPair pair = new PointPair(antenna1, antenna2);
                    possiblePairs.add(pair);
                }
            }
        }

        return possiblePairs;

    }

    public static int solver1(char[][] frequencyMap, int rows, int cols) {
        ArrayList<Point> antennas = getAntennas(frequencyMap, rows, cols);
        int count = 0;
        ArrayList<PointPair> possiblePairs = getPossiblePairs(antennas);
        
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                char currentFrequency = frequencyMap[i][j];
                
                    Point currentPoint = new Point(i, j, currentFrequency);
                    for (PointPair pair : possiblePairs) {
                        if (pair.p1.equals(currentPoint) || pair.p2.equals(currentPoint)) {
                            continue;
                        }

                        if(pair.isOnSameLine(currentPoint)) {
                            int distance1 = pair.p1.distance(currentPoint);
                            int distance2 = pair.p2.distance(currentPoint);
                            if(distance1 == 4*distance2 || distance2 == 4*distance1) {
                                count++;
                                break;
                            }
                        }
                    }
                
            }
        }

        return count;
        
    }

    public static int solver2(char[][] frequencyMap, int rows, int cols) {
        ArrayList<Point> antennas = getAntennas(frequencyMap, rows, cols);
        int count = 0;
        ArrayList<PointPair> possiblePairs = getPossiblePairs(antennas);
        
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                char currentFrequency = frequencyMap[i][j];
                
                    Point currentPoint = new Point(i, j, currentFrequency);
                    for (PointPair pair : possiblePairs) {

                        if(pair.isOnSameLine(currentPoint)) {
                            count++;
                            break;
                        }
                    }
                
            }
        }

        return count;
        
    }

}

class Point {
    int x;
    int y;
    char frequency;

    public Point(int y, int x, char frequency) {
        this.x = x;
        this.y = y;
        this.frequency = frequency;
    }

    public int distance(Point p) {
        int vertDistance = Math.abs(this.x - p.x);
        int horizDistance = Math.abs(this.y - p.y);
        return (vertDistance*vertDistance) + (horizDistance * horizDistance);
    }

    @Override
    public String toString() {
        return this.frequency + " = (" + y + ", " + x + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same object reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Null or different class
        }
        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }

}

class PointPair {
    Point p1;
    Point p2;

    public PointPair(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isOnSameLine(Point p3){
        int x1 = this.p1.x;
        int y1 = this.p1.y;

        int x2 = this.p2.x;
        int y2 = this.p2.y;

        int x3 = p3.x;
        int y3 = p3.y;

        return (y2-y1)*(x3-x2) == (y3-y2)*(x2-x1);
    }

    @Override
    public String toString() {
        return p1.toString() + " | " + p2.toString();
    }
}
