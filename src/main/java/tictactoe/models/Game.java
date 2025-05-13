package tictactoe.models;

import tictactoe.exceptions.InvalidGameConstructionParametersException;
import tictactoe.exceptions.InvalidMoveException;
import tictactoe.exceptions.InvalidPlayerCountException;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> playerList;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<Move> moveList;

    //default constructor which is private
    private Game(){

    }
    //There should be static builder method. Job of getBuilder is to create an object of the inner class
    public static Builder getBuilder(){
        return new Builder();
    }

    /*
    //make the constructor private, because no one able to create object of Game class at outside
    private Game(Board board, List<Player> playerList) {
        this.board = board;
        this.playerList = playerList;
    }*/
    //Setters
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public void setMoveList(List<Move> moveList) {
        this.moveList = moveList;
    }

    //getters for game state
    public GameState getGameState() {
        return gameState;
    }

    /***/

    public void displayBoard() {
        board.displayBoard();
    }

    public void makeNextMove(Game game) throws InvalidMoveException {
        //1) first pick the player to know whose turn it is, so get the index of the current player
        //who tries to make the move -> in simple, we say ask the player to make the move
        Player currentPlayerToMove = playerList.get(nextPlayerIndex);

        System.out.println("It is " + currentPlayerToMove.getPlayerName() + "'s turn and index is " + currentPlayerToMove);
        //check adjacent cell state, if cell is empty then make the move. For this we need row and column of the board to go to particular cell

        //2) now, let the player decide to move. The player who is going to decide the move then logic of deciding the move in Player class
        //decideMove - where do you want to move -> it is in the board...

        //Move move = currentPlayerToMove.decideMove();

        Move move = currentPlayerToMove.decideMove(board);

        //3) once decided to move, then validate the move, if the move is correct or not
        boolean isValidMove = validateCurrentMove(move);

        //4) if it is invalid move, then throw the exception.
        if (!isValidMove) {
            throw new InvalidMoveException("Move is not valid");
        }

        //5) if valid move, now to validate the move, then move ahead
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        System.out.println("Move happened at: " + row + ", " + col );

        //6) update it in board
        //so, you have to set current player and mention cell state is filled
        board.getBoard().get(row).get(col).setCellState(CellState.FILLED);
        board.getBoard().get(row).get(col).setPlayer(currentPlayerToMove);

        //now update current situation in the list of move. Because in list of moves we have who is going to move(Player) and at which cell, the player going to move.

        //moveList.add(move); //if you add move in moveList, then it means you added the player who played(so, you mentioned current player details, cell state(filled or not)) -> but this player is already playing in the board

        /*
        Move has player and cell attributes.
        when you decide move, you add the current player, then add the cell with row and column
        later you update the cell in the board that the cell state is FILLED and player index. so, you update which is already present in the board
        So, move has different cell and board has different cell. You update the cell in the board
        But cell and board move should be same. So create one more Move and update the cell in the board
        */
        //So, created a finalMove which is resembling and talking about the same cell which is present inside your board

        //Move finalMove = new Move(currentPlayerToMove, board.getBoard().get(row).get(col));
        //moveList.add(finalMove);

        /*
        Instead of doing this, we just add one more thing as  Move move = currentPlayerToMove.decideMove(board);
        instead of  Move move = currentPlayerToMove.decideMove(); then simply do
        moveList.add(move) instead of moveList.add(finalMove)
        */

        //7) Now, I am updating to the same cell
        moveList.add(move);

        //8) check if this was a winning move
    }
    private boolean validateCurrentMove(Move move) {
        Cell currentCell = move.getCell();
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        //once get the row and col, then the condition with row and column is in between the board size and that cell which you are trying to move is EMPTY or not. This would be valid move
        //Simply return true or not
        return row >= 0 && row < board.getSize() &&
                col >= 0 && col < board.getSize() &&
                move.getCell().getCellState().equals(CellState.EMPTY);


    }
    //now game class create a builder
    //steps for creating a builder -> 1) Create a subclass which is static (i.e) Builder
    public static class Builder {
        //we have to copy the attributes that we initialize in the Builder. Also, see, GameController because that only we want
        private Board board;
        private List<Player> playerList;

        //Actually no need to get the board, playersList details. We have to set it. So add only setters
        //set the board and return to the same Builder object (i.e) Builder class -> class is blueprint of idea or blueprint for creating the object
        public Builder setBoard(Board board) {
            this.board = board;
            return this;
        }

        //set the playerList and return to the same Builder object (i.e) Builder class -> class is blueprint of idea or blueprint for creating the object
        public Builder setPlayerList(List<Player> playerList) {
            this.playerList = playerList;
            return this;
        }

        private void validate() throws InvalidGameConstructionParametersException, InvalidPlayerCountException {
            //What are all validation?
            //1) Size of the board(Dimension should not be less than 3)
            if (this.board.getSize() < 3) {
                throw new InvalidGameConstructionParametersException("The size of the board cannot be less than 3");
            }
            //2) players should be dimension - 1 (board size - 1)
            if (this.playerList.size() != this.board.getSize() - 1) {
                throw new InvalidPlayerCountException("The number of players in the game should be board size - 1");
            }

        }

        //we have build method -> build the particular object and return
        //This will check the validation and return the Game object
        //Benefit of builder is even before return the game, it will do the validation
        public Game build() throws InvalidGameConstructionParametersException, InvalidPlayerCountException {
            //validation
            validate();

            Game game = new Game();
            game.setBoard(board);
            game.setGameState(GameState.IN_PROGRESS);
            game.setMoveList(new ArrayList<>());//moves is empty arraylist right now
            //cannot predict winner right now, so don't set setWinner now as game.setWinner();
            //cannot say that the first player in the playerList will be winner. So, avoid the below line
            /*
            game.setWinner(playerList.get(0));
            */
            //initially player of the index will be 0 in the list
            game.setNextPlayerIndex(0);
            game.setPlayerList(playerList);

            return game;

        }
    }
}









//}
