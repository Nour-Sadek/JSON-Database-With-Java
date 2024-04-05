package org.example.server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import Command.Command;

public class Main {

    private static Map<String, String> database = new HashMap<>();
    private static Set<String> commands = Set.of("get", "set", "delete");
    private static Map<String, String> ERROR = Map.of("response", "ERROR", "reason", "No such key");
    private static Map<String, String> OK = Map.of("response", "OK");
    private static String ADDRESS = "127.0.0.1";
    private static int PORT = 23456;
    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {

        // Create a server
        ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
        System.out.println("Server started!");

        // Find a Client connection once
        Socket socket = server.accept();
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output  = new DataOutputStream(socket.getOutputStream());

        // Receive message from the user
        Command clientCommand = gson.fromJson(input.readUTF(), Command.class);

        while (!clientCommand.getType().equals("exit")) {

            String type = clientCommand.getType();
            if (!commands.contains(type)) output.writeUTF(gson.toJson(Map.of("reason", "ERROR")));
            else {
                String key = clientCommand.getKey();
                switch (type) {
                    case "set":
                        database.put(key, clientCommand.getValue());
                        output.writeUTF(gson.toJson(OK));
                        break;
                    case "get":
                        if (!database.containsKey(key)) output.writeUTF(gson.toJson(ERROR));
                        else output.writeUTF(gson.toJson(Map.of("response", "OK", "value", database.get(key))));
                        break;
                    case "delete":
                        if (!database.containsKey(key)) output.writeUTF(gson.toJson(ERROR));
                        else {
                            database.remove(key);
                            output.writeUTF(gson.toJson(OK));
                        }
                        break;
                }
            }

            // Find another client connection
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output  = new DataOutputStream(socket.getOutputStream());

            // Receive message from the user
            clientCommand = gson.fromJson(input.readUTF(), Command.class);

        }

        output.writeUTF(gson.toJson(OK));
        server.close();

    }

}
