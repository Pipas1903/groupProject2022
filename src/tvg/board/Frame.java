package tvg.board;

import tvg.board.Board;
import tvg.game.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame  {
    private Game jogo;

    public Frame(Game jogo){

       this.jogo=jogo;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setLayout(null);
        this.setVisible(true);
        this.add(jogo.getGameBoard(), new Integer(0));

    }

}
