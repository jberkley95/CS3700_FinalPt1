import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author John Berkley
 * CPP Class: CS 3700
 * Date Created: Dec 08, 2018
 */

public class RPSClient {

    private static Integer port = 1337;
    private static String host = "localhost";
    private static String[] choices = {"Rock", "Paper", "Scissors"};
    private static int player0Wins = 0, player1Wins = 0, player2Wins = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("How many games would you like to play?");
        int numGames = new Scanner(System.in).nextInt();

        try {
            for (int i = 0; i < numGames; i++) {
                String input;

                Socket clientSocket = new Socket(RPSClient.host, RPSClient.port);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                input = choices[(int) (Math.random() * 3)];

                // Transmit input to the main player and provide feedback
                outToServer.writeBytes(input + "\n");
                System.out.println("Your input (" + input + ") was successfully transmitted.");

                // Close socket
                clientSocket.close();
            }

        } catch (ConnectException e) {
            String client1Choice;
            String client2Choice;

            ServerSocket mainSocket = new ServerSocket(RPSClient.port);

            for (int i = 0; i < numGames; i++) {
                // Player one
                Socket client1 = mainSocket.accept();
                BufferedReader client1Input = new BufferedReader(new InputStreamReader(client1.getInputStream()));

                // Player two
                Socket client2 = mainSocket.accept();
                BufferedReader client2Input = new BufferedReader(new InputStreamReader(client2.getInputStream()));

                // Get client inputs
                String client0Choice = choices[(int) (Math.random() * 3)];
                client1Choice = client1Input.readLine();
                client2Choice = client2Input.readLine();

                System.out.println("### Game #" + (i + 1) + " ####");

                if (client1Choice.equals(client2Choice)) {
                    System.out.println("Player 1 draws with Player 2.");
                } else if (client1Choice.equals("Rock") && client2Choice.equals("Scissors")) {
                    System.out.println("Player 1 beats player 2.");
                    player1Wins++;
                } else if (client1Choice.equals("Scissors") && client2Choice.equals("Rock")) {
                    System.out.println("Player 2 beats player 1.");
                    player2Wins++;
                } else if (client1Choice.equals("Rock") && client2Choice.equals("Paper")) {
                    System.out.println("Player 2 beats player 1.");
                    player2Wins++;
                } else if (client1Choice.equals("Paper") && client2Choice.equals("Rock")) {
                    System.out.println("Player 1 beats player 2.");
                    player1Wins++;
                } else if (client1Choice.equals("Scissors") && client2Choice.equals("Paper")) {
                    System.out.println("Player 1 beats player 2.");
                    player1Wins++;
                } else if (client1Choice.equals("Paper") && client2Choice.equals("Scissors")) {
                    System.out.println("Player 2 beats player 1.");
                    player2Wins++;
                }

                if (client0Choice.equals(client2Choice)) {
                    System.out.println("Player 0 draws with Player 2.");
                } else if (client0Choice.equals("Rock") && client2Choice.equals("Scissors")) {
                    System.out.println("Player 0 beats player 2.");
                    player0Wins++;
                } else if (client0Choice.equals("Scissors") && client2Choice.equals("Rock")) {
                    System.out.println("Player 2 beats player 0.");
                    player2Wins++;
                } else if (client0Choice.equals("Rock") && client2Choice.equals("Paper")) {
                    System.out.println("Player 2 beats player 0.");
                    player2Wins++;
                } else if (client0Choice.equals("Paper") && client2Choice.equals("Rock")) {
                    System.out.println("Player 0 beats player 2.");
                    player0Wins++;
                } else if (client0Choice.equals("Scissors") && client2Choice.equals("Paper")) {
                    System.out.println("Player 0 beats player 2.");
                    player0Wins++;
                } else if (client0Choice.equals("Paper") && client2Choice.equals("Scissors")) {
                    System.out.println("Player 2 beats player 0.");
                    player2Wins++;
                }

                if (client1Choice.equals(client0Choice)) {
                    System.out.println("Player 1 draws with Player 0.");
                } else if (client1Choice.equals("Rock") && client0Choice.equals("Scissors")) {
                    System.out.println("Player 1 beats player 0.");
                    player1Wins++;
                } else if (client1Choice.equals("Scissors") && client0Choice.equals("Rock")) {
                    System.out.println("Player 0 beats player 1.");
                    player0Wins++;
                } else if (client1Choice.equals("Rock") && client0Choice.equals("Paper")) {
                    System.out.println("Player 0 beats player 1.");
                    player0Wins++;
                } else if (client1Choice.equals("Paper") && client0Choice.equals("Rock")) {
                    System.out.println("Player 1 beats player 0.");
                    player1Wins++;
                } else if (client1Choice.equals("Scissors") && client0Choice.equals("Paper")) {
                    System.out.println("Player 1 beats player 0.");
                    player1Wins++;
                } else if (client1Choice.equals("Paper") && client0Choice.equals("Scissors")) {
                    System.out.println("Player 0 beats player 1.");
                    player0Wins++;
                }

                client1.close();
                client2.close();

                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            }

            mainSocket.close();

            System.out.println("\nResults:");
            System.out.println("Player 0 Wins: " + player0Wins);
            System.out.println("Player 1 Wins: " + player1Wins);
            System.out.println("Player 2 Wins: " + player2Wins);
        }
    }
}