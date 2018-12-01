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

            List<Integer> freqChanges = new ArrayList();
            List<Integer> freqs = new ArrayList();

            while((line = bufferedReader.readLine()) != null) {
                int fc = Integer.parseInt(line);
                freqChanges.add(fc);
            }

            // Always close files.
            bufferedReader.close();

            int sum = 0;
            // read freqChanges, sum changes until double change found
            for (int i = 0; i < freqChanges.size(); i++) {
                int element = freqChanges.get(i);

                sum += element;

                if (freqs.contains(sum)) {
                    System.out.println("first reaches " + sum + " twice");
                    exit(0);
                } else {
                    freqs.add(sum);
                }

                // loop again if gone through
                if (i == freqChanges.size() - 1) {
                    i = -1;
                    //System.out.println("don't get out");
                }
                //System.out.println("sum is " + sum);
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