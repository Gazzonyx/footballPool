package footballpool;

import footballpool.core.Game;
import footballpool.core.Player;
import footballpool.core.Team;
import footballpool.parser.Parser;
import java.util.ArrayList;

/**
 *
 * @author scottl
 * @version 0.1
 */
public class FootballPool 
{
    private ArrayList<Player> players;
    
    
    public FootballPool()
    {
        players = new ArrayList();
        
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
    
    
    public void addPlayer(Player player)
    {players.add(player);}
    
    public static void printPlayer(Player player)
    {
        if (player == null)
            return;
        
        System.out.println("Name: " + player.getFirstName() + " " + player.getLastName());
        System.out.println("Weekly Points: " + player.getWeeklyPoints());
        System.out.println("Seasonal Points: " + player.getSeasonalPoints());
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