package com.company;
import java.io.*;
import java.util.*;

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

            int sum = 0;

            while((line = bufferedReader.readLine()) != null) {

                // more code

                line = line.replaceAll("[^0-9]+", " ");
                String[] strings = (line.trim().split(" "));
                Integer[] arr = new Integer[strings.length];

                for (int i = 0; i < strings.length; i++) {
                    arr[i] = Integer.parseInt(strings[i]);
                }

                // find smallest and biggest numbers from an array
                Arrays.sort(arr, new Comparator<Integer>()
                {
                    @Override
                    public int compare(Integer x, Integer y)
                    {
                        return x - y;
                    }
                });

                sum += arr[arr.length - 1] - arr[0];
            }

            System.out.println("Sum: " + sum);

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