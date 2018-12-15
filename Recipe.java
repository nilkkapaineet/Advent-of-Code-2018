package com.company;

public class Recipe {
    private Recipe following;
    private Recipe previous;
    private int score;
    public Recipe(int s) {
        this.score = s;
    }

    public Recipe() {

    }
    public int getScore() {
        return this.score;
    }

    public Recipe getFollowing() {
        return following;
    }

    public Recipe getPrevious() {
        return previous;
    }

    public void setFollowing(Recipe following) {
        this.following = following;
    }

    public void setPrevious(Recipe previous) {
        this.previous = previous;
    }

    public void setScore(int worth) {
        this.score = worth;
    }
}
