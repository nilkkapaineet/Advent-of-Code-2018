package com.company;

public class Main {

    public static void main(String[] args) {
	// actually 9a with linked node list
        Marble marbleCenter = new Marble(2);
        Marble marbleLeft = new Marble(0);
        Marble marbleRight = new Marble(1);
        marbleCenter.setFollowing(marbleRight);
        marbleCenter.setPrevious(marbleLeft);
        marbleLeft.setFollowing(marbleCenter);
        marbleLeft.setPrevious(marbleRight);
        marbleRight.setPrevious(marbleCenter);
        marbleRight.setFollowing(marbleLeft);
        long[] players = new long[493];
        int lastMove = 7186300;

        // rounds
        for (int i=3; i<=lastMove; i++) {
            if (i%23 != 0) {
                // move once
                marbleLeft = marbleCenter;
                marbleCenter = marbleRight;
                marbleRight = marbleRight.getFollowing();
                // insert new marble
                Marble newMarble = new Marble(i);
                newMarble.setFollowing(marbleRight);
                newMarble.setPrevious(marbleCenter);
                marbleCenter.setFollowing(newMarble);
                marbleRight.setPrevious(newMarble);
                marbleLeft = marbleCenter;
                marbleCenter = newMarble;
            } else {
                // round of points
                // move 7 marbles counter clockwise
                for (int j=0; j<7; j++) {
                    marbleRight = marbleCenter;
                    marbleCenter = marbleLeft;
                    marbleLeft = marbleLeft.getPrevious();
                }
                // remove marble
                players[i%players.length] += marbleCenter.getWorth();
                players[i%players.length] += i;
                marbleLeft.setFollowing(marbleRight);
                marbleRight.setPrevious(marbleLeft);
                marbleCenter = marbleRight;
                marbleRight = marbleRight.getFollowing();
            }
        }
        long highest = 0;
        for (long i : players) {
            if (i > highest) {
                highest = i;
            }
        }
        System.out.println(highest);
    }
}
