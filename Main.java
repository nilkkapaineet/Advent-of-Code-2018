package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here

        List<Integer> circle = new ArrayList<>();
        circle.add(0);
        circle.add(1);
        int[] players = new int[493];
        int lastMove = 71863;
        int currentPosition = 1;

        for (int i=2; i<=lastMove; i++) {
            if (i%23 != 0) {
                if (currentPosition+2 <= circle.size() ) {
                    circle.add(currentPosition+2, i);
                    currentPosition += 2;
                } else {
                    circle.add(1, i);
                    currentPosition = 1;
                }
            } else {
                // round of points
                if (currentPosition-7 >= 0) {
                    currentPosition -= 7;
                    players[i%players.length] += i;
                    players[i%players.length] += circle.get(currentPosition);
                    circle.remove(currentPosition);
                } else {
                    // current position goes round to the back of the circle
                    int temp = 7-currentPosition;
                    temp = circle.size()-temp;
                    currentPosition = temp;
                    players[i%players.length] += i;
                    players[i%players.length] += circle.get(currentPosition);
                    circle.remove(currentPosition);
                }
            }
        }

        int highest = 0;
        for (int i : players) {
            if (i > highest) {
                highest = i;
            }
        }
        System.out.println(highest);
    }
}
