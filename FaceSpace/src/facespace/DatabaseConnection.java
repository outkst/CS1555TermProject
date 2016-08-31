package facespace;

import java.sql.*;  //import the file containing definitions for the parts
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.*;

/**
 * @author Kyle Monto (kwm19@pitt.edu) | Joe Meszar (jwm54@pitt.edu)
 */
public class DatabaseConnection {

    private static Connection connection;       // used to hold the jdbc connection to the DB
    private Statement statement;                // used to create an instance of the connection
    private PreparedStatement prepStatement;    // used to create a prepared statement, that will be later reused
    private ResultSet resultSet;                // used to hold the result of your query (if one exists)
    private String query;                       // this will hold the query we are using

    /**
     * @param url The URL of the database to connect to. (e.g. jdbc:oracle:thin:@localhost:1521:xe)
     * @param username The username of the database account.
     * @param password The password of the database account.
     * 
     * @throws SQLException
     */
    public DatabaseConnection(String url, String username, String password) throws SQLException {
        // register the oracle driver.  
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        // create the database connection
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Close the database connection.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean closeConnection() throws SQLException {
        connection.close();
        
        return true;
    }

    /**
     * Adds a new user to a group in the database system.
     * @param userEmail The user's email address.
     * @param groupName The name of the group to add the user into.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean addToGroup(String userEmail, String groupName) throws SQLException, Exception {
        try {
            //initialize input variables
            int userID = 0;
            int groupID = 0;
            
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //make sure group exists
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM GROUPS WHERE UPPER(NAME) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, groupName);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No Group Found");
            } else {
                groupID = resultSet.getInt(1);
                closeSQLObjects();
            }

            // show the user input
            System.out.println(String.format("User ID: {%d} Group ID: {%d}", userID, groupID));

            //Insert query statement
            query = "INSERT INTO GROUPMEMBERS (GROUPID, USERID, DATEJOINED) VALUES (?, ?, current_timestamp)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, groupID);
            prepStatement.setInt(2, userID);
            prepStatement.executeUpdate();
            closeSQLObjects();

            // get the updated data
            query = "SELECT G.NAME, U.FNAME, U.LNAME, U.EMAIL, GM.DATEJOINED FROM GROUPS G "
                    + "LEFT JOIN GROUPMEMBERS GM ON GM.GROUPID = G.ID "
                    + "LEFT JOIN USERS U ON U.ID = GM.USERID "
                    + "WHERE GM.GROUPID=? AND GM.USERID=?";
            prepStatement = connection.prepareStatement(query); //create a new instance
            prepStatement.setInt(1, groupID);
            prepStatement.setInt(2, userID);
            resultSet = prepStatement.executeQuery();

            // display the new list of users
            System.out.println("\nAfter successful insert, data is...\n"
                    + "[RECORD#] [GROUPNAME],[FNAME],[LNAME],[EMAIL],[DATEJOINED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Adds a new group to the database system.
     * 
     * @param groupName A name for the group.
     * @param description A detailed description of the group.
     * @param limit The maximum amount of members the group will allow.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws java.sql.SQLException
     */
    public boolean createGroup(String groupName, String description, int limit) throws SQLException, Exception {
        try {
            if (limit <= 0) { throw new Exception("Group membership limit must be greater than 0"); }
            
            // make sure the group name doesn't exist
            query = "SELECT COUNT(*) FROM GROUPS WHERE NAME=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, groupName);
            resultSet = prepStatement.executeQuery();
            closeSQLObjects();
            
            //Insert query statement
            query = "INSERT INTO GROUPS (NAME, DESCRIPTION, LIMIT, DATECREATED) VALUES (?, ?, ?, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, groupName);
            prepStatement.setString(2, description);
            prepStatement.setInt(3, limit);
            prepStatement.executeUpdate();
            closeSQLObjects();

            // get the new list of users
            statement = connection.createStatement(); //create an instance
            query = "Select * FROM groups";
            resultSet = statement.executeQuery(query);

            // display a list of the groups
            System.out.println("\nQuery success, displaying groups:\n"
                    + "[RECORD#] [NAME],[DESCRIPTION],[LIMIT],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Adds a new user to the database system.
     * 
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param dateOfBirth The date of birth of the user.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws java.sql.SQLException
     */
    public boolean createUser(String firstName, String lastName, String email, String dateOfBirth) throws SQLException, Exception {
        try {
            // Create the query and insert
            query = "INSERT INTO USERS(FNAME, LNAME, EMAIL, DOB, LASTLOGIN, DATECREATED) VALUES (?, ?, ?, TO_DATE(?,'DD-MON-YYYY'), NULL, current_timestamp)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, firstName);
            prepStatement.setString(2, lastName);
            prepStatement.setString(3, email);
            prepStatement.setString(4, dateOfBirth);
            prepStatement.executeUpdate();
            closeSQLObjects();

            // get the new list of users
            statement = connection.createStatement(); //create an instance
            query = "SELECT * FROM USERS";
            resultSet = statement.executeQuery(query);

            // display the new list of users
            System.out.println("\nQuery success, created user information is:\n"
                    + "[RECORD#] [ID],[FNAME],[LNAME],[EMAIL],[BIRTHDATE],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5) + ", "
                        + resultSet.getString(7));
                counter++;
            }

            System.out.println("\nSUCCESS!");
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Displays all of a user's pending, and established, friendships.
     * 
     * @param userEmail The email address of the user from which to display friends.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws java.sql.SQLException
     */
    public boolean displayFriends(String userEmail) throws SQLException, Exception {
        try {
            int userID = 0;

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }
            closeSQLObjects();

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d}", userID));

            //query to display friends
            query = "SELECT U.FNAME AS FIRSTNAME,\n"
                    + "U.LNAME AS LASTNAME,\n"
                    + "U.EMAIL AS EMAILADDRESS,\n"
                    + "F.APPROVED AS APPROVED,\n"
                    + "F.DATEAPPROVED AS DATEAPPROVED\n"
                    + "FROM FRIENDSHIPS F, USERS U\n"
                    + "WHERE F.FRIENDID = U.ID\n"
                    + "AND F.USERID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, friendships are:\n"
                    + "[RECORD#] [FNAME],[LNAME],[EMAIL],[APPROVED?],[DATEAPPROVED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Given a user, look up all of the messages sent to that user (either
     * directly or via a group that they belong to).
     * 
     * @param userEmail The email address of the user from which to display all messages.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws java.sql.SQLException
     */
    public boolean displayMessages(String userEmail) throws SQLException, Exception {
        try {
            int userID = 0;

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }
            closeSQLObjects();
            
            //query to display friends
            query = "SELECT U.FNAME AS FIRSTNAME,\n"
                    + "  U.LNAME AS LASTNAME,\n"
                    + "  M.SUBJECT AS SUBJECT,\n"
                    + "  M.BODY AS BODY,\n"
                    + "  M.DATECREATED AS DATE_RECEIVED\n"
                    + "FROM MESSAGES M, USERS U\n"
                    + "WHERE RECIPIENTID = ? \n"
                    + "AND U.ID = M.SENDERID\n"
                    + "ORDER BY DATE_RECEIVED";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nMessages for the user are:\n"
                    + "[RECORD#] [FNAME],[LNAME],[SUBJECT],[BODY],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Given a user, look up all of the messages sent to that user (either
     * directly or via a group that they belong to).
     * 
     * @param userEmail The email address of the user from which to display new messages.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean displayNewMessages(String userEmail) throws SQLException, Exception {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            Timestamp lastLogin = null;
            
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
                closeSQLObjects();
            }

            query = "SELECT LASTLOGIN FROM USERS WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            resultSet = prepStatement.executeQuery();

            //check result set to see whether last log in is null
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                lastLogin = resultSet.getTimestamp(1);
                closeSQLObjects();
            }

            if (lastLogin == null) {
                //query if last login is null, just display all messages
                System.out.println("\nNo previous login, displaying all messages:");
                query = "SELECT U.FNAME AS FIRSTNAME,\n"
                        + "  U.LNAME AS LASTNAME,\n"
                        + "  U.EMAIL AS EMAILADDRESS,\n"
                        + "  M.SUBJECT AS SUBJECT,\n"
                        + "  M.BODY AS BODY,\n"
                        + "  M.DATECREATED AS DATE_RECEIVED\n"
                        + "FROM MESSAGES M, USERS U\n"
                        + "WHERE RECIPIENTID = ? \n"
                        + "AND U.ID = M.SENDERID\n"
                        + "ORDER BY DATE_RECEIVED";
            } else {
                //query if last login is not null, and will display all message
                //after the time of last login
                query = "SELECT U.FNAME AS FIRSTNAME,\n"
                        + "  U.LNAME AS LASTNAME,\n"
                        + "  U.EMAIL AS EMAILADDRESS,\n"
                        + "  M.SUBJECT AS SUBJECT,\n"
                        + "  M.BODY AS BODY,\n"
                        + "  M.DATECREATED AS DATE_RECEIVED\n"
                        + "FROM MESSAGES M, USERS U\n"
                        + "WHERE RECIPIENTID = ? \n"
                        + "AND U.ID = M.SENDERID\n"
                        + "AND M.DATECREATED > ?\n"
                        + "ORDER BY DATE_RECEIVED";
            }

            prepStatement = connection.prepareStatement(query);

            if (lastLogin == null) {
                //query if last login is null, just display all messages
                prepStatement.setInt(1, userID);
            } else {
                //query if last login is not null, and will display all message
                //after the time of last login
                prepStatement.setInt(1, userID);
                prepStatement.setTimestamp(2, lastLogin);
            }

            resultSet = prepStatement.executeQuery();

            System.out.println("\nNew Messages for the user are:\n"
                    + "[RECORD#] [FNAME],[LNAME],[EMAIL],[SUBJECT],[BODY],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5) + ", "
                        + resultSet.getString(6));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Remove a user and all of their information from the system. 
     * 
     * @param userEmail The email address of the user to remove.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean dropUser(String userEmail) throws SQLException, Exception {
        try {            
            // intialize input variables for user
            int userID = 0;
            
            // build a sql query to get the user's ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, 
            //  otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
                closeSQLObjects();
            }
            
            // use this ID to delete the user, which will fire a 
            //      trigger and remove all of their data
            query = "DELETE FROM USERS WHERE ID=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            resultSet = prepStatement.executeQuery();
            
            System.out.println("\nUser successfully removed!");
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }
    
    
    /**
     * Creates an established friendship from one user to another inside the database.
     * 
     * @param userEmail The email address of the user from which to start the friendship.
     * @param friendEmail The email address of the user from which to establish the friendship with.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws java.sql.SQLException
     */
    public boolean establishFriendship(String userEmail, String friendEmail) throws SQLException, Exception {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            int friendID = 0;

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No user found matching this userEmail");
            } else {
                userID = resultSet.getInt(1);
                closeSQLObjects();
            }

            if (friendEmail.equals(userEmail)) {
                throw new Exception("You cannot friend yourself!");
            }

            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, friendEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No user found matching this friendEmail");
            } else {
                friendID = resultSet.getInt(1);
            }

            /**
             * GET THE STATUS OF THIS FRIENDSHIP (IF EXISTS), AND EITHER
             *
             * 1) CREATE A NEW PENDING FRIENDSHIP 2) CREATE AN APPROVED
             * FRIENDSHIP 3) TELL THE USER THAT A FRIENDSHIP ALREADY EXISTS
             *
             */
            //Select the current status of a friendship between the 2 users
            query = "SELECT * FROM FRIENDSHIPS WHERE USERID = ? AND FRIENDID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            prepStatement.setInt(2, friendID);
            resultSet = prepStatement.executeQuery();

            //normally insert userid, friendid, then friendid, userid
            boolean oppositeDirection = false;
            //if resultSet has a next then there is a friendship present
            if (resultSet.next()) {
                //if the result is 0 then its pending
                int status = resultSet.getInt(3);
                if (status == 0) {
                    throw new Exception("Friendship is already pending");

                } else if (status == 1) {
                    throw new Exception("Friendship is already established");
                }
            }
            closeSQLObjects();

            query = "INSERT INTO FRIENDSHIPS (USERID, FRIENDID) VALUES (?, ?)";
            String countQuery = "SELECT COUNT(*) FROM FRIENDSHIPS WHERE USERID = ? AND FRIENDID = ?";

            //insert in both directions
            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            prepStatement.setInt(2, friendID);
            prepStatement.executeUpdate();

            prepStatement = connection.prepareStatement(countQuery);
            prepStatement.setInt(1, friendID);
            prepStatement.setInt(2, userID);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == 0) {
                    prepStatement = connection.prepareStatement(query);
                    prepStatement.setInt(1, friendID);
                    prepStatement.setInt(2, userID);
                    prepStatement.executeUpdate();
                }
            }
            closeSQLObjects();

