package tvg.game;

import tvg.ConsoleUI;
import tvg.board.Tile;
import tvg.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private List<Player> playerList;
    private int round;
    private List<Tile> openTiles = new ArrayList<>();
    Player winner;


    public Game(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void start() {
        playingOrder();
    }

    public void rounds() {
        for (Player i : playerList) {
            removeFaintedPlayer();
            if (checkGameStatus()) {
                turn(i);
                break;
            }
        }
    }

    public void turn(Player i) {
        System.out.println(i.getName() + Messages.THROW_DICE);
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        int move = Dice.throwDice();
        System.out.println(Messages.DICE_FACE + move);

    }
    public void playerMoveOnBoard() {

    }

    // this method refreshes the screen for the rest of the players
    public void refreshScreen(Player currentPlayer) {
        for (Player i : playerList) {
            if (i != currentPlayer) {

            }
        }
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
        removeFaintedPlayer();
        if (playerList.size() == 1) {
            winner = playerList.get(0);
            System.out.println("Game over \nThe winner is: " + playerList.get(0).getName());
            return false;
        }
        return true;
    }

    public void removeFaintedPlayer() {
        playerList.removeIf(player -> player.getLifePoints() <= 0 & player.getListOfOwnedTiles().size() == 0);
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

    public void playerTurnDecision(Player player, Tile tile) {
        Scanner sc = new Scanner(System.in);
        ConsoleUI.printTurnOption();
        if (sc.nextInt() == 1) {
            if (tile.isBuyable()) {
                player.setLifePoints(player.getLifePoints() - tile.getPrice());
            }
        }
    }

}


