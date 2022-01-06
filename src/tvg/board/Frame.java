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
    }

    public void start() {
        jogo.rounds();
    }

    public void setGame(Game game) {
        this.remove(jogo.getGameBoard());
        this.jogo = game;

        this.add(jogo.getGameBoard(), new Integer(0));

        jogo.turnButtonsOnForCurrentPlayer();

        this.repaint();
        this.validate();

        jogo.rounds();
        jogo.getGameBoard().validate();

        jogo.getGameBoard().updateUI();
        this.validate();
    }

}