            //just a query to show that the row was inserted
            query = "SELECT U.FNAME, U.LNAME, U.EMAIL, F.APPROVED, F.DATEAPPROVED\n"
                    + "FROM FRIENDSHIPS F, USERS U\n"
                    + "WHERE ((F.USERID = ? AND F.FRIENDID = ?)\n"
                    + "OR (F.USERID = ? AND F.FRIENDID = ?))\n"
                    + "AND F.USERID = U.ID";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            prepStatement.setInt(2, friendID);
            prepStatement.setInt(3, userID);
            prepStatement.setInt(4, friendID);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, friendship status:\n"
                    + "[RECORD#] [FNAME],[LNAME],[EMAIL],[APPROVED?],[DATEAPPROVED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Creates a pending friendship from one user to another inside the
     * database.
     * 
     * @param userEmail The email address of the user from which to start the friendship.
     * @param friendEmail The email address of the user from which to establish the friendship with.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws java.sql.SQLException
     */
    public boolean initiateFriendship(String userEmail, String friendEmail) throws SQLException, Exception {
        try {
            int userID = 0;
            int friendID = 0;

            // query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            // check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("The email entered does not exist");
            } else {
                userID = resultSet.getInt(1);
                closeSQLObjects();
            }

            if (friendEmail.equals(userEmail)) {
                throw new Exception("You cannot friend yourself!");
            }

            // query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, friendEmail);
            resultSet = prepStatement.executeQuery();

            // check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("The friend name entered does not exist");
            } else {
                friendID = resultSet.getInt(1);
                closeSQLObjects();
            }

