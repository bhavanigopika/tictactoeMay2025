package tictactoe.strategies.gamewinningstrategy;

import tictactoe.models.Board;
import tictactoe.models.Cell;
import tictactoe.models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneGameWinningStrategy implements GameWinningStrategy{
    //I need hashmap to get time complexity of O(n) - finding all row, column, diagonal checking
    //Maintain the list of hashmap for row and column like
    /*
    //if 3 * 3 board size
            0                1                     2
    0 [{X -> 1, O -> 2}], [{X -> 2, O -> 0}], [{X -> 0, O -> 1}]
    1 ...
    2 ...
    */
    private List<HashMap<Character, Integer>> rowSymbolCounts = new ArrayList<>();
    private List<HashMap<Character, Integer>> colSymbolCounts = new ArrayList<>();
    //Maintain the hashmap for L-R diagonal and R-L diagonal
    //for Left to Right diagonal and Right to Left Diagonal, we are not going to maintain list, just to store in hashmap
    //because it stores only 3 values if  3 * 3 board size for each left-right diagonal and right-left diagonal
    private HashMap<Character, Integer> topLeftDiagonalSymbolCounts = new HashMap<>();
    private HashMap<Character, Integer> topRightDiagonalSymbolCounts = new HashMap<>();

    //Now, initialize the hashmap in constructor
    //every row and column create a hashmap and this maintains in a list like 3 hashmap in row and 3 hashmap in col if 3*3 board size
    public OrderOneGameWinningStrategy(int size) {
        for(int i = 0; i < size; i++){
            rowSymbolCounts.add(new HashMap<>());
            colSymbolCounts.add(new HashMap<>());
        }
    }

    //check if cell is present from Left to Right diagonal or not (i.e) i==j
    private boolean isCellOnTopLeftDiagonal(int row, int col) {
        //i == j
        return row == col;
    }

    //check if cell is present from Right to Left diagonal or not (i.e) i+j==n-1
    private boolean isCellOnTopRightDiagonal(int row, int col, int size) {
        //i + j == n - 1
        return row + col == size - 1;
    }

    @Override
    public boolean checkWinner(Board board, Cell moveCell, Player player) {
        //get the row, col, symbol to check
        int row = moveCell.getRow();
        int col = moveCell.getCol();
        char symbol = moveCell.getPlayer().getPlayerSymbol();
        int size = board.getSize();

        //1) In row, if my hashmap is not initialized with the symbol(e.g., X or O) for the current player, then put it with symbol and initialize the hashmap value at 0.
        if(!rowSymbolCounts.get(row).containsKey(symbol)){
            rowSymbolCounts.get(row).put(symbol, 0);
        }
        //If already initialized, then just increment the count in row
        rowSymbolCounts.get(row).put(symbol, rowSymbolCounts.get(row).get(symbol) + 1);

        //2) Similarly in col, if my hashmap is not initialized with the symbol(e.g., X or O) for the current player, then put it with symbol and initialize the hashmap value at 0.
        if(!colSymbolCounts.get(col).containsKey(symbol)){
            colSymbolCounts.get(col).put(symbol, 0);
        }
        //If already initialized, then just increment the count in col
        colSymbolCounts.get(col).put(symbol, colSymbolCounts.get(col).get(symbol) + 1);

        //3) In Left to Right Diagonal, if(row == col)
        if(isCellOnTopLeftDiagonal(row, col)){
            //if symbol and count of symbol is not in left-right diagonal hashmap
            if(!topLeftDiagonalSymbolCounts.containsKey(symbol)){
                topLeftDiagonalSymbolCounts.put(symbol, 0);
            }
            //if it is present
            topLeftDiagonalSymbolCounts.put(symbol, topLeftDiagonalSymbolCounts.get(symbol) + 1);
        }

        //4) In Right to Left Diagonal, if(row + col == size - 1)
        if(isCellOnTopRightDiagonal(row, col, size)){
           //if symbol and count of symbol is not in right-left diagonal hashmap
           if(!topRightDiagonalSymbolCounts.containsKey(symbol)){
               topRightDiagonalSymbolCounts.put(symbol, 0);
           }

           //if it is present
           topRightDiagonalSymbolCounts.put(symbol, topRightDiagonalSymbolCounts.get(symbol) + 1);

        }

        //5) return true if row, col, L-R diagonal and R-L diagonal is equal to size, so player is won otherwise return false
        if((rowSymbolCounts.get(row).get(symbol) == size) || (colSymbolCounts.get(col).get(symbol) == size)){
            return true;
        }

        if(isCellOnTopLeftDiagonal(row, col) && topLeftDiagonalSymbolCounts.get(symbol) == size){
            return true;
        }

        if(isCellOnTopRightDiagonal(row, col, size) && topRightDiagonalSymbolCounts.get(symbol) == size){
            return true;
        }

        //6)  list of the move(all moves)  is equal to n * n(board dimension) -> draw => This we do it in Game.java class
        return false;
    }
}
