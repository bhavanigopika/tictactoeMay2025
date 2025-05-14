package tictactoe.models;

import tictactoe.exceptions.InvalidGameConstructionParametersException;
import tictactoe.exceptions.InvalidMoveException;
import tictactoe.exceptions.InvalidPlayerCountException;
import tictactoe.factories.GameWinningStrategyFactory;
import tictactoe.strategies.gamewinningstrategy.GameWinningStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> playerList;
    private Player winner;
    private GameState gameState;
    private int nextPlayerIndex;
    private List<Move> moveList;
    private GameWinningStrategy gameWinningStrategy;

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

    public void setGameWinningStrategy(GameWinningStrategy gameWinningStrategy) {
        this.gameWinningStrategy = gameWinningStrategy;
    }

    //getters for game state
    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    /***/

    public void displayBoard() {
        this.board.displayBoard();
    }

    public void makeNextMove(Game game) throws InvalidMoveException {
        //1) first pick the player to know whose turn it is, so get the index of the current player
        //who tries to make the move -> in simple, we say, ask the player to make the move...so, first get the index
        Player currentPlayerToMove = playerList.get(nextPlayerIndex);

        System.out.println("It is " + currentPlayerToMove.getPlayerName() + "'s turn and index is " + nextPlayerIndex);
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

        //7) Now, I am updating to the same cell. So, update in list of move
        moveList.add(move);

        //8) next, check if this was a winning move, so strategyWinning comes here
        if(this.gameWinningStrategy.checkWinner(board, move.getCell(), currentPlayerToMove)){
            //if it is true, then players has won automatically set the game state
            gameState = GameState.ENDED;
            winner = currentPlayerToMove;
        }else if(moveList.size() == board.getSize() * board.getSize()){ //list of the move(all moves)  is equal to n * n(board dimension) -> draw
            gameState = GameState.DRAW;
        }

        //9) update the next player index
        nextPlayerIndex = nextPlayerIndex + 1;
        //here, let's say, we have 3 players(player p1,player p2,player p3). So, index is 3 (0, 1, 2) because of 3 players.
        //So, player1(X), player2(O), player3(R)
        //NextPlayerIndex = 0 -> p1 played at index 0.
        //NextPlayerIndex = 1 -> p2 played at index 1.
        //NextPlayerIndex = 2 -> p3 is playing at index 2.
        //Currently, it is player p3's turn. p3 made a move and I say nextPlayerIndex = nextPlayerIndex + 1.
        //So, NextPlayerIndex = 3 -> Do we have player p4 move at index = 4? NO.
        //Whose move should it be next? Player p1. So, I do "mod" here. nextPlauerIndex = nextPlayerIndex % playerList.size()
        //nextPlayerIndex = 3 playerList size = 3
        //if 3 % 3 = 0. Then nextPlayerIndex = 0 whose corresponding player is player p1. Then player p1 will be the next move. So, I use this condition
        //index - 0, 1, 2, 0, 1, 2 -> It is cyclic
        nextPlayerIndex = nextPlayerIndex % playerList.size();

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
        private int size;
        private List<Player> playerList;
        private GameWinningStrategy gameWinningStrategy;

        //setters
        /***/
        //Actually no need to get the board, playersList details. We have to set it. So add only setters
        //set the size and return to the same Builder object (i.e) Builder class -> class is blueprint of idea or blueprint for creating the object
         public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        //set the playerList and return to the same Builder object (i.e) Builder class -> class is blueprint of idea or blueprint for creating the object
        public Builder setPlayerList(List<Player> playerList) {
            this.playerList = playerList;
            return this;
        }

        //set game winning strategy
        public Builder setGameWinningStrategy(String winningStrategy) {
            this.gameWinningStrategy = GameWinningStrategyFactory.getGameWinningStrategy(winningStrategy, this.size);
            return this;
        }
        /***/

        private void validate() throws InvalidGameConstructionParametersException, InvalidPlayerCountException {
            //What are all validation?
            //1) Size of the board(Dimension should not be less than 3)
            if (this.size < 3) {
                throw new InvalidGameConstructionParametersException("The size of the board cannot be less than 3");
            }
            //2) players should be dimension - 1 (board size - 1)
            if (this.playerList.size() != this.size - 1) {
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
            game.setBoard(new Board(size));
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

            //game.setGameWinningStrategy(new OrderOneGameWinningStrategy(board.getSize()));
            //now, can you also have it according to user input? -> like user able to pass which strategy able to use -> using Factory Design Pattern
            //set the game winning strategy here
            game.setGameWinningStrategy(this.gameWinningStrategy);
            return game;

        }
    }
}

/* 1.5 hours in machine coding
Overview -> 2 mins
Requirement Gathering -> 10 - 15 min
Class Diagram and coding models -> 15 mins
Time to complete code -> 1 hr
 */







//}
