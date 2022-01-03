package tvg.server;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    // OPEN A CLIENT SOCKET
    Scanner scan = new Scanner(System.in);
    InetAddress hostName;
    int portNumber;
    Socket clientSocket;
    Game game;


    public void getServerInfo() throws IOException {
        System.out.print("Server IP: ");
        String host = scan.nextLine();
        this.hostName = InetAddress.getByName(host);
        System.out.print("Port: ");
        portNumber = scan.nextInt();
        scan.nextLine();
        clientSocket = new Socket(hostName, portNumber);
    }

    public void speak() throws IOException, ClassNotFoundException {

        while (clientSocket.isBound()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            String received = "";

            while ((line = in.readLine()) != null) {
                received += line + "\n";
            }

            System.out.println(received);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String message = scan.nextLine();
            out.println(message);

            if (received.equals("start")) {
                receiveGame();

                System.out.println(game);
                Frame frame = new Frame(game);
                frame.start();
            }
        }
    }

    public void sendGameAfterTurn() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(game);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void receiveGame() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        Object object = objectInputStream.readObject();

        if (object instanceof Game) {
            game = (Game) object;
        }

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client cliente = new Client();
        cliente.getServerInfo();
        cliente.speak();


    }
}

