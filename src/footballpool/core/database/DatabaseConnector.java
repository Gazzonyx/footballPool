/**
 * Handles all database access.
 * 
 * @author scottl
 * @version 0.1
 */
package footballpool.core.database;

//import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Connection;
import footballpool.Testable;
import footballpool.core.Player;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnector implements Testable
{
    private Connection connection;
    
    /**
     * Only for testing.  Creates a connection to the test database.
     * @throws SQLException 
     */
    public DatabaseConnector() throws SQLException
    {
        // TODO: read configuation from a file.
        // TODO: come up with a better password for the test box
        // TODO: Fix DHCP with DNS.  When did this break?
        this("jdbc:mysql://192.168.1.189/", "root", "password");
    }
    
    public DatabaseConnector(String url, String user, String pass) throws SQLException
    {
        connection = (Connection)DriverManager.getConnection(url, user, pass);
    }    
    
    
    public Player getPlayer(String emailAddress) throws SQLException
    {
        if (emailAddress == null)
            return new Player(-1, "", "");
        
        ResultSet rs;
        Player outgoing;
        
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {
            rs = statement.executeQuery("SELECT id, firstName, lastName, nickName, FROM players WHERE email='" + emailAddress +"'");
            outgoing = new Player(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("nickName"), emailAddress);
            if (rs != null)
                rs.close();
        }

        return outgoing;
    }
    
    
    @Override
    public boolean doTest() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
