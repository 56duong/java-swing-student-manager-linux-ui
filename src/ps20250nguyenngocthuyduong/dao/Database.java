package ps20250nguyenngocthuyduong.dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import ps20250nguyenngocthuyduong.config.Config;

/**
* This class provides methods for connecting to and interacting with a SQL Server database.
*/
public class Database {
    /**
     * The name of the database.
     */
    private static String databaseName = Config.SQLSERVER_DATABASE_NAME;
    
    /**
     * The username used to access the database.
     */
    private static String userName = Config.SQLSERVER_USERNAME;
    
    /**
     * The password used to access the database.
     */
    private static String password = Config.SQLSERVER_PASSWORD;
    
    /**
     * The port number of the database server.
     */
    private static int port = Config.SQLSERVER_DATABASE_PORT;

    /**
     * Constructs a new Database object.
     */
    public Database() {
    }
    
    
    
    /**
    * Gets a connection to the default SQL Server database specified by this class.
    *
    * @return a {@code Connection} object representing the connection to the default database
    */
    public static Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:" + port + ";"
                        + "databaseName=" + databaseName + ";"
                        + "encrypt=true;trustServerCertificate=true;";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return connection;
    }
    
    /**
    * Gets a connection to the SQL Server database with the specified name.
    *
    * @param dbName the name of the database to connect to
    * @return a {@code Connection} object representing the connection to the specified database
    */
    public static Connection getConnection(String dbName) {
        String url = "jdbc:sqlserver://localhost:" + port + ";"
                        + "databaseName=" + dbName + ";"
                        + "encrypt=true;trustServerCertificate=true;";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return connection;
    }
    
    

    /**
    * Closes the specified {@code ResultSet}.
    *
    * @param resultSet the {@code ResultSet} to close
    */
    public static void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    /**
    * Closes the specified {@code PreparedStatement}.
    *
    * @param preparedStatement the {@code PreparedStatement} to close
    */
    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    /**
    * Closes the specified {@code Statement}.
    *
    * @param statement the {@code Statement} to close
    */
    public static void closeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    /**
    * Closes the specified {@code Connection}.
    *
    * @param connection the {@code Connection} to close
    */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
}
