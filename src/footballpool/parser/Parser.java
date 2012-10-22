/*
 * Parses the nfl.com/scores page.
 * 
 */
package footballpool.parser;

import footballpool.core.Game;
import footballpool.core.Team;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author scottl
 * @version 0.1
 */
public class Parser 
{
    private String website;
    private Document webDoc;
    private static final boolean debug = true;
    private int weekNumber = 0;
    
    /* TODO: grep for the current week and use that as default: "nfl.global.scores.week	= "REG7";" */
    public Parser(int weekNumber) throws IOException
    {
        this.weekNumber = weekNumber;
        website = "http://nfl.com/scores/2012/REG" + weekNumber;
        webDoc = Jsoup.connect(website).get();
    }
    
    
    public void printDoc()
    {
        for (Element element : webDoc.getAllElements())
            System.err.println(element);
    }
    
    public ArrayList<Game> getGames()
    {
        ArrayList<Game> games = new ArrayList(16);
        
        Team[] teams = new Team[2];
        Team team;
        int runNumber = 1;
        
        for (Element element : webDoc.getElementsByClass("scorebox-wrapper"))
        {
            for (Element teamWrapper : element.getElementsByClass("team-wrapper"))
            {
                team = getTeam(teamWrapper);
                team.setScore(weekNumber, getScore(teamWrapper));
                
                teams[runNumber % 2] = team;
                runNumber++;
            }
            
            games.add(new Game(weekNumber, teams[0], teams[1]));
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
    
    private Team getTeam(Element teamWrapper)
    {return new Team(teamWrapper.getElementsByClass("team-name").text());}
        
    
    
    
    private static void debug(String output)
    {
        System.out.println("DEBUG : " + output + "------------------------\n");
    }
}
