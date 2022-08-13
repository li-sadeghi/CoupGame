package gamelogic.players;

import gamelogic.Game;
import gamelogic.cards.Card;
import gamelogic.cards.CardsType;
import gamelogic.play.ActionType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class CautiousKiller extends Bot {
    public int playerForAssassinate;

    public CautiousKiller(ArrayList<Card> cards, Game game) {
        super(cards, game);
    }

    @Override
    public void doAction() throws IOException, InterruptedException {

        if (this.getCoins() >= 10) {
            int myTurn = this.getTurn();
            int randomNumber = Random.getRandomPlayer(myTurn, this.getGame());
            this.getGame().takeCoup(randomNumber);
        } else {
            if (this.hasCard(CardsType.MURDERER) && getCoins() > 2) {
                getGame().setLastActionPlayed(ActionType.TAKE_ASSASSINATE);
                decreaseCoins(3);
                getGame().increaseCoins(3);
                setPlayerForAssassinate();
                getGame().addEvent("Player" + getTurn() + " assassinate on player" + playerForAssassinate);
                getGame().getGamePageController().setGamePage();
                Player thisPlayer = this;
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (getGame().isBlockedAction()) {
                            return;
                        } else {
                            getGame().action(thisPlayer, ActionType.TAKE_ASSASSINATE);
                            getGame().setBlockedAction(false);
                        }
                    }
                }));
                timeline.setCycleCount(1);
                blockingAction(timeline);
            } else if (!hasCard(CardsType.MURDERER)) {
                if (this.hasCard(CardsType.AMBASSADOR)) {
                    getGame().doAction(ActionType.TAKE_EXCHANGE);
                } else {
                    if (this.getCoins() > 0) {
                        getGame().doAction(ActionType.TAKE_NEW_EXCHANGE);
                    } else {
                        getGame().setLastActionPlayed(ActionType.TAKE_FOREIGN_AID);
                        Player thisPlayer = this;
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (getGame().isBlockedAction()) {
                                    return;
                                } else {
                                    getGame().action(thisPlayer, ActionType.TAKE_FOREIGN_AID);
                                    getGame().setBlockedAction(false);
                                }
                            }
                        }));
                        timeline.setCycleCount(1);
                        blockingAction(timeline);
                    }
                }
            } else {
                getGame().doAction(ActionType.TAKE_INCOME);
            }
        }
    }

    @Override
    public boolean blockAction() {
        return false;
    }

    public void blockingAction(Timeline timeline) throws InterruptedException {
        getGame().getGamePageController().getBlockingLabel().setVisible(true);
        getGame().getGamePageController().getBlockingField().setVisible(true);
        getGame().getGamePageController().getBlockButton().setVisible(true);
        timeline.playFromStart();
    }

    public void setPlayerForAssassinate() {
        int randomPlayer = Random.getRandomPlayer(getTurn(), getGame());
        this.playerForAssassinate = randomPlayer;
    }
}
