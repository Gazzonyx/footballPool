package footballpool.core;

import java.util.HashMap;
import java.util.Map;

/**
 * A player in the football pool.
 * 
 * @author scottl
 * @version 0.1
 */
public class Player 
{
    private String firstName, lastName, emailAddress, nickName;
    private int weeklyPoints = 0;
    private int seasonalPoints = 0;
    private Map<Integer, Boolean> weeksActive;
    
    
    public Player(String first, String last)
    {
        firstName = first;
        lastName = last;
        weeksActive = new HashMap();
    }
    
    public Player(String first, String last, String nick)
    {
        this(first, last);
        setNickName(nick);
    }
    
    public Player(String first, String last, String nick, String email)
    {
        this(first, last, nick);
        setEmail(email);
    }
    
    
    public void setNickName(String nick)
    {nickName = nick;}
    
    public String getNickName()
    {return nickName;}
    
    public void setEmail(String email)
    {emailAddress = email;}
    
    public String getEmail()
    {return emailAddress;}
    
    public void setActive(int week, boolean isActive)
    {weeksActive.put(week, isActive);}
    
    public boolean isActive(int week)
    {return weeksActive.get(week);}
    
    public void addWeeklyPoints(int points)
    {weeklyPoints += points;}
    
    public int getWeeklyPoints()
    {return weeklyPoints;}
    
    public void addSeasonalPoints(int points)
    {seasonalPoints += points;}
    
    public int getSeasonalPoints()
    {return seasonalPoints;}
    
    
    @Override
    public String toString()
    {return firstName + " " + lastName + "\nWeekly Points: " + weeklyPoints + "\nSeasonal Points: " + seasonalPoints;}
}
