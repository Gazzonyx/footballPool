/**
 * Handles all database access.  Somewhat thread safe.
 * 
 * @author scottl
 * @version 0.2
 */
package footballpool.core.database;

import footballpool.Testable;
import footballpool.core.Player;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnector implements Testable
{
    private Connection connection;
    private final Object connectionLock = new Object();
    
    
    /**
     * Only for testing.  Creates a connection to the test database.
     * @throws SQLException 
     */
    public DatabaseConnector() throws SQLException
    {
        // TODO: read configuation from a file.
        // TODO: come up with a better password for the test box
        // TODO: Fix DHCP with DNS.  When did this break?
        // TODO: Create a non-root user for the test database.  That'd be smart.
        this("jdbc:mysql://192.168.1.189/footballPool", "root", "password");
    }
    
    public DatabaseConnector(String url, String user, String pass) throws SQLException
    {
        connection = DriverManager.getConnection(url, user, pass);
    }    
    
    
    /**
     * Returns a player from the database with given email address.  
     * If the email address is not found, returns an empty {@link Player} 
     * with an id of -1.  This method is thread safe.
     * @param emailAddress Email address of requested player.
     * @return The {@link Player} requested.
     * @throws SQLException Should not happen, but it could, I guess...
     */
    public Player getPlayer(String emailAddress) throws SQLException
    {
        if (emailAddress == null)
            return new Player(-1, "", "");
        
        ResultSet rs;
        Player outgoing;
        
        try
        {
           PreparedStatement statement;
           synchronized(connectionLock)
           {
                statement = connection.prepareStatement(
                "SELECT p.`id`, p.`firstName`, p.`lastName`, p.`nickName` FROM players p " +
                "WHERE p.`email`=?");
           }
           statement.setString(1, emailAddress);
           rs = statement.executeQuery();
           if (!rs.next())
           {
               rs.close();
               return new Player(-1);
           }
           
           outgoing = new Player(rs.getInt("id"), rs.getString("firstName"), 
                rs.getString("lastName"), rs.getString("nickName"), emailAddress);
           rs.close();
        }
        catch(SQLException ex)
        {throw ex;}

        return outgoing;
    }
    
    /**
     * Adds a player if they do not exist in the database.  Players are 
     * identified by email address.  This method is thread safe.
     * @param player A player to add.
     * @return true if the player was added to the database, false otherwise.
     */
    public boolean addPlayer(Player player)
    {
        if (player == null)
            return false;
        
        String email = player.getEmail();
        if (email == null || email.equals("") || ! email.contains("@"))
            return false;
        
        try
        {
            if (getPlayer(email).id != -1)
                return false;
            
            PreparedStatement statement;
            synchronized(connectionLock)
            {
                statement = connection.prepareStatement(
                    "INSERT INTO players (firstName, lastName, nickName, email)"
                    + "VALUES (?, ?, ?, ?)");
            }
            statement.setString(1, player.getFirstName());
            statement.setString(2, player.getLastName());
            statement.setString(3, player.getNickName());
            statement.setString(4, email);
            statement.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
                    
    @Override
    public boolean doTest() 
    {
        if (addPlayer(new Player(1, "Test", "User", "TU", "user@example.com")))
        {
            Player p = null;
            
            try
            {p = getPlayer("user@example.com");}
            catch(SQLException se)
            {
                se.printStackTrace();
                return false;
            }
            
            System.out.println("Player P: " + p);
        }
        
        return true;
    }
    
    /**
     * Closes the Connection and sets it to null.  Is thread-safe-ish.  That is,
     *  will not close the connection while another thread holds the connection
     *  lock.
     */
    public void close()
    {
        synchronized(connectionLock)
        {
            try 
                {connection.close();}
            catch (SQLException ex)
                {Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);}
            finally
                {connection = null;}
        }
    }
    
    
    
    /**
     * Just tests the class.  Nothing sexy.
     * @throws SQLException 
     */
    public static void main(String[] args)
    {
        try
        {
            DatabaseConnector dc = new DatabaseConnector();
            dc.doTest();
        }
        catch(Exception e)
        {e.printStackTrace();}
    }
}
