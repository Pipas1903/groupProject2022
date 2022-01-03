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
    private List<ClientHandler> allClientsList;
    private static List<GameManager> allGames = new ArrayList<>();

    private Player player;

    private String line;
    private String name;

    public ClientHandler(Socket socket, List<ClientHandler> list) {
        this.clientSocket = socket;
        this.allClientsList = list;
    }

    @Override
    public void run() {
        try {
            clientJoin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientJoin() throws IOException {

        PrintWriter out = null;
        BufferedReader in = null;
        allGames = new ArrayList<>();


        try {

            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Insert your name: ");
            out.println("stop");

            name = in.readLine();
            System.out.println("Client " + name + " wrote their name");
            player = new Player(name);

            out.println("Do you wish to create or join a game? \n1 - create a game \n2 - join a game");
            out.println("stop");
            line = in.readLine();

            if (line.equals("1")) {

                GameManager gameManager = new GameManager();
                allGames.add(gameManager);
                out.println("Insert a name for your game: ");
                out.println("stop");

                line = in.readLine();
                gameManager.setGameName(line);

                gameManager.addClientSocket(clientSocket);
                gameManager.addPlayer(player);
                player.setHost(true);
                out.println("Game created successfully!");
                out.println("stop");

                while (allClientsList.size() < 1) {
                    wait();
                }

                gameManager.startGame();
            }

            if (line.equals("2")) {
                player.setHost(false);
                int id = 0;

                for (GameManager games : allGames) {
                    out.println(id + " " + games.getGameName());
                    id++;
                }
                out.println("stop");

                line = in.readLine();

                allGames.get(Integer.parseInt(line)).addClientSocket(clientSocket);
                allGames.get(Integer.parseInt(line)).addPlayer(player);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAllClientsList(List<ClientHandler> allClientsList) {
        this.allClientsList = allClientsList;
    }

}

