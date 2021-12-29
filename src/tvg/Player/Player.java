package tvg.Player;

import tvg.Game.Tile;

import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private Socket socket;
    private String name;
    private int money;
    private int order;
    private List<Tile> listOfOwnedTiles = new ArrayList<>();
    private int position;

    public Player(String name) {
        this.money = 800;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
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
}
