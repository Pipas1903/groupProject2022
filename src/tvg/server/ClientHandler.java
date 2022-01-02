package tvg.server;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private static HashMap<Socket, Player> playerList = new HashMap();
    private static List<Socket> SocketList = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();

    Scanner sc = new Scanner(System.in);
    Game game;
    Frame frame;

    public ClientHandler(Socket socket, List<Socket> list) {
        this.clientSocket = socket;
        this.SocketList = list;
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
            playerList.put(clientSocket, player);
            players.add(player);

            System.out.println("Client " + line + " wrote their name");

            boolean notQuit = true;

            if (SocketList.size() == 2) {

                game = new Game(players);

                for (Socket socket : SocketList) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(game);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }

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

