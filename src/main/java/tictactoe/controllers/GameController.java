package tictactoe.controllers;

import tictactoe.exceptions.InvalidGameConstructionParametersException;
import tictactoe.exceptions.InvalidMoveException;
import tictactoe.exceptions.InvalidPlayerCountException;
import tictactoe.models.Board;
import tictactoe.models.Game;
import tictactoe.models.GameState;
import tictactoe.models.Player;

import java.util.List;

public class GameController {
    //client not to connect business logic(service layer) directly. So, creater layer in between called controller
    //Create/Start a Game now. How? Using Builder. Here using a lot of attributes and a lot of validation so, create GameBuilder
    public Game startGame(int size, List<Player> playerList) throws InvalidGameConstructionParametersException, InvalidPlayerCountException {
        try {
            return Game.getBuilder()
                    .setBoard(new Board(size))
                    .setPlayerList(playerList)
                    .build(); //Here redline because of exceptions. It is not good idea to throw the exception to the client. use try-block
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null; //if there is exception, null should be return
    }

    public void displayBoard(Game game){
        //Board class have the responsibility to print the board...So, write condition in board...
        //But, GameController should call to the Game...So, from the game only, we call the board
        game.displayBoard();
    }

    //Get the game state
    public GameState getGameStatus(Game game){
        return game.getGameState();
    }

    public void executeGame(Game game) throws InvalidMoveException {//execute move means make next move
        game.makeNextMove(game);
    }
}
