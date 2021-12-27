package tvg;

import tvg.Game.Game;
import tvg.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> lista = new ArrayList<>();
        lista.add(new Player("joao"));
        lista.add(new Player("rui"));
        lista.add(new Player("parra"));
        lista.add(new Player("flepa"));

        new Game(lista).playingOrder();
    }
}
