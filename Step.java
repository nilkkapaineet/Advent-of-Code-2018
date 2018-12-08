package com.company;

import java.util.ArrayList;
import java.util.List;

public class Step {

    private boolean processed;
    private List<Character> stepsBefore = new ArrayList<>();
    private List<Character> next2process = new ArrayList<>();
    private char letter;

    public Step () {
        this.processed = false;
    }
    public Step (char l, char n) {
        this.letter = l;
        this.next2process.add(n);
        this.processed = false;
    }
    public Step (char l) {
        this.letter = l;
        this.processed = false;
    }

    public List<Character> getNext2process() {
        return this.next2process;
    }

    public void setNext2process(char n) {
        this.next2process.add(n);
    }

    public boolean isProcessed() {
        return this.processed;
    }
    public char getLetter() {
        return this.letter;
    }
    public void processing (boolean change) {
        this.processed = change;
    }
    public void processThisFirst(Character s) {
        stepsBefore.add(s);
    }
    public List<Character> getStepsBefore() {
        return this.stepsBefore;
    }
    public boolean clear2process(List<Step> steps) {
        // go through stepsBefore and check if they are already processed
        for (char ss : getStepsBefore()) {
            for (Step s : steps) {
                if (s.getLetter() == ss) {
                    if (!s.processed) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
}
