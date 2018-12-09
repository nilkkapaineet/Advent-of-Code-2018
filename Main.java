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
    public static void main(String[] args) {

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

            List<Integer> numbers = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m = p.matcher(line);
                while (m.find()) {
                    int n = Integer.parseInt(m.group());
                    // append n to list
                    numbers.add(n);
                }
            }
            // Always close files.
            bufferedReader.close();

            /*
            A header, which is always exactly two numbers:
The quantity of child nodes.
The quantity of metadata entries.
Zero or more child nodes (as specified in the header).
One or more metadata entries (as specified in the header).
             */
            // recursive system...
/*
            int[] header = new int[2];
            header[0] = numbers.get(0);
            header[1] = numbers.get(1);
            numbers.remove(0);
            numbers.remove(0);

            Node firstNode = new Node(1);
            firstNode.setNumberOfChildNodes(header[0]);
            firstNode.setNumberOfMetadataEntries(header[1]);
*/
            int totalMetadata = 0;
            totalMetadata += recMetadata(numbers, 0);

            System.out.println("total: " + totalMetadata);

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public static int recMetadata(List<Integer> numbers, int order) {
        order++;
        int tempData = 0;
        int noChildren = numbers.get(0);
     //   System.out.println(numbers.get(0));
        numbers.remove(0);
        int noMetadata = numbers.get(0);
     //   System.out.println(numbers.get(0) );
        numbers.remove(0);
        Node node = new Node(order);
        node.setNumberOfChildNodes(noChildren);
        node.setNumberOfMetadataEntries(noMetadata);

        if (noChildren == 0) {
            // carry on counting metadata
            for (int i=0; i<noMetadata; i++) {
           //     System.out.println(numbers.size() + "_" + noMetadata + "-" + i + "*" + numbers.get(0));
                node.addMetadata(numbers.get(0));
                tempData += numbers.get(0);
      //          System.out.println(numbers.get(0) + "_");
                numbers.remove(0);
            }
            //System.out.println("what" + tempData);
            return tempData;
        } else {
            // recursion for children
            for (int i=0; i<noChildren; i++) {
                tempData += recMetadata(numbers, order);
            }
        }

        for (int i=0; i<noMetadata; i++) {
            tempData += numbers.get(0);
            numbers.remove(0);
        }

        return tempData;
    }

}