package org.academiadecodigo.bootcamp.client;

public class Player {
        private String username;
        private boolean isReady;

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
    }

