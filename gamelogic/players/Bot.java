package gamelogic.players;

import gamelogic.Game;
import gamelogic.cards.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class Bot extends Player {
    public Bot(ArrayList<Card> cards, Game game) {
        super(cards, game);
    }

    public void burnCard() {
        if (this.getCards().size() == 1) {
            this.setAlive(false);
            this.getCards().remove(0);
            return;
        }
        Random random = new Random();
        int randomInt = random.nextInt();
        int index = randomInt % 2;
        this.getCards().remove(index);
    }

    public abstract void doAction() throws IOException, InterruptedException;

    public abstract boolean blockAction();
//    public abstract void challenge();
}
