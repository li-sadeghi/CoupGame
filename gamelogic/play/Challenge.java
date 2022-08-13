package gamelogic.play;

import gamelogic.Game;
import gamelogic.cards.CardsType;
import gamelogic.players.Player;

public class Challenge {
    public static int runChallengeReturnLoser(int playerInChallenge, int otherPlayer, CardsType cardType, Game game) {
        Player player1 = game.getPlayer(playerInChallenge);
        Player player2 = game.getPlayer(otherPlayer);
        if (player1.hasCard(cardType)) return otherPlayer;
        else return playerInChallenge;
    }
}
