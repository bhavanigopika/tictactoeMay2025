package tictactoe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> board;

    //Difference between list and arraylist -> list is interface, so cannot create object of list; arraylist is one of the implementation of list
    //So, Arraylist implements list

    //Create constructor because, I don't want to someone to create board without initialize the attributes
    //here size is enough to initialize the attributes, no need board here
    public Board(int size) {
        this.size = size;
        this.board = new ArrayList<>();//1D arraylist -[]
        //once get the size, then initialize the board like 3 * 3 size, the 9 cell should create
        for(int i = 0; i < size; i++){
            this.board.add(new ArrayList<>()); //It creates say, 3 size... then 3 list []
                                                                                    // []
                                                                                    // [] will be created
            //in each list, create cell(row and column) like
            /*
            [[cell(0,0), cell(0,1), cell(0,2)],
            [cell(1,0), cell(1,1), cell(1,2)]
            [cell(2,0), cell(2,1), cell(2,2)]]
            */
            for(int j = 0; j < size; j++){
                this.board.get(i).add(new Cell(i, j));//every cell has value defined, once we create the constructor of Cell in Cell class like (0,0) (0,1) (1,1) (2,3) etc.,
            }
        }
    }
    //I don't want to no body set the board the set the size of the board. So, need only getters, get the board size and get the cell of the board
    public int getSize() {
        return size;
    }

     public List<List<Cell>> getBoard() {
        return board;
    }
    //now display the board
    public void displayBoard(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(board.get(i).get(j).getCellState().equals(CellState.EMPTY)){
                    System.out.printf("|   |");
                }else{
                    System.out.printf("| " + board.get(i).get(j).getPlayer().getPlayerSymbol() + " |");
                }
            }
            System.out.println("\n");
        }
    }
}
