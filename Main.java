package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int[] mouth = {0, 0};
        int[] target = {10, 725};
        int[][] erosionLevel = new int[target[0]+6][target[1]+6]; // 15*15 grid system
        int[][] geoIndex = new int[target[0]+6][target[1]+6];
        int depth = 8787;

        for (int y=0; y<target[1]+6; y++) {
            for (int x=0; x<target[0]+6; x++) {
                /*
                The region at 0,0 (the mouth of the cave) has a geologic index of 0.
                The region at the coordinates of the target has a geologic index of 0.
                If the region's Y coordinate is 0, the geologic index is its X coordinate times 16807.
                If the region's X coordinate is 0, the geologic index is its Y coordinate times 48271.
                Otherwise, the region's geologic index is the result of multiplying the erosion levels of the regions at X-1,Y and X,Y-1.
                */
                if (x == mouth[0] && y == mouth[1]) {
                    geoIndex[x][y] = 0;
                } else if (x == target[0] && y == target[1]) {
                    geoIndex[x][y] = 0;
                } else if (y == 0) {
                    geoIndex[x][y] = x*16807;
                } else if (x == 0) {
                    geoIndex[x][y] = y*48271;
                } else {
                    geoIndex[x][y] = (erosionLevel[x-1][y] * erosionLevel[x][y-1]);
                }
                erosionLevel[x][y] = (geoIndex[x][y]+depth)%20183;
            }
        }

        /*
        A region's erosion level is its geologic index plus the cave system's depth, all modulo 20183. Then:
        If the erosion level modulo 3 is 0, the region's type is rocky.
If the erosion level modulo 3 is 1, the region's type is wet.
If the erosion level modulo 3 is 2, the region's type is narrow.
         */
        // rocky as ., wet as =, narrow as |,
        int riskLevel = 0;
        for (int y=0; y<target[1]+6; y++) {
            for (int x = 0; x < target[0]+6; x++) {
                int cave = erosionLevel[x][y]%3;
                if (x == mouth[0] && y == mouth[0]) {
                    System.out.print("M");
                    if (cave == 1) {
                        riskLevel += 1;
                    } else if (cave == 2) {
                        riskLevel += 2;
                    }
                    continue;
                } else if (x == target[0] && y == target[1]) {
                    System.out.print("T");
                    if (cave == 1) {
                        riskLevel += 1;
                    } else if (cave == 2) {
                        riskLevel += 2;
                    }
                    continue;
                }
                if (cave == 0) {
                    System.out.print(".");
                } else if (cave == 1) {
                    System.out.print("=");
                    if (x<target[0]+1 && y<target[1]+1) {
                        riskLevel += 1;
                    }

                } else if (cave == 2) {
                    System.out.print("|");
                    if (x<target[0]+1 && y<target[1]+1) {
                        riskLevel += 2;
                    }
                } else {
                    System.out.print("E");
                }
            }
            System.out.println("");
        }

        System.out.println("Risk level is " + riskLevel);
        /*
        Before you go in, you should determine the risk level of the area. For the the rectangle that has
        a top-left corner of region 0,0 and a bottom-right corner of the region containing the target,
        add up the risk level of each individual region: 0 for rocky regions, 1 for wet regions, and 2 for narrow regions.
         */
    }
}
