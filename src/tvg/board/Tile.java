package tvg.board;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Tile extends JPanel {

    private int price;
    private int upgradePrice;
    private int damageDealt;
    private String name;
    private boolean buyable;
    private int number;
    private boolean armed;
    private boolean upgraded;
    JLabel nameLabel;
    static int totalTiles = 0;

    public Tile(int xCoord, int yCoord, int width, int height, String labelString, int rotationDegrees, boolean buyable) {
        number = totalTiles;
        totalTiles++;
        setBorder(new LineBorder(new Color(0, 0, 0)));
        setBounds(xCoord, yCoord, width, height);
        name = labelString;
        this.setLayout(null);
        this.buyable=buyable;

        if (rotationDegrees == 0) {
            nameLabel = new JLabel(labelString);
            nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            nameLabel.setBounds(0, 20, this.getWidth(), 40);
            this.add(nameLabel);
        } else {

            // rotating a Jlabel: https://www.daniweb.com/programming/software-development/threads/390060/rotate-jlabel-or-image-in-label

            nameLabel = new JLabel(labelString) {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    AffineTransform aT = g2.getTransform();
                    Shape oldshape = g2.getClip();
                    double x = getWidth() / 2.0;
                    double y = getHeight() / 2.0;
                    aT.rotate(Math.toRadians(rotationDegrees), x, y);
                    g2.setTransform(aT);
                    g2.setClip(oldshape);
                    super.paintComponent(g);
                }
            };
            if (rotationDegrees == 90) {
                nameLabel.setBounds(20, 0, this.getWidth(), this.getHeight());
            }
            if (rotationDegrees == -90) {
                nameLabel.setBounds(-10, 0, this.getWidth(), this.getHeight());
            }
            if (rotationDegrees == 180) {
                nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            }
            if (rotationDegrees == 135 || rotationDegrees == -135 || rotationDegrees == -45 || rotationDegrees == 45) {
                nameLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            }
            nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            this.add(nameLabel);
        }

    }

    // NEEDS CHANGES, GROUPS OF TWO, NOT THREE
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.number == 1 || this.number == 3 || this.number == 4 || this.number == 5) {
            g.drawRect(0, this.getHeight() - 20, this.getWidth(), 20);
            g.setColor(Color.BLUE);
            g.fillRect(0, this.getHeight() - 20, this.getWidth(), 20);
        }
        if(this.number == 18) {
            setBackground(new Color(255,114,111));
        }
        if (this.number == 12) {

        }
        //if(this.number ==
        if (this.number == 7 || this.number == 9 || this.number == 10 || this.number == 11) {
            g.drawRect(0, 0, 20, this.getHeight());
            g.setColor(new Color(224,100,168));
            g.fillRect(0, 0, 20, this.getHeight());
        }
        if (this.number == 13 || this.number == 15 || this.number == 16 || this.number == 17) {
            g.drawRect(0, 0, this.getWidth(), 20);
            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, this.getWidth(), 20);
        }
        if (this.number == 19 || this.number == 20 || this.number == 22 || this.number == 23) {
            g.drawRect(this.getWidth() - 20, 0, 20, this.getHeight());
            g.setColor(new Color(75,122,71));
            g.fillRect(this.getWidth() - 20, 0, 20, this.getHeight());
        }

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isArmed() {
        return armed;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }

    public boolean isBuyable() {
        return buyable;
    }
}
