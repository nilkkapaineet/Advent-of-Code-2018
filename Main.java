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

            List<Nanobot> nanobots = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(line);
                int[] n = new int[4];
                int i = 0;
                while (m.find()) {
                    n[i] = Integer.parseInt(m.group());
                    i++;
                }
                Nanobot nanobot = new Nanobot(n);
                nanobots.add(nanobot);
            }
            // Always close files.
            bufferedReader.close();

            // find strongest nanobot i.e. greatest range
            Nanobot nanobot = new Nanobot();
            int range = 0;
            for (Nanobot n : nanobots) {
                if (n.getRange() > range) {
                    nanobot = n;
                    range = n.getRange();
                }
            }
            System.out.println("Strongest nanobot is a nanobot with range of " + nanobot.getRange() );

            // find nanobots within range
            int withinRange = 0;
            for (Nanobot n : nanobots) {
                if (isWithinRange(withinRange, nanobot, n) ) {
                    withinRange++;
                }
            }

            System.out.println("There are " + withinRange + " within range.");

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

    public static boolean isWithinRange(int w, Nanobot range, Nanobot n) {
        boolean withinRange = false;
        int manhattanDistance = Math.abs(range.getX() - n.getX()) + Math.abs(range.getY() - n.getY()) + Math.abs(range.getZ() - n.getZ());
        if (manhattanDistance <= range.getRange() ) {
            withinRange = true;
        }
//        System.out.println(w + ": " + manhattanDistance + " " + range.getRange());
        return withinRange;
    }
}