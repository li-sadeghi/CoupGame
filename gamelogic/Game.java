package gamelogic;

import controllers.GamePageController;
import gamelogic.cards.Card;
import gamelogic.cards.CardsType;
import gamelogic.play.ActionType;
import gamelogic.play.Challenge;
import gamelogic.players.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;

public class Game {
    private int turn;
    private int remainingCoins = 42;
    private int remainingCards = 7;
    private Human player1;
    private Bot player2;
    private Bot player3;
    private Bot player4;
    private ArrayList<Card> player1Cards = new ArrayList<>();
    private ArrayList<Card> player2Cards = new ArrayList<>();
    private ArrayList<Card> player3Cards = new ArrayList<>();
    private ArrayList<Card> player4Cards = new ArrayList<>();
    private ArrayList<Card> remainCards = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private boolean isChallengeTime = false;
    private final Semaphore semaphore = new Semaphore(1);
    private CardsType lastCardPlayed;
    private ActionType lastActionPlayed;
    int numberOfChallenges = 0;
    String events = "";
    private GamePageController gamePageController;
    private boolean isChallengeWasSuccessful = false;
    private boolean isBlockedAction = false;
    private boolean isBlockingTime = false;


    public Game(ArrayList<String> bots, ArrayList<String> player1Cards,
                ArrayList<String> player2Cards,
                ArrayList<String> player3Cards,
                ArrayList<String> player4Cards) {

        this.turn = 1;

        this.setPlayer1Cards(getCards(player1Cards));
        this.setPlayer2Cards(getCards(player2Cards));
        this.setPlayer3Cards(getCards(player3Cards));
        this.setPlayer4Cards(getCards(player4Cards));

        this.player1 = new Human(this.player1Cards, this);

        for (int i = 0; i < bots.size(); i++) {
            switch (bots.get(i)) {
                case "CautiousKiller":
                    if (i == 0) this.setPlayer2(new CautiousKiller(this.player2Cards, this));
                    else if (i == 1) this.setPlayer3(new CautiousKiller(this.player3Cards, this));
                    else this.setPlayer4(new CautiousKiller(this.player4Cards, this));
                    break;
                case "CoupPlotter":
                    if (i == 0) this.setPlayer2(new CoupPlotter(this.player2Cards, this));
                    else if (i == 1) this.setPlayer3(new CoupPlotter(this.player3Cards, this));
                    else this.setPlayer4(new CoupPlotter(this.player4Cards, this));
                    break;
                case "MyBot":
                    if (i == 0) this.setPlayer2(new MyBot(this.player2Cards, this));
                    else if (i == 1) this.setPlayer3(new MyBot(this.player3Cards, this));
                    else this.setPlayer4(new MyBot(this.player4Cards, this));
                    break;
                case "Paranoid":
                    if (i == 0) this.setPlayer2(new Paranoid(this.player2Cards, this));
                    else if (i == 1) this.setPlayer3(new Paranoid(this.player3Cards, this));
                    else this.setPlayer4(new Paranoid(this.player4Cards, this));
                    break;
                default:
                    break;
            }
        }
        this.players.add(player1);
        this.players.add(player2);
        this.players.add(player3);
        this.players.add(player4);
        this.setRemainCards();

    }


