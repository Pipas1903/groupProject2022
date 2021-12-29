package tvg.game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener {

    private ArrayList<Square> allSquares = new ArrayList<Square>();
    private ArrayList<Square> unbuyableSquares = new ArrayList<Square>(); // squares like "Go", "Chances" etc...

    public ArrayList<Square> getUnbuyableSquares() {
        return unbuyableSquares;
    }

    public ArrayList<Square> getAllSquares() {
        return allSquares;
    }

    public Square getSquareAtIndex(int location) {
        return allSquares.get(location);
    }

    Square square00;
    Square square01;
    Square square02;
    Square square03;
    Square square04;
    Square square05;
    Square square06;
    Square square07;
    Square square08;
    Square square09;
    Square square10;
    Square square11;
    Square square12;
    Square square13;
    Square square14;
    Square square15;
    Square square16;
    Square square17;
    Square square18;
    Square square19;
    Square square20;
    Square square21;
    Square square22;
    Square square23;


    public Board(int xCoord, int yCoord, int width, int height) {
        setBorder(new LineBorder(new Color(0, 0, 0)));
        setBounds(xCoord, yCoord, 712, 712);
        this.setLayout(null);
        initializeSquares();
    }

    private void initializeSquares() {
        // TODO Auto-generated method stub
        String[] squareNames = {
                "Go",
                "Oriental Ave",
                "Community Chest",
                "Vermont Ave",
                "Connecticut Ave",
                "Roll once",
                "St. Charles Place",
                "Chance",
                "States Ave",
                "Virginia Ave",
                "Free Parking",
                "St. James Place",
                "Community Chest",
                "Tennessee Ave",
                "New York Ave",
                "Squeeze Play",
                "Pacific Ave",
                "North Carolina Ave",
                "Chance",
                "Pennsylvania Ave"
        };


        // squares on the top
        square00 = new Square(6, 6, 100, 100, squareNames[0], 135);
        this.add(square00);
        allSquares.add(square00);
        unbuyableSquares.add(square00);
        square00.addMouseListener(this);

        square01 = new Square(106, 6, 100, 100, squareNames[1], 180);
        this.add(square01);
        allSquares.add(square01);
        square01.addMouseListener(this);

        square02 = new Square(206, 6, 100, 100, squareNames[2], 180);
        this.add(square02);
        allSquares.add(square02);
        unbuyableSquares.add(square02);
        square02.addMouseListener(this);

        square03 = new Square(306, 6, 100, 100, squareNames[3], 180);
        this.add(square03);
        allSquares.add(square03);
        square03.addMouseListener(this);

        square04 = new Square(406, 6, 100, 100, squareNames[4], 180);
        this.add(square04);
        allSquares.add(square04);
        square04.addMouseListener(this);

        square05 = new Square(506, 6, 100, 100, squareNames[5], 180);
        this.add(square05);
        allSquares.add(square05);
        unbuyableSquares.add(square05);
        square05.addMouseListener(this);

        square06 = new Square(606, 6, 100, 100, squareNames[6], -135);
        this.add(square06);
        allSquares.add(square06);
        unbuyableSquares.add(square06);
        square06.addMouseListener(this);

        // squares on the right
        square07 = new Square(606, 106, 100, 100, squareNames[6], -90);
        this.add(square07);
        allSquares.add(square07);
        square07.addMouseListener(this);

        square08 = new Square(606, 206, 100, 100, squareNames[7], -90);
        this.add(square08);
        allSquares.add(square08);
        unbuyableSquares.add(square08);
        square08.addMouseListener(this);

        square09 = new Square(606, 306, 100, 100, squareNames[8], -90);
        this.add(square09);
        allSquares.add(square09);
        square09.addMouseListener(this);

        square10 = new Square(606, 406, 100, 100, squareNames[9], -90);
        this.add(square10);
        allSquares.add(square10);
        square10.addMouseListener(this);

        square11 = new Square(606, 506, 100, 100, squareNames[10], -90);
        this.add(square11);
        allSquares.add(square11);
        unbuyableSquares.add(square11);
        square11.addMouseListener(this);

        square12 = new Square(606, 606, 100, 100, squareNames[10], -45);
        this.add(square12);
        allSquares.add(square12);
        unbuyableSquares.add(square12);
        square12.addMouseListener(this);


        // squares on the bottom
        square13 = new Square(506, 606, 100, 100, squareNames[11], 0);
        this.add(square13);
        allSquares.add(square13);
        square13.addMouseListener(this);

        square14 = new Square(406, 606, 100, 100, squareNames[12], 0);
        this.add(square14);
        allSquares.add(square14);
        unbuyableSquares.add(square14);
        square14.addMouseListener(this);

        square15 = new Square(306, 606, 100, 100, squareNames[13], 0);
        this.add(square15);
        allSquares.add(square15);
        square15.addMouseListener(this);

        square16 = new Square(206, 606, 100, 100, squareNames[14], 0);
        this.add(square16);
        allSquares.add(square16);
        square16.addMouseListener(this);

        square17 = new Square(106, 606, 100, 100, squareNames[15], 0);
        this.add(square17);
        allSquares.add(square17);
        unbuyableSquares.add(square17);
        square17.addMouseListener(this);

        square18 = new Square(6, 606, 100, 100, squareNames[15], 45);
        this.add(square18);
        allSquares.add(square18);
        unbuyableSquares.add(square18);
        square18.addMouseListener(this);


        // squares on the left
        square19 = new Square(6, 506, 100, 100, squareNames[16], 90);
        this.add(square19);
        allSquares.add(square19);
        square19.addMouseListener(this);

        square20 = new Square(6, 406, 100, 100, squareNames[17], 90);
        this.add(square20);
        allSquares.add(square20);
        square20.addMouseListener(this);

        square21 = new Square(6, 306, 100, 100, squareNames[18], 90);
        this.add(square21);
        allSquares.add(square21);
        unbuyableSquares.add(square21);
        square21.addMouseListener(this);

        square22 = new Square(6, 206, 100, 100, squareNames[19], 90);
        this.add(square22);
        allSquares.add(square22);
        square22.addMouseListener(this);

        square23 = new Square(6, 106, 100, 100, squareNames[19], 90);
        this.add(square23);
        allSquares.add(square23);
        square23.addMouseListener(this);


        // setting prices
        square01.setPrice(100);
        square03.setPrice(100);
        square04.setPrice(120);

        square06.setPrice(140);
        square08.setPrice(140);
        square09.setPrice(160);

        square11.setPrice(180);
        square13.setPrice(180);
        square14.setPrice(200);

        square16.setPrice(300);
        square17.setPrice(300);
        square19.setPrice(320);

        // setting rent prcies
        square01.setRentPrice(6);
        square03.setRentPrice(6);
        square04.setRentPrice(8);

        square06.setRentPrice(10);
        square08.setRentPrice(10);
        square09.setRentPrice(12);

        square11.setRentPrice(14);
        square13.setRentPrice(14);
        square14.setRentPrice(16);

        square16.setRentPrice(26);
        square17.setRentPrice(26);
        square19.setRentPrice(28);
    }

        @Override
        public void mouseClicked (MouseEvent e){
            for (Square list: allSquares) {
                if(e.getSource()==list){
                    System.out.println(list.getPrice());
                }
            }
        }

        @Override
        public void mousePressed (MouseEvent e){

        }

        @Override
        public void mouseReleased (MouseEvent e){

        }

        @Override
        public void mouseEntered (MouseEvent e){

        }

        @Override
        public void mouseExited (MouseEvent e){

        }

}
