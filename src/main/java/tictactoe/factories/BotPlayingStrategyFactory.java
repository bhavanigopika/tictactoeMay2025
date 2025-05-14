package tictactoe.factories;

import tictactoe.models.BotDifficultyLevel;
import tictactoe.strategies.botplayingstrategy.BotPlayingStrategy;
import tictactoe.strategies.botplayingstrategy.EasyBotPlayingStrategy;
import tictactoe.strategies.botplayingstrategy.HardBotPlayingStrategy;
import tictactoe.strategies.botplayingstrategy.MediumBottPlayingStrategy;

public class BotPlayingStrategyFactory {
   public static BotPlayingStrategy getBotPlayingStrategyByDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
       if(botDifficultyLevel.equals(BotDifficultyLevel.EASY)){
           return new EasyBotPlayingStrategy();
       }
       else if(botDifficultyLevel.equals(BotDifficultyLevel.MEDIUM)){
           return new MediumBottPlayingStrategy();
       }
       else if(botDifficultyLevel.equals(BotDifficultyLevel.HARD)){
           return new HardBotPlayingStrategy();
       }
       return null;
   }
}
