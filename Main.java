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

            List<Step> steps = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
                char first = line.charAt(5);
                char second = line.charAt(36);
                // form step class, check if it exist already
                boolean firstFound = false;
                boolean secondFound = false;
                for (Step s : steps) {
                    if (s.getLetter() == first) {
                        // first step found
                        firstFound = true;
                        s.setNext2process(second);
                    }
                    if (s.getLetter() == second) {
                        // Step already exists, add first step to be processed first
                        s.processThisFirst(first);
                        secondFound = true;
                    }
                }
                if (!firstFound) {
                    Step step = new Step(first, second);
                    steps.add(step);
                }
                if (!secondFound) {
                    Step step = new Step(second);
                    step.processThisFirst(first);
                    steps.add(step);
                }
                // steps are now in the list
            }
            // Always close files.
            bufferedReader.close();

            // recursive search to find the order of steps
            // go through list of steps and find one that hasn't prerequisities
            // there could be many available steps in the beginning as well
            List<Character> availableSteps = new ArrayList<>();
            for (Step s : steps) {
                if (s.getStepsBefore().isEmpty() ) {
                    s.processing(true);
                    availableSteps.add(s.getLetter() );
                }
            }
            availableSteps.sort(Comparator.naturalOrder());
            char c = availableSteps.get(0);
            System.out.print(c);
            availableSteps.remove(0);

            Step startStep = new Step();
            for (Step s : steps) {
                if (s.getLetter() == c) {
                    startStep = s;
                }
            }
            recSearch(startStep, steps, 1, availableSteps);

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

    public static void recSearch(Step s, List<Step> steps, int processLevel, List<Character> availableSteps) {
        ++processLevel;

        // released steps added to list of availableSteps if they're really released
        List<Character> nextOnes = s.getNext2process();
        nextOnes.sort(Comparator.naturalOrder());

        // check if nextones are available to be processed
        for (char no : nextOnes) {
            for (Step ns : steps) {
                if (no == ns.getLetter()) {
                    if (ns.clear2process(steps) ) {
                        // don't add if already found
                        boolean addFound = false;
                        for (char da : availableSteps) {
                            if (da == ns.getLetter() ) {
                                addFound = true;
                            }
                        }
                        if (!addFound) {
                            availableSteps.add(ns.getLetter());
                        }
                    }
                }
            }
        }

        // if there's stuff in availableSteps, print them first
        // possible nextones added to available steps
        availableSteps.sort(Comparator.naturalOrder());

        if (!availableSteps.isEmpty() ){
            outerloop:
            for (char avs : availableSteps) {
                for (Step sav : steps) {
                    if (sav.getLetter() == avs) {
                        availableSteps.remove(0);
                        sav.processing(true);
                        System.out.print(sav.getLetter() );
                        recSearch(sav, steps, processLevel, availableSteps);
                        break outerloop;
                    }
                }
            }
        }
    }
}
