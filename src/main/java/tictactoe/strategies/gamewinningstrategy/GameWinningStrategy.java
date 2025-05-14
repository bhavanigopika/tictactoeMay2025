package tictactoe.strategies.gamewinningstrategy;

import tictactoe.models.Board;
import tictactoe.models.Cell;
import tictactoe.models.Player;

public interface GameWinningStrategy {
    //add behaviour here
    //check whether the move is winning move or not, so return type is true or false.
    //what I need to know if it is winning move -> I need board, row, column, symbol
    //row, column get from "Cell"
    //symbol get from "Player"
    //ofcourse I can get take these all are from move class or from cell itself by move.getCell().getRow() or move.getCell().getColumn() or move.getPlayer().getSymbol or cell.getPlayer().getSymbol()
    boolean checkWinner(Board board, Cell moveCell, Player player);
}
