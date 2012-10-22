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
        Parser nflParser;
        
        try
            {
                for (int ct = 1; ct <= week; ct++)
                {
                    nflParser = new Parser(ct);

                    for (Game game : nflParser.getGames())
                    {
                        printLine();
                        System.out.println(game);
                        printScore(ct, game.getHome());
                        printScore(ct, game.getAway());
                        System.out.println("Winner : " + game.getWinner());
                    }
                    
                    // Try not to tick off nfl.com too much for scraping
                    Thread.sleep(1000);
                }
            }
        catch(Exception e)
            {e.printStackTrace();}
    }
    
    
    public static void main(String[] args) 
    {
        FootballPool fbp = new FootballPool();
    }
    
    
    
    
    private static void printScore(int week, Team team)
    {
        System.out.println(team.name + " : " + team.getScore(week));  
    }
    
    private static void printLine()
    {System.out.println("---------------------------------");}
}
