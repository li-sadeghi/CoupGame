package gamelogic.players;

import gamelogic.Game;
import gamelogic.cards.Card;
import gamelogic.cards.CardsType;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private int coins;
    private int turn;
    private ArrayList<Card> cards;
    private ArrayList<Card> deadCards = new ArrayList<>();
    private boolean isAlive;
    private Game game;

    public Player(ArrayList<Card> cards, Game game) {
        this.coins = 2;
        this.cards = cards;
        this.isAlive = true;
        this.game = game;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void decreaseCoins(int coins) {
        this.setCoins(this.coins - coins);
    }

    public void increaseCoins(int coins) {
        this.setCoins(this.coins + coins);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ArrayList<Card> getDeadCards() {
        return deadCards;
    }

    public void setDeadCards(ArrayList<Card> deadCards) {
        this.deadCards = deadCards;
    }

    public void killCard(int index) {
        Card card = this.cards.get(index);
        this.cards.remove(index);
        this.deadCards.add(card);
        if (cards.size() == 0) isAlive = false;
    }

    public boolean hasCard(CardsType cardType) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (cards.get(i).getCardsType() == cardType) return true;
        }
        return false;
    }

    public void changeCard(CardsType cardType) {
        int index = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (cards.get(i).getCardsType() == cardType) index = i;
        }
        Card card = cards.get(index);
        this.cards.remove(index);
        ArrayList<Card> gameCards = game.getRemainCards();
        Collections.shuffle(gameCards);
        this.cards.add(gameCards.get(0));
        gameCards.remove(0);
        gameCards.add(card);
        game.setRemainCards(gameCards);
    }

}
