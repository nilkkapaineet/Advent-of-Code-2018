package com.company;

public class Marble {
    private Marble previous;
    private Marble following;
    private int worth;
    public Marble(int w) {
        this.worth = w;
    }

    public int getWorth() {
        return worth;
    }

    public Marble getFollowing() {
        return following;
    }

    public Marble getPrevious() {
        return previous;
    }

    public void setFollowing(Marble following) {
        this.following = following;
    }

    public void setPrevious(Marble previous) {
        this.previous = previous;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }
}
