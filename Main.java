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
        List<PointOfLight> pols = new ArrayList<>();
        int lowestX = 0;
        int lowestY = 0;
        int highestX = 0;
        int highestY = 0;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            List<String> lines = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                Pattern p = Pattern.compile("(-?[0-9]+)");
                Matcher m = p.matcher(line);
                lines.add(line);
                int numbers[] = new int[4];
                int i=0;
                while (m.find()) {
                    int n = Integer.parseInt(m.group());
                    // append n to list
                    numbers[i] = n;
                    i++;
                }
                if (numbers[0] < lowestX) {
                    lowestX = numbers[0];
                }
                if (numbers[1] < lowestY) {
                    lowestY = numbers[1];
                }
                if (numbers[0] > highestX) {
                    highestX = numbers[0];
                }
                if (numbers[1] > highestY) {
                    highestY = numbers[1];
                }
            }
            // Always close files.
            bufferedReader.close();

            // not so elegant solution to have another round
            for (String line2: lines) {
                Pattern p = Pattern.compile("(-?[0-9]+)");
                Matcher m = p.matcher(line2);
                int numbers[] = new int[4];
                int i=0;
                while (m.find()) {
                    int n = Integer.parseInt(m.group());
                    // append n to list
                    numbers[i] = n;
                    i++;
                }
                // init Point Of Light
                // make coordinate transformation first
                numbers = transformCoordinates(numbers, highestX, highestY, lowestX, lowestY);
                PointOfLight pol = new PointOfLight(numbers);
                pols.add(pol);

                //System.out.println(numbers[0] + " " + numbers[1] + " " + numbers[2] + " " + numbers[3]);
            }
            // Always close files.
            bufferedReader.close();

       //     System.out.println("hx: " + highestX + " hy: " + highestY + " lx: " + lowestX + " ly: " + lowestY + " size: " + pols.size() );

            Collections.sort(pols, Comparator.comparingInt(PointOfLight ::getY));

            boolean aligned = false;
            while (true) {
          //      System.out.println("sec " + i);
                int[] limits = limits(pols);
                if (limits[3] - limits[1] <= 8) {
                   // System.out.println("x: " + limits[0] + " -> " + limits[3] + " y: " + limits[1] + " -> " + limits[3]);
                    printSky(pols, (highestY - lowestY), highestX, limits[0], limits[1], limits[3]);
                    if (limits[3] - limits[1] <= 8) {
                    //    System.out.println(" jep ");
                    }
                    exit (0);
                }
                aligned = permutation(pols);
            }

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

    public static int[] limits(List<PointOfLight> pols) {
        int lowestXx = 1000;
        int lowestYy = 1000;
        int highestXx = -1000;
        int highestYy = -1000;
        for (PointOfLight p : pols) {
                if (p.getX() < lowestXx) {
                    lowestXx = p.getX();
                }
                if (p.getY() < lowestYy) {
                    lowestYy = p.getY();
                }
                if (p.getX() > highestXx) {
                    highestXx = p.getX();
                }
                if (p.getY() > highestYy) {
                    highestYy = p.getY();
                }
        }
        int[] retVal = new int[4];
        retVal[0] = lowestXx;
        retVal[1] = lowestYy;
        retVal[2] = highestXx;
        retVal[3] = highestYy;

        return retVal;
    }

    public static boolean permutation(List<PointOfLight> pols) {
        // permutate point coordinates according to their velocity
        boolean retValue = false;
        int lowestXx = 1000;
        int lowestYy = 1000;
        int highestXx = -1000;
        int highestYy = -1000;
        for (PointOfLight p : pols) {
            p.setX(p.getX()+p.getVx());
            p.setY(p.getY()+p.getVy());
            if (p.getY() <= 4 || p.getY() >= 11) {
           //     retValue = true;
             //   System.out.println(" dad a");
               // break;
            }
            if (p.getX() < lowestXx) {
                lowestXx = p.getX();
            }
            if (p.getY() < lowestYy) {
                lowestYy = p.getY();
            }
            if (p.getX() > highestXx) {
                highestXx = p.getX();
            }
            if (p.getY() > highestYy) {
                highestYy = p.getY();
            }
        }
        if (highestYy-lowestYy < 8) {
           retValue = true;
        }
        return retValue;
    }

    public static void printSky(List<PointOfLight> pols, int lines, int hx, int lx, int ly, int hy) {
        for (int y=ly; y<hy+1; y++) {
            // take all same y pols out of list and print them
            List<Integer> xs = new ArrayList<>();
            for (PointOfLight ps : pols) {
                if (ps.getY() == y) {
                    xs.add(ps.getX() );
                }
            }
//            PointOfLight pol = pols.get(y);
            for (int x=lx; x<(hx+1); x++) {
                // check for list if there's point of light x-position
                boolean found = false;
                for (int xxx : xs) {
                    if (xxx == x) {
                        found =true;
                    }
                }
                if (found) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
    }

    public static List<PointOfLight> organizePOLs(List<PointOfLight> pols) {
        // organize list so that second argument is at the top
        return pols;
    }

    public static int[] transformCoordinates(int[] xy, int hx, int hy, int lx, int ly) {
        // 0 and 1 have x and y
        // negative coordinates must be transformed to positive for easier display purposes
        xy[0] = xy[0]-lx;
        xy[1] = xy[1]-ly;
        return xy;
    }
}