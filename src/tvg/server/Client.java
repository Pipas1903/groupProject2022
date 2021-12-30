package tvg.server;

import tvg.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // OPEN A CLIENT SOCKET
    Scanner scan = new Scanner(System.in);
    InetAddress hostName;
    int portNumber;
    Socket clientSocket;


    public void getServerInfo() throws IOException {
        System.out.print("Server IP: ");
        String host = scan.nextLine();
        this.hostName = InetAddress.getByName(host);
        System.out.print("Port: ");
        portNumber = scan.nextInt();
        scan.nextLine();
        clientSocket =  new Socket(hostName, portNumber);
    }

    public void speak() throws IOException {
        while(clientSocket.isBound()){
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println(in.readLine());

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String message = scan.nextLine();
            out.println(message);


        }
    }

    public static void main(String[] args) throws IOException {
        Client cliente = new Client();
        cliente.getServerInfo();
        cliente.speak();

    }
}

