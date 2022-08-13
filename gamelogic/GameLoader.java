package gamelogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameLoader {
    public static Game getGame() throws FileNotFoundException {
        String path = "./src/main/resources/gameinformations.txt";
        File gameFile = new File(path);
        String jsonData = getJsonData(gameFile);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        GameInformations gameInformations = gson.fromJson(jsonData, GameInformations.class);
        Game game = new Game(gameInformations.getBotPlayers(),
                gameInformations.getPlayer1Cards(),
                gameInformations.getPlayer2Cards(),
                gameInformations.getPlayer3Cards(),
                gameInformations.getPlayer4Cards());
        return game;
    }

    private static String getJsonData(File gameFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(gameFile);
        String jsonData = "";
        while (scanner.hasNext()) {
            jsonData += scanner.nextLine();
        }
        return jsonData;
    }
}
