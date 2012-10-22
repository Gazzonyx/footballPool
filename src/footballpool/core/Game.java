/*
 * Models a game.
 */
package footballpool.core;

/**
 *
 * @author scottl
 * @version 0.1
 */
public class Game 
{
    private Team home, away;
    private boolean isOver = false;
    private int week;
    
    
    public Game(int weekNumber, Team home, Team away)
    {
        this.week = weekNumber;
        this.home = home;
        this.away = away;
    }
    
    
    public Team getHome()
    {return home;}
    
    public Team getAway()
    {return away;}
    
    public Team getWinner()
    {
/*
        if (!isOver)
            return new Team("");
*/        
        int homeScore = home.getScore(week);
        int awayScore = away.getScore(week);
        
        // Well, it's better than return null or the wrong Team...
        if (homeScore == awayScore)
            return new Team("Tie");
        
        return (homeScore > awayScore ? home : away);
    }
    
    
    @Override
    public String toString()
    {
        return "Week: " + week + "\nHome Team : " + home + "\nAway Team: " + away;
    }
}
