package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {
    ServerSocket socket;
    Socket clientSocket;
    private List<ClientHandler> clientList;
    public static final int DEFAULT_PORT = 9999;
    public static final int MAX_CLIENTS = 2;
    private int playerCount = 1;
    private Semaphore turnSempahore;

    private ExecutorService executor;
    private Game game;


    public Server() throws IOException {
        clientList = new LinkedList<>();
        socket = new ServerSocket(DEFAULT_PORT);
        executor = Executors.newFixedThreadPool(2);
        turnSempahore=new Semaphore(1);
        System.out.println("Server Started");
    }

    public void waitForConnections() throws IOException {
        ClientHandler clientHandler;

        System.out.println("Waiting for client");

        clientSocket = socket.accept();
        System.out.println("Client Connected");
        Thread clientThread = new Thread(clientHandler = new ClientHandler(clientSocket, this,playerCount, turnSempahore));
        playerCount++;
        clientList.add(clientHandler);
        executor.submit(clientThread);
        if (clientList.size() == 2) {
            game = new Game(this, clientList.get(0),clientList.get(1),clientList);
            clientList.get(0).setGame(game);
            clientList.get(1).setGame(game);
         //   game.init();




        }

    }

    public void start() throws IOException {
        while (clientList.size() < MAX_CLIENTS) {
            waitForConnections();
        }

        //clientListReady todos true -->)
    }
    public List<ClientHandler> getClientList() {
        return clientList;
    }
}
