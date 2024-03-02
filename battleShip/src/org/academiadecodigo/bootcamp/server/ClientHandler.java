package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.client.Player;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private Prompt prompt;
    private Player player;
    private static final CountDownLatch barrier = new CountDownLatch(2);
    private BufferedReader inputFromServer;
    private PrintStream outputFromServer;

    public ClientHandler(Socket clientSocket, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        setup();
    }
    public void setup() throws IOException {
        prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outputFromServer = new PrintStream(clientSocket.getOutputStream());
        player = new Player();
        outputFromServer.println("BOAT BOAT BOAT BOAT BOAT BOAT");
    }

    @Override
    public void run() {
        System.out.println("Running" + Thread.currentThread());
        setName();
        setReady();
        waitForOtherPlayers();
        System.out.println("super banana");
        // metodo para decidir posicao inicial dos barco
        //thread para esperar
        //game logic waiting for input == map observer
    }

    public void setName() {
        StringInputScanner getName = new StringInputScanner();
        getName.setMessage("Please enter your username: ");
        String name = prompt.getUserInput(getName);
        player.setUsername(name);


    }

    public void setReady() {
        HashSet<String> isReadyOptions = new HashSet<>();

        isReadyOptions.add("y");
        isReadyOptions.add("yes");

        StringSetInputScanner isReady = new StringSetInputScanner(isReadyOptions);
        isReady.setMessage("Are you ready to sink some ships? (yes/no)\nAnswer: ");
        isReady.setError("Please confirm you are ready to play");

        String readyAnswer = prompt.getUserInput(isReady);
        if (readyAnswer.contains("y")) {
            player.setReady(true);
            System.out.println(player.isReady());

        }
    }

    public void waitForOtherPlayers() {
        try {
            barrier.countDown();
            outputFromServer.println("Waiting for your opponent");
            barrier.await();
            outputFromServer.println("Please select the positions for your ships");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }



}

