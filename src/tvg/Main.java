package tvg;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;
import tvg.server.Server;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Player> lista = new ArrayList<>();
        Game jogo = new Game(lista);

        lista.add(new Player("joao", jogo));
        lista.add(new Player("rui", jogo));
        lista.add(new Player("parra", jogo));
        lista.add(new Player("flepa", jogo));


        Frame frame = new Frame(jogo);
        frame.start();
    }
}
