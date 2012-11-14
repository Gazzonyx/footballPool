/*
 * Holds a team's info.
 */
package footballpool.core;

import footballpool.Conference;
import footballpool.Division;
import java.util.ArrayList;

/**
 *
 * @author scottl
 * @version 0.1
 */
public class Team 
{
    final public String name;
    final public Division division;
    final public Conference conference;
    private ArrayList<Integer> scores;
    
    
    public Team(String name, Conference conf, Division div)
    {
        this.name = name;
        this.conference = conf;
        this.division = div;
        
        int numWeeks = 20;
        scores = new ArrayList(numWeeks);
        for (int ct = 0; ct < numWeeks; ct++)
            scores.add(0);
    }
    
    
    public void setScore(int weekNumber, int points)
    {
        scores.set(weekNumber, points);
    }
    
    public int getScore(int week)
    {return scores.get(week);}
    
    
    /**
     * Creates all teams.
     * @return An ArrayList of all teams in the NFL.
     */
    public static ArrayList<Team> getAllTeams()
    {
        ArrayList<Team> outgoing = new ArrayList<Team>(32);
        
        // AFC North
        outgoing.add(new Team("Ravens", Conference.AFC, Division.NORTH));
        outgoing.add(new Team("Bengals", Conference.AFC, Division.NORTH));
        outgoing.add(new Team("Browns", Conference.AFC, Division.NORTH));
        outgoing.add(new Team("Steelers", Conference.AFC, Division.NORTH));
        
        // AFC East
        outgoing.add(new Team("Bills", Conference.AFC, Division.EAST));
        outgoing.add(new Team("Dolphins", Conference.AFC, Division.EAST));
        outgoing.add(new Team("Patriots", Conference.AFC, Division.EAST));
        outgoing.add(new Team("Jets", Conference.AFC, Division.EAST));
        
        // AFC South
        outgoing.add(new Team("Texans", Conference.AFC, Division.SOUTH));
        outgoing.add(new Team("Colts", Conference.AFC, Division.SOUTH));
        outgoing.add(new Team("Jaguars", Conference.AFC, Division.SOUTH));
        outgoing.add(new Team("Titans", Conference.AFC, Division.SOUTH));
        
        // AFC West
        outgoing.add(new Team("Broncos", Conference.AFC, Division.WEST));
        outgoing.add(new Team("Chiefs", Conference.AFC, Division.WEST));
        outgoing.add(new Team("Raiders", Conference.AFC, Division.WEST));
        outgoing.add(new Team("Chargers", Conference.AFC, Division.WEST));
        
        // NFC North
        outgoing.add(new Team("Bears", Conference.NFC, Division.NORTH));
        outgoing.add(new Team("Lions", Conference.NFC, Division.NORTH));
        outgoing.add(new Team("Packers", Conference.NFC, Division.NORTH));
        outgoing.add(new Team("Vikings", Conference.NFC, Division.NORTH));
                
        // NFC East
        outgoing.add(new Team("Cowboys", Conference.NFC, Division.EAST));
        outgoing.add(new Team("Giants", Conference.NFC, Division.EAST));
        outgoing.add(new Team("Eagles", Conference.NFC, Division.EAST));
        outgoing.add(new Team("Redskins", Conference.NFC, Division.EAST));
        
        // NFC South
        outgoing.add(new Team("Falcons", Conference.NFC, Division.SOUTH));
        outgoing.add(new Team("Panthers", Conference.NFC, Division.SOUTH));
        outgoing.add(new Team("Saints", Conference.NFC, Division.SOUTH));
        outgoing.add(new Team("Buccaneers", Conference.NFC, Division.SOUTH));
        
        // NFC West
        outgoing.add(new Team("Cardinals", Conference.NFC, Division.WEST));
        outgoing.add(new Team("49ers", Conference.NFC, Division.WEST));
        outgoing.add(new Team("Seahawks", Conference.NFC, Division.WEST));
        outgoing.add(new Team("Rams", Conference.NFC, Division.WEST));
        
        return outgoing;
    }
    
    /**
     * Two teams with the same name are equal.
     * @param team The Team to check against this Team.
     * @return true if their team names match.
     */
    @Override
    public boolean equals(Object team)
    {
     if (! (team instanceof Team))
         return false;
     return ((Team)team).name.equals(name);
    }
    
    @Override
    public String toString()
    {return name;}
    
    
}
