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

            List<FixedPoint> nanobots = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                Pattern p = Pattern.compile("-?[0-9]+");
                Matcher m = p.matcher(line);
                int[] n = new int[4];
                int i = 0;
                while (m.find()) {
                    n[i] = Integer.parseInt(m.group());
                    i++;
                }
                FixedPoint nanobot = new FixedPoint(n);
                nanobots.add(nanobot);
            }
            // Always close files.
            bufferedReader.close();

            // go through each fixedpoint and find following constellations
            int constellations = 0;

            List<FixedPoint> prospects = new ArrayList<>();

            while (!nanobots.isEmpty() ) {
                constellations++;

                FixedPoint fp = nanobots.get(0);
                nanobots.remove(0);
                fp.addConstellation(constellations);

                // go through rest of the list
                // put those fixedpoints that are close enough into prospects
                for (int i=0; i<nanobots.size(); i++) {
                    FixedPoint next = nanobots.get(i);
                    // check manhattan distance
                    int md = Math.abs(fp.getX() - next.getX()) + Math.abs(fp.getY() - next.getY()) + Math.abs(fp.getZ() - next.getZ()) + Math.abs(fp.getTime() - next.getTime());
                    if (md <= 3) {
                        // if next fixed point is no more than 3 units away fp, it belongs into same constellation
                        // but it must go into prospects because prospects may have more fixedpoints within range as well
                        next.addConstellation(constellations);
                        prospects.add(next);
                        nanobots.remove(i);
                        i--;
                    }
                }
                while (!prospects.isEmpty()) {
                    // now with the prospects go through the list of rest fixedpoints
                    // also the following fixedpoints belong in the same constellation
                    FixedPoint next = prospects.get(0);
                    prospects.remove(0);
                    for (int j=0; j<nanobots.size(); j++) {
                        FixedPoint temp = nanobots.get(j);
                        int md = Math.abs(temp.getX() - next.getX()) + Math.abs(temp.getY() - next.getY()) + Math.abs(temp.getZ() - next.getZ()) + Math.abs(temp.getTime() - next.getTime());
                        if (md <= 3) {
                            // if next fixed point is no more than 3 units away fp, it belongs into same constellation
                            // but it must go into prospects because prospects may have more fixedpoints within range as well
                            temp.addConstellation(constellations);
                            prospects.add(temp);
                            nanobots.remove(j);
                            j--;
                        }
                    }
                }

            }

            System.out.println("There are " + constellations + " constellations");

            /*
            // find strongest nanobot i.e. greatest range
            FixedPoint nanobot = new FixedPoint();
            int range = 0;
            for (FixedPoint n : nanobots) {
                if (n.getRange() > range) {
                    nanobot = n;
                    range = n.getRange();
                }
            }
            System.out.println("Strongest nanobot is a nanobot with range of " + nanobot.getRange() );

            // find nanobots within range
            int withinRange = 0;
            for (FixedPoint n : nanobots) {
                if (isWithinRange(withinRange, nanobot, n) ) {
                    withinRange++;
                }
            }

            System.out.println("There are " + withinRange + " within range.");
            */


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

    public static boolean isWithinRange(int w, FixedPoint range, FixedPoint n) {
        boolean withinRange = false;
        int manhattanDistance = Math.abs(range.getX() - n.getX()) + Math.abs(range.getY() - n.getY()) + Math.abs(range.getZ() - n.getZ());
        if (manhattanDistance <= range.getTime() ) {
            withinRange = true;
        }
//        System.out.println(w + ": " + manhattanDistance + " " + range.getRange());
        return withinRange;
    }
}