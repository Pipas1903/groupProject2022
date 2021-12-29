package tvg.board;

public class Tile {

    private int price;
    private int upgradedPrice;
    private String name;
    private boolean buyable;
    private int position;
    private boolean armed;

    // cor
    // botão

    public Tile(int price, String name) {
        this.name = name;
        this.price = price;
        this.buyable = true;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public int getPosition() {
        return position;
    }

    public int getPrice() {
        return price;
    }
}
