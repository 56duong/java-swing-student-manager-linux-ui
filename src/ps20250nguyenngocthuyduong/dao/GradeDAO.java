package ps20250nguyenngocthuyduong.dao;

import java.util.ArrayList;
import java.util.List;
import ps20250nguyenngocthuyduong.models.Grade;

public interface GradeDAO extends DAO<Grade> {
    /**
     * Retrieves the top N Grade records from the database based on their average grade, where N is specified
     * by the `top` parameter.
     *
     * @param top the number of records to retrieve
     * @return a List of the top N Grade objects in the database, sorted in descending order of average grade
     */
    public List<Grade> getTop(int top);
    
    /**
     * Deletes a Grade record from the database based on its TestID and StudentID.
     *
     * @param testID the TestID of the Grade to delete
     * @param studentID the StudentID of the Grade to delete
     * @return the number of rows affected by the operation
     */
    public int deleteByID(String testID, String studentID);
    
    /**
     * Retrieves a Grade record from the database based on its TestID and StudentID.
     *
     * @param testID the TestID of the Grade to retrieve
     * @param studentID the StudentID of the Grade to retrieve
     * @return the Grade object with the specified TestID and StudentID, or null if no such record exists
     */
    public Grade getByID(String testID, String studentID);
    
    /**
    * Retrieves a List of Grade objects from the database that correspond to a particular test ID.
    * @param testID the test ID to retrieve grades for
    * @return a List of Grade objects from the database that correspond to the specified test ID
    */
    public List<Grade> getByTestID(String testID);
    
    /**
    * Retrieves an ArrayList of Doubles representing the average grades for each subject on a particular test.
    * @param testID the test ID to retrieve average grades for
    * @return an ArrayList of Doubles representing the average grades for each subject on the specified test, in the order TAGrade, TinHocGrade, GDTCGrade
    */
    public ArrayList<Double> getAVGByTestID(String testID);
    
    /**
    * Retrieves the top N Grade records from the database based on their average grade for a given test,
    * where N is specified by the `top` parameter, and optionally filtered by a search text.
    *
    * @param top the number of records to retrieve. Use a positive value to retrieve the top N records,
    *            or 0 to retrieve all records.
    * @param testID the ID of the test to filter by. Use null to retrieve records for all tests.
    * @param searchText a text to search for in the concatenated string of relevant fields.
    *                   Use null to skip the search filter.
    * @return a List of the top N Grade objects in the database that match the filters, sorted in descending order
    *         of average grade if top is positive, or by test ID if top is 0.
    */
    public List<Grade> getTopTestIDContain(int top, String testID, String searchText);
}
