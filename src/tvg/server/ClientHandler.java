package tvg.server;

import tvg.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private static List<Player> playerList = new ArrayList<>();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        clientJoin();
    }

    public void clientJoin() {

        PrintWriter out = null;
        BufferedReader in = null;

        try {

            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Insert your name: ");
            String line;

            line = in.readLine();
            Player player = new Player(line);
            player.setSocket(clientSocket);
            playerList.add(player);

            System.out.println("Client " + line + " wrote their name");

            boolean notQuit = true;

            while (notQuit) {
                out.println("If you wish to see the players that already joined, press 1");
                out.println("Press 2 to quit this menu");
                line = in.readLine();

                if (line.equals("1")) {
                    out.println();
                    out.println("Players that joined: ");
                    for (int i = 0; i < playerList.size(); i++) {
                        out.println(playerList.get(i).getName());
                    }
                }

                if (line.equals("2")) {
                    out.println();
                    out.println("From now on, you won't be able to refresh the players in the game!");
                    notQuit = false;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

