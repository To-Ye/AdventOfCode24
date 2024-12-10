package Day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    public static void main(String[] args) {
        try {
            String diskMap = reader1("input.txt");

            ArrayList<Long> individualBlocks = getIndividualBlocks(diskMap);

            ArrayList<Long> rearrangedBlocks = rearrange1(individualBlocks);


            long result1 = solver1(rearrangedBlocks);
            System.out.println("Solution part 1: " + result1);

            // printBlock(individualBlocks);

            long[] rearrangedBlocks2 = rearrange2(individualBlocks);
            long result2 = solver2(rearrangedBlocks2);
            System.out.println("Solution part 2: " + result2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String reader1(String inputPath) throws FileNotFoundException{
        File inputFile = new File("AdventOfCode24//Day09//" + inputPath);
        Scanner inputScanner = new Scanner(inputFile);
        String diskMap = "";

        while(inputScanner.hasNextLine()) {
            diskMap = inputScanner.nextLine();
        }

        inputScanner.close();
        return diskMap;

    }
    

    public static ArrayList<Long> getIndividualBlocks(String diskMap) {
        
        boolean empty = false;
        ArrayList<Long> individualBlocks = new ArrayList<>();

        Pattern digitPattern = Pattern.compile("\\d{1}");

        Matcher matcher = digitPattern.matcher(diskMap);

        Long id = 0L;
        
        while (matcher.find()) {
            // Convert matched string to integer and add to list
            Long currentMatch = Long.parseLong(matcher.group());
            if(!empty){
                while(currentMatch > 0) {
                    individualBlocks.add(id);
                    currentMatch--;
                }
                empty = true;
                id++;
            } else {
                while(currentMatch > 0) {
                    individualBlocks.add(-1L);
                    currentMatch--;
                }
                empty = false;
            }
            
        }        

        return individualBlocks;
    }

    public static ArrayList<Long> rearrange1(ArrayList<Long> individualBlocks) {
        int blocksLength = individualBlocks.size();
        ArrayList<Long> rearrangedBlocks = new ArrayList<>();

        int j = blocksLength-1;

        for (int i = 0; i <= j; i++) {
            Long currentLong = individualBlocks.get(i);
            Long lastLong = individualBlocks.get(j);
            while(lastLong == -1L) {
                j--;
                lastLong = individualBlocks.get(j);
            }

            if (currentLong != -1L) {
                rearrangedBlocks.add(currentLong);
            } else if (i <= j){
                rearrangedBlocks.add(lastLong);
                j--;
            }
        }

        return rearrangedBlocks;
    }

    public static long solver1 (ArrayList<Long> rearrangedBlocks){
        long checkSum = 0L;
        int blocksLength = rearrangedBlocks.size();

        for(int i = 0; i < blocksLength; i++) {
            long fileId = rearrangedBlocks.get(i);
            long currentProduct = (long) (fileId * i);
            checkSum += currentProduct;
        }

        return checkSum;
    }


    public static long[] rearrange2(ArrayList<Long> individualBlocks) {
               

        int length = individualBlocks.size();

        long[] blocksArray = new long[length];

        for(int j = 0; j < length; j++) {
            blocksArray[j] = individualBlocks.get(j);
        }
        
        for(int j = length-1; j >= 0; j--) {
            Long currentLong = blocksArray[j];

            if (currentLong == -1L) {
                continue;
            }

            int startIndex = j;
            int filesLength = 0;

            for (; j >= 0; j--) {
                if (blocksArray[j] == currentLong) {
                    filesLength++;
                } else {
                    break;
                }
            }

            j++;


            for (int k = 0; k < j; k++){
                if (blocksArray[k] != -1L) {
                    continue;
                }

                int startIndexFree = k;

                int freeSpace = 0;
                for (; k < j; k++) {
                    if (blocksArray[k] == -1L) {
                        freeSpace++;
                    } else {
                        break;
                    }
                }

                k--;

                if (freeSpace >= filesLength) {
                    int offset = 0;
                    for (int l = startIndexFree; l < filesLength+startIndexFree; l++) {
                        blocksArray[l] = currentLong;
                        blocksArray[startIndex-offset] = -1L;
                        offset++;
                    }
                    break;
                }

            }
        
        }        

        return blocksArray;
    }

    public static long solver2(long[] blocksArray){
        long checkSum = 0;
        int length = blocksArray.length;

        for(int i = 0; i < length; i++) {
            long fileId = blocksArray[i];
            if (fileId == -1L) {
                continue;
            }
            checkSum += fileId * i;
        }
        

        return checkSum;
    }


}
