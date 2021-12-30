package tvg.server;

import tvg.game.Game;
import tvg.player.Player;
import tvg.server.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static int port = 930;
    private static List<ClientHandler> threadsList = new ArrayList<>();
    private static List<Player> playerList = new ArrayList<>();
    Game game = new Game(playerList);

    public static void main(String[] args) {
        initializerServer();

    }

    public static void initializerServer() {

        try {

            serverSocket = new ServerSocket(port);
            System.out.println("waiting for client to connect");

            while (true) {

                clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                clientHandler.start();

                System.out.println("client connected - " + clientSocket.getInetAddress().getHostAddress());

                threadsList.add(clientHandler);
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());

        }


    }


}
