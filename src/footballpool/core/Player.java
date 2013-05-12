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
    public final int id;
    
    public Player(int id)
    {
        this.id = id;
    }
    
    public Player(int id, String first, String last)
    {
        this(id);
        firstName = first;
        lastName = last;
    }
    
    public Player(int id, String first, String last, String nick)
    {
        this(id, first, last);
        setNickName(nick);
    }
    
    public Player(int id, String first, String last, String nick, String email)
    {
        this(id, first, last, nick);
        setEmail(email);
    }
    
    
    public String getFirstName()
    {return firstName;}
    
    public String getLastName()
    {return lastName;}
    
    public void setNickName(String nick)
    {nickName = nick;}
    
    public String getNickName()
    {return nickName;}
    
    public void setEmail(String email)
    {emailAddress = email;}
    
    public String getEmail()
    {return emailAddress;}

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
