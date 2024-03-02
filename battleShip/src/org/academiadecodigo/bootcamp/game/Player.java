package org.academiadecodigo.bootcamp.game;

import org.academiadecodigo.bootcamp.MapAndLogic.Map;
import org.academiadecodigo.bootcamp.server.ClientHandler;

public class Player {
        private String username;
        private boolean isReady;
        private Map map;
        private ClientHandler clientHandler;
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
        public void setMap(){
            map=new Map(this);
        }
    }

