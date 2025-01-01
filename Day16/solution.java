package Day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class solution {
    public static int rows = 0;
    public static int columns = 0;
    public static char[][] inputMap;
    public static int startX = 0;
    public static int startY = 0;
    public static int endX = 0;
    public static int endY = 0;
    public static ArrayList<Integer> visited = new ArrayList<Integer>();
    public static void main(String[] args) {
        
        try {
            getDimensions();
            inputMap = new char[rows][columns];
            reader();

            
            int result = bfs();
            
            System.out.println(result);
            System.out.println(countOs());
            
            
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void getDimensions() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day16//input.txt");
        Scanner inputScanner = new Scanner(inputFile);
        
        int i = 0;
        int j = 0;
        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            i++;
            j = line.length();
        }

        rows = i;
        columns = j;

        inputScanner.close();
    }

    public static void reader() throws FileNotFoundException {
        File inputFile = new File("AdventOfCode24//Day16//input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        int row = 0;
        while(inputScanner.hasNextLine()){
            String line = inputScanner.nextLine();
            int xIndexOfS = line.indexOf("S");
            int xIndexOfE = line.indexOf("E");

            if (xIndexOfS != -1) {
                startY = row;
                startX = xIndexOfS;
            }

            if (xIndexOfE != -1) {
                endY = row;
                endX = xIndexOfE;
            }

            inputMap[row] = line.toCharArray();
            row++;
        }

        inputScanner.close();
    }

    public static void printMaze() {
        for (int i = 0; i < rows; i++) {
            System.out.println(inputMap[i]);
        }
        System.out.println();
    }

    // 135512

    public static int bfs(){
        // turningCostMap[currentDirection][targetDirection] = cost of turning from currentDirection to targetDirection
        // North = n = 0
        // South = s = 1
        // East = e = 2

        int[][] turningCostMap = {{0, 2000, 1000, 1000},
                                  {2000, 0, 1000, 1000},
                                  {1000, 1000, 0, 2000},
                                  {1000, 1000, 2000, 0}};

        
        HashMap<String, Integer> visited = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node startingNode = new Node(startX, startY, 0, 2, null);
        queue.add(startingNode);
        visited.put(startX + " " + startY, 0);
        int minPath = Integer.MAX_VALUE;

        int i = 0;
        while(!queue.isEmpty()){

            Node currNode = queue.poll();
            int xCurrent = currNode.x;
            int yCurrent = currNode.y;
            int currentDirection = currNode.direction;
            
            
            if (xCurrent == endX && yCurrent == endY){
                if (currNode.cost <= minPath){
                    System.out.println("found at iteration: " + i);
                    minPath = currNode.cost;
                    backMarkPath(currNode);
                    continue;
                } else {
                    return minPath;
                } 
            }

            if (minPath != Integer.MAX_VALUE){
                if (currNode.cost > minPath){
                    return minPath;
                }
            }

            //North == 0
            if (inputMap[yCurrent-1][xCurrent] != '#'){

                int turningCost = turningCostMap[currentDirection][0];
                int newCost = currNode.cost + turningCost + 1;
                // if (newCost <= 135512){
                    Node northNode = new Node(xCurrent, yCurrent-1, newCost, 0, currNode);
                    queue.add(northNode);
                    visited.put(xCurrent + " " + (yCurrent-1), newCost);
                // }
            }

            //South == 1
            if (inputMap[yCurrent+1][xCurrent] != '#'){
                    
                int turningCost = turningCostMap[currentDirection][1];
                int newCost = currNode.cost + turningCost + 1;
                if (newCost <= 135512){
                    Node southNode = new Node(xCurrent, yCurrent+1, newCost, 1, currNode);
                    queue.add(southNode);
                    visited.put(xCurrent + " " + (yCurrent+1), newCost);
                }
                    

            }

            //East == 2
            if (inputMap[yCurrent][xCurrent+1] != '#'){

                int turningCost = turningCostMap[currentDirection][2];
                int newCost = currNode.cost + turningCost + 1;
                if (newCost <= 135512){
                    Node eastNode = new Node(xCurrent+1, yCurrent, newCost, 2, currNode);
                    queue.add(eastNode);
                    visited.put((xCurrent+1) + " " + yCurrent, newCost);
                }   

            }

            //West == 3
            if (inputMap[yCurrent][xCurrent-1] != '#'){
                    
                int turningCost = turningCostMap[currentDirection][3];
                int newCost = currNode.cost + turningCost + 1;
                
                if (newCost <= 135512){
                    Node westNode = new Node(xCurrent-1, yCurrent, newCost, 3, currNode);
                    queue.add(westNode);
                    visited.put((xCurrent-1) + " " + yCurrent, newCost);
                }
    

            }

            i++;

            
        }

        return minPath;
    }

    
    public static void backMarkPath(Node endNode){
        Node currentNode = endNode;

        while (currentNode != null){
            
            int x = currentNode.x;
            int y = currentNode.y;
            inputMap[y][x] = 'O';
            currentNode = currentNode.priorNode;
            
        }

    }

    public static int countOs(){
        int count = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (inputMap[i][j] == 'O'){
                    count++;
                }
            }
        }

        return count;
    }



}


class Node implements Comparable<Node> {
    int x;
    int y;
    int cost;
    int direction; //the direction from where we got to this node
    Node priorNode;

    public Node(int x, int y, int cost, int direction, Node priorNode){
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.direction = direction;
        this.priorNode = priorNode;
    }

    @Override
    public boolean equals(Object obj) {
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int compareTo(Node other) {
        if (this.cost == other.cost){
            return 0;
        } else if (this.cost > other.cost){
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + " cost: " + cost;
    }

}



