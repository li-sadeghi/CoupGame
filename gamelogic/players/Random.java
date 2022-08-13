package gamelogic.players;

import gamelogic.Game;

public class Random {
    public static int getRandomPlayer(int myTurn, Game game) {
        java.util.Random random = new java.util.Random();
        int randomNumber = myTurn;
        while (randomNumber == myTurn || !game.getPlayer(randomNumber).isAlive()) {
            randomNumber = random.nextInt();
            if (randomNumber < 0) randomNumber *= -1;
            randomNumber = (randomNumber % 4) + 1;
        }
        return randomNumber;
    }

    public static int getPositiveRandom() {
        java.util.Random random = new java.util.Random();
        int randomNumber = random.nextInt();
        if (randomNumber < 0) randomNumber *= -1;
        return randomNumber;
    }
}
