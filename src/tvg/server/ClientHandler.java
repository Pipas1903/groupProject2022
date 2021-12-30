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

            while (notQuit) {
                out.println("If you wish to see the players that already joined, press 1            Press 2 to quit this menu");
                line = in.readLine();

                if (line.equals("1")) {
                    out.println("    Players that joined: " + playerList.iterator().next().getName());
                    line = in.readLine();
                }

                if (line.equals("2")) {
                    out.println("     From now on, you won't be able to refresh the players in the game!");
                    notQuit = false;
                    line = in.readLine();
                }

            }

            out.println("do you wish to create a game or join one?    1 - create       2 - join");
            line = in.readLine();

            if (line.equals("1")) {
                Game game = new Game(playerList);
                Frame frame = new Frame(game);
                frame.start();
            }
            if (line.equals("2")) {
                out.println("please wait for a game to start");
            }

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

