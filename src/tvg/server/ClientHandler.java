package tvg.server;

import tvg.common.Messages;
import tvg.common.UpdateMessages;
import tvg.player.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;

/*
 * client handler
 */
public class ClientHandler extends Thread {

    public static volatile List<GameManager> ExistingGames = new ArrayList<>();

    public Socket clientSocket;
    private Player player;

    private String line;
    private String name;

    private PrintWriter out = null;
    private BufferedReader in = null;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        clientJoin();
    }

    private void clientJoin() {

        try {
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(Messages.INSERT_NAME);
            out.println(Messages.STOP);

            name = in.readLine();
            player = new Player(name);

            print(UpdateMessages.CLIENT + name + UpdateMessages.WROTE_NAME);

            out.println(Messages.QUESTION_CREATE_OR_JOIN);
            out.println(Messages.CREATE_GAME);
            out.println(Messages.JOIN_GAME);
            out.println(Messages.STOP);

            line = in.readLine();

            if (line.equals("1")) createGame();
            if (line.equals("2")) joinGame();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createGame() throws IOException, ClassNotFoundException, InterruptedException {

        out.println(Messages.INSERT_GAME_NAME);
        out.println(Messages.STOP);

        line = in.readLine();

        GameManager gameManager = new GameManager();
        gameManager.setGameName(line);

        ExistingGames.add(gameManager);

        gameManager.addPlayer(clientSocket, player);

        print(UpdateMessages.CLIENT + name + UpdateMessages.CREATED_GAME + line);

        out.println(Messages.GAME_CREATED);
        out.println(Messages.CHOOSE_NUMBER_OF_PLAYERS);
        out.println(Messages.STOP);

        line = in.readLine();

        int number = Integer.parseInt(line);

        out.println(Messages.NUMBER_OF_PLAYERS_CHOSEN + line + Messages.PLAYERS);

        chooseGameMode(gameManager);

        out.println(Messages.PRESS_ENTER);
        out.println(Messages.STOP);
        in.readLine();

        while (gameManager.getClientSocketList().size() < number) {
            synchronized (ExistingGames.get(0)) {
                ExistingGames.get(0).wait();
            }
        }

        for (Map.Entry<Socket, Player> map : gameManager.getClientSocketList().entrySet()) {

            out = new PrintWriter(map.getKey().getOutputStream());

            out.println(Messages.INITIATE_GAME);
            out.println(Messages.STOP);
            out.flush();
        }

        gameManager.startGame();
    }

    private void joinGame() throws IOException {

        printExistingGames();

        int index = Integer.parseInt(line);

        System.out.println(name + UpdateMessages.JOINED_GAME + ExistingGames.get(index).getGameName());

        out.println(Messages.WELCOME_TO_GAME + ExistingGames.get(index).getGameName());
        out.println(Messages.PRESS_ENTER);
        out.println(Messages.STOP);

        in.readLine();
        ExistingGames.get(index).addPlayer(clientSocket, player);

        synchronized (ExistingGames.get(index)) {
            ExistingGames.get(index).notifyAll();
        }
    }

    private void chooseGameMode(GameManager gameManager) throws IOException {
        do {
            out.println(Messages.CHOOSE_GAME_MODE);
            out.println(Messages.UNTIL_ONE_SURVIVOR);
            out.println(Messages.LIMITED_ROUNDS);
            out.println(Messages.STOP);

            line = in.readLine();

        } while (!line.equals("1") && !line.equals("2"));

        if (line.equals("1")) {
            gameManager.setGameMode("until one survivor");
            print(UpdateMessages.CHOSE_GAME_MODE + UpdateMessages.UNTIL_ONE_SURVIVOR);
        }
        if (line.equals("2")) {
            gameManager.setGameMode("ten rounds");
            print(UpdateMessages.CHOSE_GAME_MODE + UpdateMessages.LIMITED_ROUNDS);
        }
    }

    private void printExistingGames() throws IOException {

        do {
            int id = 0;

            for (GameManager games : ExistingGames) {
                out.println(games);
                out.println(id + " - " + games.getGameName());
                id++;
            }
            out.println(Messages.REFRESH);
            out.println(Messages.STOP);

            line = in.readLine();

        } while (line.equalsIgnoreCase("r"));

    }

    private void print(String message) {
        System.out.println(message);
    }

}

