package tictactoe;

import tictactoe.controllers.GameController;
import tictactoe.exceptions.InvalidGameConstructionParametersException;
import tictactoe.exceptions.InvalidMoveException;
import tictactoe.exceptions.InvalidPlayerCountException;
import tictactoe.models.Board;
import tictactoe.models.Bot;
import tictactoe.models.BotDifficultyLevel;
import tictactoe.models.Game;
import tictactoe.models.GameState;
import tictactoe.models.Player;
import tictactoe.models.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeMain {
    public static void main(String[] args) throws InvalidGameConstructionParametersException, InvalidPlayerCountException, InvalidMoveException {
        //Client need not build it because this main class would get the details from client object
        /*
        Game game = Game.getBuilder()
                .setBoard()
                .build();
        */
        //So, simply say let build the game object internally that is in controller
        //client will say just start the game
        //So, it would be GameController.startGame();

        Scanner sc = new Scanner(System.in);
        System.out.println("======== Welcome to TicTacToe! ========");

        System.out.println("What is the dimension of the game? ");
        int size = sc.nextInt();//take the int input - 5

        //based on bot only I can decide how many players going to play because if size(dimension) of the board is 3(n), then players becomes 2(n-1).
        //If 1 bot is play, then 1 player going to play. So, total 2 players...
        System.out.println("Will there be a bot in the game y/n");
        String isBotString = sc.next();//take the string input

        //get the list of players, actual players would be n - 1 -> size of the board(n) - 1, if n is size of the board
        List<Player> playerList = new ArrayList<>();

        int toIterate = size - 1; // 4
        if (isBotString.equals("y")) {
            //here decided 1 bot will present so, if 1 bot is present, then I am going to add 1 human player to the game
            toIterate = toIterate - 1; // 3
        }
        //now, iterate and get the player info
        for(int i = 1; i <= toIterate; i++){
            //now, I am able to know how many players I need to add, actually.
            //get the total players based on this I am able to know how many bot and human players
            System.out.println("What is the name of the player " + i + "?");
            String playerName = sc.next();
            System.out.println("What is the symbol of the player " + i + "?");
            char playerSymbol = sc.next().charAt(0);

            //add to player list
            playerList.add(new Player(playerName, playerSymbol, PlayerType.HUMAN));
        }
        //if bot is playing
        if(isBotString.equals("y")){
            System.out.println("What is the name of the bot? ");
            String botName = sc.next();
            System.out.println("What is the symbol of the bot? ");
            char botSymbol = sc.next().charAt(0);

            //set the difficulty level of bot then add to the list of players
            playerList.add(new Bot(botName, botSymbol, BotDifficultyLevel.EASY));
        }

        //Now, start the game
        GameController gameController = new GameController();
        String winningSTrategy = "OrderOne";
        Game game = gameController.startGame(size, playerList, winningSTrategy);

        //start playing the game, it keeps on going
        //I want to check the gameStatus consistently
        //If the game status is IN_PROGRESS, then continue keep on playing the game(IN_PROGRESS) else come out of the while loop(DRAW, ENDED)
        while(gameController.getGameStatus(game).equals(GameState.IN_PROGRESS)){
            System.out.println("This is your current board");
            //now, display the board, then execute the game
            gameController.displayBoard(game);
            gameController.executeGame(game);

        }
        System.out.println("*************************** ");
        System.out.println("Game has ended. Result was: ");
        System.out.println("*************************** ");

        if(game.getGameState().equals(GameState.ENDED)){
            System.out.println("Winner is: " + gameController.getWinner(game).getPlayerName());
            gameController.displayBoard(game);
        }else {
            System.out.println("Game is drawn");
        }
    }
}
