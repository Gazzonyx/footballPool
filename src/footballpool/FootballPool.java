package footballpool;

import footballpool.core.Game;
import footballpool.core.Team;
import footballpool.parser.Parser;

/**
 *
 * @author scottl
 * @version 0.1
 */
public class FootballPool 
{
    private int week = 7;
    
    
    public FootballPool()
    {
        try
        {
            for (Game game : Parser.getRegularSeason())
                printGame(game);
        }
        catch(Exception e)
            {e.printStackTrace();}
    }
    
    
    public static void main(String[] args) 
    {
        FootballPool fbp = new FootballPool();
    }
    
    
    
    private static void printGame(Game game)
    {
        printLine();
        System.out.println(game);
        
        int week = game.getWeek();
        printScore(week, game.getHome());
        printScore(week, game.getAway());
        
        Team winner = game.getWinner();
        if (!game.gameOver())
            return;
        
        if (!winner.name.equals("Tie"))
            System.out.println("Winner : " + winner);
        else
            System.out.println("Ended in tie");
    }
    
    private static void printScore(int week, Team team)
    {
        System.out.println(team.name + " : " + team.getScore(week));  
    }
    
    private static void printLine()
    {System.out.println("---------------------------------");}
}
