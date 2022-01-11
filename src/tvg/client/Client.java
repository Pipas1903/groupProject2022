package tvg.client;

import tvg.board.Frame;
import tvg.common.Messages;
import tvg.common.UpdateMessages;
import tvg.game.Game;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Scanner scan = new Scanner(System.in);
    InetAddress hostName;
    int portNumber;
    Socket serverSocket;

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

        print("*connection established*");
    }

    public void speak() throws IOException, ClassNotFoundException {

        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);

        while (serverSocket.isBound()) {

            String line = "";
            String received = "";

            while (!(line = in.readLine()).equals("stop")) {
                received += line + "\n";
            }

            if (received.contains("init")) {

                objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
                Object object = objectInputStream.readObject();

                if (object instanceof Game) {
                    game = (Game) object;
                }
                print(UpdateMessages.RECEIVED_GAME + game);
                frame = new Frame(game);

                playingLoop();
            }

            print(received);

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

            if (game.getCurrentPlayer().getName().equals(name)) {

                if (game.getCurrentPlayer().isDead()) {
                    game.getGameBoard().textinho.setText(name + "is dead");
                    game.turnOffOtherPlayerButtons();
                    game.resetEndOfTurn();
                    game.playerIndex++;
                    game.setCurrentPlayer(game.playerList.get(game.playerIndex));
                    game.getGameBoard().textinho.setText(game.getCurrentPlayer().getName() + Messages.THROW_DICE);
                    sendGameAfterTurn();
                    continue;

                } else {

                    while (!game.getCurrentPlayer().isEndOfTurn()) {

                    }

                    if (game.getCurrentPlayer().isEndOfTurn()) {
                        game.resetEndOfTurn();
                        game.setCurrentPlayer(game.playerList.get(game.playerIndex));
                        game.getGameBoard().textinho.setText(game.getCurrentPlayer().getName() + Messages.THROW_DICE);
                        sendGameAfterTurn();
                        continue;
                    }

                }

            }
            receiveGame();
        }
    }

    public void receiveGame() throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(serverSocket.getInputStream());

        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            game = (Game) object;
            frame.setGame(game);
            game.getGameBoard().updateUI();
            frame.validate();
            print(UpdateMessages.RECEIVED_GAME + game);
        }

        if (object instanceof String) {
            System.out.println("message received: " + objectInputStream.readObject());
            serverSocket.close();
            System.exit(1);
        }
    }

    public void sendGameAfterTurn() throws IOException {
        objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());
        objectOutputStream.writeObject(game);
        objectOutputStream.flush();

        print(UpdateMessages.SENT_GAME + game);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.getServerInfo();
        client.speak();
    }

    public void print(String message) {
        System.out.println(message);
    }
}

