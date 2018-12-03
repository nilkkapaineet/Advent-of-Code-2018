package com.company;
import java.io.*;
import java.util.*;
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

            int maxHeight = 0;
            int maxWidth = 0;
            List<Integer[]> lines = new ArrayList<Integer[]>();
            while((line = bufferedReader.readLine()) != null) {
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m = p.matcher(line);
                Integer[] ns = new Integer[5];
                int i = 0;
                while (m.find()) {
                    int n = Integer.parseInt(m.group());
                    // append n to list
                    ns[i] = n;
                    i++;
                }
                lines.add(ns);

                if (ns[1]+ns[3]+1 > maxWidth) {
                    maxWidth = ns[1]+ns[3]+1;
                }
                if (ns[2]+ns[4]+1 > maxHeight) {
                    maxHeight = ns[2]+ns[4]+1;
                }

  //              System.out.println(ns[0] + " " + ns[1] + " " + ns[2] + " " + ns[3] + " " + ns[4]);
            }
            // Always close files.
            bufferedReader.close();

//            System.out.println(maxHeight + " " + maxWidth + " " + lines.size() );

            int[][] grid = new int[maxWidth][maxHeight];
            for (int i=0; i<maxWidth; i++) {
               for (int j=0; j<maxHeight; j++) {
                   grid[i][j] = 0;
               }
            }

            // fill grid
            int numberOfMultipleClaims = 0;
            for (int i=0; i<lines.size(); i++) {
                // (1,3) 4*4
                // x,y,w,h
                Integer[] n = lines.get(i);
                for (int x=n[1]; x<(n[1]+n[3]); x++) {
                    for (int y=n[2]; y<(n[2]+n[4]); y++) {
                        grid[x][y] += 1;
                        if (grid[x][y] == 2) {
                            numberOfMultipleClaims += 1;
                        }
                    }
                }
            }

            System.out.println("Number of multiple claims: " + numberOfMultipleClaims);

            /*
            for (int i=0; i<maxWidth; i++) {
                for (int j=0; j<maxHeight; j++) {
                    System.out.print(grid[i][j]);
                }
                System.out.println();
            }
            */

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