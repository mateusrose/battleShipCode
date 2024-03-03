package org.academiadecodigo.bootcamp.MapAndLogic;

import org.academiadecodigo.bootcamp.game.Player;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Map {
    private int numRows = 10;
    private int numCols = 10;
    private int playerShips;
    private Player player;
    private LinkedList<Cell> cellList;
    private int[][] missedguesses = new int[numRows][numCols];
    StringBuilder gridRepresentation = new StringBuilder();


    public Map(Player player) throws IOException {
        this.player = player;
        cellList = new LinkedList<>();
        printOceanMap();
        deployPlayerShips();
    }
    public void setGridRepresentation(){
        gridRepresentation=new StringBuilder();
    }

    public void printOceanMap() throws IOException {
        setGridRepresentation();
        gridRepresentation.append("\n");

        for (int i = 0; i < numCols; i++) {
            if (i == 0) {
                gridRepresentation.append("  ");
            }
            gridRepresentation.append("  ");
        }

        gridRepresentation.append("\n");
        gridRepresentation.append("\n");

        int cellCount = 0;

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                cellList.add(new Cell(i, j));
                System.out.println("we here i" + i);
                System.out.println("we here j" + j);
                String state = cellList.get(cellCount).getState();
                System.out.println(cellList.get(cellCount).getState());
                // gridRepresentation.append(cellList.get(cellCount).getState());
                if (j == 0) {
                    gridRepresentation.append(i).append("| ").append(state);
                } else if (j == numRows - 1) {
                    gridRepresentation.append(state).append(" |").append("\n");
                } else {
                    gridRepresentation.append(state);
                }
                cellCount++;
            }
            gridRepresentation.append("\n");
        }
        //Last section of Ocean Map
        gridRepresentation.append(" ");
        for (int i = 0; i < numCols; i++) {
            if (i == 0) {
                gridRepresentation.append(" ");
            }
            gridRepresentation.append("  ").append(i);
        }
        gridRepresentation.append("\n");
        player.getClientHandler().getOutputFromServer().println(gridRepresentation);
        player.getClientHandler().getOutputFromServer().println("\n BANANAAAAAAAAAAAAAAAA");

    }

    public void updateOceanMap() throws IOException {
        StringBuilder mapRepresentation = new StringBuilder();
        mapRepresentation.append("\n");

        for (int i = 0; i < numCols; i++) {
            if (i == 0) {
                mapRepresentation.append("  ");
            }
            mapRepresentation.append("  ");
        }

        mapRepresentation.append("\n");
        mapRepresentation.append("\n");

        int cellCount = 0;

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                System.out.println("we here i" + i);
                System.out.println("we here j" + j);
                String state = cellList.get(cellCount).getState();
                System.out.println(cellList.get(cellCount).getState());
                // gridRepresentation.append(cellList.get(cellCount).getState());
                if (j == 0) {
                    mapRepresentation.append(i).append("| ").append(state);
                } else if (j == numRows - 1) {
                    mapRepresentation.append(state).append(" |").append("\n");
                } else {
                    mapRepresentation.append(state);
                }
                cellCount++;
            }
            mapRepresentation.append("\n");
        }
        //Last section of Ocean Map
        mapRepresentation.append(" ");
        for (int i = 0; i < numCols; i++) {
            if (i == 0) {
                mapRepresentation.append(" ");
            }
            mapRepresentation.append("  ").append(i);
        }
        mapRepresentation.append("\n");
        player.getClientHandler().getOutputFromServer().println(mapRepresentation);
        player.getClientHandler().getOutputFromServer().println("\n BANANAAAAAAAAAAAAAAAA");
      }


    public void deployPlayerShips() throws IOException {
        //  Scanner input = new Scanner(System.in);

        player.getClientHandler().getOutputFromServer().println("\nDeploy your ships:");

        //Deploying five ships for player

        playerShips = 5;

        for (int i = 1; i <= playerShips; i++) {

            player.getClientHandler().getOutputFromServer().println("Enter X coordinate for your " + i + " ship: ");

            int x = Integer.parseInt(player.getClientHandler().getInputFromServer().readLine());
            System.out.println(x);
            player.getClientHandler().getOutputFromServer().println("Enter Y coordinate for your " + i + " ship: ");

            int y = Integer.parseInt(player.getClientHandler().getInputFromServer().readLine());
            System.out.println(y);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (!cellList.get(xyToIndex(x, y)).isShip())) {
                cellList.get(xyToIndex(x, y)).setState(" O ");
                cellList.get(xyToIndex(x, y)).setShip(true);
            } else if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (cellList.get(xyToIndex(x, y)).isShip()))
                player.getClientHandler().getOutputFromServer().println("You can't place two or more ships on the same location");
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                player.getClientHandler().getOutputFromServer().println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }
        updateOceanMap();
    }

    public int xyToIndex(int x, int y) {
        int index = 0;
        for (int i = 0; i < x ; i++) {
            for (int j = 0; j < y; j++) {
                index++;
                System.out.println(cellList.get(i));
            }
        }
        return index;
    }

    public void checkCell(int x, int y) throws IOException {
       Cell cell = cellList.get(xyToIndex(x,y));
       if(cell.isShip()){
        cell.setSunk(true);
        cell.setGuessable(false);
        cell.setState(" H ");
       } else{
           cell.setGuessable(false);
           cell.setState(" X ");
       }
       updateOceanMap();
    }

    public LinkedList<Cell> getCellList() {
        return cellList;
    }
}