    private ArrayList<Card> getCards(ArrayList<String> cards) {
        ArrayList<Card> cardsOfPlayer = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card card = new Card();
            switch (cards.get(i)) {
                case "Ambassador":
                    card.setCardsType(CardsType.AMBASSADOR);
                    break;
                case "Rich":
                    card.setCardsType(CardsType.RICH);
                    break;
                case "Princess":
                    card.setCardsType(CardsType.PRINCESS);
                    break;
                case "Murderer":
                    card.setCardsType(CardsType.MURDERER);
                    break;
                case "Commander":
                    card.setCardsType(CardsType.COMMANDER);
                    break;
                default:
                    break;
            }
            cardsOfPlayer.add(card);
        }
        return cardsOfPlayer;
    }


    public ArrayList<Card> getPlayer1Cards() {
        return player1Cards;
    }

    public void setPlayer1Cards(ArrayList<Card> player1Cards) {
        this.player1Cards = player1Cards;
    }

    public ArrayList<Card> getPlayer2Cards() {
        return player2Cards;
    }

    public void setPlayer2Cards(ArrayList<Card> player2Cards) {
        this.player2Cards = player2Cards;
    }

    public ArrayList<Card> getPlayer3Cards() {
        return player3Cards;
    }

    public void setPlayer3Cards(ArrayList<Card> player3Cards) {
        this.player3Cards = player3Cards;
    }

    public ArrayList<Card> getPlayer4Cards() {
        return player4Cards;
    }

    public void setPlayer4Cards(ArrayList<Card> player4Cards) {
        this.player4Cards = player4Cards;
    }

    public void changeTurn() {
        if (this.turn == 4) this.turn = 1;
        else this.turn = turn + 1;
        if (!getCurrentPlayer().isAlive()) changeTurn();
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getRemainingCoins() {
        return remainingCoins;
    }

    public void setRemainingCoins(int remainingCoins) {
        this.remainingCoins = remainingCoins;
    }

    public int getRemainingCards() {
        return remainingCards;
    }

    public void setRemainingCards(int remainingCards) {
        this.remainingCards = remainingCards;
    }

    public Human getPlayer1() {
        return player1;
    }

    public void setPlayer1(Human player1) {
        this.player1 = player1;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else if (i == 3) return player3;
        else return player4;
    }

    public Bot getPlayer2() {
        return player2;
    }

    public void setPlayer2(Bot player2) {
        this.player2 = player2;
    }

    public Bot getPlayer3() {
        return player3;
    }

    public void setPlayer3(Bot player3) {
        this.player3 = player3;
    }

    public Bot getPlayer4() {
        return player4;
    }

    public boolean isChallengeTime() {
        return isChallengeTime;
    }

    public void setChallengeTime(boolean challengeTime) {
        isChallengeTime = challengeTime;
    }

    public void setPlayer4(Bot player4) {
        this.player4 = player4;
    }

    public ArrayList<Card> getRemainCards() {
        return remainCards;
    }

    public void decreaseCoins(int coins) {
        this.setRemainingCoins(this.remainingCoins - coins);
    }

    public void increaseCoins(int coins) {
        this.setRemainingCoins(this.remainingCoins + coins);
    }

    public void setRemainCards() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            counter = 0;
            for (int i1 = 0; i1 < 4; i1++) {
                Player player = players.get(i1);
                if (i == 0) {
                    if (player.getCards().get(0).getCardsType() == CardsType.AMBASSADOR) counter++;
                    if (player.getCards().get(1).getCardsType() == CardsType.AMBASSADOR) counter++;
                    if (i1 == 3) createCard(CardsType.AMBASSADOR, 3 - counter);
                } else if (i == 1) {
                    if (player.getCards().get(0).getCardsType() == CardsType.COMMANDER) counter++;
                    if (player.getCards().get(1).getCardsType() == CardsType.COMMANDER) counter++;
                    if (i1 == 3) createCard(CardsType.COMMANDER, 3 - counter);
                } else if (i == 2) {
                    if (player.getCards().get(0).getCardsType() == CardsType.MURDERER) counter++;
                    if (player.getCards().get(1).getCardsType() == CardsType.MURDERER) counter++;
                    if (i1 == 3) createCard(CardsType.MURDERER, 3 - counter);
                } else if (i == 3) {
                    if (player.getCards().get(0).getCardsType() == CardsType.PRINCESS) counter++;
                    if (player.getCards().get(1).getCardsType() == CardsType.PRINCESS) counter++;
                    if (i1 == 3) createCard(CardsType.PRINCESS, 3 - counter);
                } else {
                    if (player.getCards().get(0).getCardsType() == CardsType.RICH) counter++;
                    if (player.getCards().get(1).getCardsType() == CardsType.RICH) counter++;
                    if (i1 == 3) createCard(CardsType.RICH, 3 - counter);
                }
            }
        }
    }

    private void createCard(CardsType cardType, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            Card card = new Card();
            card.setCardsType(cardType);
            remainCards.add(card);
        }
    }

    public void setRemainCards(ArrayList<Card> remainCards) {
        this.remainCards = remainCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void action(Player player, ActionType actionType) {
        if (actionType == ActionType.TAKE_INCOME) {//OK
            takeIncome(player);
        } else if (actionType == ActionType.TAKE_NEW_EXCHANGE) {//OK
            takeNewExchange();
        } else if (actionType == ActionType.TAKE_ASSASSINATE) {//OK ~~~~
            takeAssassinate();
        } else if (actionType == ActionType.TAKE_FOREIGN_AID) {//OK
            takeForeignAid(player);
        } else if (actionType == ActionType.TAKE_TAX) {//OK
            takeTax(player);
        } else if (actionType == ActionType.TAKE_STEAL) {//OK~~~~
            takeSteal();
        } else if (actionType == ActionType.TAKE_EXCHANGE) {//OK
            takeExchange();
        }
    }

    public void takeSteal() {
        if (getCurrentPlayer() instanceof Human) {
            gamePageController.getButtonForSteal().setVisible(true);
            gamePageController.getNoticeLabel2().setVisible(true);
            gamePageController.getNoticeAnswer2().setVisible(true);
            gamePageController.getNoticeLabel2().setText("Which Player?(2, 3 or 4)");
        } else {
            int randomPlayer = Random.getRandomPlayer(turn, this);
            getCurrentPlayer().increaseCoins(2);
            getPlayer(randomPlayer).decreaseCoins(2);
            changeTurn();
            gamePageController.setGamePage();
        }
    }

    public void takeAssassinate() {
        if (getPlayer(turn).getCoins() < 3 && !(getCurrentPlayer() instanceof CautiousKiller)) return;
        else {
            if (!(getCurrentPlayer() instanceof CautiousKiller)) {
                getCurrentPlayer().decreaseCoins(3);
                this.increaseCoins(3);
            }
        }
        if (getPlayer(turn) instanceof Human) {
            gamePageController.getNoticeLabel2().setVisible(true);
            gamePageController.getNoticeLabel2().setText("Which Player?(2,3 or 4)");
            gamePageController.getNoticeAnswer2().setVisible(true);
            gamePageController.getAssassinateButton().setVisible(true);
        } else {
            int randomPlayer = Random.getRandomPlayer(turn, this);
            if (getCurrentPlayer() instanceof CautiousKiller)
                randomPlayer = ((CautiousKiller) getCurrentPlayer()).playerForAssassinate;
            if (this.getPlayer(randomPlayer) instanceof Human) {
                gamePageController.getKillForAssassinateButton().setVisible(true);
                gamePageController.getNoticeLabel2().setVisible(true);
                gamePageController.getNoticeLabel2().setText("Which Card? (Left or Right)");
                gamePageController.getNoticeAnswer2().setVisible(true);
            } else {
                if (getPlayer(randomPlayer).getCards().size() == 1) {
                    getPlayer(randomPlayer).killCard(0);
                } else {
                    int random = Random.getPositiveRandom();
                    int index = random % 2;
                    for (int i = 0; i < getPlayer(randomPlayer).getCards().size(); i++) {
                        if (getPlayer(randomPlayer).getCards().get(i).getCardsType() == CardsType.MURDERER) {
                            if (index == i) {
                                if (i == 0) index = 1;
                                else index = 0;
                            } else break;
                        }
                    }
                    getPlayer(randomPlayer).killCard(index);
                }
                this.changeTurn();
            }
            System.out.println(player1.getCards().size());
            System.out.println(player2.getCards().size());
            System.out.println(player3.getCards().size());
            System.out.println(player4.getCards().size());
            gamePageController.setGamePage();
        }

    }

    private void takeExchange() {
        if (getPlayer(turn) instanceof Human) {
            String cards = this.remainCards.get(0).getCardsType().toString() + "  " + this.remainCards.get(1).getCardsType().toString() + "  ";
            if (getCurrentPlayer().getCards().size() == 1) {
                cards += getCurrentPlayer().getCards().get(0).getCardsType().toString();
            } else {
                cards += getCurrentPlayer().getCards().get(0).getCardsType().toString();
                cards += "  ";
                cards += getCurrentPlayer().getCards().get(1).getCardsType().toString();
            }

            gamePageController.getNoticeLabel2().setVisible(true);
            gamePageController.getNoticeLabel2().setText("Which Card or Cards?(index)");
            gamePageController.getShowCardsLabel().setText(cards);
            gamePageController.getShowCardsLabel().setVisible(true);
            gamePageController.getNoticeAnswer2().setVisible(true);
            gamePageController.getExchangeButton().setVisible(true);
            changeTurn();
        } else {
            Collections.shuffle(this.remainCards);
            if (getCurrentPlayer().getCards().size() == 1) {
                ArrayList<Card> options = new ArrayList<>();
                options.add(this.remainCards.get(0));
                options.add(this.remainCards.get(1));
                options.add(getCurrentPlayer().getCards().get(0));
                if (getCurrentPlayer() instanceof CautiousKiller) {
                    int index = -1;
                    for (int i = 0; i < options.size(); i++) {
                        if (options.get(i).getCardsType() == CardsType.MURDERER) index = i;
                    }
                    if (index != -1) {
                        getCurrentPlayer().getCards().remove(0);
                        this.remainCards.remove(1);
                        this.remainCards.remove(0);
                        getCurrentPlayer().getCards().add(options.get(index));
                        options.remove(index);
                        this.remainCards.add(options.get(0));
                        this.remainCards.add(options.get(1));
                    }
                } else {
                    Collections.shuffle(options);
                    getCurrentPlayer().getCards().remove(0);
                    this.remainCards.remove(1);
                    this.remainCards.remove(0);
                    getCurrentPlayer().getCards().add(options.get(0));
                    options.remove(0);
                    this.remainCards.add(options.get(0));
                    this.remainCards.add(options.get(1));
                }

            } else {
                ArrayList<Card> options = new ArrayList<>();
                options.add(this.remainCards.get(0));
                options.add(this.remainCards.get(1));
                options.add(getCurrentPlayer().getCards().get(0));
                options.add(getCurrentPlayer().getCards().get(1));
                if (getCurrentPlayer() instanceof CautiousKiller) {
                    int index = -1;
                    for (int i = 0; i < options.size(); i++) {
                        if (options.get(i).getCardsType() == CardsType.MURDERER) index = i;
                    }
                    if (index != -1) {
                        getCurrentPlayer().getCards().remove(1);
                        getCurrentPlayer().getCards().remove(0);
                        this.remainCards.remove(1);
                        this.remainCards.remove(0);
                        getCurrentPlayer().getCards().add(options.get(index));
                        if (index != 0) {
                            getCurrentPlayer().getCards().add(options.get(0));
                            options.remove(index);
                            options.remove(0);
                        } else {
                            getCurrentPlayer().getCards().add(options.get(1));
                            options.remove(1);
                            options.remove(0);
                        }
                        this.remainCards.add(options.get(0));
                        this.remainCards.add(options.get(1));
                    }
                } else {
                    Collections.shuffle(options);
                    getCurrentPlayer().getCards().remove(1);
                    getCurrentPlayer().getCards().remove(0);
                    this.remainCards.remove(1);
                    this.remainCards.remove(0);
                    getCurrentPlayer().getCards().add(options.get(0));
                    getCurrentPlayer().getCards().add(options.get(1));
                    options.remove(1);
                    options.remove(0);
                    this.remainCards.add(options.get(0));
                    this.remainCards.add(options.get(1));
                }

            }
            this.changeTurn();
        }


    }

    private void takeNewExchange() {
        if (getPlayer(turn).getCoins() < 1) return;
        if (getPlayer(turn) instanceof Human) {
            gamePageController.getNoticeLabel2().setVisible(true);
            gamePageController.getNoticeLabel2().setText("Which card?(Left or Right)");
            gamePageController.getNoticeAnswer2().setVisible(true);
            gamePageController.getNewExchangeButton().setVisible(true);
        } else {
            int random = Random.getPositiveRandom();
            Collections.shuffle(this.remainCards);
            if (getPlayer(turn).getCards().size() == 1) {
                Card newCard = player1.getCards().get(0);
                getPlayer(turn).getCards().remove(0);
                getPlayer(turn).getCards().add(this.getRemainCards().get(0));
                this.getRemainCards().remove(0);
                this.getRemainCards().add(newCard);
                getCurrentPlayer().decreaseCoins(1);
                this.increaseCoins(1);
                this.changeTurn();
            } else {
                Card newCard = getPlayer(turn).getCards().get(random % 2);
                getPlayer(turn).getCards().remove(random % 2);
                getPlayer(turn).getCards().add(this.getRemainCards().get(0));
                this.getRemainCards().remove(0);
                this.getRemainCards().add(newCard);
                getCurrentPlayer().decreaseCoins(1);
                this.increaseCoins(1);
                this.changeTurn();
            }
            gamePageController.setGamePage();
        }
    }

    public void takeTax(Player player) {
        player.increaseCoins(3);
        this.decreaseCoins(3);
        this.changeTurn();
    }

    public void takeForeignAid(Player player) {
        player.increaseCoins(2);
        this.decreaseCoins(2);
        this.changeTurn();
        gamePageController.setGamePage();
    }

    public void takeCoup(int player) throws IOException {
        if (this.getCurrentPlayer().getCoins() >= 7) {
            this.getCurrentPlayer().decreaseCoins(7);
            this.increaseCoins(7);
            String event = "Player" + turn + ": Take Coup on Player" + player;
            this.addEvent(event);
            gamePageController.setGamePage();
            killPlayer(player);
        }
    }

    public void killPlayer(int player) throws IOException {
        if (player == 1) {
            if (getPlayer(player).getCards().size() == 1) {
                getPlayer(player).killCard(0);
                player1.setAlive(false);
            } else {
                gamePageController.takeCoupOnPlayer1();
            }
        } else {
            int randomNumber = Random.getPositiveRandom();
            Player killedPlayer = getPlayer(player);
            if (killedPlayer.getCards().size() == 1) {
                killedPlayer.killCard(0);
                killedPlayer.setAlive(false);
            } else {
                int index = randomNumber % 2;
                if (killedPlayer instanceof CautiousKiller) {
                    for (int i = 0; i < killedPlayer.getCards().size(); i++) {
                        if (killedPlayer.getCards().get(i).getCardsType() == CardsType.MURDERER) {
                            if (index == i) {
                                if (i == 0) index = 1;
                                else index = 0;
                            } else break;
                        }
                    }
                }
                killedPlayer.killCard(index);
            }
        }
        changeTurn();
        gamePageController.setGamePage();
    }

    public void takeIncome(Player player) {
        player.increaseCoins(1);
        this.decreaseCoins(1);
        this.changeTurn();
        gamePageController.setGamePage();
    }

    public Player getCurrentPlayer() {
        if (this.turn == 1) return player1;
        else if (this.turn == 2) return player2;
        else if (this.turn == 3) return player3;
        else return player4;
    }

    public void doAction(ActionType actionType) throws IOException {
        lastActionPlayed = actionType;
        String event = "player" + turn + ": " + actionType.getAction();
        addEvent(event);
        this.setLastActionPlayed(actionType);
        Player player = this.getCurrentPlayer();
        if (actionType.isBlockable() && getCurrentPlayer() instanceof Human) {
            event = "No one blocked";
            this.addEvent(event);
        }
        if (actionType == ActionType.TAKE_ASSASSINATE) {
            this.setLastCardPlayed(CardsType.MURDERER);
        } else if (actionType == ActionType.TAKE_TAX) {
            this.setLastCardPlayed(CardsType.RICH);
        } else if (actionType == ActionType.TAKE_STEAL) {
            this.setLastCardPlayed(CardsType.COMMANDER);
        } else if (actionType == ActionType.TAKE_EXCHANGE) {
            this.setLastCardPlayed(CardsType.AMBASSADOR);
        }

        if (actionType.isChallengable()) {
            numberOfChallenges += 1;
            if (turn != 1 && player1.isAlive()) {
                if (numberOfChallenges % 2 == 0 && haveParanoid() && isAliveParanoid()) {
                    event = "player" + getParanoid() + " get Challenge";
                    addEvent(event);
                    runChallenge(getParanoid());
                    return;
                }
                challenge();
            } else {
                if (numberOfChallenges % 2 == 0 && haveParanoid() && isAliveParanoid()) {
                    event = "player" + getParanoid() + " get Challenge";
                    addEvent(event);
                    runChallenge(getParanoid());
                    return;
                }
                botChallenge();
            }
        } else {
            action(player, actionType);
        }
    }

    private boolean isAliveParanoid() {
        int paranoid = getParanoid();
        return getPlayer(paranoid).isAlive();
    }

    public void botChallenge() throws IOException {
        int randomBot = Random.getRandomPlayer(turn, this);
        int randomNum = Random.getPositiveRandom();
        if (randomNum % 2 == 0) {
            String event = "Player" + randomBot + " get Challenge";
            this.addEvent(event);
            gamePageController.setGamePage();
            runChallenge(randomBot);
        } else {
            String event = "No one get Challenge";
            this.addEvent(event);
            gamePageController.setGamePage();
            action(this.getPlayer(turn), lastActionPlayed);
        }
    }

    public void runChallenge(int player) throws IOException {
        int currentTurn = turn;
        int result = Challenge.runChallengeReturnLoser(turn, player, this.lastCardPlayed, this);
        if (result == player) {
            getCurrentPlayer().changeCard(lastCardPlayed);
            this.addEvent("player" + turn + "wins challenge");
        } else this.addEvent("player" + player + "wins challenge");
        gamePageController.setGamePage();
        Player loser = getPlayer(result);
        if (result == player) {
            action(this.getPlayer(turn), lastActionPlayed);
        }
        if (loser instanceof Human) {
            gamePageController.takeCoupOnPlayer1();
        } else {
            if (loser.getCards().size() == 1) {
                loser.killCard(0);
                loser.setAlive(false);
            } else {
                int randomNumber = Random.getPositiveRandom();
                int index = randomNumber % 2;
                if (loser instanceof CautiousKiller) {
                    for (int i = 0; i < loser.getCards().size(); i++) {
                        if (loser.getCards().get(i).getCardsType() == CardsType.MURDERER) {
                            if (index == i) {
                                if (i == 0) index = 1;
                                else index = 0;
                            } else break;
                        }
                    }
                }
                loser.killCard(index);
            }
            if (turn == 1) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (currentTurn == turn) changeTurn();
                    }
                }));
                timeline.setCycleCount(1);
                timeline.playFromStart();
            }
            if (currentTurn == turn && numberOfAlivePlayers() != 2) changeTurn();
        }
        gamePageController.setGamePage();
    }

    private int numberOfAlivePlayers() {
        int answer = 0;
        if (player1.isAlive()) answer++;
        if (player2.isAlive()) answer++;
        if (player3.isAlive()) answer++;
        if (player4.isAlive()) answer++;
        return answer;
    }

    public void challenge() {
        gamePageController.getNoticeLabel().setVisible(true);
        gamePageController.getNoticeLabel().setText("do you want to challenge?(YES or NO)");
        gamePageController.getNoticeAnswer().setVisible(true);
        gamePageController.getButtonForChallenge().setVisible(true);
    }

    public CardsType getLastCardPlayed() {
        return lastCardPlayed;
    }

    public void setLastCardPlayed(CardsType lastCardPlayed) {
        this.lastCardPlayed = lastCardPlayed;
    }

    public int getNumberOfChallenges() {
        return numberOfChallenges;
    }

    public void setNumberOfChallenges(int numberOfChallenges) {
        this.numberOfChallenges = numberOfChallenges;
    }

    public void addChallenge() {
        this.setNumberOfChallenges(this.numberOfChallenges + 1);
    }

    public boolean haveParanoid() {
        if (player2 instanceof Paranoid) return true;
        if (player3 instanceof Paranoid) return true;
        if (player4 instanceof Paranoid) return true;
        return false;
    }

    public int getParanoid() {
        if (player2 instanceof Paranoid) return player2.getTurn();
        if (player3 instanceof Paranoid) return player3.getTurn();
        if (player4 instanceof Paranoid) return player4.getTurn();
        return 0;
    }

    public void addEvent(String newEvent) {
        newEvent += "\n";
        this.events += newEvent;

    }

    public String getEvents() {
        return events;
    }

    public GamePageController getGamePageController() {
        return gamePageController;
    }

    public void setGamePageController(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }

    public ActionType getLastActionPlayed() {
        return lastActionPlayed;
    }

    public void setLastActionPlayed(ActionType lastActionPlayed) {
        this.lastActionPlayed = lastActionPlayed;
    }

    public void challengeInBlockingAction(int playerInChallenge, int challenger) {
        System.out.println(lastActionPlayed.toString());
        if (lastActionPlayed == ActionType.TAKE_FOREIGN_AID) {
            int loser = Challenge.runChallengeReturnLoser(playerInChallenge, challenger, CardsType.RICH, this);
            if (challenger == loser) {
                addEvent("player" + playerInChallenge + " wins challenge");
                isBlockedAction = true;
                changeTurn();
                getPlayer(playerInChallenge).killCard(0);
                gamePageController.setGamePage();
            } else {
                setBlockedAction(false);
                addEvent("player" + challenger + " wins challenge");
                player1.killingCard();
                changeTurn();
                gamePageController.setGamePage();
            }
        } else if (lastActionPlayed == ActionType.TAKE_ASSASSINATE) {
            int loser = Challenge.runChallengeReturnLoser(playerInChallenge, challenger, CardsType.PRINCESS, this);
            if (challenger == loser) {
                addEvent("player" + playerInChallenge + " wins challenge");
                isBlockedAction = true;
                changeTurn();
                getPlayer(playerInChallenge).killCard(0);
                gamePageController.setGamePage();
            } else {
                setBlockedAction(false);
                addEvent("player" + challenger + " wins challenge");
                player1.killingCard();
                changeTurn();
                gamePageController.setGamePage();
            }
        } else {
            int loser1 = Challenge.runChallengeReturnLoser(playerInChallenge, challenger, CardsType.COMMANDER, this);
            int loser2 = Challenge.runChallengeReturnLoser(playerInChallenge, challenger, CardsType.AMBASSADOR, this);
            if (loser1 == challenger && loser2 == challenger) {
                addEvent("player" + playerInChallenge + " wins challenge");
                isBlockedAction = true;
                changeTurn();
                getPlayer(playerInChallenge).killCard(0);
                gamePageController.setGamePage();
            } else {
                setBlockedAction(false);
                addEvent("player" + challenger + " wins challenge");
                player1.killingCard();
                changeTurn();
                gamePageController.setGamePage();
            }

        }
    }

    public void blockingAction(int blocker, int player) {
        int random = Random.getPositiveRandom();
        if (random % 3 == 0) {
            addEvent("Player" + player + " get Challenge for Player" + blocker);
            gamePageController.setGamePage();
            challengeInBlockingAction(blocker, player);
        } else {
            setBlockedAction(true);
            addEvent("Player" + blocker + " blocked Player" + player);
            this.isBlockedAction = true;
            changeTurn();
            gamePageController.setGamePage();
        }
    }

    public boolean isBlockedAction() {
        return isBlockedAction;
    }

    public void setBlockedAction(boolean blockedAction) {
        isBlockedAction = blockedAction;
    }

}
