package tictactoe.models;

import java.util.Scanner;

public class Player {
    private String playerName;
    private char playerSymbol;
    private PlayerType playerType;
    private Scanner sc;

    //if someone create a player wwhat are all the things should provide - all the attributes - so, create all the constructor
    public Player(String playerName, char playerSymbol, PlayerType playerType) {
        this.playerName = playerName;
        this.playerSymbol = playerSymbol;
        this.playerType = playerType;
        //every player initialize scanner, but it is not the best choice, let's try and use it here
        this.sc = new Scanner(System.in);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(char playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    //if the next player is HUMAN in the player list , then this decideMove will call during run time - Here, polymorphism works here
    public Move decideMove(Board board) {
        //ask the player where do you want to move
        // It is not good to define scanner everytime. So, use at initially
        System.out.println("Which row you want to move, starting from 0: ");
        int row = sc.nextInt();
        System.out.println("Which column you want to move, starting from 0: ");
        int col = sc.nextInt();

        //return new Move(this, new Cell(row, col));
        //Move has player and cell -> cell and board are in the same path now
        //pick the cell from the board itself
        Cell currentCell = board.getBoard().get(row).get(col);
        return new Move(this, currentCell);
    }
}
