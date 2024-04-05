package org.example.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import Command.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

public class Main {

    // Parameters for JCommander
    @Parameter(names="-t")
    String type;
    @Parameter(names="-k")
    String key;
    @Parameter(names="-v")
    String value;

    private static String ADDRESS = "127.0.0.1";
    private static int PORT = 23456;
    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {

        Main main = new Main();
        // Parse commandline arguments
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        // Create the message
        String clientCommand = gson.toJson(new Command(main.type, main.key, main.value));

        // Create a connection to the server
        Socket socket = new Socket(Inet4Address.getByName(ADDRESS), PORT);
        System.out.println("Client started!");

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // Send a message to the server
        System.out.println("Sent: " + clientCommand);
        output.writeUTF(clientCommand);

        // Receive the message from the server
        String serverMessage = input.readUTF();
        System.out.println("Received: " + serverMessage);

        socket.close();

    }

}
