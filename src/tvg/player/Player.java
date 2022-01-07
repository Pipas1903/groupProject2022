package tvg.player;


import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;

public class Player implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;

    private int lifePoints;
    private int order;
    private int position;
    private int diceRoll;

    private Boolean endOfTurn = Boolean.FALSE;

    private static HashMap<Integer, String> playerOwnedTiles = new HashMap<>();

    public Boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void setEndOfTurn(Boolean endOfTurn) {
        this.endOfTurn = endOfTurn;
    }

    public Player(String name) {
        this.lifePoints = 800;
        this.name = name;
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