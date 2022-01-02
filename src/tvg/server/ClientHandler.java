package tvg.server;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ClientHandler extends Thread {

    public final Socket clientSocket;

    Scanner sc = new Scanner(System.in);
    Game game;

    public String line;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
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

        try {

            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Insert your name: ");


            line = in.readLine();


            System.out.println("Client " + line + " wrote their name");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

