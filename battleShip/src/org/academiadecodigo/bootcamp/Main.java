package org.academiadecodigo.bootcamp;

import org.academiadecodigo.bootcamp.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }
}
