package tvg.board;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener, ActionListener {


    JButton armTrap;
    JButton upgradeTrap;
    JButton passTrap;
    JButton stealTrap;


    private ArrayList<Tile> allTiles = new ArrayList<>();
    private ArrayList<Tile> unbuyableTiles = new ArrayList<>(); // tiles like "Start", "Ba" etc...

    public ArrayList<Tile> getUnbuyableTiles() {
        return unbuyableTiles;
    }

    public ArrayList<Tile> getAllTiles() {
        return allTiles;
    }

    public Tile getTileAtIndex(int location) {
        return allTiles.get(location);
    }

    Tile tile00;
    Tile tile01;
    Tile tile02;
    Tile tile03;
    Tile tile04;
    Tile tile05;
    Tile tile06;
    Tile tile07;
    Tile tile08;
    Tile tile09;
    Tile tile10;
    Tile tile11;
    Tile tile12;
    Tile tile13;
    Tile tile14;
    Tile tile15;
    Tile tile16;
    Tile tile17;
    Tile tile18;
    Tile tile19;
    Tile tile20;
    Tile tile21;
    Tile tile22;
    Tile tile23;


    public Board(int xCoord, int yCoord, int width, int height) {

        setBorder(new LineBorder(new Color(0, 0, 0)));
        setBounds(xCoord, yCoord, 1000, 1000);


        armTrap = new JButton();
        armTrap.setBounds(800, 200, 100, 80);
        armTrap.setText("Arm Trap");
        armTrap.setVisible(true);
        this.setLayout(null);
        this.add(armTrap);
        initializeSquares();
    }

    // ISTO TBM TÁ UMA PIXA, MAIS VALE CAGAR PARA O ARRAY E METER O NOME DIRETO.
    private void initializeSquares() {
        // TODO Auto-generated method stub
        String[] squareNames = {"Start", "TRAP1", "event", "TRAP2", "TRAP3", "event", "TRAP4", "Chance", "TRAP5", "TRAP6", "good luck", "St. James Place", "Community Chest", "TRAP7", "TRAP8", "bad luck", "TRAP9", "TRAP10", "Chance", "TRAP11"};


        // squares on the top
        tile00 = new Tile(6, 6, 100, 100, squareNames[0], 135, true);
        this.add(tile00);
        allTiles.add(tile00);
        unbuyableTiles.add(tile00);
        tile00.addMouseListener(this);

        tile01 = new Tile(106, 6, 100, 100, squareNames[1], 180, true);
        this.add(tile01);
        allTiles.add(tile01);
        tile01.addMouseListener(this);

        tile02 = new Tile(206, 6, 100, 100, squareNames[2], 180, true);
        this.add(tile02);
        allTiles.add(tile02);
        unbuyableTiles.add(tile02);
        tile02.addMouseListener(this);

        tile03 = new Tile(306, 6, 100, 100, squareNames[3], 180, true);
        this.add(tile03);
        allTiles.add(tile03);
        tile03.addMouseListener(this);

        tile04 = new Tile(406, 6, 100, 100, squareNames[4], 180, true);
        this.add(tile04);
        allTiles.add(tile04);
        tile04.addMouseListener(this);

        tile05 = new Tile(506, 6, 100, 100, squareNames[5], 180, true);
        this.add(tile05);
        allTiles.add(tile05);
        unbuyableTiles.add(tile05);
        tile05.addMouseListener(this);

        tile06 = new Tile(606, 6, 100, 100, squareNames[6], -135, true);
        this.add(tile06);
        allTiles.add(tile06);
        unbuyableTiles.add(tile06);
        tile06.addMouseListener(this);

        // squares on the right
        tile07 = new Tile(606, 106, 100, 100, squareNames[6], -90, true);
        this.add(tile07);
        allTiles.add(tile07);
        tile07.addMouseListener(this);

        tile08 = new Tile(606, 206, 100, 100, squareNames[7], -90, true);
        this.add(tile08);
        allTiles.add(tile08);
        unbuyableTiles.add(tile08);
        tile08.addMouseListener(this);

        tile09 = new Tile(606, 306, 100, 100, squareNames[8], -90, true);
        this.add(tile09);
        allTiles.add(tile09);
        tile09.addMouseListener(this);

        tile10 = new Tile(606, 406, 100, 100, squareNames[9], -90, true);
        this.add(tile10);
        allTiles.add(tile10);
        tile10.addMouseListener(this);

        tile11 = new Tile(606, 506, 100, 100, squareNames[10], -90, true);
        this.add(tile11);
        allTiles.add(tile11);
        unbuyableTiles.add(tile11);
        tile11.addMouseListener(this);

        tile12 = new Tile(606, 606, 100, 100, squareNames[10], -45, true);
        this.add(tile12);
        allTiles.add(tile12);
        unbuyableTiles.add(tile12);
        tile12.addMouseListener(this);


        // squares on the bottom
        tile13 = new Tile(506, 606, 100, 100, squareNames[11], 0, true);
        this.add(tile13);
        allTiles.add(tile13);
        tile13.addMouseListener(this);

        tile14 = new Tile(406, 606, 100, 100, squareNames[12], 0, true);
        this.add(tile14);
        allTiles.add(tile14);
        unbuyableTiles.add(tile14);
        tile14.addMouseListener(this);

        tile15 = new Tile(306, 606, 100, 100, squareNames[13], 0, true);
        this.add(tile15);
        allTiles.add(tile15);
        tile15.addMouseListener(this);

        tile16 = new Tile(206, 606, 100, 100, squareNames[14], 0, true);
        this.add(tile16);
        allTiles.add(tile16);
        tile16.addMouseListener(this);

        tile17 = new Tile(106, 606, 100, 100, squareNames[15], 0, true);
        this.add(tile17);
        allTiles.add(tile17);
        unbuyableTiles.add(tile17);
        tile17.addMouseListener(this);

        tile18 = new Tile(6, 606, 100, 100, squareNames[15], 45, true);
        this.add(tile18);
        allTiles.add(tile18);
        unbuyableTiles.add(tile18);
        tile18.addMouseListener(this);


        // squares on the left
        tile19 = new Tile(6, 506, 100, 100, squareNames[16], 90, true);
        this.add(tile19);
        allTiles.add(tile19);
        tile19.addMouseListener(this);

        tile20 = new Tile(6, 406, 100, 100, squareNames[17], 90, true);
        this.add(tile20);
        allTiles.add(tile20);
        tile20.addMouseListener(this);

        tile21 = new Tile(6, 306, 100, 100, squareNames[18], 90, true);
        this.add(tile21);
        allTiles.add(tile21);
        unbuyableTiles.add(tile21);
        tile21.addMouseListener(this);

        tile22 = new Tile(6, 206, 100, 100, squareNames[19], 90, true);
        this.add(tile22);
        allTiles.add(tile22);
        tile22.addMouseListener(this);

        tile23 = new Tile(6, 106, 100, 100, squareNames[19], 90, true);
        this.add(tile23);
        allTiles.add(tile23);
        tile23.addMouseListener(this);


        // setting prices
        tile01.setPrice(100);
        tile03.setPrice(100);
        tile04.setPrice(120);

        tile06.setPrice(140);
        tile08.setPrice(140);
        tile09.setPrice(160);

        tile11.setPrice(180);
        tile13.setPrice(180);
        tile14.setPrice(200);

        tile16.setPrice(300);
        tile17.setPrice(300);
        tile19.setPrice(320);

        // setting damage points
        tile01.setDamageDealt(6);
        tile03.setDamageDealt(6);
        tile04.setDamageDealt(8);

        tile06.setDamageDealt(10);
        tile08.setDamageDealt(10);
        tile09.setDamageDealt(12);

        tile11.setDamageDealt(14);
        tile13.setDamageDealt(14);
        tile14.setDamageDealt(16);

        tile16.setDamageDealt(26);
        tile17.setDamageDealt(26);
        tile19.setDamageDealt(28);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Tile list : allTiles) {
            if (e.getSource() == list) {
                System.out.println(list.getPrice());
            }
        }
    }

    public void Buttons(){

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
