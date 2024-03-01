package org.academiadecodigo.bootcamp.client;

import org.academiadecodigo.bootcamp.server.ServerLauncher;

import java.io.IOException;

public class ClientLauncher {
    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", ServerLauncher.DEFAULT_PORT);
            client.start();

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}
