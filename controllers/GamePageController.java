package controllers;

import gamelogic.Game;
import gamelogic.GameLoader;
import gamelogic.cards.Card;
import gamelogic.cards.CardsType;
import gamelogic.play.ActionType;
import gamelogic.players.Bot;
import gamelogic.players.Human;
import gamelogic.players.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {
    @FXML
    Label coinsOfPlayer1;
    @FXML
    Label coinsOfPlayer2;
    @FXML
    Label coinsOfPlayer3;
    @FXML
    Label coinsOfPlayer4;
    @FXML
    TextArea gameEvents;
    @FXML
    HBox player1HBox;
    @FXML
    HBox player2HBox;
    @FXML
    HBox player3HBox;
    @FXML
    HBox player4HBox;
    @FXML
    Label remainingCoins;
    @FXML
    Label remainingCards;
    @FXML
    Label noticeLabel;
    @FXML
    TextField noticeAnswer;
    @FXML
    Label turnLabel;
    @FXML
    Button sendMessageButton;
    @FXML
    Button sendMessageButton2;
    @FXML
    Label noticeLabel2;
    @FXML
    TextField noticeAnswer2;
    @FXML
    Button buttonForChallenge;
    @FXML
    Button newExchangeButton;
    @FXML
    Button assassinateButton;
    @FXML
    Button exchangeButton;
    @FXML
    Label showCardsLabel;
    @FXML
    Button income;
    @FXML
    Button foreign;
    @FXML
    Button coup;
    @FXML
    Button tax;
    @FXML
    Button assassinate;
    @FXML
    Button exchange;
    @FXML
    Button steal;
    @FXML
    Button nExchnge;
    @FXML
    Button killForAssassinateButton;
    @FXML
    Button buttonForSteal;
    @FXML
    Button blockButton;
    @FXML
    Button killCardButton;
    @FXML
    Label blockingLabel;
    @FXML
    TextField blockingField;
    @FXML
    TextField answerForKilling;
    @FXML
    Label killingLabel;


    Game game;
    Human player1;
    Bot player2;
    Bot player3;
    Bot player4;
    String challengeText = "do you want to challenge?(YES or NO)";
    String choseCardText = "Which of your cards to return?(1 or 2)";
    String coupText = "Which of players to coup?(a number)";
    String answer = "No Answer";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.game = GameLoader.getGame();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        game.setGamePageController(this);
        player1 = (Human) game.getPlayer(1);
        player1.setTurn(1);
        player2 = (Bot) game.getPlayer(2);
        player2.setTurn(2);
        player3 = (Bot) game.getPlayer(3);
        player3.setTurn(3);
        player4 = (Bot) game.getPlayer(4);
        player4.setTurn(4);
        setGamePage();

    }

    public void setGamePage() {
        int size1 = player1.getCards().size();
        int size2 = player2.getCards().size();
        int size3 = player3.getCards().size();
        int size4 = player4.getCards().size();
        if (size1 != 0 && size2 == 0 && size3 == 0 && size4 == 0) game.addEvent("Player1 Wins!");
        if (size1 == 0 && size2 != 0 && size3 == 0 && size4 == 0) game.addEvent("Player2 Wins!");
        if (size1 == 0 && size2 == 0 && size3 != 0 && size4 == 0) game.addEvent("Player3 Wins!");
        if (size1 == 0 && size2 == 0 && size3 == 0 && size4 != 0) game.addEvent("Player4 Wins!");
        String text = "";
        text = "remaining cards: " + game.getRemainCards().size();
        remainingCards.setText(text);
        text = "remaining coins: " + game.getRemainingCoins();
        remainingCoins.setText(text);
        text = "coins: " + player1.getCoins();
        coinsOfPlayer1.setText(text);
        text = "coins: " + player2.getCoins();
        coinsOfPlayer2.setText(text);
        text = "coins: " + player3.getCoins();
        coinsOfPlayer3.setText(text);
        text = "coins: " + player4.getCoins();
        coinsOfPlayer4.setText(text);
        player1HBox.getChildren().clear();
        player2HBox.getChildren().clear();
        player3HBox.getChildren().clear();
        player4HBox.getChildren().clear();
        text = "Turn: Player" + game.getTurn();
        turnLabel.setText(text);
        ArrayList<Card> cards = player1.getCards();
        ArrayList<Card> deadCards = player1.getDeadCards();
        for (int i = 0; i < cards.size(); i++) {
            CardsType cardType = cards.get(i).getCardsType();
            String path = getPathOfCard("", cardType);
            addPictureToHBox(player1HBox, path);
        }

        addDeadCards(deadCards, 1);
        cards = player2.getCards();
        deadCards = player2.getDeadCards();
        for (int i = 0; i < cards.size(); i++) {
            String path = "images/backOfCard.png";
            addPictureToHBox(player2HBox, path);
        }
        addDeadCards(deadCards, 2);
        cards = player3.getCards();
        deadCards = player3.getDeadCards();
        for (int i = 0; i < cards.size(); i++) {
            String path = "images/backOfCard.png";
            addPictureToHBox(player3HBox, path);
        }
        addDeadCards(deadCards, 3);
        cards = player4.getCards();
        deadCards = player4.getDeadCards();
        for (int i = 0; i < cards.size(); i++) {
            String path = "images/backOfCard.png";
            addPictureToHBox(player4HBox, path);
        }
        addDeadCards(deadCards, 4);
        if (game.getTurn() != 1 || !player1.isAlive()) {
            hideAll();
        } else showAll();
        gameEvents.clear();
        gameEvents.appendText(game.getEvents());
    }

    public String getPathOfCard(String preFix, CardsType cardType) {
        String result = preFix;
        if (cardType == CardsType.AMBASSADOR) {
            result += "Ambassador.png";
        } else if (cardType == CardsType.COMMANDER) {
            result += "Commander.png";
        } else if (cardType == CardsType.MURDERER) {
            result += "Murderer.png";
        } else if (cardType == CardsType.PRINCESS) {
            result += "Princess.png";
        } else {
            result += "Rich.png";
        }
        result = "images/" + result;
        return result;
    }

    public void addDeadCards(ArrayList<Card> deadCards, int player) {
        switch (player) {
            case 1:
                for (int i = 0; i < deadCards.size(); i++) {
                    CardsType cardType = deadCards.get(i).getCardsType();
                    String path = getPathOfCard("Dead", cardType);
                    addPictureToHBox(player1HBox, path);
                }
                break;
            case 2:
                for (int i = 0; i < deadCards.size(); i++) {
                    CardsType cardType = deadCards.get(i).getCardsType();
                    String path = getPathOfCard("Dead", cardType);
                    addPictureToHBox(player2HBox, path);
                }
                break;
            case 3:
                for (int i = 0; i < deadCards.size(); i++) {
                    CardsType cardType = deadCards.get(i).getCardsType();
                    String path = getPathOfCard("Dead", cardType);
                    addPictureToHBox(player3HBox, path);
                }
                break;
            case 4:
                for (int i = 0; i < deadCards.size(); i++) {
                    CardsType cardType = deadCards.get(i).getCardsType();
                    String path = getPathOfCard("Dead", cardType);
                    addPictureToHBox(player4HBox, path);
                }
                break;
        }

    }

    public void addPictureToHBox(HBox hBox, String address) {
        Image image = new Image(address);
        ImageView pic = new ImageView();
        pic.setFitWidth(70);
        pic.setFitHeight(100);
        pic.setImage(image);
        hBox.getChildren().add(pic);
    }

    public void hideAll() {
        income.setVisible(false);
        foreign.setVisible(false);
        coup.setVisible(false);
        tax.setVisible(false);
        assassinate.setVisible(false);
        exchange.setVisible(false);
        steal.setVisible(false);
        nExchnge.setVisible(false);
    }

    public void showAll() {
        income.setVisible(true);
        foreign.setVisible(true);
        coup.setVisible(true);
        tax.setVisible(true);
        assassinate.setVisible(true);
        exchange.setVisible(true);
        steal.setVisible(true);
        nExchnge.setVisible(true);
    }

    public void takeIncome(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_INCOME);
            setGamePage();
        }
    }

    public void takeForeignAid(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_FOREIGN_AID);
            setGamePage();
        }
    }

    public void takeCoup(ActionEvent actionEvent) throws InterruptedException, IOException {
        if (game.getTurn() == 1) {
            hideAll();
            noticeLabel.setText(coupText);
            noticeAnswer.setVisible(true);
            noticeLabel.setVisible(true);
            sendMessageButton.setVisible(true);
        }
    }

    public void takeTax(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_TAX);
            setGamePage();
        }

    }

    public void takeAssassinate(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_ASSASSINATE);
            setGamePage();
        }
    }

    public void takeExchange(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_EXCHANGE);
        }
    }

    public void takeSteal(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_STEAL);
            setGamePage();
        }
    }

    public void takeNewExchange(ActionEvent actionEvent) throws IOException {
        if (game.getTurn() == 1) {
            hideAll();
            game.doAction(ActionType.TAKE_NEW_EXCHANGE);
            setGamePage();
        }
    }

    public void helpPage(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HelpPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void nextMove(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (game.getTurn() == 2) {
            player2.doAction();
            setGamePage();
        } else if (game.getTurn() == 3) {
            player3.doAction();
            setGamePage();
        } else if (game.getTurn() == 4) {
            player4.doAction();
            setGamePage();
        }
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        int playerForKill = noticeAnswer.getText().charAt(0) - '0';
        noticeLabel.setVisible(false);
        noticeAnswer.setVisible(false);
        sendMessageButton.setVisible(false);
        game.takeCoup(playerForKill);
        setGamePage();
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(Label noticeLabel) {
        this.noticeLabel = noticeLabel;
    }

    public TextField getNoticeAnswer() {
        return noticeAnswer;
    }

    public void setNoticeAnswer(TextField noticeAnswer) {
        this.noticeAnswer = noticeAnswer;
    }

    public Button getSendMessageButton2() {
        return sendMessageButton2;
    }

    public void setSendMessageButton2(Button sendMessageButton2) {
        this.sendMessageButton2 = sendMessageButton2;
    }

    public void sendMessage2(ActionEvent actionEvent) {
        String ans = noticeAnswer2.getText();
        if (ans.equals("Left")) player1.killCard(0);
        else player1.killCard(1);
        game.changeTurn();
        noticeLabel2.setVisible(false);
        noticeAnswer2.setVisible(false);
        sendMessageButton2.setVisible(false);
        setGamePage();
    }

    public void takeCoupOnPlayer1() {
        noticeAnswer2.setVisible(true);
        noticeLabel2.setVisible(true);
        noticeLabel2.setText("Which Card? (Left or Right)");
        sendMessageButton2.setVisible(true);
    }

    public void challengeButton(ActionEvent actionEvent) throws IOException {
        String ans = noticeAnswer.getText();
        if (ans.contains("YES")) {
            game.addEvent("player1 get challenge!");
            game.runChallenge(1);
        } else {
            int randomPlayer = Random.getRandomPlayer(game.getTurn(), game);
            int randomNumber = Random.getPositiveRandom();
            if (randomNumber % 4 == 0 && randomPlayer != 1) {
                game.addEvent("player" + game.getPlayer(randomPlayer).getTurn() + "get challenge!");
                game.runChallenge(randomPlayer);
            } else {
                game.addEvent("NoOne get challenge!");
                game.action(game.getCurrentPlayer(), game.getLastActionPlayed());
            }
        }
        noticeLabel.setVisible(false);
        noticeAnswer.setVisible(false);
        buttonForChallenge.setVisible(false);
        blockingLabel.setVisible(false);
        blockingField.setVisible(false);
        blockButton.setVisible(false);
        setGamePage();
    }

    public Button getButtonForChallenge() {
        return buttonForChallenge;
    }

    public void setButtonForChallenge(Button buttonForChallenge) {
        this.buttonForChallenge = buttonForChallenge;
    }

    public void newExchange(ActionEvent actionEvent) {
        String ans = noticeAnswer2.getText();
        Collections.shuffle(game.getRemainCards());
        if (ans.equals("Left")) {
            Card newCard = player1.getCards().get(0);
            player1.getCards().remove(0);
            player1.getCards().add(game.getRemainCards().get(0));
            game.getRemainCards().remove(0);
            game.getRemainCards().add(newCard);
        } else {
            Card newCard = player1.getCards().get(1);
            player1.getCards().remove(1);
            player1.getCards().add(game.getRemainCards().get(0));
            game.getRemainCards().remove(0);
            game.getRemainCards().add(newCard);
        }
        player1.decreaseCoins(1);
        game.increaseCoins(1);
        game.changeTurn();
        noticeLabel2.setVisible(false);
        noticeAnswer2.setVisible(false);
        newExchangeButton.setVisible(false);
        setGamePage();
    }

    public Label getNoticeLabel2() {
        return noticeLabel2;
    }

    public void setNoticeLabel2(Label noticeLabel2) {
        this.noticeLabel2 = noticeLabel2;
    }

    public TextField getNoticeAnswer2() {
        return noticeAnswer2;
    }

    public void setNoticeAnswer2(TextField noticeAnswer2) {
        this.noticeAnswer2 = noticeAnswer2;
    }

    public Button getSendMessageButton() {
        return sendMessageButton;
    }

    public void setSendMessageButton(Button sendMessageButton) {
        this.sendMessageButton = sendMessageButton;
    }

    public Button getNewExchangeButton() {
        return newExchangeButton;
    }

    public void setNewExchangeButton(Button newExchangeButton) {
        this.newExchangeButton = newExchangeButton;
    }

    public void doAssassinate(ActionEvent actionEvent) {
        String ans = noticeAnswer2.getText();
        int random = Random.getPositiveRandom();
        game.addEvent("Player" + game.getTurn() + " assassinate on player" + ans);
        setGamePage();
        if (ans.charAt(0) == '2') {
            if (player2.getCards().size() == 1) player2.killCard(0);
            else player2.killCard(random % 2);
        } else if (ans.charAt(0) == '3') {
            if (player3.getCards().size() == 1) player3.killCard(0);
            else player3.killCard(random % 2);
        } else {
            if (player4.getCards().size() == 1) player4.killCard(0);
            else player4.killCard(random % 2);
        }
        game.changeTurn();
        noticeLabel2.setVisible(false);
        noticeAnswer2.setVisible(false);
        assassinateButton.setVisible(false);
        setGamePage();
    }

    public Button getAssassinate() {
        return assassinate;
    }

    public void setAssassinate(Button assassinate) {
        this.assassinate = assassinate;
    }

    public void doExchange(ActionEvent actionEvent) {
        String ans = noticeAnswer2.getText();
        ArrayList<Card> options = new ArrayList<>();
        options.add(game.getRemainCards().get(0));
        options.add(game.getRemainCards().get(1));
        if (player1.getCards().size() == 1) {
            int index = ans.charAt(0) - '0';
            options.add(player1.getCards().get(0));
            player1.getCards().remove(0);
            game.getRemainCards().remove(1);
            game.getRemainCards().remove(0);
            player1.getCards().add(options.get(index - 1));
            options.remove(index - 1);
            game.getRemainCards().add(options.get(0));
            game.getRemainCards().add(options.get(1));
        } else {
            int index1 = ans.charAt(0) - '0';
            int index2 = ans.charAt(2) - '0';
            options.add(player1.getCards().get(0));
            options.add(player1.getCards().get(1));
            player1.getCards().remove(1);
            player1.getCards().remove(0);
            game.getRemainCards().remove(1);
            game.getRemainCards().remove(0);
            player1.getCards().add(options.get(index1 - 1));
            player1.getCards().add(options.get(index2 - 1));
            if (index2 > index1) {
                options.remove(index2 - 1);
                options.remove(index1 - 1);
            } else {
                options.remove(index1 - 1);
                options.remove(index2 - 1);
            }
            game.getRemainCards().add(options.get(0));
            game.getRemainCards().add(options.get(1));
        }
        noticeLabel2.setVisible(false);
        noticeAnswer2.setVisible(false);
        exchangeButton.setVisible(false);
        showCardsLabel.setVisible(false);
        setGamePage();
    }

    public Button getAssassinateButton() {
        return assassinateButton;
    }

    public void setAssassinateButton(Button assassinateButton) {
        this.assassinateButton = assassinateButton;
    }

    public Button getExchangeButton() {
        return exchangeButton;
    }

    public void setExchangeButton(Button exchangeButton) {
        this.exchangeButton = exchangeButton;
    }

    public Label getShowCardsLabel() {
        return showCardsLabel;
    }

    public void setShowCardsLabel(Label showCardsLabel) {
        this.showCardsLabel = showCardsLabel;
    }

    public void killForAssassinate(ActionEvent actionEvent) {
        String ans = noticeAnswer2.getText();
        if (ans.equals("Left")) {
            player1.killCard(0);
        } else {
            player1.killCard(1);
        }
        game.changeTurn();
        noticeLabel2.setVisible(false);
        noticeAnswer2.setVisible(false);
        killForAssassinateButton.setVisible(false);
        setGamePage();
    }

    public Button getKillForAssassinateButton() {
        return killForAssassinateButton;
    }

    public void setKillForAssassinateButton(Button killForAssassinateButton) {
        this.killForAssassinateButton = killForAssassinateButton;
    }

    public void stealPlayer(ActionEvent actionEvent) {
        String ans = noticeAnswer2.getText();
        if (ans.contains("2")) {
            player1.increaseCoins(2);
            player2.decreaseCoins(2);
        } else if (ans.contains("3")) {
            player1.increaseCoins(2);
            player3.decreaseCoins(2);
        } else {
            player1.increaseCoins(2);
            player4.decreaseCoins(2);
        }
        game.changeTurn();
        noticeLabel2.setVisible(false);
        noticeAnswer2.setVisible(false);
        buttonForSteal.setVisible(false);
        setGamePage();
    }

    public Button getButtonForSteal() {
        return buttonForSteal;
    }

    public void setButtonForSteal(Button buttonForSteal) {
        this.buttonForSteal = buttonForSteal;
    }

    public Button getBlockButton() {
        return blockButton;
    }

    public void blockActionButt(ActionEvent actionEvent) {
        String ans = blockingField.getText();
        if (ans.contains("YES")) {
            game.addEvent("Player1 Blocked Action Player" + game.getTurn());
            game.blockingAction(1, game.getTurn());
        } else {
            game.setBlockedAction(false);
            game.addEvent("No one Blocked Action Player" + game.getTurn());
        }

        blockingLabel.setVisible(false);
        blockingField.setVisible(false);
        blockButton.setVisible(false);
        noticeLabel.setVisible(false);
        noticeAnswer.setVisible(false);
        buttonForChallenge.setVisible(false);
        setGamePage();
    }

    public void killingCard() {
        killingLabel.setVisible(true);
        answerForKilling.setVisible(true);
        killingLabel.setText("Which card?(Left or Right)");
        killCardButton.setVisible(true);
    }

    public void killCard(ActionEvent actionEvent) {
        String ans = answerForKilling.getText();
        if (ans.contains("Left")) {
            player1.killCard(0);
        } else {
            player1.killCard(1);
        }

        killingLabel.setVisible(false);
        answerForKilling.setVisible(false);
        killCardButton.setVisible(false);
        setGamePage();
    }

    public Label getBlockingLabel() {
        return blockingLabel;
    }

    public void setBlockingLabel(Label blockingLabel) {
        this.blockingLabel = blockingLabel;
    }

    public TextField getBlockingField() {
        return blockingField;
    }

    public void setBlockingField(TextField blockingField) {
        this.blockingField = blockingField;
    }
}
