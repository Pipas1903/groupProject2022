package tvg.server;

import tvg.game.Game;
import tvg.player.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameManager {

    private List<Socket> clients;
    private List<Player> players = new ArrayList<>();
    private Game game;
    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void addClientSocket(Socket socket) {
        clients.add(socket);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getHost() {
        for (Player player : players) {
            if (player.isHost()) {
                return player;
            }
        }
        return null;
    }

    public void startGame() throws IOException {
        playingOrder();
        game = new Game(players);
        send();
    }

    /*
    public synchronized static void sendGame() throws IOException {
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
*/
    public void send() throws IOException {

        PrintWriter out = null;

        for (Socket client : clients) {

            out = new PrintWriter(client.getOutputStream(), true);
            out.println("start");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(game);
            objectOutputStream.flush();
            objectOutputStream.close();

        }
    }

    public void playingOrder() {

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
