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

            // form a two layer grid
            int[][][] layers = new int[(maxX)][(maxY)][2]; // last layer: 0: manhattan distance 1: closest coordinate point number
            // init
            for (int x=minX; x<maxX; x++) {
                for (int y=minY; y<maxY; y++) {
                    layers[x][y][0] = 0;
                }
            }
            // find manhattan distances from each grid point to every coordinate
            for (int x=minX; x<maxX; x++) {
                for (int y=minY; y<maxY; y++) {
                    int coordinatePointNumber = 1;
                    for (int i=0; i<myList.size(); i++) {
                        int[] c = myList.get(i);
                        // and now the distance from xy to c01
                        int distance = Math.abs(x-c[0]) + Math.abs(y-c[1]);

                        // add all manhattan distances
                        layers[x][y][0] += distance;

                        ++coordinatePointNumber;
                    }
                }
            }
            // store information of min distance to layer 1 and and coordinate number to layer 2

            // find area of coordinate points with less tha 10 000 manhattan distance
            int safeSpace = 0;
            for (int x=minX; x<maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    if (layers[x][y][0] < 10000) {
                        ++safeSpace;
                    }
                }
            }
            System.out.println("Safe space: " + safeSpace);

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
    
}
