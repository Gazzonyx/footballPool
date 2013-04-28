/*
 * Parses the nfl.com/scores page.
 * 
 */
package footballpool.parser;

import footballpool.Testable;
import footballpool.core.Game;
import footballpool.core.Team;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author scottl
 * @version 0.1
 */
public class Parser implements Testable
{
    private String website;
    private Document webDoc;
    private static final boolean debug = true;
    private int weekNumber = 0;
    private static final int parseDelay = 250;
    private static final int seasonYear = 2012;
    private static final int numTeams = 16;
    private static final int lastRegularWeekNumber = 17;
    
    
    /* TODO: grep for the current week and use that as default: "nfl.global.scores.week	= "REG7";" */
    public Parser(int weekNumber) throws IOException
    {
        this.weekNumber = weekNumber;
        website = "http://nfl.com/scores/" + seasonYear + "/REG" + weekNumber;
        webDoc = Jsoup.connect(website).get();
    }
    
    /**
     * Get every game from this parser's season.
     * @return Every game for the season, with scores is applicable.
     * @throws IOException If site can't be reached.
     */
    static public ArrayList<Game> getRegularSeason() throws IOException
    {
        ArrayList<Game> games = new ArrayList(lastRegularWeekNumber);
        
        Parser parser;
        ArrayList<Team> teams = Team.getAllTeams();
        
        for (int ct = 1; ct <= lastRegularWeekNumber; ct++)
        {
            parser = new Parser(ct);
            games.addAll(parser.getGames(teams));
            
            // don't hammer the site
            try
                {Thread.sleep(parseDelay);}
            catch(InterruptedException ie)
                {ie.printStackTrace();}
        }
        
        return games;
    }
    
    
    public void printDoc()
    {
        for (Element element : webDoc.getAllElements())
            System.err.println(element);
    }
    
    public ArrayList<Game> getGames(ArrayList<Team> teams)
    {
        ArrayList<Game> games = new ArrayList(numTeams);
        HashMap<String, Team> teamMap = new HashMap(teams.size());
        
        // Just for easy lookup.
        for (Team team : teams)
            teamMap.put(team.name, team);
        
        Team[] gameTeams = new Team[2];
        Team tempTeam;
        int runNumber = 0;
        
        for (Element element : webDoc.getElementsByClass("scorebox-wrapper"))
        {
            for (Element teamWrapper : element.getElementsByClass("team-wrapper"))
            {
                tempTeam = teamMap.get(getTeamName(teamWrapper));
                tempTeam.setScore(weekNumber, getScore(teamWrapper));
                
                gameTeams[runNumber % 2] = tempTeam;
                runNumber++;
            }
            
            games.add(new Game(weekNumber, gameTeams[0], gameTeams[1]));
            games.get(games.size() - 1).gameIsOver(isGameOver(element));
        }
        
        return games;
    }
        
    /* scorebox-wrapper -> team-wrapper -> [team-name, total-score] */
    private int getScore(Element teamWrapper)
    {
        // NFL uses "--" for the score of a game not started.
        try
            {return Integer.parseInt(teamWrapper.getElementsByClass("total-score").text());}
        catch(NumberFormatException nfe)
            {return 0;}
    }
    
    /**
     * Return the team name from a team wrapper Element.
     * @param teamWrapper An Element containing a single team (team-wrapper Element).
     * @return The team name (ie, Colts) for the team in the Element.
     */
    private String getTeamName(Element teamWrapper)
    {return teamWrapper.getElementsByClass("team-name").text();}
    
    /**
     * Tells if the game in a scorebox-wrapper Element is over or not.
     * @param scoreboxWrapper The Element that contains a scorebox-wrapper for a game.
     * @return True if the game score is final.  False otherwise.
     */
    private boolean isGameOver(Element scoreboxWrapper)
    {
        return scoreboxWrapper.getElementsByClass("time-left").text().equals("FINAL");
    }

    
    
    private static void debug(String output)
    {
        System.out.println("DEBUG : " + output + "------------------------\n");
    }
    
    public boolean doTest()
    {
        try
            {Parser.getRegularSeason();}
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
}
