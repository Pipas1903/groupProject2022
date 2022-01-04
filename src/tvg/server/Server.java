package tvg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/*
 * server's only function is to connect clients and send to all client handlers a list of the connected clients
 */

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static int port = 930;

    private static volatile List<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) {
        initializerServer();
    }

    public static void initializerServer() {

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("waiting for client to connect");

            while (true) {

                clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);

                clientHandler.start();

                System.out.println("client connected - " + clientSocket.getInetAddress().getHostAddress());

                sendActualizedList();

                clientHandlers.add(clientHandler);

                System.out.println("waiting for client to connect");

                sendActualizedList();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void sendActualizedList() {
        for (ClientHandler clientHandler : clientHandlers)
            clientHandler.setAllClientsList(clientHandlers);
    }

}
