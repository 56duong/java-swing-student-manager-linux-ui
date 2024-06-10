package ps20250nguyenngocthuyduong.dao;

import java.util.List;
import ps20250nguyenngocthuyduong.models.User;

public interface UserDAO extends DAO<User> {
    /**
     * Retrieves a user from the database by username.
     *
     * @param userName the username of the user to retrieve
     * @return the User object if found, null otherwise
     */
    public User getByName(String userName);
    
    /**
     * Validates a user's credentials by checking their username and password
     * against the database.
     *
     * @param userName the user's username
     * @param userPassword the user's password
     * @return the User object if the credentials are valid, null otherwise
     */
    public User validateUser(String userName, String userPassword);
    
    /**
     * Retrieves a list of all users from the database that contain the given
     * search text in any of their fields.
     *
     * @param searchText the text to search for
     * @return a list of User objects
     */
    public List<User> getAllContain(String searchText);
}
