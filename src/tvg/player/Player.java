package tvg.player;

import tvg.board.Tile;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private Socket socket;
    private String name;
    private int lifePoints;
    private int order;
    private List<Tile> listOfOwnedTiles = new ArrayList<>();
    private int position;

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

    public List<Tile> getListOfOwnedTiles() {
        return listOfOwnedTiles;
    }

    public void setListOfOwnedTiles(List<Tile> listOfOwnedTiles) {
        this.listOfOwnedTiles = listOfOwnedTiles;
    }

    public int getPosition() {
        return position;
    }

    public void addTilesToList(Tile e) {
        listOfOwnedTiles.add(e);
    }

    public void removeTilesFromList(Tile e){
        listOfOwnedTiles.remove(e);
    }
}
