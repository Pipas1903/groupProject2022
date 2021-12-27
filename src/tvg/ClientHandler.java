package tvg;

import tvg.Player.Player;

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

        PrintWriter out = null;
        BufferedReader in = null;

        try {
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("insert your name: ");
            String line;
            line = in.readLine();
            Player player = new Player(line);
            playerList.add(player);


            out.println();
            System.out.println("client " + line + " wrote their name");

            out.println();
            out.println("players that joined ");

            for (int i = 0; i < playerList.size(); i++) {
                out.println(playerList.get(i).getName());
            }
            boolean notQuit = true;

            while (notQuit) {
                out.println();
                out.println("press 1 to refresh or 2 to quit this menu");
                line = in.readLine();

                if (line.equals("1")) {
                    for (int i = 0; i < playerList.size(); i++) {
                        out.println(playerList.get(i).getName());
                    }
                }
                if (line.equals("2")) {
                    out.println("from now on, you won't be able to refresh the players in the game");
                    notQuit = false;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
