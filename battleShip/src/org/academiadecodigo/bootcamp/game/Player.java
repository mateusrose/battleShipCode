package org.academiadecodigo.bootcamp.game;

import com.sun.security.ntlm.Client;
import org.academiadecodigo.bootcamp.MapAndLogic.Map;
import org.academiadecodigo.bootcamp.server.ClientHandler;

import java.io.IOException;
import java.util.List;

public class Player {
        private String username;
        private boolean isReady;
        private Map map;
        private ClientHandler clientHandler;
        private int ships = 5;
        private List<ClientHandler> opponent;

    public int getShips() {
        return ships;
    }

    public void setShips(int ships) {
        this.ships = ships;
    }

    public Player(ClientHandler clientHandler){
            this.clientHandler=clientHandler;

        }

        public boolean isReady() {
            return isReady;
        }

        public void setReady(boolean ready) {
            isReady = ready;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        public ClientHandler getClientHandler(){
            return clientHandler;
        }
        public void setMap() throws IOException {
            map=new Map(this);
        }
        public void attack(){
        opponent=clientHandler.getServer().getClientList();

        }
    }

