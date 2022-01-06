package tvg.client;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    // OPEN A CLIENT SOCKET
    Scanner scan = new Scanner(System.in);
    InetAddress hostName;
    int portNumber;
    Socket serverSocket;
    Thread receiveUpdate;

    Game game;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    boolean firstIteration = true;
    String name = "";
    Frame frame;


    public void getServerInfo() throws IOException {
        System.out.print("Server IP: ");
        String host = scan.nextLine();
        this.hostName = InetAddress.getByName(host);
        System.out.print("Port: ");
        portNumber = scan.nextInt();
        scan.nextLine();
        serverSocket = new Socket(hostName, portNumber);
        System.out.println("*connection established*");
    }

    public void speak() throws IOException, ClassNotFoundException, InterruptedException {

        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);


        while (serverSocket.isBound()) {

            String line = "";
            String received = "";

            while (!(line = in.readLine()).equals("stop")) {
                received += line + "\n";
            }

            if (received.contains("init")) {

                System.out.println("receiving game ...");
                objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
                Object object = objectInputStream.readObject();

                if (object instanceof Game) {
                    game = (Game) object;
                }
                System.out.println("recebi um jogo " + game);
                System.out.println("You joined a game!");
                frame = new Frame(game);
                frame.start();

                playingLoop();
            }

            System.out.println(received);

            String message = scan.nextLine();
            out.println(message);

            if (firstIteration) {
                name = message;
                firstIteration = false;
            }
        }
    }

    public void playingLoop() throws IOException, ClassNotFoundException {

        while (true) {
            frame.repaint();
            game.getGameBoard().updateUI();
            if (!game.getCurrentPlayer().getName().equals(name)) {
                game.turnOffOtherPlayerButtons();
            }

            for(Player p : game.playerList){
                System.out.println(p.getName());
            }

            System.out.println(game.getCurrentPlayer().getName());

            if (game.getCurrentPlayer().getName().equals(name)) {
                System.out.println("passei o receive game");

                while (!game.getCurrentPlayer().isEndOfTurn()) {

                }

                System.out.println("out of while");

                if (game.getCurrentPlayer().isEndOfTurn()) {
                    System.out.println("entrou no is end of turn");
                    game.resetEndOfTurn();
                    game.setCurrentPlayer(game.playerList.get(game.playerIndex));
                    game.getGameBoard().rounds.setText(game.getCurrentPlayer().getName());
                    sendGameAfterTurn();
                    System.out.println(game.getCurrentPlayer());
                    continue;
                }
            }

            receiveGame();
        }
    }

    public void receiveGame() throws IOException, ClassNotFoundException {
        System.out.println("entrei no receive game");
        objectInputStream = new ObjectInputStream(serverSocket.getInputStream());

        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            System.out.println("olha que entrou ehehe");
            game = (Game) object;
            frame.setGame(game);
            game.getGameBoard().updateUI();
            frame.validate();
        }
        System.out.println("recebi um jogo " + game);

    }

    public void sendGameAfterTurn() throws IOException {
        objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());
        objectOutputStream.writeObject(game);
        objectOutputStream.flush();

        System.out.println("enviei um jogo " + game);
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client client = new Client();
        client.getServerInfo();
        client.speak();
    }
}

