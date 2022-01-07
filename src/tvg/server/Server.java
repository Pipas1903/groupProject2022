package tvg.server;

import tvg.common.UpdateMessages;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port = 930;

    public static void main(String[] args) {
        Server server = new Server();
        server.initializerServer();
    }

    public void initializerServer() {

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                print(UpdateMessages.WAITING_FOR_CLIENT);

                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();

                print(UpdateMessages.CLIENT_CONNECTED + clientSocket.getInetAddress().getHostAddress());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void print(String message) {
        System.out.println(message);
    }
}
