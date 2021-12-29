package tvg.game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame  {
    Board gameBoard;

    Frame(){



        gameBoard = new Board(6,6,612,612);
        gameBoard.setBackground(new Color(192,192,192));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setLayout(null);
        this.setVisible(true);
        this.add(gameBoard, new Integer(0));

    }

}
