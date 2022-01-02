package tvg.server;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static int port = 930;

    private static List<ClientHandler> clientHandlers = new ArrayList<>();

    private static List<Player> players = new ArrayList<>();

    public static HashMap<Socket, Player> playerSocket = new HashMap();

    private static Game game;

    public static void main(String[] args) {
        initializerServer();
    }

    public static void initializerServer() {

        try {

            serverSocket = new ServerSocket(port);
            System.out.println("waiting for client to connect");

            while (true) {

                clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                clientHandler.start();

                System.out.println("client connected - " + clientSocket.getInetAddress().getHostAddress());

                Player player = new Player(clientHandler.line);

                playerSocket.put(clientSocket, player);

                players.add(player);

                clientHandlers.add(clientHandler);

                sendGame();
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }


    }

    public static void sendGame() throws IOException {
        if (playerSocket.size() == 2) {

            playingOrder();
            game = new Game(players);
            send();
        }
    }

    public static void receive(Socket clientSocket) throws IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            game = (Game) object;
        }

    }

    public static void send() throws IOException {

        for (Map.Entry<Socket, Player> entry : playerSocket.entrySet()) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(entry.getKey().getOutputStream());
            objectOutputStream.writeObject(game);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }


    public static void playingOrder() {

        Random random = new Random();

        ArrayList<Integer> number = random.ints(1, 10).
                distinct().
                limit(4).
                boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setOrder(number.get(i));
        }

        players.sort(Comparator.comparing(Player::getOrder));
    }

}
