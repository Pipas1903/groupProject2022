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
        gameBoard.panelInfo(playerList);

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
        gameBoard.rounds.setText(currentPlayer.getName() + Messages.PLAYER_TURN);
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
            showPlayerInfo();
            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.armTrap) {
            armTrap();
            showPlayerInfo();
            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.upgradeTrap) {
            upgradeTrap();
            showPlayerInfo();
            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.passTurn) {
            try {
                passTurn();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            showPlayerInfo();
            gameBoard.updateUI();

        }
        if (e.getSource() == gameBoard.stealTrap) {
            stealTrap();
            showPlayerInfo();
            gameBoard.updateUI();
        }
    }

    public void showPlayer() {
        gameBoard.printPlayer(currentPlayer);
        gameBoard.updateUI();
    }

    public void showPlayerInfo() {
        System.out.println("entrei no show player info");


        gameBoard.lifePoints1.setText("life :" + this.playerList.get(0).getLifePoints());
        System.out.println("vida :" + this.playerList.get(0).getLifePoints());
        gameBoard.lifePoints1.updateUI();
        gameBoard.lifePoints1.validate();
        System.out.println("vida :" + this.playerList.get(1).getLifePoints());
        gameBoard.lifePoints2.setText("life :" + this.playerList.get(1).getLifePoints());
        gameBoard.lifePoints2.updateUI();
        gameBoard.lifePoints2.validate();
        gameBoard.printInfo1.updateUI();
        gameBoard.printInfo1.validate();
        gameBoard.printInfo2.updateUI();
        gameBoard.printInfo2.validate();

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

    public void checkEvents() {
        if (gameBoard.getTileAtIndex(playerLocation).isBadLuck()) {

            gameBoard.textinho.setText("Oh oh... You lost 150 life points");
            currentPlayer.setLifePoints(currentPlayer.getLifePoints() - luck);
            gameBoard.passTurn.setEnabled(true);

        } else if (gameBoard.getTileAtIndex(playerLocation).isGoodLuck()) {

            gameBoard.textinho.setText("YEY! You restored 150 life points");
            currentPlayer.setLifePoints(currentPlayer.getLifePoints() + luck);
            gameBoard.passTurn.setEnabled(true);

        } else if (gameBoard.getTileAtIndex(playerLocation).getName().equals("start")) {
            gameBoard.passTurn.setEnabled(true);
        } else {
            executeRandomEvent();

        }
    }

    public int findOneFreeTrap() {
        for (Tile tile : gameBoard.getAllTiles()) {
            if (!tile.isArmed()) {
                return tile.getNumber();
            }
        }
        return 30;
    }

    public void executeRandomEvent() {

        Events randomEvent = Events.randomEvent();
        gameBoard.textinho.setText(randomEvent.message);
        gameBoard.passTurn.setEnabled(true);

        switch (randomEvent) {
            case WIN_TRAP:
                int freeTrapNumber = findOneFreeTrap();
                playerArmTrap(freeTrapNumber, currentPlayer.getName());
                gameBoard.getTileAtIndex(freeTrapNumber).setOwner(currentPlayer.getName());
                break;
            case LOSE_TRAP:
                tryToRemovePlayerTrap();
                break;
            case TASTY_SNACK:
                currentPlayer.setLifePoints(currentPlayer.getLifePoints() + randomEvent.lifePoints);
                break;
            case UPGRADE_TRAP:
                tryToUpgradePlayerTrap();
                break;
            case THROW_DICE_AGAIN:
                gameBoard.passTurn.setEnabled(false);
                gameBoard.throwDice.setEnabled(true);
                break;
            case TRIP_ON_SHOE_LACE:
                currentPlayer.setLifePoints(currentPlayer.getLifePoints() - randomEvent.lifePoints);
                break;
        }
    }

    public void tryToUpgradePlayerTrap() {
        for (Map.Entry<Integer, String> entry : armedTrapsRegister.entrySet()) {
            if (entry.getValue().equals(currentPlayer.getName())) {
                if (!gameBoard.getTileAtIndex(entry.getKey()).isUpgraded()) {
                    gameBoard.getTileAtIndex(entry.getKey()).setUpgraded(true);
                    System.out.println("Player got a trap upgraded for free");
                    return;
                }
            }
        }
        gameBoard.textinho.setText("All your traps were upgraded, so you got nothing!");
    }

    public void tryToRemovePlayerTrap() {
        for (Map.Entry<Integer, String> entry : armedTrapsRegister.entrySet()) {
            if (entry.getValue().equals(currentPlayer.getName())) {
                armedTrapsRegister.remove(entry.getKey());
                System.out.println("Player lost a trap");
                return;
            }
        }
        gameBoard.textinho.setText("You don't have traps, so you lose anything!");
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
        gameBoard.panelInfo(playerList);


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

        gameBoard.textinho.setText("you stole: " + gameBoard.getTileAtIndex(playerLocation).getName());

        showPlayer();
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
        currentPlayer.setEndOfTurn(Boolean.TRUE);

        gameBoard.rounds.setText(currentPlayer.getName() + Messages.PLAYER_TURN);
        // gameBoard.rounds.setText(Messages.ROUND + round);

        gameBoard.textinho.setText(currentPlayer.getName() + Messages.THROW_DICE);
        gameBoard.updateUI();


     /*   synchronized (this.getCurrentPlayer()) {
            this.getCurrentPlayer().notifyAll();
            System.out.println("notifiquei, a culpa é dele");
        }
     */

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

     /*
        public void chooseGameMode() {
            // until death or limited rounds
            Scanner sc = new Scanner(System.in);

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
            if (playerList.size() == 1) {
                System.out.println("Game over \nThe winner is: " + playerList.get(0).getName());
                return false;
            }
            return true;
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
    */

}