package tvg.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/*
 * server's only function is to connect clients and send to all client handlers a list of the connected clients
 */

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port = 930;

    private static volatile List<ClientHandler> clientHandlers = new ArrayList<>();


    public static void main(String[] args) {
        Server server = new Server();
        server.initializerServer();
    }

    public void initializerServer() {

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("waiting for client to connect");

            while (true) {

                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
                clientHandler.start();
                System.out.println("client connected - " + clientSocket.getInetAddress().getHostAddress());
                clientHandlers.add(clientHandler);
                System.out.println("waiting for client to connect");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendActualizedList() {
        for (ClientHandler clientHandler : clientHandlers)
            clientHandler.setAllClientsList(clientHandlers);
    }

}
