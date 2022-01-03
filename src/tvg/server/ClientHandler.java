package tvg.server;

import tvg.game.Game;

import java.io.*;
import java.net.Socket;
import java.util.*;

/*
* client handler
*/
public class ClientHandler extends Thread {

    public final Socket clientSocket;
    private List<ClientHandler> allClientsList;

    Scanner sc = new Scanner(System.in);
    Game game;

    private String line;
    private String name;

    public ClientHandler(Socket socket, List<ClientHandler> list) {
        this.clientSocket = socket;
        this.allClientsList = list;
    }

    @Override
    public void run() {
        try {
            clientJoin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientJoin() throws IOException {

        PrintWriter out = null;
        BufferedReader in = null;

        try {

            out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println("Insert your name: ");
            name = in.readLine();
            System.out.println("Client " + name + " wrote their name");




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAllClientsList(List<ClientHandler> allClientsList) {
        this.allClientsList = allClientsList;
    }

}

