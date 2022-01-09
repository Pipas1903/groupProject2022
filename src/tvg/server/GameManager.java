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

    ObjectOutputStream player1Out;
    ObjectOutputStream player2Out;

    private Game game;
    private String gameName;
    private String gameMode;

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

        if (this.gameMode.equals("ten rounds")) {
            gameLoopTenRounds();
        }
        if (this.gameMode.equals("until one survivor")) {
            gameLoopUntilOneSurviver();
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

    public void removeFaintedPlayerFromGameList() {
        game.playerList.removeIf(player -> player.getLifePoints() <= 0);
    }

    // PODE E DEVE SER MELHORADO
    public void removeFaintedPlayerFromServer() throws IOException {
        for (Map.Entry<Socket, Player> client : clientSocketList.entrySet()) {
            if (client.getValue().getLifePoints() <= 0) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getKey().getOutputStream());
                objectOutputStream.writeObject(Messages.GAME_OVER);
                client.getKey().close();
                clientSocketList.remove(client.getKey());
            }
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

    public void gameLoopTenRounds() throws IOException, ClassNotFoundException {
        int rounds = 0;
        while (rounds <= 20) {
            boolean received = false;

            for (Map.Entry<Socket, Player> map : clientSocketList.entrySet()) {
                System.out.println();
                if (game.getCurrentPlayer().getName().equals(map.getValue().getName())) {
                    receive(map.getKey());
                    //removeFaintedPlayerFromGameList();
                    removeFaintedPlayerFromServer();
                    received = true;
                    break;
                }
            }

            if (received) {
                send();
            }
            rounds++;
        }
    }

    public void gameLoopUntilOneSurviver() throws IOException, ClassNotFoundException {
        while (playersList.size() > 1) {
            boolean received = false;

            for (Map.Entry<Socket, Player> map : clientSocketList.entrySet()) {
                System.out.println();
                if (game.getCurrentPlayer().getName().equals(map.getValue().getName())) {
                    receive(map.getKey());
                    // removeFaintedPlayerFromServer();
                    removeFaintedPlayerFromGameList();
                    received = true;
                    break;
                }
            }

            if (received) {
                send();
            }
        }
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
