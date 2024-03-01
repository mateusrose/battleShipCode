package org.academiadecodigo.bootcamp.MapAndLogic;

public class Map {
    private boolean[][] position = new boolean[10][10];

    public boolean[][] getPos() {
        return position;
    }

    public void setPosition(boolean[][] x) {
        position = x;
    }
    public Map(){
        for (int i = 0; i < position.length ; i++) {
            for (int j = 0; j < position.length; j++) {
                position[i][j]= false;
            }

            }
        }
    public void getArray(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println("array "+position[i][j]);
            }

        }
    }

    public static void main(String[] args) {
        Map map = new Map();
        map.getArray();
    }
}
