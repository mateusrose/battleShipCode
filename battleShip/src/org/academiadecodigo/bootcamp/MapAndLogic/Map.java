package org.academiadecodigo.bootcamp.MapAndLogic;

import org.academiadecodigo.bootcamp.game.Player;

import java.io.IOException;
import java.util.Scanner;

public class Map {
    private int numRows = 10;
    private int numCols = 10;
    private int playerShips;
    private Player player;

    private String[][] grid = new String[numRows][numCols];
    private int[][] missedguesses = new int[numRows][numCols];

    public String[][] getPos() {
        return grid;
    }

    public void setGrid(String[][] x) {
        grid = x;
    }
    public Map(Player player){
        this.player=player;
        StringBuilder gridRepresentation = new StringBuilder();

        //player.getClientHandler().getOutputFromServer().println("  ");
        gridRepresentation.append("\n");
        //System.out.print("  ");

        for(int i = 0; i < numCols; i++)
            gridRepresentation.append("   ").append(i);
           // player.getClientHandler().getOutputFromServer().println(i);
        //player.getClientHandler().getOutputFromServer().println();
        gridRepresentation.append("\n");

        //Middle section of Ocean Map
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = "    ";
                if (j == 0)
                    //player.getClientHandler().getOutputFromServer().println(i + "|" + grid[i][j]);
                    gridRepresentation.append(i).append("|").append(grid[i][j]);
                else if (j == grid[i].length - 1)
                    gridRepresentation.append(grid[i][j]).append("|").append(i).append("\n");
                  //  player.getClientHandler().getOutputFromServer().println(grid[i][j] + "|" + i);
                else
                    gridRepresentation.append(grid[i][j]);
                    //player.getClientHandler().getOutputFromServer().println(grid[i][j]);
            }
            gridRepresentation.append("\n");
            //player.getClientHandler().getOutputFromServer().println();
        }

        //Last section of Ocean Map
        gridRepresentation.append(" ");
        //player.getClientHandler().getOutputFromServer().println("  ");
        for(int i = 0; i < numCols; i++)
            gridRepresentation.append("   ").append(i);
          //  player.getClientHandler().getOutputFromServer().println(i);
       // player.getClientHandler().getOutputFromServer().println();
        gridRepresentation.append("\n");
        player.getClientHandler().getOutputFromServer().println(gridRepresentation);
    }

    public void printOceanMap(){
        player.getClientHandler().getOutputFromServer().println();
        //First section of Ocean Map
        player.getClientHandler().getOutputFromServer().println("  ");
        for(int i = 0; i < numCols; i++)
            player.getClientHandler().getOutputFromServer().println(i);
        player.getClientHandler().getOutputFromServer().println();

        //Middle section of Ocean Map
        for(int x = 0; x < grid.length; x++) {
            player.getClientHandler().getOutputFromServer().println(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                player.getClientHandler().getOutputFromServer().println(grid[x][y]);
            }

            player.getClientHandler().getOutputFromServer().println("|" + x);
        }

        //Last section of Ocean Map
        player.getClientHandler().getOutputFromServer().println("  ");
        for(int i = 0; i < numCols; i++)
            player.getClientHandler().getOutputFromServer().println(i);
        player.getClientHandler().getOutputFromServer().println();
    }
    public void deployPlayerShips() throws IOException {
      //  Scanner input = new Scanner(System.in);

        player.getClientHandler().getOutputFromServer().println("\nDeploy your ships:");

        //Deploying five ships for player

        playerShips = 5;

        for (int i = 1; i <= playerShips; ) {

            player.getClientHandler().getOutputFromServer().println("Enter X coordinate for your " + i + " ship: ");

            int x = Integer.parseInt(player.getClientHandler().getInputFromServer().readLine());

            player.getClientHandler().getOutputFromServer().println("Enter Y coordinate for your " + i + " ship: ");

            int y =Integer.parseInt(player.getClientHandler().getInputFromServer().readLine());

            if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
            {
                grid[x][y] =   "X";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "X")
                player.getClientHandler().getOutputFromServer().println("You can't place two or more ships on the same location");
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                player.getClientHandler().getOutputFromServer().println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
        }
        printOceanMap();
    }

    public static void main(String[] args) {
       // Map map = new Map();
       // map.deployPlayerShips();
    }
}
