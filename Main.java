package com.company;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
	// write your code here

        // grid full of fuel cells
        int serialNumber = 6392;
        int[][] grid = new int[300][300];
        for (int y=0; y<300; y++) {
            for (int x=0; x<300; x++) {
                int rackID = x+10;
                int powerLevel = rackID*y;
                powerLevel += serialNumber;
                powerLevel = powerLevel*rackID;
                powerLevel = (powerLevel/100)%10;
                powerLevel -= 5;
                grid[x][y] = powerLevel;
            }
        }

        // find greatest 3*3 region
        int greatest = -10000;
        int[] greatestPosition = new int[2]; // x y
        for (int y=0; y<297; y++) {
            for (int x=0; x<297; x++) {
                int sum = grid[x][y];
                sum += grid[x+1][y];
                sum += grid[x+2][y];
                sum += grid[x][y+1];
                sum += grid[x+1][y+1];
                sum += grid[x+2][y+1];
                sum += grid[x][y+2];
                sum += grid[x+1][y+2];
                sum += grid[x+2][y+2];
                if (sum > greatest) {
                    greatest = sum;
                    greatestPosition[0] = x;
                    greatestPosition[1] = y;
                }
            }
        }

        System.out.println(greatestPosition[0] + ", " + greatestPosition[1] + " with total power of " + greatest);
    }
}
