package com.company;
import java.io.*;
import java.util.*;

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

            List<String> inputStrings = new ArrayList<>();
            // handle input line char by char
            while((line = bufferedReader.readLine()) != null) {
                inputStrings.add(line);
            }

            // Always close files.
            bufferedReader.close();

            // iterate through list of inputlines
            for (int i=0; i<inputStrings.size(); i++) {
                int differ = 0;
                int differingChar = 0;
                // compare inputs character by character
                String input1 = inputStrings.get(i);
                for (int j=i+1; j<inputStrings.size(); j++) {
                    String input2 = inputStrings.get(j);
                    // do they differ?
                    for (int index = 0; index < input1.length(); index++) {
                        if (input1.charAt(index) != input2.charAt(index)) {
                            differ += 1;
                            differingChar = index;
                        }
                        if (differ > 1) {
                            // fail
                            differ = 0;
                            break;
                        }
                    }
                    if (differ == 1) {
                        // remove differing character
                        String result = input1.substring(0, differingChar) + input1.substring(differingChar + 1);
                        System.out.println("Found: " + result);
                        exit(0);
                    }
                }
            }
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