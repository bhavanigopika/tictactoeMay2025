package tictactoe.models;

public class Bot extends Player {
    private BotDifficultyLevel botDifficultyLevel;
    //Bot does not have a default constructor. So, object not create. First create constructor

   /*public Bot(String playerName, char playerSymbol, PlayerType playerType, BotDifficultyLevel botDifficultyLevel) {
        super(playerName, playerSymbol, playerType);
        this.botDifficultyLevel = botDifficultyLevel;
    }*/
    //here playerType would be bot only. So, change the above 7 to 9 line into 12 to 14 line
    public Bot(String playerName, char playerSymbol, BotDifficultyLevel botDifficultyLevel) {
        super(playerName, playerSymbol, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
    }
}
