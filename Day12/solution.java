package Day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class solution {

    public static char[][] gardenPlots;
    public static int rows;
    public static int cols;
    public static void main(String[] args) {
        try {
            setDimensions();
            gardenPlots = new char[rows][cols];

            reader();

            int result1 = solver1();
            System.out.println("Solution part 1: " + result1);
            
            int result2 = solver2();
            System.out.println("Solution part 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDimensions() throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day12//input.txt");
        Scanner fileScanner = new Scanner(inputFile);

        int i = 0;
        int j = 0;
        while (fileScanner.hasNextLine()) {
            i++;
            String line = fileScanner.nextLine();
            j = line.length();
        }

        rows = i;
        cols = j;
        fileScanner.close();
    }

    public static void reader() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day12//input.txt");
        Scanner fileScanner = new Scanner(inputFile);

        int i = 0;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            gardenPlots[i] = line.toCharArray();
            i++;
        }

        fileScanner.close();
        
    }

    public static int getPerimeter(int row, int col, char gardenPlot) {
        int perimeter = 0;
        if (row - 1 < 0 || gardenPlots[row - 1][col] != gardenPlot) {
            perimeter++;
        }

        if (row + 1 >= rows || gardenPlots[row + 1][col] != gardenPlot) {
            perimeter++;
        }

        if (col - 1 < 0 || gardenPlots[row][col - 1] != gardenPlot) {
            perimeter++;
        }

        if (col + 1 >= cols || gardenPlots[row][col + 1] != gardenPlot) {
            perimeter++;
        }

        return perimeter;
    }

    public static int solver1() {
        int totalSum = 0;
        ArrayList<GardenPlot> visited = new ArrayList<>();
        Stack<GardenPlot> stack = new Stack<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char currentPlant = gardenPlots[i][j];
                GardenPlot currGardenPlot = new GardenPlot(i, j, currentPlant);
                if (visited.contains(currGardenPlot)) {
                    continue;
                }
                int currentArea = 0;
                int currentPerimeter = 0;
                
                stack.push(currGardenPlot);
                visited.add(currGardenPlot);

                
                while(!stack.isEmpty()){
                    GardenPlot t = stack.pop();
                    
                    currentArea++;
                    currentPerimeter += getPerimeter(t.row, t.col, currentPlant);

                    if (t.row-1 >= 0){
                        GardenPlot up = new GardenPlot(t.row-1, t.col, gardenPlots[t.row-1][t.col]);
                        if (!visited.contains(up) && up.plant == currentPlant){
                            stack.push(up);
                            visited.add(up);
                        }
                    }

                    if (t.row+1 < rows){
                        GardenPlot down = new GardenPlot(t.row+1, t.col, gardenPlots[t.row+1][t.col]);
                        if (!visited.contains(down) && down.plant == currentPlant){
                            stack.push(down);
                            visited.add(down);
                        }
                    }

                    if (t.col-1 >= 0){
                        GardenPlot left = new GardenPlot(t.row, t.col-1, gardenPlots[t.row][t.col-1]);
                        if (!visited.contains(left) && left.plant == currentPlant){
                            stack.push(left);
                            visited.add(left);
                        }
                    }

                    if (t.col+1 < cols){
                        GardenPlot right = new GardenPlot(t.row, t.col+1, gardenPlots[t.row][t.col+1]);
                        if (!visited.contains(right) && right.plant == currentPlant){
                            stack.push(right);
                            visited.add(right);
                        }
                    }
                }

                totalSum += currentArea * currentPerimeter;

            }
        }

        return totalSum;
    }

    public static int getCorners(GardenPlot gardenPlot){
        int corners = 0;
        int row = gardenPlot.row;
        int col = gardenPlot.col;
        char plant = gardenPlot.plant;

        // up and left (outer corner)
        if ((row - 1 < 0 && (col - 1 < 0 || gardenPlots[row][col-1] != plant)) || 
            (col - 1 < 0 && gardenPlots[row-1][col] != plant) ||
            (row - 1 >= 0 && col - 1 >= 0 && gardenPlots[row][col-1] != plant && gardenPlots[row-1][col] != plant)){
            corners++;
        }

        // up and left (inner corner)
        if (row - 1 >= 0 && col - 1 >= 0 && gardenPlots[row-1][col] == plant && gardenPlots[row][col-1] == plant && gardenPlots[row-1][col-1] != plant){
            corners++;
        }

        // up and right (outer corner)
        if ((row - 1 < 0 && (col + 1 >= cols || gardenPlots[row][col+1] != plant)) || 
            (col + 1 >= cols && gardenPlots[row-1][col] != plant) ||
            (row - 1 >= 0 && col + 1 < cols && gardenPlots[row][col+1] != plant && gardenPlots[row-1][col] != plant)){
            corners++;
        }

        // up and right (inner corner)
        if (row - 1 >= 0 && col + 1 < cols && gardenPlots[row-1][col] == plant && gardenPlots[row][col+1] == plant && gardenPlots[row-1][col+1] != plant){
            corners++;
        }

        // down and left (outer corner)
        if ((row + 1 >= rows && (col - 1 < 0 || gardenPlots[row][col-1] != plant)) ||
            (col - 1 < 0 && gardenPlots[row+1][col] != plant) ||
            (row + 1 < rows && col - 1 >= 0 && gardenPlots[row][col-1] != plant && gardenPlots[row+1][col] != plant)){
            corners++;
        }

        // down and left (inner corner)
        if (row + 1 < rows && col - 1 >= 0 && gardenPlots[row+1][col] == plant && gardenPlots[row][col-1] == plant && gardenPlots[row+1][col-1] != plant){
            corners++;
        }

        // down and right (outer corner)
        if ((row + 1 >= rows && (col + 1 >= cols || gardenPlots[row][col+1] != plant)) ||
            (col + 1 >= cols && gardenPlots[row+1][col] != plant) ||
            (row + 1 < rows && col + 1 < cols && gardenPlots[row][col+1] != plant && gardenPlots[row+1][col] != plant)){
            corners++;
        } 

        // down and right (inner corner)
        if (row + 1 < rows && col + 1 < cols && gardenPlots[row+1][col] == plant && gardenPlots[row][col+1] == plant && gardenPlots[row+1][col+1] != plant){
            corners++;
        }

        return corners;
    }

    public static int solver2() {
        int totalSum = 0;
        ArrayList<GardenPlot> visited = new ArrayList<>();
        Stack<GardenPlot> stack = new Stack<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char currentPlant = gardenPlots[i][j];
                GardenPlot currGardenPlot = new GardenPlot(i, j, currentPlant);
                if (visited.contains(currGardenPlot)) {
                    continue;
                }
                int currentArea = 0;
                int currentCorners = 0;
                
                stack.push(currGardenPlot);
                visited.add(currGardenPlot);

                
                while(!stack.isEmpty()){
                    GardenPlot t = stack.pop();
                    
                    currentArea++;
                    currentCorners += getCorners(t);

                    if (t.row-1 >= 0){
                        GardenPlot up = new GardenPlot(t.row-1, t.col, gardenPlots[t.row-1][t.col]);
                        if (!visited.contains(up) && up.plant == currentPlant){
                            stack.push(up);
                            visited.add(up);
                        }
                    }

                    if (t.row+1 < rows){
                        GardenPlot down = new GardenPlot(t.row+1, t.col, gardenPlots[t.row+1][t.col]);
                        if (!visited.contains(down) && down.plant == currentPlant){
                            stack.push(down);
                            visited.add(down);
                        }
                    }

                    if (t.col-1 >= 0){
                        GardenPlot left = new GardenPlot(t.row, t.col-1, gardenPlots[t.row][t.col-1]);
                        if (!visited.contains(left) && left.plant == currentPlant){
                            stack.push(left);
                            visited.add(left);
                        }
                    }

                    if (t.col+1 < cols){
                        GardenPlot right = new GardenPlot(t.row, t.col+1, gardenPlots[t.row][t.col+1]);
                        if (!visited.contains(right) && right.plant == currentPlant){
                            stack.push(right);
                            visited.add(right);
                        }
                    }
                }

                totalSum += currentArea * currentCorners;

            }
        }

        return totalSum;
    }
}

class GardenPlot {
    int row;
    int col;
    char plant;

    public GardenPlot(int row, int col, char plant) {
        this.row = row;
        this.col = col;
        this.plant = plant;
    }

    @Override
    public boolean equals(Object obj) {
        GardenPlot t = (GardenPlot) obj;
        return this.row == t.row && this.col == t.col && this.plant == t.plant;
    }

    
    @Override
    public String toString() {
        return plant + " at: (" + row + ", " + col + ")";
    }
}
