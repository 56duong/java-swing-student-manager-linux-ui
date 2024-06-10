package ps20250nguyenngocthuyduong.dao;

import java.util.List;
import ps20250nguyenngocthuyduong.models.Student;

public interface StudentDAO extends DAO<Student> {
    /**
    * Retrieves a list of all Student objects from the database whose fields contain the specified search text,
    * or all Student objects if the search text is null.
    *
    * @param searchText the search text to be used to filter the results. Only Student objects whose fields contain
    *                   this text will be returned. If null, all Student objects will be returned.
    * @return a List of Student objects from the database whose fields contain the specified search text, or all
    *         Student objects if the search text is null.
    */
    public List<Student> getAllContain(String searchText);
}
