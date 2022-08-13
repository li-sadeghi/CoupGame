package gamelogic;

import java.util.ArrayList;

public class GameInformations {
    private ArrayList<String> botPlayers;
    private ArrayList<String> player1Cards;
    private ArrayList<String> player2Cards;
    private ArrayList<String> player3Cards;
    private ArrayList<String> player4Cards;

    public GameInformations() {
        botPlayers = new ArrayList<>();
        player1Cards = new ArrayList<>();
        player2Cards = new ArrayList<>();
        player3Cards = new ArrayList<>();
        player4Cards = new ArrayList<>();
    }

    public ArrayList<String> getBotPlayers() {
        return botPlayers;
    }

    public void setBotPlayers(ArrayList<String> botPlayers) {
        this.botPlayers = botPlayers;
    }

    public ArrayList<String> getPlayer1Cards() {
        return player1Cards;
    }

    public void setPlayer1Cards(ArrayList<String> player1Cards) {
        this.player1Cards = player1Cards;
    }

    public ArrayList<String> getPlayer2Cards() {
        return player2Cards;
    }

    public void setPlayer2Cards(ArrayList<String> player2Cards) {
        this.player2Cards = player2Cards;
    }

    public ArrayList<String> getPlayer3Cards() {
        return player3Cards;
    }

    public void setPlayer3Cards(ArrayList<String> player3Cards) {
        this.player3Cards = player3Cards;
    }

    public ArrayList<String> getPlayer4Cards() {
        return player4Cards;
    }

    public void setPlayer4Cards(ArrayList<String> player4Cards) {
        this.player4Cards = player4Cards;
    }

}
