package tvg.Player;

import java.net.Socket;

public class Player {

    private Socket socket;
    private String name;
    private int money;

    public Player(String name) {
        this.money = 800;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
