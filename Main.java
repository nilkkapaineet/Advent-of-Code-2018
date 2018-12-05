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

            String finalString = "";
            int veryLong = 1000000000;
            while((line = bufferedReader.readLine()) != null) {

                String modLine = "";
                // remove all instances of letters alphabetically
                for(char alphabet = 'a'; alphabet <='z'; alphabet++ ) {
                    char upper = Character.toUpperCase(alphabet);
                    String stringValueOf = String.valueOf(alphabet);
                    String UpperStringValueOf = String.valueOf(upper);
                    modLine = line.replaceAll(stringValueOf, "");
                    modLine = modLine.replaceAll(UpperStringValueOf, "");
//                    System.out.println("modline: " + modLine);

                    for (int i = 0; i < modLine.length() - 1; i++) {
                        char first = modLine.charAt(i);
                        char second = modLine.charAt(i + 1);

                        if (Character.toLowerCase(first) == Character.toLowerCase(second)) {
                            // remove chars if different case
                            if ((Character.isUpperCase(first) && Character.isLowerCase(second)) || (Character.isLowerCase(first) && Character.isUpperCase(second))) {
                                // remove
                                modLine = modLine.substring(0, i) + modLine.substring(i + 2);
                                i -= 2;
                                if (i < 0) {
                                    i = -1;
                                }
                            }
                        }
                    }
  //                  System.out.println(modLine.length() + " units mod " + modLine);
                    if (modLine.length() < veryLong) {
                        finalString = modLine;
                        veryLong = modLine.length();
                    }
                }
                System.out.println(finalString.length() + " units");
            }
            // Always close files.
            bufferedReader.close();

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
