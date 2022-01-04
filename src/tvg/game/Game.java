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

    private List<Player> playerList;
    private Board gameBoard;
    private int round = 1;
    private final int lifeRestoration = 80;
    Player winner;
    Player currentPlayer;
    private int playerIndex = 0;


    public Game(List<Player> playerList) {
        this.playerList = playerList;

        gameBoard = new Board(6, 6, 612, 612);
        gameBoard.setBackground(new Color(192, 192, 192));

        gameBoard.armTrap.addActionListener(this);
        gameBoard.throwDice.addActionListener(this);
        gameBoard.upgradeTrap.addActionListener(this);
        gameBoard.stealTrap.addActionListener(this);
        gameBoard.passTurn.addActionListener(this);



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

    public boolean playerOwnsTile(Player player) {
        return Player.getPlayerOwnedTiles().get(player.getPosition()).equals(player.getName());
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
            winner = playerList.get(0);
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
        playingOrder();
        while (winner == null) {
            rounds();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameBoard.throwDice) {
            gameBoard.updateUI();
            throwDice();

        }
        if (e.getSource() == gameBoard.armTrap) {
            gameBoard.updateUI();
            armTrap();

        }
        if (e.getSource() == gameBoard.upgradeTrap) {
            gameBoard.updateUI();
            upgradeTrap();

        }
        if (e.getSource() == gameBoard.passTurn) {
            gameBoard.updateUI();
            passTurn();
        }
        if (e.getSource() == gameBoard.stealTrap) {
            stealTrap();
        }
    }


    public void throwDice() {



        currentPlayer.setDiceRoll(Dice.throwDice());

        gameBoard.textinho.setText(currentPlayer.getName() + " rolled " + currentPlayer.getDiceRoll());

        System.out.println("PLAYER ROLLED DICE");
        System.out.println(currentPlayer.getPosition());
        showPlayer();

        if (currentPlayer.getDiceRoll() + currentPlayer.getPosition() < gameBoard.getAllTiles().size()) {
            currentPlayer.setPosition(currentPlayer.getPosition() + currentPlayer.getDiceRoll());
            changeButtonsState();
            return;
        }

        currentPlayer.setPosition(currentPlayer.getPosition() + currentPlayer.getDiceRoll() - gameBoard.getAllTiles().size());
        currentPlayer.setLifePoints(currentPlayer.getLifePoints() + lifeRestoration);


        changeButtonsState();


    }

    public void showPlayer(){
        gameBoard.printPlayer(currentPlayer);
    }





    public void changeButtonsState() {

        if (!gameBoard.getTileAtIndex(currentPlayer.getPosition()).isBuyable()) {
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

        gameBoard.throwDice.setEnabled(false);
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.passTurn.setEnabled(true);
        armTrapValidation();
        gameBoard.stealTrap.setEnabled(false);
    }

    public void armTrap() {
        gameBoard.armTrap.setEnabled(false);
        Player.playerBuyTile(currentPlayer.getPosition(), currentPlayer.getName());
        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(currentPlayer.getPosition()).getPrice());
        gameBoard.getTileAtIndex(currentPlayer.getPosition()).setArmed(true);
        gameBoard.textinho.setText("you bought: " + gameBoard.getTileAtIndex(currentPlayer.getPosition()).getName());
        gameBoard.passTurn.setEnabled(true);

    }

    public void upgradeTrap() {
        gameBoard.upgradeTrap.setEnabled(false);
        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(currentPlayer.getPosition()).getUpgradePrice());
        gameBoard.getTileAtIndex(currentPlayer.getPosition()).setUpgraded(true);
        gameBoard.textinho.setText("you upgraded: " + gameBoard.getTileAtIndex(currentPlayer.getPosition()).getName());
    }

    public void passTurn() {
        gameBoard.passTurn.setEnabled(false);
        gameBoard.armTrap.setEnabled(false);
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.stealTrap.setEnabled(false);
        gameBoard.throwDice.setEnabled(true);

        playerIndex++;

        if (playerIndex > 1) {
            playerIndex = 0;
            round++;
        }

        currentPlayer = playerList.get(playerIndex);

        gameBoard.rounds.setText(currentPlayer.getName() + Messages.PLAYER_TURN);
        gameBoard.rounds.setText(Messages.ROUND + round);
        gameBoard.textinho.setText(currentPlayer.getName() + Messages.THROW_DICE);
    }

    public void upgradeTrapValidation() {
        if (!gameBoard.getTileAtIndex(currentPlayer.getPosition()).isUpgraded()) {
            if (currentPlayer.getLifePoints() > gameBoard.getTileAtIndex(currentPlayer.getLifePoints()).getUpgradePrice()) {
                gameBoard.upgradeTrap.setEnabled(true);
                return;
            }

        }
        gameBoard.upgradeTrap.setEnabled(false);

    }

    public void armTrapValidation() {
        if (!(currentPlayer.getLifePoints() > gameBoard.getTileAtIndex(currentPlayer.getPosition()).getPrice())) {
            gameBoard.armTrap.setEnabled(false);
            return;
        }
        gameBoard.armTrap.setEnabled(true);
    }

    public void trapStatusValidation() {
        if (!gameBoard.getTileAtIndex(currentPlayer.getPosition()).isUpgraded()) {
            if (currentPlayer.getLifePoints() > gameBoard.getTileAtIndex(currentPlayer.getLifePoints()).getUpgradePrice()) {
                gameBoard.stealTrap.setEnabled(true);
            }
        }
        gameBoard.stealTrap.setEnabled(false);
    }

    public void stealTrap() {
        gameBoard.stealTrap.setEnabled(false);
        Player.removeTileFromPlayer(currentPlayer.getPosition());
        Player.playerBuyTile(currentPlayer.getPosition(), currentPlayer.getName());
        currentPlayer.setLifePoints(currentPlayer.getLifePoints() - gameBoard.getTileAtIndex(currentPlayer.getPosition()).getUpgradePrice());
        gameBoard.textinho.setText("you stealed: " + gameBoard.getTileAtIndex(currentPlayer.getPosition()).getName());
    }

    public boolean armedTrapSituation() {
        if (gameBoard.getTileAtIndex(currentPlayer.getPosition()).isArmed()) {

            if (playerOwnsTile(currentPlayer)) {
                upgradeTrapValidation();
                gameBoard.passTurn.setEnabled(true);
                gameBoard.armTrap.setEnabled(false);
                gameBoard.stealTrap.setEnabled(false);
                gameBoard.throwDice.setEnabled(false);

                return true;

            }
            gameBoard.upgradeTrap.setEnabled(false);
            gameBoard.passTurn.setEnabled(true);
            gameBoard.armTrap.setEnabled(false);
            gameBoard.throwDice.setEnabled(false);
            trapStatusValidation();
            return true;
        }

        return false;
    }
}