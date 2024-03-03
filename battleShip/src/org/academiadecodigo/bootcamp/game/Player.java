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
        private int playerNum;
        private boolean playing;

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getShips() {
        return ships;
    }

    public void setShips(int ships) {
        this.ships = ships;
    }

    public Player(ClientHandler clientHandler,int playerNum){
        this.playerNum = playerNum;
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
        public Map getMap(){
        return map;
        }
        public void attack(int x,int y) throws IOException {
        opponent=clientHandler.getServer().getClientList();
            for (int i = 0; i < opponent.size(); i++) {
                if (i!=playerNum){
                    System.out.println(i);
                    System.out.println(playerNum);
                    int j = opponent.get(i).getPlayer().getMap().xyToIndex(x,y);
                    System.out.printf("", opponent.get(i).getPlayer().getMap().getCellList().get(j).isSunk());
                    opponent.get(i).getPlayer().getMap().checkCell(x,y);
                    System.out.printf("", opponent.get(i).getPlayer().getMap().getCellList().get(j).isSunk());
                }

            }

        }
    }

