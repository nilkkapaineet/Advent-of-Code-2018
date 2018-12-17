package com.company;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;
import static java.lang.System.out;

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

            HashMap<String, String> hmap = new HashMap<String, String>();
            int lines = 0;
            String initialState = ".....";
            while((line = bufferedReader.readLine()) != null) {

                if (lines == 0) {
                    // initial state from char 15
                    initialState = initialState.concat(line.substring(15) );
                    initialState = initialState.concat("........................");
             //       System.out.println(initialState);
                }

                if (lines > 1) {
                    // rules here
                    String input = line.substring(0, 5);
                    String output = line.substring(line.length()-1 );
  //                  System.out.println(input + " -> " + output);
                    hmap.put(input, output);
                }
                lines++;
            }
            // Always close files.
            bufferedReader.close();

           System.out.println("0: " + initialState);
            // do the growing
            for (int i=1; i<21; i++) {
                // get a char by char from initial state beginning from char 3
                // char is an input, output is checked from hmap
                // if no match found, give .
                System.out.print(i + ": ..");
                String nextRound = "..";
                for (int j=0; j<initialState.length()-4; j++) {
                    String c = initialState.substring(j, j + 5);
//                    System.out.println(c);
                    // search from hmap
                    String output = ".";
                    if (hmap.containsKey(c)) {
                        output = hmap.get(c);
                    }
                    System.out.print(output);
                    nextRound = nextRound.concat(output);
                }
                initialState = nextRound;
                initialState = initialState.concat("..");
                System.out.println("");
          //      break;
            }

            // each pot produce points in respect to its position
            // positions start from -5
            int score = 0;
            for (int i=0; i<initialState.length(); i++) {
                if (initialState.charAt(i) == '#') {
                    score += (i-5);
                }
            }
            System.out.println("score is: " + score);

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
