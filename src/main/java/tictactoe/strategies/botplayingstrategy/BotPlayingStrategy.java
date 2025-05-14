package tictactoe.strategies.botplayingstrategy;

import tictactoe.models.Board;
import tictactoe.models.Move;
import tictactoe.models.Player;

public interface BotPlayingStrategy {
    //Bot decides to move
    //By bot, it needs to know the player details and board
    Move decideMove(Player player, Board board);
}
