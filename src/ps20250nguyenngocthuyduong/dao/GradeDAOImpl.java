package ps20250nguyenngocthuyduong.dao;

import java.util.List;
import ps20250nguyenngocthuyduong.models.Grade;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Implements the {@link GradeDAO} interface to provide CRUD operations for managing
 * Grade records in a database.
 */
public class GradeDAOImpl implements GradeDAO {
    /**
     * Adds a new Grade record to the database.
     *
     * @param grade the Grade object to add
     * @return the number of rows affected by the operation
     */
    @Override
    public int add(Grade grade) {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO Grade VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, grade.getTestID());
            statement.setString(2, grade.getStudentID());
            statement.setDouble(3, grade.getTAGrade());
            statement.setDouble(4, grade.getTinHocGrade());
            statement.setDouble(5, grade.getGDTCGrade());
            
            int rowAffected = statement.executeUpdate();
            
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);
            
            return rowAffected;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return 0;
    }

    
    
    /**
     * Updates an existing Grade record in the database based on its TestID and StudentID.
     *
     * @param grade the updated Grade object
     * @return the number of rows affected by the operation
     */
    @Override
    public int updateByID(Grade grade) {
        Connection connection = Database.getConnection();
        String sql = "UPDATE Grade SET TAGrade=?, TinHocGrade=?, GDTCGrade=? WHERE TestID=? AND StudentID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, grade.getTAGrade(), java.sql.Types.DOUBLE);
            statement.setObject(2, grade.getTinHocGrade(), java.sql.Types.DOUBLE);
            statement.setObject(3, grade.getGDTCGrade(), java.sql.Types.DOUBLE);
            statement.setString(4, grade.getTestID());
            statement.setString(5, grade.getStudentID());
            
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
    public int deleteByID(String testID, String studentID) {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM Grade WHERE TestID=? AND StudentID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testID);
            statement.setString(2, studentID);
            
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
    public Grade getByID(String testID, String studentID) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Grade WHERE TestID=? AND StudentID=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testID);
            statement.setString(2, studentID);
            ResultSet result = statement.executeQuery();
            
            if(result.next()) {
                Grade grade = new Grade(
                    result.getString("TestID"),
                    result.getString("StudentID"),
                    (Double)result.getObject("TAGrade"),
                    (Double)result.getObject("TinHocGrade"),
                    (Double)result.getObject("GDTCGrade")
                );
                
                Database.closeResultSet(result);
                Database.closePreparedStatement(statement);
                Database.closeConnection(connection);
            
                return grade;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    
    
    @Override
    public List<Grade> getAll() {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Grade";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            List<Grade> lGrade = new ArrayList<>();
            while (result.next()) {
                Grade grade = new Grade(
                    result.getString("TestID"),
                    result.getString("StudentID"),
                    (Double)result.getObject("TAGrade"),
                    (Double)result.getObject("TinHocGrade"),
                    (Double)result.getObject("GDTCGrade")
                );
                lGrade.add(grade);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lGrade;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    @Override
    public int deleteByID(String entityID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Grade getByID(String entityID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    @Override
    public List<Grade> getTop(int top) {
        Connection connection = Database.getConnection();
        String sql = "SELECT TOP(?) WITH TIES AVG((TAGrade + TinHocGrade + GDTCGrade) / 3) AS AverageGrade , StudentID, TestID, TAGrade, TinHocGrade, GDTCGrade FROM Grade GROUP BY StudentID, TestID, TAGrade, TinHocGrade, GDTCGrade ORDER BY AverageGrade DESC;";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, top);
            ResultSet result = statement.executeQuery();
            
            List<Grade> lGrade = new ArrayList<>();
            while (result.next()) {
                Grade grade = new Grade(
                    result.getString("TestID"),
                    result.getString("StudentID"),
                    result.getDouble("TAGrade"),
                    result.getDouble("TinHocGrade"),
                    result.getDouble("GDTCGrade")
                );
                lGrade.add(grade);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lGrade;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    
    
    @Override
    public List<Grade> getByTestID(String testID) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Grade WHERE TestID=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testID);
            ResultSet result = statement.executeQuery();
            
            List<Grade> lGrade = new ArrayList<>();
            while (result.next()) {
                Grade grade = new Grade(
                    result.getString("TestID"),
                    result.getString("StudentID"),
                    (Double)result.getObject("TAGrade"),
                    (Double)result.getObject("TinHocGrade"),
                    (Double)result.getObject("GDTCGrade")
                );
                lGrade.add(grade);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lGrade;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    
    
    @Override
    public ArrayList<Double> getAVGByTestID(String testID) {
        Connection connection = Database.getConnection();
        String sql = "SELECT TestID, AVG(TAGrade) AS TA, AVG(TinHocGrade) AS TinHoc, AVG(GDTCGrade) AS GDTC\n" +
                        " FROM Grade" + 
                        " WHERE TestID = ?\n" +
                        " GROUP BY TestID";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, testID);
            ResultSet result = statement.executeQuery();
            
            ArrayList<Double> l = new ArrayList<>();
            while (result.next()) {
                l.add(result.getDouble("TA"));
                l.add(result.getDouble("TinHoc"));
                l.add(result.getDouble("GDTC"));
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return l;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    
    
    @Override
    public List<Grade> getTopTestIDContain(int top, String testID, String searchText) {
        Connection connection = Database.getConnection();
        String condition = "";
        if (top > 0) {
            condition = "TOP(?) WITH TIES";
        }
        String sql = "SELECT " + condition + " AVG((TAGrade + TinHocGrade + GDTCGrade) / 3) AS AverageGrade, Grade.StudentID, StudentName, TestID, TAGrade, TinHocGrade, GDTCGrade \n" +
                        "FROM Grade \n" +
                        "JOIN Student\n" +
                        "	ON Grade.StudentID = Student.StudentID\n" +
                        "WHERE 1=1 ";
        if (searchText != null) {
            sql += "AND CONCAT(CAST((TAGrade + TinHocGrade + GDTCGrade) / 3 AS VARCHAR), Grade.StudentID, StudentName, TestID, TAGrade, TinHocGrade, GDTCGrade) LIKE ?\n";
        }
        if (testID != null) {
            sql += "AND Grade.TestID = ?\n";
        }
        sql += "GROUP BY Grade.StudentID, StudentName, TestID, TAGrade, TinHocGrade, GDTCGrade \n" +
                "ORDER BY " + (top > 0 ? "AverageGrade DESC" : "TestID") + ";";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int parameterIndex = 1;
            if (top > 0) {
                statement.setInt(parameterIndex++, top);
            }
            if (searchText != null) {
                statement.setString(parameterIndex++, "%" + searchText + "%");
            }
            if (testID != null) {
                statement.setString(parameterIndex++, testID);
            }
            ResultSet result = statement.executeQuery();

            List<Grade> lGrade = new ArrayList<>();
            while (result.next()) {
                Grade grade = new Grade(
                    result.getString("TestID"),
                    result.getString("StudentID"),
                    (Double)result.getObject("TAGrade"),
                    (Double)result.getObject("TinHocGrade"),
                    (Double)result.getObject("GDTCGrade")
                ); //de tra ve null, neu khong thi database null => 0.0
                lGrade.add(grade);
            }

            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lGrade;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
