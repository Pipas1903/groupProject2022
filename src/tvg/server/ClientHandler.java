package tvg.server;

import tvg.game.Game;

import java.io.*;
import java.net.Socket;
import java.util.*;

/*
 * client handler
 */
public class ClientHandler extends Thread {

    public final Socket clientSocket;
    private List<ClientHandler> allClientsList;
    private static List<GameManager> allGames;

    Scanner sc = new Scanner(System.in);
    Game game;

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
            name = in.readLine();
            System.out.println("Client " + name + " wrote their name");

            out.println("Do you wish to: 1 - create a game || 2 - join a game");
            line = in.readLine();

            if (line.equals("1")) allGames.add(new GameManager());

            if (line.equals("2")) {
                int id = 1;

                for (GameManager games : allGames) {
                    out.println(id + " " + games.getGameName());
                    id++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAllClientsList(List<ClientHandler> allClientsList) {
        this.allClientsList = allClientsList;
    }

}

