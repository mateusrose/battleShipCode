package org.academiadecodigo.bootcamp.server;

import java.io.IOException;

public class ServerLauncher {

    public static final int DEFAULT_PORT = 8000;

    public static void main(String[] args) {
        try {
            Server server = new Server(DEFAULT_PORT);
            server.start();

        } catch (IOException e) {
            System.err.println("Error opening server socket: " + e.getMessage());

        }
    }
}
