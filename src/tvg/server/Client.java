package tvg.server;

import tvg.board.Frame;
import tvg.game.Game;
import tvg.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    // OPEN A CLIENT SOCKET
    Scanner scan = new Scanner(System.in);
    InetAddress hostName;
    int portNumber;
    Socket clientSocket;
    private static List<Player> playerList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void getServerInfo() throws IOException {
        System.out.print("Server IP: ");
        String host = scan.nextLine();
        this.hostName = InetAddress.getByName(host);
        System.out.print("Port: ");
        portNumber = scan.nextInt();
        scan.nextLine();
        clientSocket = new Socket(hostName, portNumber);

    }

    public void speak() throws IOException {
        while (clientSocket.isBound()) {
            System.out.println("insert your name");
            //out.println("Insert your name: ");
            String line;
            line = sc.nextLine();
            //line = in.readLine();
            Player player = new Player(line);
            player.setSocket(clientSocket);
            playerList.add(player);

            System.out.println("Client " + line + " wrote their name");

            boolean notQuit = true;

            while (notQuit) {
                //out.println("If you wish to see the players that already joined, press 1");
                //out.println("Press 2 to quit this menu");
                //line = in.readLine();
                line = sc.nextLine();
                if (line.equals("1")) {
                    //out.println();
                    //out.println("Players that joined: ");
                    System.out.println("joined players");
                    for (int i = 0; i < playerList.size(); i++) {
                        System.out.println(playerList.get(i).getName());
                        // out.println(playerList.get(i).getName());
                    }
                }

                if (line.equals("2")) {
                    //out.println();
                    //out.println("From now on, you won't be able to refresh the players in the game!");
                    System.out.println("From now on, you won't be able to refresh the players in the game!");
                    notQuit = false;
                }

            }
            //out.println("do you wish to create a game or join one?");
            //out.println("1 - create \n2 - join");
            //line = in.readLine();
            System.out.println("create game or join?");
            System.out.println("1 - create \n2 - join");
            line = sc.nextLine();
            if (line.equals("1")) {
                Game game = new Game(playerList);
                Frame frame = new Frame(game);
                frame.start();
            }
            if (line.equals("2")) {
                //out.println("please wait for a game to start");
                System.out.println("please wait for a game to start");
            }

            // } catch (IOException e) {
            //   e.printStackTrace();
            // }
        }


    }

    public List<Player> getFourRandomPlayers() {
        Random random = new Random();

        List<Player> chosenPlayers = new ArrayList<>();
        ArrayList<Integer> number = random.ints(0, playerList.size()).
                distinct().
                limit(4).
                boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));

        for (int i = 0; i < 4; i++) {
            chosenPlayers.add(playerList.get(number.get(i)));
        }

        return chosenPlayers;
    }

    public static void main(String[] args) throws IOException {
        Client cliente = new Client();
        cliente.getServerInfo();
        cliente.speak();
    }


}

