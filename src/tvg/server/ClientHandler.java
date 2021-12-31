package tvg.server;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private static List<Player> playerList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    Game game;
    Frame frame;


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
            String line;

            line = in.readLine();
            Player player = new Player(line);
            player.setSocket(clientSocket);
            playerList.add(player);

            System.out.println("Client " + line + " wrote their name");

            boolean notQuit = true;

            while (playerList.size() != 2) {
                out.println("waiting for other clients");
                game = new Game(playerList);
            }
            frame = new Frame(game);
            frame.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Player> getFourRandomPlayers() {
        Random random = new Random();

        List<Player> chosenPlayers = new ArrayList<>();
        ArrayList<Integer> number = random.ints(0, playerList.size()).
                distinct().
                limit(4).
                boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));

        for (int i = 0; i < 4; i++) {
            chosenPlayers.add(playerList.get(number.get(i)));
        }

        return chosenPlayers;
    }

}

