package tvg.game;

import tvg.ConsoleUI;
import tvg.board.Board;
import tvg.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements ActionListener {

    private List<Player> playerList;
    private Board gameBoard;
    private int round;
    private final int lifeRestoration = 80;
    Player winner;
    Player currentPlayer;
    boolean click = true;

    public Game(List<Player> playerList) {
        this.playerList = playerList;

        gameBoard = new Board(6, 6, 612, 612);
        gameBoard.setBackground(new Color(192, 192, 192));
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void start() {
        playingOrder();
    }

    public void rounds() {
        playingOrder();
        for (Player i : playerList) {
            currentPlayer = i;
            playerTurnDecision(i);
        }

    }

    public void turn(Player player) {
        System.out.println(player.getName() + Messages.THROW_DICE);
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        player.setDiceRoll(Dice.throwDice());
        System.out.println(Messages.DICE_FACE + player.getDiceRoll());

        if (player.getDiceRoll() + player.getPosition() < gameBoard.getAllTiles().size()) {
            player.setPosition(player.getPosition() + player.getDiceRoll());
            playerTurnDecision(player);
            return;
        }

        player.setPosition(player.getPosition() + player.getDiceRoll() - gameBoard.getAllTiles().size());
        player.setLifePoints(player.getLifePoints() + lifeRestoration);
    }

    public void playerTurnDecision(Player player) {
        // checking if tile is buyable, if it isn't, player can't do anything

        gameBoard.throwDice.addActionListener(this);

/*
        gameBoard.textinho.setText("you rolled " + player.getDiceRoll());
        System.out.println("carreguei na merda do botão");*/

        if (!gameBoard.getAllTiles().get(player.getPosition()).isBuyable()) {

            // EVENT HAPPENS
            gameBoard.passTurn.setEnabled(true);
            gameBoard.armTrap.setEnabled(false);
            gameBoard.stealTrap.setEnabled(false);
            gameBoard.upgradeTrap.setEnabled(false);
            gameBoard.throwDice.setEnabled(false);
            ConsoleUI.printPassTurn();
        }
    }




        /*
        // checking if tile has owner
        if (gameBoard.getTileAtIndex(player.getPosition()).isArmed()) {

            if (playerOwnsTile(player)) {
                if (!gameBoard.getTileAtIndex(player.getPosition()).isUpgraded()) {
                    gameBoard.upgradeTrap.setEnabled(true);
                    gameBoard.passTurn.setEnabled(true);
                    gameBoard.armTrap.setEnabled(false);
                    gameBoard.stealTrap.setEnabled(false);
                    ConsoleUI.printUpgradeOption();

                    return;
                }

            }
            if (!gameBoard.getTileAtIndex(player.getPosition()).isUpgraded()) {
                gameBoard.upgradeTrap.setEnabled(false);
                gameBoard.passTurn.setEnabled(true);
                gameBoard.armTrap.setEnabled(false);
                gameBoard.stealTrap.setEnabled(true);
                ConsoleUI.printStealOption();


                return;
            }
        }
        ConsoleUI.printBuyOption();
        gameBoard.armTrap.setEnabled(true);
        gameBoard.upgradeTrap.setEnabled(false);
        gameBoard.passTurn.setEnabled(true);
        gameBoard.stealTrap.setEnabled(false);
        System.out.println(Player.getPlayerOwnedTiles());
*/


    public boolean playerOwnsTile(Player player) {
        return Player.getPlayerOwnedTiles().get(player.getPosition()).equals(player.getName());
    }


    public void mouseClicked(MouseEvent e) {
        for (JButton button : gameBoard.buttonsList)
            if (e.getSource() == button && button == gameBoard.throwDice) {
                System.out.println("carreguei na merda do botão");
                gameBoard.throwDice.updateUI();
            }
    }

    public void armTrap(Player player) {
        Player.playerBuyTile(player.getPosition(), player.getName());
        player.setLifePoints(player.getLifePoints() - gameBoard.getTileAtIndex(player.getPosition()).getPrice());
        gameBoard.getTileAtIndex(player.getPosition()).setArmed(true);
        System.out.println("tile is yours!");
    }


    private void hasPlayerWon() {

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

    public void playingOrder() {

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
        //removeFaintedPlayer();
        if (playerList.size() == 1) {
            winner = playerList.get(0);
            System.out.println("Game over \nThe winner is: " + playerList.get(0).getName());
            return false;
        }
        return true;
    }

    public void removeFaintedPlayer() {
        if (playerList.isEmpty()) return;
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
            currentPlayer.setDiceRoll(Dice.throwDice());
            gameBoard.textinho.setText(currentPlayer.getName() + " rolled " + currentPlayer.getDiceRoll());
            gameBoard.armTrap.setEnabled(true);
        }
    }

    public void armTrap() {

    }

    public void throwDice() {

    }

    public void upgradeTrap() {

    }
}


