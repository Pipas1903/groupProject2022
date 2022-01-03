package tvg.server;

public class GameManager {
/*
    public synchronized static void sendGame() throws IOException {
        if (playerSocket.size() == 2) {

            playingOrder();
            game = new Game(players);
            send();
        }
    }

    public static void receive(Socket clientSocket) throws IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            game = (Game) object;
        }

    }

    public static void send() throws IOException {

        for (Map.Entry<Socket, Player> entry : playerSocket.entrySet()) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(entry.getKey().getOutputStream());
            objectOutputStream.writeObject(game);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }


    public static void playingOrder() {

        Random random = new Random();

        ArrayList<Integer> number = random.ints(1, 10).
                distinct().
                limit(4).
                boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setOrder(number.get(i));
        }

        players.sort(Comparator.comparing(Player::getOrder));
    }*/
}
