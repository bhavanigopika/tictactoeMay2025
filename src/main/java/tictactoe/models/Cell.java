package tictactoe.models;

public class Cell {
    private int row;
    private int col;
    private Player player;
    private CellState cellState;

    //I should not allow anyone to be able to create a cell without passing the bare minimum value of row and column
    //So, create constructor now
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        //tell the cell state when a new cell is created
        this.cellState = CellState.EMPTY;
        //we cannot define which player is play when we define the cell, don't create the
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }
}
