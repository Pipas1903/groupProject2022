package tvg.client;

import tvg.board.Frame;
import tvg.game.Game;

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
    Game game;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    private boolean firstIteration = true;
    private String name = "";


    public void getServerInfo() throws IOException {
        System.out.print("Server IP: ");
        String host = scan.nextLine();
        this.hostName = InetAddress.getByName(host);
        System.out.print("Port: ");
        portNumber = scan.nextInt();
        scan.nextLine();
        serverSocket = new Socket(hostName, portNumber);
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

                System.out.println("receiving game ...");

                receiveGame();

                System.out.println("You joined a game!");

                Frame frame = new Frame(game);

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
            synchronized (game.getCurrentPlayer()){
            if (!game.getCurrentPlayer().getName().equals(name)) {
                game.turnOffOtherPlayerButtons();
            }
            System.out.println(game.getCurrentPlayer().isEndOfTurn());
            if (game.getCurrentPlayer().getName().equals(name)) {
                System.out.println("passei o receive game");
                if (game.getCurrentPlayer().isEndOfTurn()) {
                    System.out.println("entrou no is end of turn");
                    sendGameAfterTurn();
                    break;
                }
            }
            receiveGame();
            }
        }
    }

    public void receiveGame() throws IOException, ClassNotFoundException {
        System.out.println("entrei no receivegame");
        objectInputStream = new ObjectInputStream(serverSocket.getInputStream());

        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            game = (Game) object;
        }
        System.out.println("recebi um jogo " + game);

    }

    public void sendGameAfterTurn() throws IOException {
        System.out.println("enviei um jogo " + game);
        objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());
        objectOutputStream.writeObject(game);
        objectOutputStream.flush();
        objectOutputStream.close();

        System.out.println("enviei um jogo " + game);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client cliente = new Client();
        cliente.getServerInfo();
        cliente.speak();


    }
}

