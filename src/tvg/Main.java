package tvg;

import tvg.game.Game;
import tvg.board.Tile;
import tvg.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> lista = new ArrayList<>();
        lista.add(new Player("joao"));
        lista.add(new Player("rui"));
        lista.add(new Player("parra"));
        lista.add(new Player("flepa"));

        Player jpo = new Player("joao");
        Tile jk= new Tile(200, "tile1");

        new Game(lista).playingOrder();
    }
}
