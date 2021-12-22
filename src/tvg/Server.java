package tvg;

import tvg.Player.Player;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static String ip = "";
    private static int port = 93;
    private static List<Player> playerList = new ArrayList<>();
    private static List<Threads> threadsList = new ArrayList<>();

    public static void main(String[] args) {
        initializerServer();
    }

    public static void initializerServer() {

        try {
            //ip = InetAddress.getLocalHost();
            serverSocket = new ServerSocket(port);
            System.out.println("waiting for client to connect");

            while (playerList.size() < 4) {

                clientSocket = serverSocket.accept();
                Threads thread = new Threads(clientSocket);
                thread.start();

                System.out.println("client connected " + clientSocket.getInetAddress().getHostAddress());
                System.out.println("Digite o nome:");

                Scanner sc = new Scanner(System.in);

                String name = sc.nextLine();

                Player player = new Player(name);

                threadsList.add(thread);

                playerList.add(player);
                thread.getPlayer().getName();

            }


        } catch (Exception e) {
            e.getMessage();
            e.getMessage();
        }


    }


}
