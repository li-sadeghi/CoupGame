package gamelogic.players;

import gamelogic.Game;
import gamelogic.cards.Card;
import gamelogic.play.ActionType;

import java.io.IOException;
import java.util.ArrayList;

public class Paranoid extends Bot {
    public Paranoid(ArrayList<Card> cards, Game game) {
        super(cards, game);
    }

    @Override
    public void doAction() throws IOException {
        if (this.getCoins() >= 10) {
            int myTurn = this.getTurn();
            int randomNumber = Random.getRandomPlayer(myTurn, this.getGame());
            this.getGame().takeCoup(randomNumber);
        } else {
            getGame().doAction(ActionType.TAKE_INCOME);
        }

    }

    @Override
    public boolean blockAction() {
        return false;
    }


}
