package tvg.player;


import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;

public class Player implements Serializable {

    private String name;
    private int lifePoints;
    private int order;
    private static HashMap<Integer, String> playerOwnedTiles = new HashMap<>();
    private int position;
    private int diceRoll;
    private boolean isHost;
    private boolean yourTurn;
    JLabel playerSymbol;

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public static HashMap<Integer, String> getPlayerOwnedTiles() {
        return playerOwnedTiles;
    }

    public static void playerBuyTile(Integer tileNumber, String playerName) {
        playerOwnedTiles.put(tileNumber, playerName);
    }

    public static void removeTileFromPlayer(Integer tileNumber, String playerName) {
        playerOwnedTiles.remove(tileNumber, playerName);
    }

    public Player(String name) {
        this.lifePoints = 800;
        this.name = name;
        playerSymbol = new JLabel();
        playerSymbol.setOpaque(true);
        playerSymbol.setText(name);

    }

    public void putSymbolInTile(int x, int y) {
        playerSymbol.setBounds(x, y, 100, 30);
    }

    public void setPlayerSymbolColor(Color color) {
        playerSymbol.setBackground(color);
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}