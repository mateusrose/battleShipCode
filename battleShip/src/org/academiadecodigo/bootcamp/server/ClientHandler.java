package org.academiadecodigo.bootcamp.server;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.client.ImageBoat;
import org.academiadecodigo.bootcamp.client.Images;
import org.academiadecodigo.bootcamp.game.Game;
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
    private Game game;
    boolean hasWaited;

    public ClientHandler(Socket clientSocket, Server server, int playerNum, Semaphore turnSemaphore) throws IOException {

        this.playerNum = playerNum;
        this.turnSemaphore = turnSemaphore;
        this.clientSocket = clientSocket;
        this.server = server;
        setup();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setup() throws IOException {
        prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream(),true));
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outputFromServer = new PrintStream(clientSocket.getOutputStream(),true);
        player = new Player(this, playerNum, game);
        outputFromServer.println(Images.wierdBoat);
    }

    //client
    @Override
    public synchronized void run() {
        System.out.println("Running" + Thread.currentThread());
        setName();
        setReady();
        waitForOtherPlayers();
        try {
            player.setMap();
            waitForOtherPlayers();
            game.setMaps(this);
            gameStarted = true;
            player.setPlaying(true);
            //   player.getMap().opponentPrintOceanMap();
            while (gameStarted) {
                bestGame();
                if (player.getShips()<=0){
                    player.getClientHandler().getOutputFromServer().println("  ____    _    __  __ _____    _____     _______ ____  _ _ _ \n" +
                            " / ___|  / \\  |  \\/  | ____|  / _ \\ \\   / / ____|  _ \\| | | |\n" +
                            "| |  _  / _ \\ | |\\/| |  _|   | | | \\ \\ / /|  _| | |_) | | | |\n" +
                            "| |_| |/ ___ \\| |  | | |___  | |_| |\\ V / | |___|  _ <|_|_|_|\n" +
                            " \\____/_/   \\_\\_|  |_|_____|  \\___/  \\_/  |_____|_| \\_(_|_|_)");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bestGame() throws IOException {
        waitGameTurn();
        gameTurn();
    }

    public synchronized void setName() {
        StringInputScanner getName = new StringInputScanner();
        getName.setMessage("Please enter your username: ");
        String name = prompt.getUserInput(getName);
        player.setUsername(name);


    }

    public synchronized void setReady() {
        HashSet<String> isReadyOptions = new HashSet<>();

        isReadyOptions.add("y");
        isReadyOptions.add("yes");
        isReadyOptions.add("YES");
        isReadyOptions.add("Y");

        StringSetInputScanner isReady = new StringSetInputScanner(isReadyOptions);
        isReady.setMessage("                           _\n" +
                "                          (_)\n" +
                "           --\"\"-------   0/      ^^\n" +
                " .___...../ /__| |__\\ \\_/H__,      ^^\n" +
                "  \\                        /\n" +
                "#####^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~\\O/~~\\Q/~^~^~rr"+"\nAre you ready to sink some ships? (yes/no)\nAnswer: ");
        isReady.setError("Please confirm you are ready to play");

        String readyAnswer = prompt.getUserInput(isReady);
        if (readyAnswer.contains("y") || readyAnswer.contains("Y")) {
            player.setReady(true);
            System.out.println(player.isReady());

        }
    }

    public void waitForOtherPlayers() {
        try {
            System.out.println("waiting" + Thread.currentThread());
            outputFromServer.println("Waiting for your opponent");
            barrier.await();
            outputFromServer.println("Both players ready to roll!");
            barrier.await(); // Wait for both players to complete their turn
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }


    public void waitGameTurn() {
        try {
            if (!hasWaited) {
                outputFromServer.println("Waiting for other players...");
                turnSemaphore.acquire();
                hasWaited = true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        hasWaited = false;
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
        player.attack(x, y, outputFromServer);
        turnSemaphore.release();
    }

}

