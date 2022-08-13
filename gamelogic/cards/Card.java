package gamelogic.cards;

public class Card {
    private CardsType cardsType;
    private boolean isBurnt;

    public Card() {
        setBurnt(false);
    }

    public CardsType getCardsType() {
        return cardsType;
    }

    public void setCardsType(CardsType cardsType) {
        this.cardsType = cardsType;
    }

    public boolean isBurnt() {
        return isBurnt;
    }

    public void setBurnt(boolean burnt) {
        isBurnt = burnt;
    }
}
