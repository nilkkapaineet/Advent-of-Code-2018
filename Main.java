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

            while((line = bufferedReader.readLine()) != null) {

                for (int i=0; i<line.length()-1; i++) {
                    char first = line.charAt(i);
                    char second = line.charAt(i+1);

                    if (Character.toLowerCase(first) == Character.toLowerCase(second) ) {
                        // remove chars if different case
                        if ( (Character.isUpperCase(first) && Character.isLowerCase(second) )  || (Character.isLowerCase(first) && Character.isUpperCase(second) ) ){
                            // remove
                            line = line.substring(0, i) + line.substring(i+2);
                            i -= 2;
                            if (i<0) {
                                i = -1;
                            }
                        }
                    }
                }
                System.out.println(line.length() + " units");
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
