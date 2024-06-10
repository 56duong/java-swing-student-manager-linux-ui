package ps20250nguyenngocthuyduong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ps20250nguyenngocthuyduong.models.Test;
import ps20250nguyenngocthuyduong.utils.Validator;

public class TestDAOImpl implements TestDAO {

    @Override
    public int deleteByID(String testID) {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM Test WHERE TestID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testID);
            
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
    public List<Test> getAll() {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Test";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            List<Test> lTest = new ArrayList<>();
            while (result.next()) {
                Test test = new Test(
                    result.getString("TestID"),
                    result.getString("TestName"),
                    result.getString("TestDate")
                );
                lTest.add(test);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lTest;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    @Override
    public int add(Test test) {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO Test VALUES (?, ?, ?)";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, test.getTestID());
            statement.setString(2, test.getTestName());
            statement.setObject(3, Validator.isNotNull(null, test.getTestDate(), null) ? java.sql.Date.valueOf(test.getTestDate()) : null);
            
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
    public int updateByID(Test test) {
        Connection connection = Database.getConnection();
        String sql = "UPDATE Test SET TestName=?, TestDate=? WHERE TestID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, test.getTestName());
            statement.setObject(2, Validator.isNotNull(null, test.getTestDate(), null) ? java.sql.Date.valueOf(test.getTestDate()) : null);
            statement.setString(3, test.getTestID());
            
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
    public Test getByID(String testID) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Test WHERE TestID=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testID);
            ResultSet result = statement.executeQuery();
            
            if(result.next()) {
                Test test = new Test(
                    result.getString("TestID"),
                    result.getString("TestName"),
                    result.getString("TestDate")
                );
                
                Database.closeResultSet(result);
                Database.closePreparedStatement(statement);
                Database.closeConnection(connection);
            
                return test;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
}
