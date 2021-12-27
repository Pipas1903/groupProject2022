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
            out.println("players that joined ");
            for (int i = 0; i < playerList.size(); i++) {
                out.println(playerList.get(i).getName());

            }
            out.println();
            System.out.println("client " + line + " wrote their name");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
