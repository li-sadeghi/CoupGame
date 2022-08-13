package gamelogic.players;

import gamelogic.Game;
import gamelogic.cards.Card;

import java.util.ArrayList;

public class Human extends Player {
    public Human(ArrayList<Card> cards, Game game) {
        super(cards, game);
    }

    public void burnCard(int index) {
        if (this.getCards().size() == 1) {
            this.setAlive(false);
            this.getCards().remove(0);
            return;
        }
        this.getCards().remove(index - 1);
    }


    public void killingCard() {
        getGame().getGamePageController().killingCard();
    }
}
