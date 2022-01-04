package tvg.server;

import tvg.game.Game;
import tvg.player.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;

/*
 * client handler
 */
public class ClientHandler extends Thread {

    public final Socket clientSocket;
    private volatile List<ClientHandler> allClientsList;
    public static volatile List<GameManager> allGames = new ArrayList<>();

    private Player player;

    private String line;
    private String name;

    PrintWriter out = null;
    BufferedReader in = null;

    public static volatile Boolean ready = false;

    public ClientHandler(Socket socket, List<ClientHandler> list) {
        this.clientSocket = socket;
        this.allClientsList = list;
    }

    @Override
    public void run() {
        clientJoin();
    }

    public void clientJoin() {


        allGames = new ArrayList<>();


        try {
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Insert your name: ");
            out.println("stop");

            name = in.readLine();
            System.out.println("Client " + name + " wrote their name");
            player = new Player(name);

            out.println("Do you wish to create or join a game?\n1 - create a game\n2 - join a game");
            out.println("stop");
            line = in.readLine();

            if (line.equals("1")) createGame();

            if (line.equals("2")) joinGame();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("foda-se");
        }

    }

    public void createGame() throws IOException {

        out.println("Insert a name for your game: ");
        out.println("stop");

        line = in.readLine();

        GameManager gameManager = new GameManager();
        allGames.add(gameManager);

        gameManager.setGameName(line);

        gameManager.addClientSocket(clientSocket);
        gameManager.addPlayer(player);

        System.out.println("Client " + name + " created a game called " + line);

        player.setHost(true);

        out.println("Game created successfully!");
        out.println("press enter to continue");
        out.println("stop");

        in.readLine();
        // while (!ready) {
        //   System.out.println("waiting");
        // }
        for (Socket client : gameManager.clients) {
            out = new PrintWriter(client.getOutputStream());

            out.println("init");
            out.println("stop");
        }

        gameManager.startGame();
    }

    public void joinGame() throws IOException {
        player.setHost(false);
        int id = 0;

        do {
            for (GameManager games : allGames) {
                out.println(id + " - " + games.getGameName());
                id++;
            }
            out.println("if there's no games, press 'a' to refresh");
            out.println("stop");

            line = in.readLine();

        } while (line.equals("a"));


        int index = Integer.parseInt(line);

        allGames.get(index).addClientSocket(clientSocket);
        allGames.get(index).addPlayer(player);

        System.out.println(name + " joined game " + allGames.get(index).getGameName());

        out.println("Welcome to " + allGames.get(index).getHost().getName() + "'s game!");
        out.println("stop");

        in.readLine();
        ready = true;
    }

    public void setAllClientsList(List<ClientHandler> allClientsList) {
        this.allClientsList = allClientsList;
    }

}

