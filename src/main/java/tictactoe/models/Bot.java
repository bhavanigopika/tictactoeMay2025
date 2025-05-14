package tictactoe.models;

import tictactoe.factories.BotPlayingStrategyFactory;
import tictactoe.strategies.botplayingstrategy.BotPlayingStrategy;

public class Bot extends Player {
    private BotDifficultyLevel botDifficultyLevel;
    private BotPlayingStrategy botPlayingStrategy;
    //Bot does not have a default constructor. So, object not create. First create constructor

    /*
    public Bot(String playerName, char playerSymbol, PlayerType playerType, BotDifficultyLevel botDifficultyLevel) {
        super(playerName, playerSymbol, playerType);
        this.botDifficultyLevel = botDifficultyLevel;
    }*/
    //here playerType would be bot only. So, change the above 7 to 9 line into 12 to 14 line
    public Bot(String playerName, char playerSymbol, BotDifficultyLevel botDifficultyLevel) {
        super(playerName, playerSymbol, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategyByDifficultyLevel(botDifficultyLevel);
    }

    //if the next player is BOT in the player list , then this decideMove will call during run time - Here, polymorphism works here
    public Move decideMove(Board board) {
        //here bot going to do a random move. How? Accordidng to the bot playing strategy. So, get the object of botPlayingStrategy in Bot class
        //set the bot difficulty level in botPlayingStrategy
        //So, get the bot playing strategy which we did in strategies package
        //How this know here when bot constructor called so, we define the factories as getBotPlayingStrategyByDifficultyLevel

        return botPlayingStrategy.decideMove(this, board);
    }
}
