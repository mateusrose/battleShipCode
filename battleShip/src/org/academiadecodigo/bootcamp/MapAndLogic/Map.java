package org.academiadecodigo.bootcamp.MapAndLogic;

public class Map {
    private boolean[] X = new boolean[10];
    private boolean[] Y = new boolean[10];

    public boolean[] getX() {
        return X;
    }

    public void setX(boolean[] x) {
        X = x;
    }

    public boolean[] getY() {
        return Y;
    }

    public void setY(boolean[] y) {
        Y = y;
    }
    public Map(){
        for (int i = 0; i < X.length ; i++) {
            X[i]= false;
            for (int j = 1; j < Y.length ; j++) {
                Y[j]= false;

            }
        }
    }
}
