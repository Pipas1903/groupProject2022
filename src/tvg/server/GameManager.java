package tvg.server;

import tvg.common.Messages;
import tvg.game.Game;
import tvg.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class GameManager {

    private HashMap<Socket, Player> clientSocketList = new HashMap<>();
    private List<Player> playersList = new ArrayList<>();

    private Game game;
    private String gameName;

    ObjectInputStream objectInputStream = null;

    public void addPlayer(Socket socket, Player player) {
        clientSocketList.put(socket, player);
        playersList.add(player);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public HashMap<Socket, Player> getClientSocketList() {
        return clientSocketList;
    }

    public void startGame() throws IOException, ClassNotFoundException {

        playingOrder();

        System.out.println("initializing game");

        game = new Game(playersList);
        game.setCurrentPlayer(playersList.get(0));

        send();

        gameLoop();
    }

    public void send() throws IOException {

        ObjectOutputStream objectOutputStream = null;

        for (Map.Entry<Socket, Player> map : getClientSocketList().entrySet()) {

            System.out.println("sending game to client " + map.getKey().getInetAddress());
            objectOutputStream = new ObjectOutputStream(map.getKey().getOutputStream());
            objectOutputStream.writeObject(game);
            objectOutputStream.flush();
        }
    }

    public void gameLoop() throws IOException, ClassNotFoundException {
        int playersAlive = 4;

        while (playersAlive > 1) {

            playersAlive = 0;

            boolean received = false;

            for (Map.Entry<Socket, Player> map : clientSocketList.entrySet()) {
                System.out.println();
                if (game.getCurrentPlayer().getName().equals(map.getValue().getName())) {
                    receive(map.getKey());
                    received = true;
                    break;
                }
            }

            if (received) {
                for (Player player : game.playerList) {
                    if (player.getLifePoints() <= 0) {
                        player.setDead(true);
                        System.out.println(player.getName() + " is dead.");
                    }
                }

                send();
            }
            for (Player player : game.playerList) {
                if (!player.isDead()) {
                    playersAlive++;
                }
            }

            if (playersAlive == 1) {
                winner();
            }
        }

    }

    public void winner() throws IOException, ClassNotFoundException {
        for (Player player : game.playerList) {
            if (!player.isDead()) {
                Map.Entry<Socket, Player> entry = Objects.requireNonNull(clientSocketList.entrySet().stream().filter(p -> p.getValue().getName().equals(player.getName())).findAny().orElse(null));
                receive(entry.getKey());
                String winner = entry.getValue().getName();
                game.getGameBoard().textinho.setText("The winner is: " + winner + "!");
                send();
                System.out.println("sent winner");
                System.out.println("GAME " + gameName + " IS OVER");
                return;
            }
        }
    }

    public void receive(Socket clientSocket) throws IOException, ClassNotFoundException {

        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            game = (Game) object;
            System.out.println("Recebi um jogo: " + game);
        }
    }

    public void playingOrder() {

        Random random = new Random();

        ArrayList<Integer> number = random.ints(1, 10).
                distinct().
                limit(4).
                boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));

        for (int i = 0; i < playersList.size(); i++) {
            playersList.get(i).setOrder(number.get(i));
        }

        playersList.sort(Comparator.comparing(Player::getOrder));
    }

}
