package tvg;

import tvg.board.Frame;
import tvg.game.Game;
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
        Game jogo = new Game(lista);

        Frame frame = new Frame(jogo);

    }
}
