package entrapped.server;

import entrapped.common.Messages;
import entrapped.common.UpdateMessages;
import entrapped.player.Player;

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


        out.println(Messages.PRESS_ENTER);
        out.println(Messages.STOP);
        in.readLine();

        while (gameManager.getClientSocketList().size() < 4) {
            synchronized (ExistingGames.get(0)) {
                ExistingGames.get(0).wait();
            }
        }

        ExistingGames.remove(gameManager);

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

