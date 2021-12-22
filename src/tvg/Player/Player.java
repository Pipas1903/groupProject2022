package tvg.Player;

import java.net.Socket;

public class Player {

    String name;
    int money;

    public Player (String name){
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

    public void setSocket(Socket socket) {

    }
}
