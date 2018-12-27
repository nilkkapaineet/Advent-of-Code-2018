package com.company;

import java.util.ArrayList;
import java.util.List;

public class FixedPoint {

    private int x;
    private int y;
    private int z;
    private int time;
    private List<Integer> constellations = new ArrayList<>();

    public FixedPoint() {
    }

    public FixedPoint(int[] ints) {
        this.x = ints[0];
        this.y = ints[1];
        this.z = ints[2];
        this.time = ints[3];
    }

    public void addConstellation(int n) {
        constellations.add(n);
    }

    public List<Integer> getConstellations() {
        return constellations;
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

    public int getTime() {
        return time;
    }
}
