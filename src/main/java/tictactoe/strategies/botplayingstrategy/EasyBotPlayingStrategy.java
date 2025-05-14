package tictactoe.strategies.botplayingstrategy;

import tictactoe.models.Board;
import tictactoe.models.CellState;
import tictactoe.models.Move;
import tictactoe.models.Player;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {
    @Override
    public Move decideMove(Player player, Board board) {
        //It will randomly make a move at anywhere. If it finds cells is empty then it will make a move
        //It works as whichever next cell is empty, then it will place the next move

        //If you want to pick more random, then pick all empty cell, randomize the cell and pick one of them then say it is going to be the cell
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                if(board.getBoard().get(i).get(j).getCellState().equals(CellState.EMPTY)){
                    //I made a move here
                    //get the player, get the cell from the board itself
                    //so don't mentioned as new Cell(i, j) (i.e) return new Move(player, new Cell(i, j));
                    //mention ad board.getBoard(i).get(j) (i.e) see below line
                    //So, use current cell as board.getBoard().get(i).get(j)
                    return new Move(player, board.getBoard().get(i).get(j));
                }
            }
        }
        return null;
    }
}
