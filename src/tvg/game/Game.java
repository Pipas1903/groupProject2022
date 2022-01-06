package tvg.game;

import tvg.ConsoleUI;
import tvg.board.Board;
import tvg.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Game implements ActionListener, Serializable {

    private static final long serialVersionUID = 1L;

    public List<Player> playerList = new ArrayList<>();

    private Board gameBoard;
    private int round = 1;
    private final int lifeRestoration = 80;
    Player currentPlayer = null;
    private int playerIndex = 0;
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
        gameBoard.panelInfo(this.playerList);

        start();
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

    public void start() {
        playingOrder();
    }

    public void rounds() {
        currentPlayer = playerList.get(0);

        gameBoard.rounds.setText(currentPlayer.getName() + Messages.PLAYER_TURN);
        gameBoard.rounds.setText(Messages.ROUND + round);
        gameBoard.textinho.setText(currentPlayer.getName() + Messages.THROW_DICE);
        checkGameStatus();
    }

    public boolean playerOwnsTile() {
        return Player.getPlayerOwnedTiles().get(playerLocation).equals(currentPlayer.getName());
    }

    public void chooseGameMode() {
        // until death or limited rounds
        Scanner sc = new Scanner(System.in);
        ConsoleUI.printModeSelection();

        if (sc.nextInt() == 1) {
            tenRoundsGameMode();

        } else if (sc.nextInt() == 2) {
            longVersionGameMode();
        }

    }

    public synchronized void playingOrder() {

        Random random = new Random();

        ArrayList<Integer> number = random.ints(1, 10).
                distinct().
                limit(4).
                boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));

        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).setOrder(number.get(i));
        }

        playerList.sort(Comparator.comparing(Player::getOrder));

    }

    public boolean checkGameStatus() {
        removeFaintedPlayer();
        if (playerList.size() == 1) {
            System.out.println("Game over \nThe winner is: " + playerList.get(0).getName());
            return false;
        }
        return true;
    }

    public void removeFaintedPlayer() {
        playerList.removeIf(player -> player.getLifePoints() <= 0 & !Player.getPlayerOwnedTiles().containsValue(player.getName()));
    }

    public void tenRoundsGameMode() {
        while (round <= 10 & checkGameStatus()) {
            start();
            round++;
        }
    }

    public void longVersionGameMode() {
        start();
        while (checkGameStatus()) {
            rounds();
        }
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
            passTurn();
            gameBoard.updateUI();
        }
        if (e.getSource() == gameBoard.stealTrap) {
            stealTrap();
            gameBoard.updateUI();
        }
    }

    public void showPlayer() {
        gameBoard.printPlayer(currentPlayer);
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
        }
        System.out.println(currentPlayer.getName() + " AT TILE NUMBER " + currentPlayer.getPosition());
        showPlayer();
        playerLocation = currentPlayer.getPosition();
        changeButtonsState();

    }

    public void changeButtonsState() {

        if (!gameBoard.getTileAtIndex(playerLocation).isBuyable()) {

            gameBoard.passTurn.setEnabled(true);

            gameBoard.upgradeTrap.setEnabled(false);
            gameBoard.stealTrap.setEnabled(false);
            gameBoard.armTrap.setEnabled(false);
            gameBoard.throwDice.setEnabled(false);

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

        Player.playerOwnTile(playerLocation, currentPlayer.getName());

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

        Player.removeCurrentTileOwner(playerLocation);
        Player.playerOwnTile(playerLocation, currentPlayer.getName());

        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(playerLocation).getUpgradePrice());
        gameBoard.textinho.setText("you stole: " + gameBoard.getTileAtIndex(playerLocation).getName());
        gameBoard.updateUI();

        showPlayer();
        System.out.println(currentPlayer.getName() + " HAS " + currentPlayer.getLifePoints() + " LIFE POINTS AFTER STEALING TRAP");
    }

    public void passTurn() {
        System.out.println(currentPlayer.getName() + " PASSED TURN");

        currentPlayer.setEndOfTurn(true);

        gameBoard.throwDice.setEnabled(false);
        gameBoard.passTurn.setEnabled(false);
        gameBoard.armTrap.setEnabled(false);
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);

        playerIndex++;

        if (playerIndex >= playerList.size()) {
            playerIndex = 0;
            round++;
        }

        currentPlayer = playerList.get(playerIndex);


        gameBoard.rounds.setText(currentPlayer.getName() + Messages.PLAYER_TURN);
        gameBoard.rounds.setText(Messages.ROUND + round);
        gameBoard.textinho.setText(currentPlayer.getName() + Messages.THROW_DICE);
        gameBoard.updateUI();
    }

    public void resetEndOfTurn() {
        for (Player player : playerList) {
            player.setEndOfTurn(false);
        }
    }

    public void turnOffOtherPlayerButtons() {
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.passTurn.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);
        gameBoard.throwDice.setEnabled(false);
        gameBoard.armTrap.setEnabled(false);
        gameBoard.updateUI();
    }
}