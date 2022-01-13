package tvg;

import tvg.client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       /* List<Player> lista = new ArrayList<>();

        lista.add(new Player("joao"));
        lista.add(new Player("rui"));
        lista.add(new Player("parra"));
        lista.add(new Player("flepa"));

        Game jogo = new Game(lista);

        Frame frame = new Frame(jogo);
*/
        Client client = new Client();
        client.getServerInfo();
        client.speak();

    }
}
