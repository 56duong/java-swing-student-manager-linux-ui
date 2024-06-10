package ps20250nguyenngocthuyduong.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ps20250nguyenngocthuyduong.models.User;

public class UserDAOImpl implements UserDAO {
    
    @Override
    public int add(User user) {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO [User] VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserID());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            
            int rowAffected = statement.executeUpdate();
            
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);
            
            return rowAffected;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return 0;
    }

    
    
    @Override
    public int updateByID(User user) {
        Connection connection = Database.getConnection();
        String sql = "UPDATE [User] SET UserName=?, Password=?, Role=? WHERE UserID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getUserID());
            
            int rowAffected = statement.executeUpdate();
            
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);
            
            return rowAffected;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return 0;
    }

    
    
    @Override
    public int deleteByID(String userID) {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM [User] WHERE UserID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            
            int rowAffected = statement.executeUpdate();
            
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);
            
            return rowAffected;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return 0;
    }

    
    
    @Override
    public User getByID(String userID) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM [User] WHERE UserID=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);
            ResultSet result = statement.executeQuery();
            
            if(result.next()) {
                User user = new User(
                    result.getString("UserID"),
                    result.getString("UserName"),
                    result.getString("Password"),
                    result.getString("Role")
                );
                
                Database.closeResultSet(result);
                Database.closePreparedStatement(statement);
                Database.closeConnection(connection);
            
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    
    
    @Override
    public List<User> getAll() {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM [User]";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            List<User> lUser = new ArrayList<>();
            while (result.next()) {
                User user = new User(
                    result.getString("UserID"),
                    result.getString("UserName"),
                    result.getString("Password"),
                    result.getString("Role")
                );
                lUser.add(user);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lUser;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    
    
    @Override
    public User getByName(String userName) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM [User] WHERE UserName=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();
            
            if(result.next()) {
                User user = new User(
                    result.getString("UserID"),
                    result.getString("UserName"),
                    result.getString("Password"),
                    result.getString("Role")
                );
                
                Database.closeResultSet(result);
                Database.closePreparedStatement(statement);
                Database.closeConnection(connection);
            
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    
    
    @Override
    public User validateUser(String userName, String userPassword) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM [User] WHERE UserName=? AND Password=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, userPassword);
            ResultSet result = statement.executeQuery();
            
            if(result.next()) {
                User user = new User(
                    result.getString("UserID"),
                    result.getString("UserName"),
                    result.getString("Password"),
                    result.getString("Role")
                );
                
                Database.closeResultSet(result);
                Database.closePreparedStatement(statement);
                Database.closeConnection(connection);
            
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    @Override
    public List<User> getAllContain(String searchText) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM [User]";
        if (searchText != null) {
            sql += " WHERE CONCAT(UserID, UserName, Password, IIF(Role = '1', N'Giảng viên', IIF(Role = '2', N'Cán bộ ĐT', 'Admin'))) LIKE ?\n";
        }
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int parameterIndex = 1;
            if (searchText != null) {
                statement.setString(parameterIndex++, "%" + searchText + "%");
            }
            
            ResultSet result = statement.executeQuery();
            
            List<User> lUser = new ArrayList<>();
            while (result.next()) {
                User user = new User(
                    result.getString("UserID"),
                    result.getString("UserName"),
                    result.getString("Password"),
                    result.getString("Role")
                );
                lUser.add(user);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lUser;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

}
