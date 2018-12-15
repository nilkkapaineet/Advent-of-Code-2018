package com.company;

public class Main {

    public static void main(String[] args) {
        // actually 9a with linked node list
        Recipe firstRecipe = new Recipe(3); // current first
        Recipe secondRecipe = new Recipe(7); // current second
        firstRecipe.setFollowing(secondRecipe);
        secondRecipe.setFollowing(firstRecipe);
        int lastMove = 147061;
        Recipe veryFirstRecipe = new Recipe(); // always first to keep on track
        veryFirstRecipe = firstRecipe;
        Recipe lastRecipe = new Recipe(); // always last to keep on track
        lastRecipe = secondRecipe;

        // rounds

        for (int i=0; i<lastMove; i++) {
            //how many digits there are in the sum of recipe scores?
            int score = firstRecipe.getScore() + secondRecipe.getScore();
            if (score < 10) {
                // add one recipe
                Recipe newRecipe = new Recipe(score);
                lastRecipe.setFollowing(newRecipe);
                newRecipe.setFollowing(veryFirstRecipe);
                lastRecipe = newRecipe;
            //    System.out.println("one recipe added: " + lastRecipe.getScore() );
            } else {
                // add two recipes
                // second score would be sum%10, first score 1
                Recipe newRecipe = new Recipe(1);
                lastRecipe.setFollowing(newRecipe);
                newRecipe.setFollowing(veryFirstRecipe);
                lastRecipe = newRecipe;
          //      System.out.println("first recipe added: " + lastRecipe.getScore() );
                // first added, now the second
                Recipe newLast = new Recipe(score%10);
                lastRecipe.setFollowing(newLast);
                newLast.setFollowing(veryFirstRecipe);
                lastRecipe = newLast;
        //        System.out.println("second recipe added: " + lastRecipe.getScore() );
            }
            // move
            // first steps
            int numberOfSteps = firstRecipe.getScore()+1;
            for (int j=0; j<numberOfSteps; j++) {
                firstRecipe = firstRecipe.getFollowing();
            }
            numberOfSteps = secondRecipe.getScore()+1;
            for (int j=0; j<numberOfSteps; j++) {
                secondRecipe = secondRecipe.getFollowing();
            }
        }

        // print out next ten
        for (int i=0; i<lastMove; i++) {
            veryFirstRecipe = veryFirstRecipe.getFollowing();
        }
        for (int i=0; i<10; i++) {
            System.out.print(veryFirstRecipe.getScore());
            veryFirstRecipe = veryFirstRecipe.getFollowing();
        }

    }
}
