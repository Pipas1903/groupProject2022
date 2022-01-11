package tvg.player;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private int lifePoints;
    private int order;
    private int position;
    private int diceRoll;

    private Boolean endOfTurn = Boolean.FALSE;
    private boolean dead = false;

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

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