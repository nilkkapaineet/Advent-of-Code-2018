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

            int howManyLines = 0;
            int lineLength = 0;
            List<String> lines = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
                howManyLines++;
                lines.add(line);
                lineLength = line.length();
   //             char first = line.charAt(5);
            }
            // Always close files.
            bufferedReader.close();

            char[][] grid = new char[lineLength][howManyLines];
            for (int i=0; i<lines.size(); i++) {
                String tempLine = lines.get(i);
                // read initial state, there's either . # or |  as a state
                for (int j=0; j<lineLength; j++) {
                    grid[j][i] = tempLine.charAt(j);
                }
            }

            // grid initialized, go through a process but first we need a copy of the grid
            char[][] gridCopy = new char[lineLength][howManyLines];
            for (int i=0; i<howManyLines; i++) {
                // read initial state, there's either . # or |  as a state
                for (int j=0; j<lineLength; j++) {
                    gridCopy[j][i] = grid[j][i];
                }
            }
            //System.arraycopy( grid, 0, gridCopy, 0, grid.length );


            for (int i=0; i<howManyLines; i++) {
                // read initial state, there's either . # or |  as a state
                for (int j=0; j<lineLength; j++) {
                    System.out.print(grid[j][i]);
                }
                System.out.println("");
            }
            System.out.println("----------initial-----------------------");

            // open "." acre will become filled with trees if three or more adjacent acres contained trees. Otherwise, nothing happens.
            // An acre filled with trees "|" will become a lumberyard if three or more adjacent acres were lumberyards. Otherwise, nothing happens.
            // An acre containing a lumberyard "#" will remain a lumberyard if it was adjacent to at least one other lumberyard and at least one acre containing trees. Otherwise, it becomes open.
            // there are max 8 adjacent acres

            for (int rounds=0; rounds<10; rounds++) {
                for (int i = 0; i < howManyLines; i++) { // y
                    for (int j = 0; j < lineLength; j++) { // x
                        char c = grid[j][i];
                        if (c == '.') {
                            gridCopy[j][i] = processOpenGround(j, i, c, grid, howManyLines, lineLength);
                        }
                        if (c == '|') {
                            gridCopy[j][i] = processTrees(j, i, c, grid, howManyLines, lineLength);
                        }
                        if (c == '#') {
                            gridCopy[j][i] = processLumberyard(j, i, c, grid, howManyLines, lineLength);
                        }
                    }
                }
                // next state is in the gridCopy
                for (int i = 0; i < howManyLines; i++) {
                    // read initial state, there's either . # or |  as a state
                    for (int j = 0; j < lineLength; j++) {
                        grid[j][i] = gridCopy[j][i];
                    }
                }
            }

            // get score
            int wooden = 0;
            int ly = 0;
            for (int i=0; i<howManyLines; i++) {
                // read initial state, there's either . # or |  as a state
                for (int j=0; j<lineLength; j++) {
                    System.out.print(gridCopy[j][i]);
                    if (gridCopy[j][i] == '|') {
                        wooden++;
                    }
                    if (gridCopy[j][i] == '#') {
                        ly++;
                    }
                }
                System.out.println("");
            }
            System.out.println("score: " + wooden + "*" + ly + "= " + (wooden*ly) );

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

    public static char processOpenGround(int x, int y, char input, char[][] grid, int howManyLines, int lineLength) {
        // check adjacent acres i.e. +/- 1 node unless over the limits
        // if three or more trees --> |, otherwise .
        int trees = 0;
        for (int yy = -1; yy <= 1; yy++) {
            for (int xx = -1; xx <= 1; xx++) {
                if ((xx+x) == x && (yy+y) == y) {
                    continue; // You are not neighbor to yourself
                }
                if ((xx+x) >= 0 && (yy+y) >= 0 && (xx+x) < lineLength && (yy+y) < howManyLines) {
                    if (grid[xx+x][yy+y] == '|') {
                        trees++;
                    }
                }
            }
        }

        if (trees >= 3) {
            return '|';
        } else {
            return '.';
        }
    }

    public static char processTrees(int x, int y, char input, char[][] grid, int howManyLines, int lineLength) {
        int lumberyards = 0;
        for (int yy = -1; yy <= 1; yy++) {
            for (int xx = -1; xx <= 1; xx++) {
                if ((xx+x) == x && (yy+y) == y) {
                    continue; // You are not neighbor to yourself
                }
                if ((xx+x) >= 0 && (yy+y) >= 0 && (xx+x) < lineLength && (yy+y) < howManyLines) {
                    if (grid[xx+x][yy+y] == '#') {
                        lumberyards++;
                    }
                }
            }
        }
        if (lumberyards >= 3) {
            return '#';
        } else {
            return '|';
        }
    }

    public static char processLumberyard(int x, int y, char input, char[][] grid, int howManyLines, int lineLength) {
        int lumberyards = 0;
        int trees = 0;
        for (int yy = -1; yy <= 1; yy++) {
            for (int xx = -1; xx <= 1; xx++) {
                if ((xx+x) == x && (yy+y) == y) {
                    continue; // You are not neighbor to yourself
                }
                if ((xx+x) >= 0 && (yy+y) >= 0 && (xx+x) < lineLength && (yy+y) < howManyLines) {
                    if (grid[xx+x][yy+y] == '|') {
                        trees++;
                    }
                    if (grid[xx+x][yy+y] == '#') {
                        lumberyards++;
                    }
                }
            }
        }
        if (lumberyards >= 1 && trees >= 1) {
            return '#';
        } else {
            return '.';
        }
    }

}
