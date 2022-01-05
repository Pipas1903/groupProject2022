package tvg.board;

import tvg.board.Board;
import tvg.game.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private Game jogo;

    public Frame(Game jogo) {
        this.jogo = jogo;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1410, 750);
        this.setLayout(null);

        this.add(jogo.getGameBoard(), new Integer(0));
        this.setResizable(false);
        this.setVisible(true);
    }

    public void start() {
        jogo.rounds();
    }

}
