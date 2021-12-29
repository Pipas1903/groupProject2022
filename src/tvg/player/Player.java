package tvg.player;

import tvg.board.Tile;

import java.net.Socket;
import java.util.HashMap;

public class Player {

    private Socket socket;
    private String name;
    private int lifePoints;
    private int order;
    private static HashMap<Integer, String> playerOwnedTiles;
    private int position;
    private int lastPosition;

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public static HashMap<Integer, String> getPlayerOwnedTiles() {
        return playerOwnedTiles;
    }

    public static void playerBuyTile(Integer tileNumber, String playerName) {
        playerOwnedTiles.put(tileNumber,playerName);
    }

    public static void removeTileFromPlayer(Integer tileNumber, String playerName){
        playerOwnedTiles.remove(tileNumber,playerName);
    }

    public Player(String name) {
        this.lifePoints = 800;
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}