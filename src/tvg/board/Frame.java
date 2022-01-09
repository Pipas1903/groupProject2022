package tvg.board;

import tvg.game.Game;

import javax.swing.*;

public class Frame extends JFrame {
    private Game jogo;

    public Frame(Game jogo) {
        this.jogo = jogo;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1410, 850);
        this.setLayout(null);

        this.add(jogo.getGameBoard(), new Integer(0));
        this.setResizable(false);
        this.setVisible(true);

        jogo.showFirstPlayer();
    }

    public void setGame(Game game) {
        this.remove(jogo.getGameBoard());
        this.jogo = game;

        this.add(jogo.getGameBoard(), new Integer(0));

        jogo.turnButtonsOnForCurrentPlayer();

        this.repaint();

        jogo.getGameBoard().updateUI();

    }

}
