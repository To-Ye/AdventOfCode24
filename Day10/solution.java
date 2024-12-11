package Day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    public static void main(String[] args) {
        try {
            int[] dimensions = getDimensions();
            int rows = dimensions[0];
            int cols = dimensions[1];
            Position[][] map = reader(rows, cols);

            ArrayList<Position> trailHeads = gettrailHeadsAndConnect(map, rows, cols);  

            int result1 = solver1(trailHeads);
            System.out.println("Solution part 1: " + result1);

            int result2 = solver2(trailHeads);
            System.out.println("Solution part 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Position[][] reader(int rows, int cols) throws FileNotFoundException{
        Position[][] map = new Position[rows][cols];
        File inputFile = new File("AdventOfCode24/Day10/input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        String regex = "\\d{1}";
        Pattern pattern = Pattern.compile(regex);

        int i = 0;
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            Matcher matcher = pattern.matcher(line);

            int j = 0;
            
            while(matcher.find()){
                int height = Integer.parseInt(matcher.group());
                Position currentPosition = new Position(i, j, height);
                map[i][j] = currentPosition;
                j++;
            }

            i++;

        }

        inputScanner.close();
        return map;
    }

    public static int[] getDimensions() throws FileNotFoundException{
        int[] dimensions = new int[2];
        File inputFile = new File("AdventOfCode24/Day10/input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        int i = 0;
        int j = 0;
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();

            j = line.length();
            i++;
        }

        dimensions[0] = i;
        dimensions[1] = j;
        inputScanner.close();

        return dimensions;
    }

    public static ArrayList<Position> gettrailHeadsAndConnect(Position[][] map, int rows, int cols){
        ArrayList<Position> trailHeads = new ArrayList<Position>();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                Position currentPosition = map[i][j];
                
                if (currentPosition.height == 0){
                    trailHeads.add(currentPosition);
                }

                if (i > 0){
                    currentPosition.up = map[i-1][j];
                }

                if (i < rows-1){
                    currentPosition.down = map[i+1][j];
                }

                if (j > 0){
                    currentPosition.left = map[i][j-1]; 
                }

                if (j < cols-1){
                    currentPosition.right = map[i][j+1];
                }
            }
        }

        return trailHeads;
    }

    public static int dfs1(Position trailHead){
        int count = 0;
        HashSet<Position> visited = new HashSet<Position>();
        Stack<Position> stack = new Stack<Position>();
        stack.push(trailHead);

        while(!stack.isEmpty()){
            Position current = stack.pop();
            visited.add(current);
            
            if(current.height == 9){
                count++;
            }

            if (current.up != null && !visited.contains(current.up) && current.up.height == current.height + 1){
                stack.push(current.up);
            }

            if (current.down != null && !visited.contains(current.down) && current.down.height == current.height + 1){
                stack.push(current.down);
            }

            if (current.left != null && !visited.contains(current.left) && current.left.height == current.height + 1){
                stack.push(current.left);
            }

            if (current.right != null && !visited.contains(current.right) && current.right.height == current.height + 1){
                stack.push(current.right);
            }
        }


        return count;
    }

    public static int dfs2(Position trailHead){
        int count = 0;
        Stack<Position> stack = new Stack<Position>();
        stack.push(trailHead);

        while(!stack.isEmpty()){
            Position current = stack.pop();
            
            if(current.height == 9){
                count++;
            }

            if (current.up != null && current.up.height == current.height + 1){
                stack.push(current.up);
            }

            if (current.down != null && current.down.height == current.height + 1){
                stack.push(current.down);
            }

            if (current.left != null && current.left.height == current.height + 1){
                stack.push(current.left);
            }

            if (current.right != null && current.right.height == current.height + 1){
                stack.push(current.right);
            }
        }


        return count;
    }

    public static int solver1(ArrayList<Position> trailHeads){
        int sum = 0;

        for(Position trailHead : trailHeads){
            sum += dfs1(trailHead);
        }

        return sum;
    }

    public static int solver2(ArrayList<Position> trailHeads){
        int sum = 0;

        for(Position trailHead : trailHeads){
            sum += dfs2(trailHead);
        }

        return sum;
    }

}

class Position {
    int y;
    int x;
    int height;

    Position up;
    Position down;
    Position left;
    Position right;


    public Position(int y, int x, int height) {
        this.y = y;
        this.x = x;
        this.height = height;
    }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ", " + height + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position other = (Position) obj;
            return this.y == other.y && this.x == other.x;
        }
        return false;
    }

}
