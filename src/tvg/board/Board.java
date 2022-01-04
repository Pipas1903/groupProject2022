package tvg.board;

import tvg.player.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener, ActionListener, Serializable {

    public JButton armTrap;
    public JButton upgradeTrap;
    public JButton passTurn;
    public JButton stealTrap;
    public JButton throwDice;
    public JLabel textinho;
    public JLabel rounds;
    public JPanel info;
    public JLabel name;
    public JLabel price;
    public JLabel upgradePrice;
    public JLabel printPlayer1;


    private ArrayList<Tile> allTiles = new ArrayList<>();
    private ArrayList<Tile> unbuyableTiles = new ArrayList<>();

    public ArrayList<Tile> getUnbuyableTiles() {
        return unbuyableTiles;
    }

    public ArrayList<JButton> buttonsList = new ArrayList<>();

    public ArrayList<Tile> getAllTiles() {
        return allTiles;
    }

    public Tile getTileAtIndex(int location) {
        return allTiles.get(location);
    }


    public Board(int xCoord, int yCoord, int width, int height) {

        setBorder(new LineBorder(new Color(0, 0, 0)));
        setBounds(xCoord, yCoord, 1100, 710);
        Buttons();
        textBoxes();

        this.setLayout(null);
        this.add(textinho);
        this.add(rounds);


        this.add(stealTrap);
        this.add(passTurn);
        this.add(upgradeTrap);
        this.add(armTrap);
        this.add(throwDice);
        this.add(info);



        buttonsList.add(stealTrap);
        buttonsList.add(passTurn);
        buttonsList.add(upgradeTrap);
        buttonsList.add(armTrap);
        buttonsList.add(throwDice);

        initializeSquares();
    }

    private void initializeSquares() {
        // TODO Auto-generated method stub
        String[] squareNames = {
                "Start",
                "TRAP1",
                "event",
                "TRAP2",
                "TRAP3",
                "TRAP4",
                "event",
                "TRAP5",
                "Chance",
                "TRAP6",
                "TRAP7",
                "TRAP8",
                "good luck",
                "St. James Place",
                "Community Chest",
                "TRAP9",
                "TRAP10",
                "TRAP11",
                "bad luck",
                "TRAP12",
                "TRAP13",
                "Chance",
                "TRAP14",
                "TRAP15"
        };


        // squares on the top
        Tile tile00 = new Tile(6, 6, 100, 100, squareNames[0], -45, true);
        this.add(tile00);
        allTiles.add(tile00);
        unbuyableTiles.add(tile00);
        tile00.addMouseListener(this);


        Tile tile01 = new Tile(106, 6, 100, 100, squareNames[1], 0, true);
        this.add(tile01);
        allTiles.add(tile01);
        tile01.addMouseListener(this);


        Tile tile02 = new Tile(206, 6, 100, 100, squareNames[2], 0, false);
        this.add(tile02);
        allTiles.add(tile02);
        unbuyableTiles.add(tile02);
        tile02.addMouseListener(this);

        Tile tile03 = new Tile(306, 6, 100, 100, squareNames[3], 0, true);
        this.add(tile03);
        allTiles.add(tile03);
        tile03.addMouseListener(this);

        Tile tile04 = new Tile(406, 6, 100, 100, squareNames[4], 0, true);
        this.add(tile04);
        allTiles.add(tile04);
        tile04.addMouseListener(this);

        Tile tile05 = new Tile(506, 6, 100, 100, squareNames[5], 0, true);
        this.add(tile05);
        allTiles.add(tile05);
        unbuyableTiles.add(tile05);
        tile05.addMouseListener(this);

        Tile tile06 = new Tile(606, 6, 100, 100, squareNames[6], 45, false);
        this.add(tile06);
        allTiles.add(tile06);
        unbuyableTiles.add(tile06);
        tile06.addMouseListener(this);

        // squares on the right
        Tile tile07 = new Tile(606, 106, 100, 100, squareNames[7], -90, true);
        this.add(tile07);
        allTiles.add(tile07);
        tile07.addMouseListener(this);

        Tile tile08 = new Tile(606, 206, 100, 100, squareNames[8], -90, false);
        this.add(tile08);
        allTiles.add(tile08);
        unbuyableTiles.add(tile08);
        tile08.addMouseListener(this);

        Tile tile09 = new Tile(606, 306, 100, 100, squareNames[9], -90, true);
        this.add(tile09);
        allTiles.add(tile09);
        tile09.addMouseListener(this);

        Tile tile10 = new Tile(606, 406, 100, 100, squareNames[10], -90, true);
        this.add(tile10);
        allTiles.add(tile10);
        tile10.addMouseListener(this);

        Tile tile11 = new Tile(606, 506, 100, 100, squareNames[11], -90, true);
        this.add(tile11);
        allTiles.add(tile11);
        unbuyableTiles.add(tile11);
        tile11.addMouseListener(this);

        Tile tile12 = new Tile(606, 606, 100, 100, squareNames[12], -45, false);
        this.add(tile12);
        allTiles.add(tile12);
        unbuyableTiles.add(tile12);
        tile12.addMouseListener(this);


        // squares on the bottom
        Tile tile13 = new Tile(506, 606, 100, 100, squareNames[13], 0, true);
        this.add(tile13);
        allTiles.add(tile13);
        tile13.addMouseListener(this);

        Tile tile14 = new Tile(406, 606, 100, 100, squareNames[14], 0, false);
        this.add(tile14);
        allTiles.add(tile14);
        unbuyableTiles.add(tile14);
        tile14.addMouseListener(this);

        Tile tile15 = new Tile(306, 606, 100, 100, squareNames[15], 0, true);
        this.add(tile15);
        allTiles.add(tile15);
        tile15.addMouseListener(this);

        Tile tile16 = new Tile(206, 606, 100, 100, squareNames[16], 0, true);
        this.add(tile16);
        allTiles.add(tile16);
        tile16.addMouseListener(this);

        Tile tile17 = new Tile(106, 606, 100, 100, squareNames[17], 0, true);
        this.add(tile17);
        allTiles.add(tile17);
        unbuyableTiles.add(tile17);
        tile17.addMouseListener(this);

        Tile tile18 = new Tile(6, 606, 100, 100, squareNames[18], 45, false);
        this.add(tile18);
        allTiles.add(tile18);
        unbuyableTiles.add(tile18);
        tile18.addMouseListener(this);


        // squares on the left
        Tile tile19 = new Tile(6, 506, 100, 100, squareNames[19], 90, true);
        this.add(tile19);
        allTiles.add(tile19);
        tile19.addMouseListener(this);

        Tile tile20 = new Tile(6, 406, 100, 100, squareNames[20], 90, true);
        this.add(tile20);
        allTiles.add(tile20);
        tile20.addMouseListener(this);

        Tile tile21 = new Tile(6, 306, 100, 100, squareNames[21], 90, false);
        this.add(tile21);
        allTiles.add(tile21);
        unbuyableTiles.add(tile21);
        tile21.addMouseListener(this);

        Tile tile22 = new Tile(6, 206, 100, 100, squareNames[22], 90, true);
        this.add(tile22);
        allTiles.add(tile22);
        tile22.addMouseListener(this);

        Tile tile23 = new Tile(6, 106, 100, 100, squareNames[23], 90, true);
        this.add(tile23);
        allTiles.add(tile23);
        tile23.addMouseListener(this);


        // setting prices
        tile01.setPrice(80);

        tile03.setPrice(100);
        tile04.setPrice(120);
        tile05.setPrice(130);

        tile07.setPrice(140);

        tile09.setPrice(150);
        tile10.setPrice(140);
        tile11.setPrice(130);

        tile13.setPrice(90);

        tile15.setPrice(110);
        tile16.setPrice(130);
        tile17.setPrice(120);

        tile19.setPrice(150);
        tile20.setPrice(100);

        tile22.setPrice(120);
        tile23.setPrice(110);

        // setting damage points
        tile01.setDamageDealt(8);

        tile03.setDamageDealt(10);
        tile04.setDamageDealt(12);
        tile05.setDamageDealt(14);

        tile07.setDamageDealt(18);

        tile09.setDamageDealt(25);
        tile10.setDamageDealt(18);
        tile11.setDamageDealt(14);

        tile13.setDamageDealt(9);

        tile15.setDamageDealt(11);
        tile16.setDamageDealt(14);
        tile17.setDamageDealt(12);

        tile19.setDamageDealt(25);
        tile20.setDamageDealt(10);

        tile22.setDamageDealt(12);
        tile23.setDamageDealt(11);

    }

    public void panel(Tile tile) {

        name.setText(tile.getName());
        price.setText("Price: "+ tile.getPrice());
        upgradePrice.setText("Upgrade Price: " + tile.getUpgradePrice());
        info.updateUI();

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        for (Tile tile : allTiles) {
            if (e.getSource() == tile) {
                 panel(tile);

            }
        }
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


    public void textBoxes() {
        textinho = new JLabel();
        textinho.setBounds(800, 320, 200, 30);
        textinho.setVisible(true);
        textinho.setOpaque(true);

        rounds = new JLabel();
        rounds.setBounds(873, 15, 100, 30);
        rounds.setForeground(Color.RED);
        rounds.setVisible(true);
        //rounds.setOpaque(true);

        info = new JPanel();
        info.setBounds(800,400,250,250);
        info.setPreferredSize(new Dimension(250,250));
        info.setLayout(new GridLayout(5,1,0,20));
        info.setVisible(true);
        info.setOpaque(true);


        name = new JLabel();
        name.setHorizontalAlignment(JLabel.CENTER);
        info.add(name);

        price = new JLabel();
        info.add(price);

        upgradePrice = new JLabel();
        info.add(upgradePrice);

        printPlayer1 = new JLabel();
        printPlayer1.setVisible(true);
        printPlayer1.setOpaque(true);
        info.add(printPlayer1);


    }

    public void Buttons() {

        armTrap = new JButton();
        armTrap.setBounds(800, 45, 100, 80);
        armTrap.setText("Arm Trap");
        armTrap.setVisible(true);
        armTrap.setEnabled(false);



        upgradeTrap = new JButton();
        upgradeTrap.setBounds(900, 45, 100, 80);
        upgradeTrap.setText("Upgrade Trap");
        upgradeTrap.setVisible(true);
        upgradeTrap.setEnabled(false);

        passTurn = new JButton();
        passTurn.setBounds(800, 125, 100, 80);
        passTurn.setText("Pass Turn");
        passTurn.setVisible(true);
        passTurn.setEnabled(false);

        stealTrap = new JButton();
        stealTrap.setBounds(900, 125, 100, 80);
        stealTrap.setText("Steal Trap");
        stealTrap.setVisible(true);
        stealTrap.setEnabled(false);

        throwDice = new JButton();
        throwDice.setBounds(850, 225, 100, 80);
        throwDice.setText("Throw Dice");
        throwDice.setVisible(true);
    }




    public void printPlayer(Player player){
            System.out.println(player.getPosition());
            printPlayer1.setText("34gergegergergergreger");
            printPlayer1.setBounds(xLocationsOfPlayer1[player.getPosition()],yLocationsOfPlayer1[player.getPosition()],10,10);
            printPlayer1.updateUI();


    }



    @Override
    public void actionPerformed(ActionEvent e) {}

    int[] xLocationsOfPlayer1 = {31, 131, 231, 331, 431, 531,
            531, 531, 531, 531, 531,
            431, 331, 231, 131, 31,
            31, 31, 31, 31};

    int[] yLocationsOfPlayer1 = {33, 33, 33, 33, 33, 33,
            133, 233, 333, 433, 533,
            533, 533, 533, 533, 533,
            433, 333, 233, 133};

    int[] xLocationsOfPlayer2 = {61, 191, 291, 361, 461, 561,
            561, 561, 561, 561, 561,
            461, 361, 261, 161, 61,
            61, 61, 61, 61};

    int[] yLocationsOfPlayer2 = {33, 33, 33, 33, 33, 33,
            133, 233, 333, 433, 533,
            533, 533, 533, 533, 533,
            433, 333, 233, 133};

}
