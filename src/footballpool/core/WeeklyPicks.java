package footballpool.core;

import java.util.HashMap;

/**
 * Holds picks for the week.
 * @author scottl
 * @version 0.1
 */
public class WeeklyPicks 
{
    private static final int maxWeeklyGames = 16;
    
    private HashMap<Team, Integer> pointsMap = new HashMap(maxWeeklyGames);
    private HashMap<Game, Team> gameMap = new HashMap(maxWeeklyGames);
    
    
    public void setPredictedWinner(Game game, Team team, int points)
    {
        gameMap.put(game, team);
        pointsMap.put(team, points);
    }
    
    public Team getPredictedWinner(Game game)
    {return gameMap.get(game);}
    
    /**
     * Get the number of points wagered on a weekly game.
     * @param game The game.
     * @return The number of points wagered for the game.
     */
    public int getGameValue(Game game)
    {return pointsMap.get(gameMap.get(game));}
}
