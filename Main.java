package com.company;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class Main {
    public static void main(String [] args) {

        // The name of the file to open.
        String fileName = "file.txt";

        // This will reference one line at a time
        String line = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            int minX = 1000;
            int minY = 1000;
            int maxX = 0;
            int maxY = 0;
            int numberOfCoordinates = 0;
            List<int[]> myList = new ArrayList<int[]>();

            while((line = bufferedReader.readLine()) != null) {

                // put found coordinates into list
                // find min/max x/y
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m = p.matcher(line);
                int[] coords = new int[2];
                int i = 0;
                while (m.find()) {
                    int n = Integer.parseInt(m.group());
                    // append n to list
                    coords[i] = n;
                    i++;
                }
                if (coords[0] < minX) {
                    minX = coords[0];
                }
                if (coords[0] > maxX) {
                    maxX = coords[0];
                }
                if (coords[1] < minY) {
                    minY = coords[1];
                }
                if (coords[1] > maxY) {
                    maxY = coords[1];
                }
                myList.add(coords);
                ++numberOfCoordinates;
            }
            // Always close files.
            bufferedReader.close();

            // extend coordinates a bit
            ++maxX;
            ++maxY;
            --minX;
            --minY;
          //  System.out.println(minX + " " + minY + " " + maxX + " " + maxY);

            // form a two layer grid
            int[][][] layers = new int[(maxX)][(maxY)][2]; // last layer: 0: manhattan distance 1: closest coordinate point number
            // init
            for (int x=minX; x<maxX; x++) {
                for (int y=minY; y<maxY; y++) {
                 //   System.out.println(x + " " + y);
                    layers[x][y][0] = 1000;
                }
            }
            // find manhattan distances from each grid point to every coordinate
            for (int x=minX; x<maxX; x++) {
                for (int y=minY; y<maxY; y++) {
                    int coordinatePointNumber = 1;
                    for (int i=0; i<myList.size(); i++) {
                        int[] c = myList.get(i);
                        //System.out.println(c[0] + " " + c[1]);
                        // and now the distance from xy to c01
                        int distance = Math.abs(x-c[0]) + Math.abs(y-c[1]);
                        if (layers[x][y][0] > distance) {
                            layers[x][y][0] = distance;
                            layers[x][y][1] = coordinatePointNumber;
                  //          System.out.println("shorter found: " + coordinatePointNumber);
                        } else if (layers[x][y][0] == distance) {
                            // distance even with two (or more) coordinate points --> assign to no one
                            layers[x][y][0] = distance;
                            layers[x][y][1] = 0; // 0 means it's even and assigned to no one
                //            System.out.println("this is even");
                        }
                        ++coordinatePointNumber;
                    }
                }
            }
            // store information of min distance to layer 1 and and coordinate number to layer 2

            // find out the greatest area hold by coordinate point numbers
            // but it shouldn't be infinite i.e. on the limits of the grid
            int[] areaHold = new int[numberOfCoordinates+1];
            List<Integer> ignoreList = new ArrayList<>();
            for (int x=minX; x<maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    int hold = layers[x][y][1];
                    ++areaHold[hold];
                    if (x == minX || y == minY || x == maxX-1 || y == maxY-1) {
                        ignoreList.add(hold);
                    }
              //      System.out.print(hold);
                }
            //    System.out.println("");
            }

            // ignore area if it's limited on the edges of array
            System.out.println("Greatest are is hold by index " + getIndexOfLargest(areaHold, ignoreList) + " with area of " + getLargestValue(areaHold, ignoreList));

        }

        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public static int getIndexOfLargest( int[] array, List<Integer> ignore )
    {
        if ( array == null || array.length == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) {
                for (int ig=0; ig<ignore.size(); ig++) {
                    int c = ignore.get(ig);
                    if (c != i) {
                        largest = i;
                    }
                }
            }
        }
        return largest; // position of the first largest found
    }

    public static int getLargestValue( int[] decMax, List<Integer> ignore ) {
        // ignore area if it's limited on the edges of array
        int max = 0;
        for (int counter = 1; counter < decMax.length; counter++) {
            if (decMax[counter] > max) {
                for (int ig=0; ig<ignore.size(); ig++) {
                    int c = ignore.get(ig);
                    if (c != counter) {
                        max = decMax[counter];
                    }
                }
            }
        }
        return max;
    }

}
