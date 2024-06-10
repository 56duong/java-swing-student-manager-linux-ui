package ps20250nguyenngocthuyduong.dao;

import java.util.List;
import ps20250nguyenngocthuyduong.models.Student;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {
    
    @Override
    public int add(Student student) {
        Connection connection = Database.getConnection();
        String sql = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentID());
            statement.setString(2, student.getStudentName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getPhoneNumber());
            statement.setString(5, student.getSex());
            statement.setString(6, student.getAddress());
            
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
    public int updateByID(Student student) {
        Connection connection = Database.getConnection();
        String sql = "UPDATE Student SET StudentName=?, Email=?, PhoneNumber=?, Sex=?, Address=? WHERE StudentID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getPhoneNumber());
            statement.setString(4, student.getSex());
            statement.setString(5, student.getAddress());
            statement.setString(6, student.getStudentID());
            
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
    public int deleteByID(String studentID) {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM Student WHERE StudentID=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentID);
            
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
    public Student getByID(String studentID) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Student WHERE StudentID=?";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentID);
            ResultSet result = statement.executeQuery();
            
            if(result.next()) {
                Student student = new Student(
                    result.getString("StudentID"),
                    result.getString("StudentName"),
                    result.getString("Email"),
                    result.getString("PhoneNumber"),
                    result.getString("Sex"),
                    result.getString("Address")
                );
                
                Database.closeResultSet(result);
                Database.closePreparedStatement(statement);
                Database.closeConnection(connection);
            
                return student;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    
    
    @Override
    public List<Student> getAll() {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Student";
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            List<Student> lStudent = new ArrayList<>();
            while (result.next()) {
                Student student = new Student(
                    result.getString("StudentID"),
                    result.getString("StudentName"),
                    result.getString("Email"),
                    result.getString("PhoneNumber"),
                    result.getString("Sex"),
                    result.getString("Address")
                );
                lStudent.add(student);
            }
            
            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lStudent;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return null;
    }

    @Override
    public List<Student> getAllContain(String searchText) {
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM Student";
        if (searchText != null) {
            sql += " WHERE CONCAT(StudentID, StudentName, Email, PhoneNumber, Sex, Address) LIKE ?\n";
        }
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int parameterIndex = 1;
            if (searchText != null) {
                statement.setString(parameterIndex++, "%" + searchText + "%");
            }

            ResultSet result = statement.executeQuery();

            List<Student> lStudent = new ArrayList<>();
            while (result.next()) {
                Student student = new Student(
                    result.getString("StudentID"),
                    result.getString("StudentName"),
                    result.getString("Email"),
                    result.getString("PhoneNumber"),
                    result.getString("Sex"),
                    result.getString("Address")
                );
                lStudent.add(student);
            }

            Database.closeResultSet(result);
            Database.closePreparedStatement(statement);
            Database.closeConnection(connection);

            return lStudent;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
}