            /**
             * USE THE USER_ID AND FRIEND_ID TO CREATE A "FRIENDSHIP". THIS
             * DEPENDS ON THE TRIGGER THAT WAS BUILT, WHICH WILL DO ONE OF TWO
             * THINGS:
             *
             * 1) Create a "Pending Friendship" 2) Create a "Friendship"
             *
             */
            // insert statement for establishing pending friendship
            query = "INSERT INTO FRIENDSHIPS (USERID, FRIENDID) VALUES (?, ?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            prepStatement.setInt(2, friendID);
            prepStatement.executeUpdate();
            closeSQLObjects();

            /**
             * GRAB THE INSERTED ROW FROM THE DB AND DISPLAY TO USER.
             *
             */
            // query to show that the row was inserted
            query = "SELECT U.FNAME, U.LNAME, U.EMAIL, F.APPROVED, F.DATEAPPROVED\n"
                    + "FROM FRIENDSHIPS F, USERS U\n"
                    + "WHERE ((F.USERID = ? AND F.FRIENDID = ?)\n"
                    + "OR (F.USERID = ? AND F.FRIENDID = ?))\n"
                    + "AND F.USERID = U.ID";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            prepStatement.setInt(2, friendID);
            prepStatement.setInt(3, userID);
            prepStatement.setInt(4, friendID);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nAfter successful insert, data is...\n"
                    + "[RECORD#] [FNAME],[LNAME],[EMAIL],[APPROVED?],[DATEAPPROVED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Lists all the groups in the database.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean listAllGroups() throws SQLException, Exception {
        try {
            query = "SELECT NAME, DESCRIPTION, LIMIT, DATECREATED FROM GROUPS";
            prepStatement = connection.prepareStatement(query);
            resultSet = prepStatement.executeQuery();

            System.out.println("\n[RECORD#] [NAME],[DESCRIPTION],[LIMIT],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter++ + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Lists all the users in the database.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean listAllUsers() throws SQLException, Exception {
        try {
            query = "SELECT FNAME, LNAME, EMAIL, DOB, LASTLOGIN, DATECREATED FROM USERS";
            prepStatement = connection.prepareStatement(query);
            resultSet = prepStatement.executeQuery();

            System.out.println("[RECORD#] [FNAME],[LNAME],[EMAIL],[DOB],[LASTLOGIN],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter++ + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5) + ", "
                        + resultSet.getString(6));
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Logs in the user by updating their last login to the current timestamp.
     * 
     * @param userEmail The email address of the user to log in.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean logInUser(String userEmail) throws SQLException, Exception {
        try {
            int userID = 0;
            
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("The user name entered does not exist");
            } else {
                userID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //Insert statement for establishing pending friendship
            query = "UPDATE USERS \n"
                    + "SET LASTLOGIN = CURRENT_TIMESTAMP\n"
                    + "WHERE ID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            prepStatement.executeUpdate();
            closeSQLObjects();

            //just a query to show that the row was inserted
            query = "select id, fname, lname, email, lastlogin\n"
                    + "from users\n"
                    + "where id = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userID);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, user is now logged-in:\n"
                    + "[RECORD#] [ID],[FNAME],[LNAME],[EMAIL],[LASTLOGIN]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Searches firstname, lastname, and email as applicable search fields.
     * breaks up search string by using regular regular expression on
     * whitespace. runs queries for each of the search terms.
     * 
     * @param searchTerms The search term(s) to search for. Terms are broken
     *      down into tokens separated by whitespace.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean searchForUser(String searchTerms) throws SQLException, Exception {
        try {
            int userID = 0;

            String[] searchItems = searchTerms.split("\\s+");
            System.out.println("searchItems length = " + searchItems.length);

            //query that looks at firstname, lastname, and email as applicable search fields
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT ID, FNAME, LNAME, EMAIL FROM USERS WHERE ");
            for (int x = 0; x < searchItems.length; x++) {
                if (x == 0) {
                    queryBuilder.append("(");
                } else {
                    queryBuilder.append("OR (");
                }

                queryBuilder.append("FNAME LIKE '%").append(searchItems[x]).append("%'");
                queryBuilder.append(" OR LNAME LIKE '%").append(searchItems[x]).append("%'");
                queryBuilder.append(" OR UPPER(EMAIL) LIKE '%").append(searchItems[x]).append("%'");
                queryBuilder.append(") ");
            }

            prepStatement = connection.prepareStatement(queryBuilder.toString());
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, search results:\n"
                    + "[RECORD#] [ID],[FNAME],[LNAME],[EMAIL]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Send a message to an entire group of users.
     * 
     * @param userEmail The email address of the user sending the message.
     * @param groupName The name of the group to send the message to.
     * @param messageSubject The subject of the message to send.
     * @param messageBody The body (content) of the message to send.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean sendMessageToGroup(String userEmail, String groupName, String messageSubject, String messageBody) throws SQLException, Exception {
        try {
            int senderID = 0;
            int groupID = 0;
            int recipID = 0;

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                senderID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //make sure group exists
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM GROUPS WHERE UPPER(NAME) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, groupName);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No Group Found");
            } else {
                groupID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //query to get the users that are a part of the group 
            query = "SELECT USERID FROM GROUPMEMBERS WHERE GROUPID = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, groupID);
            resultSet = prepStatement.executeQuery();

            //Insert statement for establishing pending friendship
            query = "INSERT INTO MESSAGES (SENDERID, SUBJECT, BODY, RECIPIENTID, GROUPID, DATECREATED) VALUES (?, ?, ?, ?, ?, current_timestamp)";
            prepStatement = connection.prepareStatement(query);

            boolean groupHasMember = false;
            while (resultSet.next()) {
                prepStatement.setInt(1, senderID);
                prepStatement.setString(2, messageSubject);
                prepStatement.setString(3, messageBody);
                prepStatement.setInt(4, resultSet.getInt(1));
                prepStatement.setInt(5, groupID);
                prepStatement.executeUpdate();
                groupHasMember = true;
            }
            closeSQLObjects();
            
            //execute the insert
            if (!groupHasMember) {
                throw new Exception("The group you entered has no members");
            }

            //just a query to show that the row was inserted
            query = "SELECT M.ID, G.NAME, U.FNAME, U.LNAME, M.SUBJECT, M.BODY, RU.FNAME, RU.LNAME, M.DATECREATED FROM MESSAGES M "
                    + "LEFT JOIN USERS U ON U.ID = M.SENDERID "
                    + "LEFT JOIN USERS RU ON RU.ID = M.RECIPIENTID "
                    + "LEFT JOIN GROUPS G ON G.ID = M.GROUPID "
                    + "WHERE M.SENDERID=? AND G.ID=? AND M.SUBJECT=? AND M.BODY=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, senderID);
            prepStatement.setInt(2, groupID);
            prepStatement.setString(3, messageSubject);
            prepStatement.setString(4, messageBody);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, messages sent:\n"
                    + "[RECORD#] [ID],[GROUP],[SENDERFNAME],[SENDERLNAME],[SUBJECT],[BODY],[RECFNAME],[RECLNAME],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5) + ", "
                        + resultSet.getString(6) + ", "
                        + resultSet.getString(7) + ", "
                        + resultSet.getString(8) + ", "
                        + resultSet.getString(9));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Send a message to an individual user.
     * 
     * @param userEmail The email address of the user sending the message.
     * @param recipEmail The email address of the user receiving the message.
     * @param messageSubject The subject of the message to send.
     * @param messageBody The body of the message to send.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean sendMessageToUser(String userEmail, String recipEmail, String messageSubject, String messageBody) throws SQLException, Exception {
        try {
            int senderID = 0;
            int recipID = 0;

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                senderID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, recipEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                recipID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //Insert statement for establishing pending friendship
            query = "INSERT INTO MESSAGES (SENDERID, SUBJECT, BODY, RECIPIENTID, DATECREATED) VALUES (?, ?, ?, ?, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, senderID);
            prepStatement.setString(2, messageSubject);
            prepStatement.setString(3, messageBody);
            prepStatement.setInt(4, recipID);
            prepStatement.executeUpdate();
            closeSQLObjects();

            //just a query to show that the row was inserted
            query = "SELECT M.ID, U.FNAME, U.LNAME, M.SUBJECT, M.BODY, RU.FNAME, RU.LNAME, M.DATECREATED FROM MESSAGES M "
                    + "LEFT JOIN USERS U ON U.ID=M.SENDERID "
                    + "LEFT JOIN USERS RU ON RU.ID=M.RECIPIENTID "
                    + "WHERE M.SENDERID=? AND M.RECIPIENTID=? AND M.SUBJECT=? AND M.BODY=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, senderID);
            prepStatement.setInt(2, recipID);
            prepStatement.setString(3, messageSubject);
            prepStatement.setString(4, messageBody);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, message sent:\n"
                    + "[RECORD#] [ID],[SENDERFNAME],[SENDERLNAME],[SUBJECT],[BODY],[RECIPFNAME],[RECIPLNAME],[DATECREATED]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5) + ", "
                        + resultSet.getString(6) + ", "
                        + resultSet.getString(7) + ", "
                        + resultSet.getString(8));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Given two users (userA and userB), find a path, if one exists, between
     * the userA and the userB with at most 3 hop between them. A hop is defined
     * as a friendship between any two users.
     * 
     * @param startEmail The email address of the user to begin with.
     * @param endEmail The email address of the user to try and get to from the starting email address.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean threeDegrees(String startEmail, String endEmail) throws SQLException, Exception {
        try {
            int startID = 0;
            int endID = 0;
            
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE LOWER(EMAIL) = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, startEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                startID = resultSet.getInt(1);
                closeSQLObjects();
            }
            
            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, endEmail);
            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                endID = resultSet.getInt(1);
                closeSQLObjects();
            }

            //Select statement for establishing pending friendship
            query = "SELECT FRIENDID FROM\n"
                    + "FRIENDSHIPS \n"
                    + "WHERE USERID = ?\n"
                    + "AND APPROVED = 1\n"
                    + "ORDER BY FRIENDID";

            String countQuery = "SELECT COUNT(FRIENDID) FROM\n"
                    + "FRIENDSHIPS \n"
                    + "WHERE USERID = ?\n"
                    + "AND APPROVED = 1\n"
                    + "ORDER BY FRIENDID";

            //get the number of rows returned
            prepStatement = connection.prepareStatement(countQuery);
            prepStatement.setInt(1, startID);
            resultSet = prepStatement.executeQuery();
            int numRows = 0;
            while (resultSet.next()) {
                numRows = resultSet.getInt(1);
            }
            closeSQLObjects();

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, startID);
            resultSet = prepStatement.executeQuery();

            LinkedList connections = new LinkedList();
            LinkedList currentPath = new LinkedList();
            Hashtable<Integer, LinkedList> currentFriendships = new Hashtable<Integer, LinkedList>();
            currentPath.add(startID);
            int curConnection = 0;
            int degrees = 1;
            int count = 0;
            Integer currentID = new Integer(startID);
            Integer lastID = null;
            currentFriendships.put(startID, new LinkedList());
            boolean success = false;
            while (true) {
                if (degrees <= 3) {
                    if (resultSet.next()) {

                        if (resultSet.getInt(1) == endID) {
                            currentPath.add(resultSet.getInt(1));
                            success = true;
                            break;
                            //completely found
                        }
                        if (!currentPath.contains(resultSet.getInt(1))) {
                            LinkedList l = currentFriendships.get(currentID);
                            l.add(resultSet.getInt(1));
                            currentFriendships.put(currentID, l);
                        }
                        count++;
                    }
                }
                //if you are on the last row, remove the last user that did not 
                //work, grab a new userID and get all of their friends
                if (count == numRows || (degrees > 3 && !currentFriendships.isEmpty())) {

                    LinkedList l = currentFriendships.get(currentID);

                    int nextSearch = 0;
                    if (!l.isEmpty()) {
                        nextSearch = (Integer) l.removeFirst();
                    } else if (lastID != null) {
                        degrees--;
                        currentPath.remove(currentID);
                        currentFriendships.remove(currentID);
                        currentID = lastID;
                        l = currentFriendships.get(currentID);
                        if (l.isEmpty()) {
                            currentFriendships.remove(currentID);
                            currentPath.remove(currentID);
                            degrees--;
                            currentID = (Integer) currentPath.getLast();
                            l = currentFriendships.get(currentID);
                            if (l.isEmpty()) {
                                currentFriendships.remove(currentID);
                                currentPath.remove(currentID);
                                degrees--;
                                currentID = (Integer) currentPath.getLast();
                                l = currentFriendships.get(currentID);
                                if (l.isEmpty()){
                                    success = false;
                                    break;
                                } else {
                                    nextSearch = (Integer) l.removeFirst();
                                }
                            } else {
                                nextSearch = (Integer) l.removeFirst();
                            }
                        } else {
                            nextSearch = (Integer) l.removeFirst();
                        }
                        
                    } else {
                        success = false;
                        break;
                    }

                    //get the number of rows for this user
                    prepStatement = connection.prepareStatement(countQuery);
                    prepStatement.setInt(1, nextSearch);
                    resultSet = prepStatement.executeQuery();
                    while (resultSet.next()) {
                        numRows = resultSet.getInt(1);
                    }
                    closeSQLObjects();
                    count = 0;
                    
                    //get the list of friendIDs for the next user
                    prepStatement = connection.prepareStatement(query);
                    prepStatement.setInt(1, nextSearch);
                    resultSet = prepStatement.executeQuery();

                    currentPath.add(nextSearch);
                    lastID = currentID;
                    currentID = new Integer(nextSearch);
                    currentFriendships.put(currentID, new LinkedList());

                    degrees++;
                } else if (count == numRows && currentFriendships.isEmpty()) {
                    success = false;
                    break;
                    //no dice
                }
                if (degrees > 3 && currentFriendships.isEmpty()) {
                    success = false;
                    break;
                    //too many degrees
                }
            }
            if (success) {
                query = "select FNAME, LNAME from users WHERE ID = ?";
                
                System.out.println("\nQuery success, path for three degrees:");
                int x=0;
                for (int i = 0; i < currentPath.size(); i++) {
                    prepStatement = connection.prepareStatement(query);
                    prepStatement.setInt(1, (Integer) currentPath.get(i));
                    resultSet = prepStatement.executeQuery();
                    while(resultSet.next()){
                        if (x==0) {
                            System.out.print(resultSet.getString(1) + " " + resultSet.getString(2));
                            x++;
                        } else {
                            System.out.print(" -> " + resultSet.getString(1) + " "
                                + resultSet.getString(2));
                        }
                    }
                }
            } else {
                System.out.println("No successful 3 degree matching could be made");
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    /**
     * Display the top K who have sent or received the highest number of
     * messages during for the past X months. X and K should be input parameters
     * to this function.
     *
     * The current query treats all messages equally (aka no special
     * consideration for group messages), so if a user sends 1 message to a
     * group of 10 people, that would count as them sending 10 messages.
     * 
     * @return True if the function performed successfully; otherwise False.
     * @throws SQLException
     */
    public boolean topMessagers() throws SQLException, Exception {
        try {
            //initialize input variables for User and Friend info
            int numMonths = 0;
            int numResults = 0;
            String numberOfMonths = null;
            String numberOfResults = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the amount of months
            do {
                System.out.print("Please enter the number of months: ");
                numberOfMonths = keyIn.nextLine().trim();
            } while (numberOfMonths == null || numberOfMonths.equalsIgnoreCase("") || !Pattern.matches("\\d+", numberOfMonths));

            numMonths = Integer.parseInt(numberOfMonths);
            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.print("Please enter the number of results you would like to see: ");
                numberOfResults = keyIn.nextLine().trim().toUpperCase();
            } while (numberOfResults == null || numberOfResults.equalsIgnoreCase("") || !Pattern.matches("\\d+", numberOfResults));

            numResults = Integer.parseInt(numberOfResults);
            //query to make sure user exists and get their ID
            query = "SELECT M.MESSAGE_COUNT, U.FNAME, U.LNAME, U.EMAIL FROM\n"
                    + "(SELECT S.USERID AS USER_ID, S.TOTAL_SENT + R.TOTAL_RECEIVED AS MESSAGE_COUNT \n"
                    + "FROM \n"
                    + "(\n"
                    + "SELECT SENDERID AS USERID, COUNT(*) AS TOTAL_SENT\n"
                    + "FROM MESSAGES \n"
                    + "WHERE DATECREATED > CURRENT_TIMESTAMP - NUMTOYMINTERVAL(?, 'MONTH')\n"
                    + "GROUP BY SENDERID\n"
                    + "ORDER BY SENDERID\n"
                    + ") S, \n"
                    + "(\n"
                    + "SELECT RECIPIENTID AS USERID, COUNT(*) AS TOTAL_RECEIVED\n"
                    + "FROM MESSAGES \n"
                    + "WHERE DATECREATED > CURRENT_TIMESTAMP - NUMTOYMINTERVAL(?, 'MONTH')\n"
                    + "GROUP BY RECIPIENTID\n"
                    + "ORDER BY RECIPIENTID\n"
                    + ") R\n"
                    + "WHERE S.USERID = R.USERID\n"
                    + "ORDER BY MESSAGE_COUNT DESC) M, USERS U\n"
                    + "WHERE M.USER_ID = U.ID AND ROWNUM <= ?";

            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, numberOfMonths);
            prepStatement.setString(2, numberOfMonths);
            prepStatement.setInt(3, numResults);
            resultSet = prepStatement.executeQuery();

            System.out.println("\nQuery success, top messages are:\n"
                    + "[RECORD#] [MSG_COUNT],[FNAME],[LNAME],[EMAIL]");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
                counter++;
            }
        } finally {
            closeSQLObjects();
        }
        
        return true;
    }

    private void closeSQLObjects() {
        try {
            if (statement != null) { statement.close(); }
            if (prepStatement != null) { prepStatement.close(); }
            if (resultSet != null) { resultSet.close(); }
        } catch (SQLException e) {
            System.out.println(String.format("!! Cannot close object. Error: %s", e.getMessage()));
        }
    }
}
