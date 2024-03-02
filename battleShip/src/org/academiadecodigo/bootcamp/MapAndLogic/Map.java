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

    private String[][] grid = new String[numRows][numCols];
    private int[][] missedguesses = new int[numRows][numCols];
    StringBuilder gridRepresentation = new StringBuilder();

    public String[][] getPos() {
        return grid;
    }

    public void setGrid(String[][] x) {
        grid = x;
    }

    public Map(Player player) throws IOException {
        this.player = player;
        cellList = new LinkedList<>();
        printOceanMap();
        // deployPlayerShips();
    }


    public void printOceanMap() throws IOException {
        gridRepresentation.append("\n");

        for (int i = 0; i < numCols; i++) {
            if (i == 0) {
                gridRepresentation.append("  ");

            }
            gridRepresentation.append("   ").append(i);
        }

        gridRepresentation.append("\n");

        int cellCount = 0;

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                cellList.add(new Cell(i, j));
                System.out.println("we here i" + i);
                System.out.println("we here j" + j);

                String state = cellList.get(cellCount).getState();
                System.out.println(cellList.get(cellCount).getState());

                gridRepresentation.append(cellList.get(cellCount).getState());
                if (j == 0) {
                    gridRepresentation.append(i).append("| ").append(state);
                } else if (j == grid[i].length - 1) {
                    gridRepresentation.append(state).append(" |").append(i).append("\n");
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
            gridRepresentation.append("   ").append(i);
        }
        gridRepresentation.append("\n");
        player.getClientHandler().getOutputFromServer().println(gridRepresentation);
        player.getClientHandler().getOutputFromServer().println("\n BANANAAAAAAAAAAAAAAAA");

    }
    public void updateOceanMap() throws IOException {
        gridRepresentation.append("\n");

        for (int i = 0; i < numCols; i++) {
            if (i == 0) {
                gridRepresentation.append("  ");

            }
            gridRepresentation.append("   ").append(i);
        }
        gridRepresentation.append("\n");
        int cellCount = 0;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                String state = cellList.get(cellCount).getState();
                System.out.println(cellList.get(cellCount).getState());
                gridRepresentation.append(cellList.get(cellCount).getState());
                if (j == 0) {
                    gridRepresentation.append(i).append("| ").append(state);
                } else if (j == grid[i].length - 1) {
                    gridRepresentation.append(state).append(" |").append(i).append("\n");
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
            gridRepresentation.append("   ").append(i);
        }
        gridRepresentation.append("\n");
        player.getClientHandler().getOutputFromServer().println(gridRepresentation);
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

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (Objects.equals(grid[x][y], " "))) {
                grid[x][y] = " X ";

            } else if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && Objects.equals(grid[x][y], "X"))
                player.getClientHandler().getOutputFromServer().println("You can't place two or more ships on the same location");
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                player.getClientHandler().getOutputFromServer().println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }
        printOceanMap();
    }

    public static void main(String[] args) {
        // Map map = new Map();
        // map.deployPlayerShips();
    }
}
