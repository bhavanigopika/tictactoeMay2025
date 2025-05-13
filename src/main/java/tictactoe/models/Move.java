package tictactoe.models;

public class Move {
    //every move going to have a player
    private Player player;
    //every move contain the cell
    private Cell cell;

    //create a move when we provide a player and cell. So, create constructor for player and cell

    public Move(Player player, Cell cell) {
        this.player = player;
        this.cell = cell;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
