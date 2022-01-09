package tvg.game;

import tvg.board.Board;
import tvg.board.Tile;
import tvg.common.Messages;
import tvg.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Game implements ActionListener, Serializable {

    private static final long serialVersionUID = 1L;

    public List<Player> playerList;
    public HashMap<Integer, String> armedTrapsRegister = new HashMap<>();

    int luck = 150;

    private Board gameBoard;
    private int round = 1;
    private final int lifeRestoration = 80;
    Player currentPlayer = null;

    public int playerIndex = 0;

    int playerLocation;

    public Game(List<Player> playerList) {

        this.playerList = playerList;

        gameBoard = new Board(6, 6, 612, 612);
        gameBoard.setBackground(new Color(192, 192, 192));

        gameBoard.armTrap.addActionListener(this);
        gameBoard.throwDice.addActionListener(this);
        gameBoard.upgradeTrap.addActionListener(this);
        gameBoard.stealTrap.addActionListener(this);
        gameBoard.passTurn.addActionListener(this);
        gameBoard.setPlayerList(playerList);
        gameBoard.panelInfo();


    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void showFirstPlayer() {
        gameBoard.rounds.setText(Messages.ROUND + round);
        gameBoard.textinho.setText(currentPlayer.getName() + Messages.THROW_DICE);
    }

    public boolean playerOwnsTile() {
        return armedTrapsRegister.get(playerLocation).equals(currentPlayer.getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameBoard.throwDice) {
            throwDice();
            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.armTrap) {
            armTrap();

            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.upgradeTrap) {
            upgradeTrap();


            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.passTurn) {
            try {
                passTurn();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.stealTrap) {
            stealTrap();

            gameBoard.updateUI();

        }
    }

    public void showPlayer() {
        gameBoard.printPlayer(currentPlayer);
        gameBoard.updateUI();
    }

    public void throwDice() {

        resetEndOfTurn();

        currentPlayer.setDiceRoll(Dice.throwDice());

        gameBoard.textinho.setText(currentPlayer.getName() + " rolled " + currentPlayer.getDiceRoll());


        System.out.println("PLAYER ROLLED DICE");
        System.out.println(currentPlayer.getPosition());
        showPlayer();
        System.out.println("PLAYER ROLLED " + currentPlayer.getDiceRoll());


        if (currentPlayer.getDiceRoll() + currentPlayer.getPosition() < gameBoard.getAllTiles().size()) {

            currentPlayer.setPosition(currentPlayer.getPosition() + currentPlayer.getDiceRoll());


        } else {
            currentPlayer.setPosition(currentPlayer.getPosition() + currentPlayer.getDiceRoll() - gameBoard.getAllTiles().size());
            currentPlayer.setLifePoints(currentPlayer.getLifePoints() + lifeRestoration);

            System.out.println(currentPlayer.getName() + " LIFE POINTS AFTER PASSING ON START " + currentPlayer.getLifePoints());
        }
        System.out.println(currentPlayer.getName() + " AT TILE NUMBER " + currentPlayer.getPosition());
        showPlayer();

        playerLocation = currentPlayer.getPosition();
        changeButtonsState();

    }

    public void changeButtonsState() {

        if (!gameBoard.getTileAtIndex(playerLocation).isBuyable()) {

            checkEvents();

            gameBoard.upgradeTrap.setEnabled(false);
            gameBoard.stealTrap.setEnabled(false);
            gameBoard.armTrap.setEnabled(false);

            return;
        }

        if (armedTrapSituation()) {
            return;
        }

        armTrapValidation();
        gameBoard.passTurn.setEnabled(true);
        gameBoard.throwDice.setEnabled(false);
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);

    }

    public void checkEvents() {
        if (gameBoard.getTileAtIndex(playerLocation).isBadLuck()) {

            gameBoard.textinho.setText("Oh oh... You lost 150 life points");

            currentPlayer.setLifePoints(Math.max(currentPlayer.getLifePoints() - luck, 0));

            System.out.println(currentPlayer.getName() + " LANDED ON BAD LUCK AND HAS NOW " + currentPlayer.getLifePoints());

            gameBoard.passTurn.setEnabled(true);
            gameBoard.throwDice.setEnabled(false);
            gameBoard.updateUI();

        } else if (gameBoard.getTileAtIndex(playerLocation).isGoodLuck()) {

            gameBoard.textinho.setText("YEY! You restored 150 life points");
            currentPlayer.setLifePoints(currentPlayer.getLifePoints() + luck);
            gameBoard.passTurn.setEnabled(true);
            gameBoard.throwDice.setEnabled(false);
            gameBoard.updateUI();

            System.out.println(currentPlayer.getName() + " LANDED ON GOOD LUCK AND HAS NOW " + currentPlayer.getLifePoints());

        } else if (gameBoard.getTileAtIndex(playerLocation).getName().equals("start")) {

            gameBoard.passTurn.setEnabled(true);
            gameBoard.throwDice.setEnabled(false);
            gameBoard.updateUI();

            System.out.println(currentPlayer.getName() + " LANDED ON START AND HAS NOW " + currentPlayer.getLifePoints());
        } else {
            executeRandomEvent();
        }
    }

    public void executeRandomEvent() {

        Events randomEvent = Events.randomEvent();
        gameBoard.textinho.setText(randomEvent.message);

        gameBoard.passTurn.setEnabled(true);
        gameBoard.throwDice.setEnabled(false);

        switch (randomEvent) {
            case WIN_TRAP:
                System.out.println(currentPlayer.getName() + " GOT EVENT " + Events.WIN_TRAP);

                int freeTrapNumber = findOneFreeTrap();

                if (freeTrapNumber <= gameBoard.getAllTiles().size()) {
                    playerArmTrap(freeTrapNumber, currentPlayer.getName());
                    gameBoard.getTileAtIndex(freeTrapNumber).setOwner(currentPlayer.getName());
                    gameBoard.getTileAtIndex(freeTrapNumber).setArmed(true);
                    gameBoard.updateUI();
                    break;
                }

                gameBoard.textinho.setText("<html>There are no more available traps.<br> Take 100 life points as compensation.</html>");
                currentPlayer.setLifePoints(currentPlayer.getLifePoints() + 100);

                break;

            case LOSE_TRAP:

                System.out.println(currentPlayer.getName() + " GOT EVENT " + Events.LOSE_TRAP);
                tryToRemovePlayerTrap();
                break;

            case TASTY_SNACK:

                System.out.println(currentPlayer.getName() + " GOT EVENT " + Events.TASTY_SNACK + " AND HAS NOW " + currentPlayer.getLifePoints());
                currentPlayer.setLifePoints(currentPlayer.getLifePoints() + randomEvent.lifePoints);

                break;

            case UPGRADE_TRAP:

                System.out.println(currentPlayer.getName() + " GOT EVENT " + Events.UPGRADE_TRAP);
                tryToUpgradePlayerTrap();
                break;

            case THROW_DICE_AGAIN:

                System.out.println(currentPlayer.getName() + " GOT EVENT " + Events.THROW_DICE_AGAIN);

                gameBoard.throwDice.setEnabled(true);
                gameBoard.passTurn.setEnabled(false);
                break;

            case TRIP_ON_SHOE_LACE:

                currentPlayer.setLifePoints(currentPlayer.getLifePoints() - randomEvent.lifePoints);
                System.out.println(currentPlayer.getName() + " GOT EVENT " + Events.TRIP_ON_SHOE_LACE + " AND HAS NOW " + currentPlayer.getLifePoints());

                break;
        }
        gameBoard.updateUI();
    }

    public int findOneFreeTrap() {
        for (Tile tile : gameBoard.getAllTiles()) {
            if (!tile.isArmed() && tile.isBuyable()) {
                return tile.getNumber();
            }
        }
        return 30;
    }

    public void tryToUpgradePlayerTrap() {
        for (Map.Entry<Integer, String> entry : armedTrapsRegister.entrySet()) {
            if (entry.getValue().equals(currentPlayer.getName())) {
                if (!gameBoard.getTileAtIndex(entry.getKey()).isUpgraded()) {
                    gameBoard.getTileAtIndex(entry.getKey()).setUpgraded(true);
                    System.out.println("Player got a trap upgraded for free");
                    System.out.println("upgraded trap: " + gameBoard.getTileAtIndex(entry.getKey()));
                    return;
                }
            }
        }
        gameBoard.textinho.setText("No traps to upgrade were found");
    }

    public void tryToRemovePlayerTrap() {
        for (Map.Entry<Integer, String> entry : armedTrapsRegister.entrySet()) {
            if (entry.getValue().equals(currentPlayer.getName()) && !gameBoard.getTileAtIndex(entry.getKey()).isUpgraded()) {
                armedTrapsRegister.remove(entry.getKey());
                gameBoard.getTileAtIndex(entry.getKey()).setArmed(false);
                gameBoard.getTileAtIndex(entry.getKey()).setOwner("");
                System.out.println("Player lost a trap");
                System.out.println("lost trap: " + gameBoard.getTileAtIndex(entry.getKey()));
            }
        }
        gameBoard.textinho.setText("<html>You don't have traps,<br> so you lose anything!</html>");
    }

    public void armTrapValidation() {

        if (!(currentPlayer.getLifePoints() > gameBoard.getTileAtIndex(currentPlayer.getPosition()).getPrice())) {
            gameBoard.armTrap.setEnabled(false);
            return;
        }
        gameBoard.armTrap.setEnabled(true);
    }

    public void upgradeTrapValidation() {

        if (!gameBoard.getTileAtIndex(playerLocation).isUpgraded()) {

            if (currentPlayer.getLifePoints() > gameBoard.getTileAtIndex(playerLocation).getUpgradePrice()) {
                gameBoard.upgradeTrap.setEnabled(true);
                return;
            }

        }
        gameBoard.upgradeTrap.setEnabled(false);
    }

    public void trapStatusValidation() {

        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(playerLocation).getDamageDealt());


        System.out.println("PLAYER " + currentPlayer.getName() + " FELL ON A TRAP AND LOST " + gameBoard.getTileAtIndex(playerLocation).getDamageDealt() + " LIFE POINTS");

        if (!gameBoard.getTileAtIndex(playerLocation).isUpgraded()) {
            if (currentPlayer.getLifePoints() > gameBoard.getTileAtIndex(playerLocation).getUpgradePrice()) {
                gameBoard.stealTrap.setEnabled(true);
                return;
            }
        }
        gameBoard.stealTrap.setEnabled(false);
    }

    public boolean armedTrapSituation() {

        if (gameBoard.getTileAtIndex(playerLocation).isArmed()) {

            if (playerOwnsTile()) {

                upgradeTrapValidation();

                gameBoard.passTurn.setEnabled(true);

                gameBoard.armTrap.setEnabled(false);
                gameBoard.stealTrap.setEnabled(false);
                gameBoard.throwDice.setEnabled(false);
                return true;
            }

            trapStatusValidation();
            gameBoard.passTurn.setEnabled(true);
            gameBoard.upgradeTrap.setEnabled(false);
            gameBoard.armTrap.setEnabled(false);
            gameBoard.throwDice.setEnabled(false);

            return true;
        }

        return false;
    }

    public void armTrap() {

        gameBoard.armTrap.setEnabled(false);

        playerArmTrap(playerLocation, currentPlayer.getName());
        gameBoard.getTileAtIndex(playerLocation).setOwner(currentPlayer.getName());

        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(playerLocation).getPrice());


        gameBoard.getTileAtIndex(playerLocation).setArmed(true);
        gameBoard.textinho.setText("you bought: " + gameBoard.getTileAtIndex(playerLocation).getName());
        gameBoard.passTurn.setEnabled(true);

        gameBoard.updateUI();

        showPlayer();

        System.out.println(currentPlayer.getName() + " HAS " + currentPlayer.getLifePoints() + " LIFE POINTS AFTER ARMING TRAP");
    }

    public void upgradeTrap() {

        gameBoard.upgradeTrap.setEnabled(false);

        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(playerLocation).getUpgradePrice());


        gameBoard.getTileAtIndex(playerLocation).setUpgraded(true);
        gameBoard.textinho.setText("you upgraded: " + gameBoard.getTileAtIndex(playerLocation).getName());

        gameBoard.updateUI();

        showPlayer();


        System.out.println(currentPlayer.getName() + " HAS " + currentPlayer.getLifePoints() + " LIFE POINTS AFTER UPGRADING TRAP");

    }

    public void stealTrap() {

        gameBoard.stealTrap.setEnabled(false);
        playerLoseTrap(playerLocation);
        playerArmTrap(playerLocation, currentPlayer.getName());
        gameBoard.getTileAtIndex(playerLocation).setOwner(currentPlayer.getName());

        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(playerLocation).getUpgradePrice());
        gameBoard.updateUI();

        showPlayer();

        gameBoard.textinho.setText("you stole: " + gameBoard.getTileAtIndex(playerLocation).getName());


        gameBoard.textinho.setText("you stole: " + gameBoard.getTileAtIndex(playerLocation).getName());

        gameBoard.updateUI();


        System.out.println(currentPlayer.getName() + " HAS " + currentPlayer.getLifePoints() + " LIFE POINTS AFTER STEALING TRAP");


    }

    public void passTurn() throws IOException {
        System.out.println(currentPlayer.getName() + " PASSED TURN");

        gameBoard.throwDice.setEnabled(false);
        gameBoard.passTurn.setEnabled(false);
        gameBoard.armTrap.setEnabled(false);
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);

        playerIndex++;
        System.out.println("player index: " + playerIndex);

        if (playerIndex >= playerList.size()) {
            playerIndex = 0;
            round++;
            System.out.println("resetou");
        }
        showPlayer();

        //showPlayerInfo();
        currentPlayer.setEndOfTurn(Boolean.TRUE);

        gameBoard.rounds.setText(Messages.ROUND + round);

        gameBoard.textinho.setText(currentPlayer.getName() + Messages.THROW_DICE);
        gameBoard.updateUI();
    }

    public void resetEndOfTurn() {
        for (Player player : playerList) {
            player.setEndOfTurn(Boolean.FALSE);
        }
        currentPlayer.setEndOfTurn(Boolean.FALSE);

    }

    public void turnOffOtherPlayerButtons() {
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.passTurn.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);
        gameBoard.throwDice.setEnabled(false);
        gameBoard.armTrap.setEnabled(false);
        gameBoard.updateUI();
    }

    public void turnButtonsOnForCurrentPlayer() {
        gameBoard.throwDice.setEnabled(true);

        gameBoard.armTrap.setEnabled(false);
        gameBoard.passTurn.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);
        gameBoard.upgradeTrap.setEnabled(false);

        gameBoard.updateUI();
    }

    public HashMap<Integer, String> getArmedTrapsRegister() {
        return armedTrapsRegister;
    }

    public void setArmedTrapsRegister(HashMap<Integer, String> armedTrapsRegister) {
        this.armedTrapsRegister = armedTrapsRegister;
    }

    public void playerArmTrap(Integer tileNumber, String playerName) {
        armedTrapsRegister.put(tileNumber, playerName);
    }

    public void playerLoseTrap(Integer tileNumber) {
        armedTrapsRegister.remove(tileNumber);
    }
}