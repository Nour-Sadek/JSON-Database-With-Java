package org.example.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Set;

public class Main {

    private static int DATABASE_SIZE = 1000;
    private static String[] database = new String[DATABASE_SIZE];
    private static Set<String> commands = Set.of("get", "set", "delete");
    private static String ERROR = "ERROR";
    private static String OK = "OK";
    private static String ADDRESS = "127.0.0.1";
    private static int PORT = 23456;

    public static void main(String[] args) throws IOException {

        Arrays.fill(database, "");

        // Create a server
        ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
        System.out.println("Server started!");

        // Find a Client connection once
        Socket socket = server.accept();
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output  = new DataOutputStream(socket.getOutputStream());

        // Receive message from the user
        String clientMessage = input.readUTF();

        while (!clientMessage.startsWith("exit")) {

            String[] words = clientMessage.split(" ");
            if (words.length < 2 || !commands.contains(words[0])) output.writeUTF(ERROR);
            else {
                String command = words[0];
                if (!correctIndex(words[1])) output.writeUTF(ERROR);
                else {
                    int index = Integer.parseInt(words[1]) - 1;
                    switch (command) {
                        case "set":
                            if (words.length > 2) {
                                String text = "";
                                for (int i = 2; i < words.length; i++) {
                                    text = text + words[i] + " ";
                                }
                                text = text.substring(0, text.length() - 1);
                                database[index] = text;
                            }
                            output.writeUTF(OK);
                            break;
                        case "get":
                            if (database[index].isEmpty()) output.writeUTF(ERROR);
                            else output.writeUTF(database[index]);
                            break;
                        case "delete":
                            database[index] = "";
                            output.writeUTF(OK);
                            break;
                    }
                }
            }

            // Find another client connection
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output  = new DataOutputStream(socket.getOutputStream());

            // Receive message from the user
            clientMessage = input.readUTF();

        }

        output.writeUTF(OK);
        server.close();

    }

    private static boolean correctIndex(String userInput) {
        try {
            int index = Integer.parseInt(userInput);
            return index > 0 && index <= DATABASE_SIZE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
