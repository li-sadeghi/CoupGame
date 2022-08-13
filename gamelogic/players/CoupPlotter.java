package gamelogic.players;

import gamelogic.Game;
import gamelogic.cards.Card;
import gamelogic.play.ActionType;

import java.io.IOException;
import java.util.ArrayList;

public class CoupPlotter extends Bot {
    public CoupPlotter(ArrayList<Card> cards, Game game) {
        super(cards, game);
    }

    @Override
    public void doAction() throws IOException {
        if (this.getCoins() < 7) this.getGame().doAction(ActionType.TAKE_TAX);
        else {
            int myTurn = this.getTurn();
            int randomNumber = Random.getRandomPlayer(myTurn, this.getGame());
            this.getGame().takeCoup(randomNumber);
        }
    }

    @Override
    public boolean blockAction() {
        return false;
    }


}
