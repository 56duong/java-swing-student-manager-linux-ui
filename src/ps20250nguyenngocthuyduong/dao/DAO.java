package ps20250nguyenngocthuyduong.dao;

import java.util.List;

/**
* This interface defines the CRUD methods that should be implemented by all DAO classes.
* @param <T> the type of object that the DAO operates on
*/
public interface DAO<T> {
    /**
    * Adds a new object of type T to the database.
    * @param t the object to add to the database
    * @return an integer representing the number of rows affected by the operation
    */
    public int add(T t);
    
    /**
    * Updates an existing object of type T in the database, based on its unique ID.
    * @param t the object to update in the database
    * @return an integer representing the number of rows affected by the operation
    */
    public int updateByID(T t);
    
    /**
    * Deletes an existing object of type T from the database, based on its unique ID.
    * @param entityID the ID of the object to delete from the database
    * @return an integer representing the number of rows affected by the operation
    */
    public int deleteByID(String entityID);
    
    /**
    * Retrieves an object of type T from the database, based on its unique ID.
    * @param entityID the ID of the object to retrieve from the database
    * @return an object of type T, or null if no matching record was found in the database
    */
    public T getByID(String entityID);
    
    /**
    * Retrieves all objects of type T from the database.
    * @return a List of all objects of type T in the database
    */
    public List<T> getAll();
}
