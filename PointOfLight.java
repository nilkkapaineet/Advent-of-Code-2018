package com.company;

public class PointOfLight {

    private int x;
    private int y;
    private int vx;
    private int vy;

    public PointOfLight(int[] pol) {
        this.x = pol[0];
        this.y = pol[1];
        this.vx = pol[2];
        this.vy = pol[3];
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
