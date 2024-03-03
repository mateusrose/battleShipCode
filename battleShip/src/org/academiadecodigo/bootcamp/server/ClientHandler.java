package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.client.ImageBoat;
import org.academiadecodigo.bootcamp.game.Player;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private Prompt prompt;
    private Player player;
    private final static CyclicBarrier barrier = new CyclicBarrier(2);
    private final Semaphore turnSemaphore;
    private BufferedReader inputFromServer;
    private PrintStream outputFromServer;
    private int playerNum;
    private int loopCounter = 0;
    private boolean gameStarted = false;

    public ClientHandler(Socket clientSocket, Server server, int playerNum, Semaphore turnSemaphore) throws IOException {
        this.playerNum = playerNum;
        this.turnSemaphore=turnSemaphore;
        this.clientSocket = clientSocket;
        this.server = server;
        setup();
    }

    public void setup() throws IOException {
        prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outputFromServer = new PrintStream(clientSocket.getOutputStream());
        player = new Player(this, playerNum);
        outputFromServer.println("BOAT BOAT BOAT BOAT BOAT BOAT");
    }

    //client
    @Override
    public void run() {
        System.out.println("Running" + Thread.currentThread());
        setName();
        setReady();
        waitForOtherPlayers();
        try {
            player.setMap();
            waitForOtherPlayers();
            gameStarted = true;
            player.setPlaying(true);
            while (gameStarted) {
                waitGameTurn();
                gameTurn();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        isReadyOptions.add("YES");
        isReadyOptions.add("Y");

        StringSetInputScanner isReady = new StringSetInputScanner(isReadyOptions);
        isReady.setMessage("Are you ready to sink some ships? (yes/no)\nAnswer: ");
        isReady.setError("Please confirm you are ready to play");

        String readyAnswer = prompt.getUserInput(isReady);
        if (readyAnswer.contains("y") || readyAnswer.contains("Y") ) {
            player.setReady(true);
            System.out.println(player.isReady());

        }
    }

    public void waitForOtherPlayers() {
        try {
            System.out.println("waiting" + Thread.currentThread());
            outputFromServer.println(ImageBoat.IMAGE1);
            outputFromServer.println("Waiting for your opponent");
            barrier.await();
            outputFromServer.println("Both players ready to roll!");
            barrier.await(); // Wait for both players to complete their turn
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
    public void waitGameTurn(){
        try {
            outputFromServer.println("Waiting for other players...");
            turnSemaphore.acquire(); // Acquire the semaphore to wait for the turn
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public Player getPlayer() {
        return player;
    }

    public PrintStream getOutputFromServer() {
        return outputFromServer;
    }

    public BufferedReader getInputFromServer() {
        return inputFromServer;
    }

    public Server getServer() {
        return this.server;
    }

    public void gameTurn() throws IOException {
        outputFromServer.println("Where do you wish to attack? Column: ");
        int x = Integer.parseInt(inputFromServer.readLine());
        outputFromServer.println("Where do you wish to attack? Row: ");
        int y = Integer.parseInt(inputFromServer.readLine());
        player.attack(x, y);
        turnSemaphore.release();
    }
}

