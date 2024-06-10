package ps20250nguyenngocthuyduong.models;

/**
 * The LoggedInUser class represents the currently logged-in user in the application.
 * It provides static methods to set and retrieve the current user, check if a user is
 * currently logged in, and check the user's role. The class also provides a method to
 * log out the current user.
 */
public class LoggedInUser {
    // The current user.
    private static User currentUser;

    /**
     * Returns the current user.
     * @return the current user, or null if no user is currently logged in.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     * @param user the user to set as the current user.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Checks if a user is currently logged in.
     * @return true if a user is currently logged in, false otherwise.
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Checks if the current user is a GiangVien.
     * @return true if the current user is a GiangVien, false otherwise.
     */
    public static boolean isGiangVien() {
        return isLoggedIn() && currentUser.getRole().equals("1");
    }
    
    /**
     * Checks if the current user is a CBDT.
     * @return true if the current user is a CBDT, false otherwise.
     */
    public static boolean isCBDT() {
        return isLoggedIn() && currentUser.getRole().equals("2");
    }
    
    /**
     * Checks if the current user is an Admin.
     * @return true if the current user is an Admin, false otherwise.
     */
    public static boolean isAdmin() {
        return isLoggedIn() && currentUser.getRole().equals("3");
    }
    
    /**
     * Logs out the current user by setting the current user to null.
     */
    public static void logOut() {
        currentUser = null;
    }
}
