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

    public static volatile List<Player> players = new ArrayList<>();

    private Player player;

    private String line;
    private String name;

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

        PrintWriter out = null;
        BufferedReader in = null;

        allGames = new ArrayList<>();


        try {
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Insert your name: ");
            out.println("stop");


            GameManager gameManager = new GameManager();
            Player player = new Player("jogador");
            gameManager.addPlayer(player);
            gameManager.addClientSocket(clientSocket);
            gameManager.startGame();

/*
            name = in.readLine();
            System.out.println("Client " + name + " wrote their name");
            player = new Player(name);

            out.println("Do you wish to create or join a game?\n1 - create a game\n2 - join a game");
            out.println("stop");
            line = in.readLine();

            if (line.equals("1")) {

                out.println("Insert a name for your game: ");
                out.println("stop");

                GameManager gameManager = new GameManager();
                allGames.add(gameManager);

                line = in.readLine();
                gameManager.setGameName(line);

                System.out.println("Client " + name + " created a game called: " + line);

                gameManager.addClientSocket(clientSocket);
                gameManager.addPlayer(player);

                player.setHost(true);
                players.add(player);

                out.println("Game created successfully!");
                out.println("stop");

                while (!ready) {
                }
                gameManager.startGame();
            }


            if (line.equals("2")) {
                player.setHost(false);
                int id = 0;

                for (GameManager game : allGames) {
                    out.println(id + " - " + game.getGameName());
                    id++;
                }
                out.println("if there's no games, press 'a' to refresh");
                out.println("stop");

                line = in.readLine();

                while (line.equals("a")) {
                    for (GameManager games : allGames) {
                        out.println(id + " - " + games.getGameName());
                        id++;
                    }

                    out.println("if there's no games, press 'a' to refresh");
                    out.println("stop");
                    line = in.readLine();
                }

                int index = Integer.parseInt(line);
                allGames.get(index).addClientSocket(clientSocket);
                allGames.get(index).addPlayer(player);

                System.out.println(name + " joined game " + allGames.get(index).getGameName());

                out.println("Welcome to " + allGames.get(index).getHost().getName() + "'s game!");
                out.println("stop");

                players.add(player);
                ready = true;
                in.readLine();
                System.out.println(ready);
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAllClientsList(List<ClientHandler> allClientsList) {
        this.allClientsList = allClientsList;
    }

}

