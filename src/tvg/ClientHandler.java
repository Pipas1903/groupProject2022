package tvg;

import tvg.Player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private String name;
    private Player player = new Player(name);

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPlayerName() {
        return name;
    }

    @Override
    public void run() {

        PrintWriter out = null;
        BufferedReader in = null;

        try {
            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("escreve o teu nome: ");
            String line;
            name = in.readLine();
            while (clientSocket.isConnected()) {
                line = in.readLine();
                System.out.println(name + ":" + line);
                out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
