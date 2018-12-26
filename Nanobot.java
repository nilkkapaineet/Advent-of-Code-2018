package com.company;

public class Nanobot {

    private int x;
    private int y;
    private int z;
    private int range;

    public Nanobot() {
    }

    public Nanobot(int[] ints) {
        this.x = ints[0];
        this.y = ints[1];
        this.z = ints[2];
        this.range = ints[3];
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getRange() {
        return range;
    }
}
