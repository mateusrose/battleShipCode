package org.academiadecodigo.bootcamp.MapAndLogic;

public class Cell {
    private String state = " ~";
    private int y;
    private int x;
    boolean isSet;
    boolean isChosen;
    boolean isGuessable;


    public Cell(int x,int y){
        this.x=x;
        this.y=y;

    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public boolean isGuessable() {
        return isGuessable;
    }

    public void setGuessable(boolean guessable) {
        isGuessable = guessable;
    }

    public void setState() {

    }

}
