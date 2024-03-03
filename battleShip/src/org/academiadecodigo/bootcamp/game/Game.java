package org.academiadecodigo.bootcamp.game;

import com.sun.security.ntlm.Client;
import org.academiadecodigo.bootcamp.server.ClientHandler;
import org.academiadecodigo.bootcamp.server.Server;

import java.io.IOException;
import java.util.List;

public class Game {
    Server server;
    ClientHandler player1;
    ClientHandler player2;
    private List<ClientHandler> clientList;
    public List<ClientHandler> getClientList() {
        return clientList;
    }
    public Game(Server server, ClientHandler player1, ClientHandler player2, List<ClientHandler> clientList) {
        this.clientList=clientList;
        this.player1 = player1;
        this.player2 = player2;
        this.server = server;
    }
    public void setMaps(ClientHandler clientHandler){
        clientHandler.getPlayer().getMap().setOpponentCellList(clientHandler.getPlayer().getMap().getCellList());
    }
    public void init() {
        while (player1.getPlayer().getShips() > 0 && player2.getPlayer().getShips() > 0) {
            if (player1.getPlayer().isPlaying() && player2.getPlayer().isPlaying()) {
                try {
                    player2.wait();
                    player1.gameTurn();
                    player1.wait();
                    player2.gameTurn();
                    notifyAll();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
