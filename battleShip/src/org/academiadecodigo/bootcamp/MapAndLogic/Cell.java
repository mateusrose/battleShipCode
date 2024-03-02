package org.academiadecodigo.bootcamp.MapAndLogic;

public class Cell {
    private String state = " ~ ";
    private int y;
    private int x;
    boolean isSunk;
    boolean Ship;
    boolean isGuessable = true;


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

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public boolean isShip() {
        return Ship;
    }

    public void setShip(boolean ship) {
        Ship = ship;
    }

    public boolean isGuessable() {
        return isGuessable;
    }

    public void setGuessable(boolean guessable) {
        isGuessable = guessable;
    }

 }
