package facespace;

import java.sql.*;  //import the file containing definitions for the parts
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.*;

/**
 *
 * @author Kyle Monto (kwm19@pitt.edu) | Joe Meszar (jwm54@pitt.edu)
 */
public class DatabaseConnection {

    private static Connection connection; //used to hold the jdbc connection to the DB
    private Statement statement; //used to create an instance of the connection
    private PreparedStatement prepStatement; //used to create a prepared statement, that will be later reused
    private ResultSet resultSet; //used to hold the result of your query (if one exists)
    private String query;  //this will hold the query we are using

    public DatabaseConnection() throws SQLException {

        //to run in netbeans need to add ojbdc6.jar to project libraries
        String username = "";
        String password = "";

        // create a scanner to get user input
        Scanner keyIn = new Scanner(System.in);

        // get the username and password
        System.out.println("Please enter DB username: ");
        username = keyIn.nextLine().toLowerCase();
        System.out.println("Please enter DB password: ");
        password = keyIn.nextLine();
        try {
            // Register the oracle driver.  
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            //This is the location of the database. This is the database in oracle provided to our school class
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

            //create a connection to DB on class3.cs.pitt.edu
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception Ex) {
            System.out.println("Error connecting to database.  Machine Error: " + Ex.toString());

        } finally {
            /*
             * NOTE: the connection should be created once and used through out the whole project;
             * Is very expensive to open a connection therefore you should not close it after 
             * every operation on database
             */
            //Moved the connection to its only method and is inside of a finally block 
            //in the main program
//            connection.close();
        }
    }

    /**
     * Adds a new user to the database system.
     */
    public void createUser() {
        try {
            //initialize input variables
            String firstName = null;
            String lastName = null;
            String email = null;
            String dateOfBirth = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the firstName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a first name:");
                firstName = keyIn.nextLine().trim().toUpperCase();
            } while (firstName == null || firstName.equalsIgnoreCase(""));

            // get the lastName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a last name:");
                lastName = keyIn.nextLine().trim().toUpperCase();
            } while (lastName == null || lastName.equalsIgnoreCase(""));

            // get a valid email and normalize (lowercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a valid email address: ");
                email = keyIn.nextLine().trim().toLowerCase();
            } while (email == null || email.equalsIgnoreCase("") || !Pattern.matches("^([a-zA-Z0-9]+([\\.+_-][a-zA-Z0-9]+)*)@(([a-zA-Z0-9]+((\\.|[-]{1,2})[a-zA-Z0-9]+)*)\\.[a-zA-Z]{2,6})$", email));

            // get the date of birth (DOB) and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Enter a valid date of birth (DD-MON-YYYY): ");
                dateOfBirth = keyIn.nextLine().trim().toUpperCase();
            } while (dateOfBirth == null || dateOfBirth.equalsIgnoreCase("") || !Pattern.matches("[0123]{1}\\d{1}-\\w{3}-\\d{4}", dateOfBirth));

            // show the user input
            System.out.println(String.format("First Name: {%s} LastName: {%s} Email: {%s} DOB: {%s}", firstName, lastName, email, dateOfBirth));

            //Insert query statement
            query = "INSERT INTO USERS(FNAME, LNAME, EMAIL, DOB, LASTLOGIN, DATECREATED) VALUES (?, ?, ?, TO_DATE(?,'DD-MON-YYYY'), NULL, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of the insert statement
            prepStatement.setString(1, firstName);
            prepStatement.setString(2, lastName);
            prepStatement.setString(3, email);
            prepStatement.setString(4, dateOfBirth);

            //execute the insert
            prepStatement.executeUpdate();

            // get the new list of users
            statement = connection.createStatement(); //create an instance
            query = "Select * FROM USERS";
            resultSet = statement.executeQuery(query);

            // display the new list of users
            System.out.println("\nAfter the insert, data is...\n");
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
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    /**
     * *
     * Creates a pending friendship from one user to another inside the
     * database.
     */
    public void initiateFriendship() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String firstNameUser = null;
            String lastNameUser = null;

            int friendID = 0;
            String firstNameFriend = null;
            String lastNameFriend = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameUser == null || lastNameUser.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameUser);
            prepStatement.setString(2, lastNameUser);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // get the first name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's first name: ");
                firstNameFriend = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameFriend == null || firstNameFriend.equalsIgnoreCase(""));

            // get the last name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's last name: ");
                lastNameFriend = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameFriend == null || lastNameFriend.equalsIgnoreCase(""));

            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameFriend);
            prepStatement.setString(2, lastNameFriend);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                friendID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d} ID of friend: {%d}", userID, friendID));

            //Insert statement for establishing pending friendship
            query = "INSERT INTO FRIENDSHIPS (USERID, FRIENDID) VALUES (?, ?)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of of insert statement
            prepStatement.setInt(1, userID);
            prepStatement.setInt(2, friendID);
            //execute the insert
            prepStatement.executeUpdate();

            //just a query to show that the row was inserted
            query = "SELECT U.FNAME, U.LNAME, F.APPROVED, F.DATEAPPROVED\n"
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

            System.out.println("\nAfter the insert, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
                counter++;
            }
            resultSet.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            switch (errorCode) {
                case 20001:
                    System.out.println("Friendship already pending");
                    break;
                case 20002:
                    System.out.println("Friendship already established");
                    break;
                default:
                    System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
                    break;
            }
        } catch (Exception e) {

            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    /**
     * *
     * Creates an established friendship from one user to another inside the
     * database.
     */
    public void establishFriendship() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String firstNameUser = null;
            String lastNameUser = null;

            int friendID = 0;
            String firstNameFriend = null;
            String lastNameFriend = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameUser == null || lastNameUser.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameUser);
            prepStatement.setString(2, lastNameUser);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // get the first name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's first name: ");
                firstNameFriend = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameFriend == null || firstNameFriend.equalsIgnoreCase(""));

            // get the last name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's last name: ");
                lastNameFriend = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameFriend == null || lastNameFriend.equalsIgnoreCase(""));

            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameFriend);
            prepStatement.setString(2, lastNameFriend);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                friendID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d} ID of friend: {%d}", userID, friendID));

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
                    System.out.println("Friendship is pending");
                    //carry out from the opposite direction
                    oppositeDirection = true;

                } else if (status == 1) { //otherwise its already established
                    System.out.println("Friendship is already established");
                    //quit operation
                    return;
                }
            }

            query = "INSERT INTO FRIENDSHIPS (USERID, FRIENDID) VALUES (?, ?)";

            if (oppositeDirection == false) {
                //insert in both directions
                //Create the prepared statement
                prepStatement = connection.prepareStatement(query);

                //Set parameters of of insert statement
                prepStatement.setInt(1, userID);
                prepStatement.setInt(2, friendID);
                //execute the insert
                prepStatement.executeUpdate();

                prepStatement = connection.prepareStatement(query);

                //Set parameters of of insert statement
                prepStatement.setInt(1, friendID);
                prepStatement.setInt(2, userID);
                //execute the insert
                prepStatement.executeUpdate();

            } else {
                //insert in only the opposite direction of the 
                prepStatement = connection.prepareStatement(query);

                //Set parameters of of insert statement
                prepStatement.setInt(1, friendID);
                prepStatement.setInt(2, userID);
                //execute the insert
                prepStatement.executeUpdate();
            }

            //just a query to show that the row was inserted
            query = "SELECT U.FNAME, U.LNAME, F.APPROVED, F.DATEAPPROVED\n"
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

            System.out.println("\nAfter the insert, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
                counter++;
            }
            resultSet.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            switch (errorCode) {
                case 20001:
                    System.out.println("Friendship already pending");
                    break;
                case 20002:
                    System.out.println("Friendship already established");
                    break;
                default:
                    System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
                    break;
            }
        } catch (Exception e) {

            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    public void displayFriends() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String firstNameUser = null;
            String lastNameUser = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameUser == null || lastNameUser.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameUser);
            prepStatement.setString(2, lastNameUser);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d}", userID));

            //query to display friends
            query = "SELECT U.FNAME AS FIRSTNAME,\n"
                    + "U.LNAME AS LASTNAME,\n"
                    + "F.APPROVED AS APPROVED,\n"
                    + "F.DATEAPPROVED AS DATEAPPROVED\n"
                    + "FROM FRIENDSHIPS F, USERS U\n"
                    + "WHERE F.FRIENDID = U.ID\n"
                    + "AND F.USERID = ?";
            prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, userID);

            resultSet = prepStatement.executeQuery();

            System.out.println("\nAfter the insert, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
                counter++;
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
        } catch (Exception e) {

            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a new group to the database system.
     */
    public void createGroup() {
        try {
            //initialize input variables
            String groupName = null;
            String description = null;
            String limitS = null;
            int limitI = 0;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the firstName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a group name:");
                groupName = keyIn.nextLine().trim().toUpperCase();
            } while (groupName == null || groupName.equalsIgnoreCase(""));

            // get the lastName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a description for the group:");
                description = keyIn.nextLine().trim();
            } while (description == null || description.equalsIgnoreCase(""));

            // get a valid email and normalize (lowercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a membership limit: ");
                limitS = keyIn.nextLine().trim();

            } while (limitS == null || limitS.equalsIgnoreCase("") || !Pattern.matches("\\d+", limitS));

            limitI = Integer.parseInt(limitS);
            if (limitI <= 0) {
                System.out.println("Group membership limit must be greater than 0");
                return;
            }

            // show the user input
            System.out.println(String.format("Group Name: {%s} description: {%s} limit: {%s}", groupName, description, limitS));

            //Insert query statement
            query = "INSERT INTO GROUPS (NAME, DESCRIPTION, LIMIT, DATECREATED) VALUES (?, ?, ?, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of the insert statement
            prepStatement.setString(1, groupName);
            prepStatement.setString(2, description);
            prepStatement.setInt(3, limitI);

            //execute the insert
            prepStatement.executeUpdate();

            // get the new list of users
            statement = connection.createStatement(); //create an instance
            query = "Select * FROM groups";
            resultSet = statement.executeQuery(query);

            // display the new list of users
            System.out.println("\nAfter the insert, data is...\n");
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
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a new user to a group in the database system.
     */
    public void addToGroup() {
        try {
            //initialize input variables
            String firstName = null;
            String lastName = null;
            String groupName = null;
            int userID = 0;
            int groupID = 0;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the firstName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a first name:");
                firstName = keyIn.nextLine().trim().toUpperCase();
            } while (firstName == null || firstName.equalsIgnoreCase(""));

            // get the lastName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a last name:");
                lastName = keyIn.nextLine().trim().toUpperCase();
            } while (lastName == null || lastName.equalsIgnoreCase(""));

            //make sure user exists
            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstName);
            prepStatement.setString(2, lastName);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // get the groupName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a group name:");
                groupName = keyIn.nextLine().trim().toUpperCase();
            } while (groupName == null || groupName.equalsIgnoreCase(""));

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
            }

            // show the user input
            System.out.println(String.format("User ID: {%d} Group ID: {%d}", userID, groupID));

            //Insert query statement
            query = "INSERT INTO GROUPMEMBERS (GROUPID, USERID, DATEJOINED) VALUES (?, ?, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of the insert statement
            prepStatement.setInt(1, groupID);
            prepStatement.setInt(2, userID);

            //execute the insert into groupmembers
            prepStatement.executeUpdate();

            query = "SELECT * FROM GROUPMEMBERS WHERE GROUPID = ? AND USERID = ?";
            prepStatement = connection.prepareStatement(query); //create an instance

            prepStatement.setInt(1, groupID);
            prepStatement.setInt(2, userID);

            //execute query to display result set
            resultSet = prepStatement.executeQuery();

            // display the new list of users
            System.out.println("\nAfter the insert, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3));
                counter++;
            }
            resultSet.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            switch (errorCode) {
                case 20000:
                    System.out.println("Group Membership limit is already met");
                    break;
                default:
                    System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
                    break;
            }
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else if (e.getMessage().equals("No Group Found")) {
                System.out.println("The group name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    //method to log in the user by updating their last login to the current time stamp
    public void logInUser() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String firstName = null;
            String lastName = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstName = keyIn.nextLine().trim().toUpperCase();
            } while (firstName == null || firstName.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastName = keyIn.nextLine().trim().toUpperCase();
            } while (lastName == null || lastName.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstName);
            prepStatement.setString(2, lastName);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d}", userID));

            //Insert statement for establishing pending friendship
            query = "UPDATE USERS \n"
                    + "SET LASTLOGIN = CURRENT_TIMESTAMP\n"
                    + "WHERE ID = ?";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of of insert statement
            prepStatement.setInt(1, userID);
            //execute the UPDATE to log in the user
            prepStatement.executeUpdate();

            //just a query to show that the row was inserted
            query = "select id, fname, lname, lastlogin\n"
                    + "from users\n"
                    + "where id = ?";
            prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, userID);

            resultSet = prepStatement.executeQuery();

            System.out.println("\nAfter the update, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4));
                counter++;
            }
            resultSet.close();
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    //Send message to user
    public void sendMessageToUser() {
        try {
            //initialize input variables for User and Friend info
            int senderID = 0;
            String firstNameSender = null;
            String lastNameSender = null;

            int recipID = 0;
            String firstNameRecip = null;
            String lastNameRecip = null;

            String messageSubject = null;
            String messageBody = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the sender's first name: ");
                firstNameSender = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameSender == null || firstNameSender.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the sender's last name: ");
                lastNameSender = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameSender == null || lastNameSender.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameSender);
            prepStatement.setString(2, lastNameSender);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                senderID = resultSet.getInt(1);
            }

            // get the first name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter recipient's first name: ");
                firstNameRecip = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameRecip == null || firstNameRecip.equalsIgnoreCase(""));

            // get the last name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter recipient's last name: ");
                lastNameRecip = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameRecip == null || lastNameRecip.equalsIgnoreCase(""));

            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameRecip);
            prepStatement.setString(2, lastNameRecip);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                recipID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of sender: {%d} ID of recipient: {%d}", senderID, recipID));

            // get the subject of the message
            do {
                System.out.println("Please enter your message subject: ");
                messageSubject = keyIn.nextLine().trim();
            } while (messageSubject == null || messageSubject.equalsIgnoreCase(""));

            // get the body of the message
            do {
                System.out.println("Please enter your message body: ");
                messageBody = keyIn.nextLine().trim();
            } while (messageBody == null || messageBody.equalsIgnoreCase(""));

            //Insert statement for establishing pending friendship
            query = "INSERT INTO MESSAGES (SENDERID, SUBJECT, BODY, RECIPIENTID, DATECREATED) VALUES (?, ?, ?, ?, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of of insert statement
            prepStatement.setInt(1, senderID);
            prepStatement.setString(2, messageSubject);
            prepStatement.setString(3, messageBody);
            prepStatement.setInt(4, recipID);
            //execute the insert
            prepStatement.executeUpdate();

            //just a query to show that the row was inserted
            query = "select * from messages\n"
                    + "where senderID = ? and RECIPIENTID = ?";
            prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, senderID);
            prepStatement.setInt(2, recipID);

            resultSet = prepStatement.executeQuery();

            System.out.println("\nAfter the insert, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3) + ", "
                        + resultSet.getString(4) + ", "
                        + resultSet.getString(5) + ", "
                        + resultSet.getString(6) + ", "
                        + resultSet.getString(7));
                counter++;
            }
            resultSet.close();
        } catch (Exception e) {

            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    //Send message to user
    public void sendMessageToGroup() {
        try {
            //initialize input variables for User and Friend info
            int senderID = 0;
            String firstNameSender = null;
            String lastNameSender = null;

            int groupID = 0;
            String groupName = null;

            int recipID = 0;

            String messageSubject = null;
            String messageBody = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the sender's first name: ");
                firstNameSender = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameSender == null || firstNameSender.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the sender's last name: ");
                lastNameSender = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameSender == null || lastNameSender.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameSender);
            prepStatement.setString(2, lastNameSender);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                senderID = resultSet.getInt(1);
            }

            // get the first name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter recipient group name: ");
                groupName = keyIn.nextLine().trim().toUpperCase();
            } while (groupName == null || groupName.equalsIgnoreCase(""));

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
            }

            // get the subject of the message
            do {
                System.out.println("Please enter your message subject: ");
                messageSubject = keyIn.nextLine().trim();
            } while (messageSubject == null || messageSubject.equalsIgnoreCase(""));

            // get the body of the message
            do {
                System.out.println("Please enter your message body: ");
                messageBody = keyIn.nextLine().trim();
            } while (messageBody == null || messageBody.equalsIgnoreCase(""));

            //query to get the users that are a part of the group 
            query = "SELECT USERID FROM GROUPMEMBERS WHERE GROUPID = ?";

            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, groupID);

            resultSet = prepStatement.executeQuery();

            //Insert statement for establishing pending friendship
            query = "INSERT INTO MESSAGES (SENDERID, SUBJECT, BODY, RECIPIENTID, GROUPID, DATECREATED) VALUES (?, ?, ?, ?, ?, current_timestamp)";

            //Create the prepared statement 
            //I tried to do a batch update, but had conflicts with
            //the trigger on the messages table when executing the batch of
            //updates, because the trigger is a before insert, in order to
            //determine the id for the message
            //going to do a batch update so turn autocommit off
//            connection.setAutoCommit(false);
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

            //execute the insert
            if (groupHasMember) {
                prepStatement.executeQuery();
                //prepStatement.executeBatch();
                //connection.commit();
                //connection.setAutoCommit(true);
            } else {
                System.out.println("The group you entered has no members");
                return;
            }

            //just a query to show that the row was inserted
            query = "select * from messages where senderid = ?";
            prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, senderID);

            resultSet = prepStatement.executeQuery();

            System.out.println("\nAfter the insert, data is...\n");
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
            resultSet.close();
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else if (e.getMessage().equals("No Group Found")) {
                System.out.println("The group name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    public void displayMessages() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String firstNameUser = null;
            String lastNameUser = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameUser == null || lastNameUser.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameUser);
            prepStatement.setString(2, lastNameUser);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d}", userID));

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

            System.out.println("\nMessages for the users are ...\n");
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
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    public void displayNewMessages() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String firstNameUser = null;
            String lastNameUser = null;
            Timestamp lastLogin = null;
            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameUser == null || lastNameUser.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameUser);
            prepStatement.setString(2, lastNameUser);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                userID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d}", userID));

            query = "SELECT LASTLOGIN FROM USERS WHERE ID = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setInt(1, userID);

            resultSet = prepStatement.executeQuery();

            //check result set to see whether last log in is null
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                lastLogin = resultSet.getTimestamp(1);
            }

            if (lastLogin == null) {
                //query if last login is null, just display all messages
                System.out.println("No previous login, displaying all messages");
                query = "SELECT U.FNAME AS FIRSTNAME,\n"
                        + "  U.LNAME AS LASTNAME,\n"
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

            System.out.println("\nMessages for the users are ...\n");
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
            resultSet.close();
        } catch (SQLException ex) {
            System.out.println(String.format("\nSQL Error: %s", ex.getMessage()));
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    // searches firstname, lastname, and email as applicable search fields,
    //breaks up search string by using regular regular expression on whitespace
    //runs querys for each of the search terms
    public void searchForUser() {
        try {
            //initialize input variables for User and Friend info
            int userID = 0;
            String searchString = null;
            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter string you would like to search (break up terms with spaces): ");
                searchString = keyIn.nextLine().trim().toLowerCase();
            } while (searchString == null || searchString.equalsIgnoreCase(""));

            String[] searchItems = searchString.split("\\s+");
            System.out.println("searchItems length = " + searchItems.length);

            //query that looks a firstname, lastname, and email as applicable search fields
            query = "select fname as firstname,\n"
                    + "lname as lastname,\n"
                    + "email as email\n"
                    + "from users\n"
                    + "where lower(fname) like ? \n"
                    + "or lower(lname) like ? \n"
                    + "or lower(email) like ?";

            prepStatement = connection.prepareStatement(query);

            System.out.println("\nSearch results ...\n");
            int counter = 1;
            for (int i = 0; i < searchItems.length; i++) {
                prepStatement.setString(1, "%" + searchItems[i] + "%");
                prepStatement.setString(2, "%" + searchItems[i] + "%");
                prepStatement.setString(3, "%" + searchItems[i] + "%");

                resultSet = prepStatement.executeQuery();

                while (resultSet.next()) {
                    System.out.println("Record " + counter + ": "
                            + resultSet.getString(1) + ", "
                            + resultSet.getString(2) + ", "
                            + resultSet.getString(3));
                    counter++;
                }
            }

            resultSet.close();
        } catch (SQLException ex) {
            System.out.println(String.format("\nSQL Error: %s", ex.getMessage()));
        } catch (Exception e) {
            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    public void threeDegrees() {
        try {
            //initialize input variables for User and Friend info
            int startID = 0;
            String firstNameStart = null;
            String lastNameStart = null;

            int endID = 0;
            String firstNameEnd = null;
            String lastNameEnd = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's first name: ");
                firstNameStart = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameStart == null || firstNameStart.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameStart = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameStart == null || lastNameStart.equalsIgnoreCase(""));

            //query to make sure user exists and get their ID
            query = "SELECT ID FROM USERS WHERE UPPER(FNAME) = ? AND UPPER(LNAME) = ?";

            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameStart);
            prepStatement.setString(2, lastNameStart);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                startID = resultSet.getInt(1);
            }

            // get the first name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's first name: ");
                firstNameEnd = keyIn.nextLine().trim().toUpperCase();
            } while (firstNameEnd == null || firstNameEnd.equalsIgnoreCase(""));

            // get the last name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's last name: ");
                lastNameEnd = keyIn.nextLine().trim().toUpperCase();
            } while (lastNameEnd == null || lastNameEnd.equalsIgnoreCase(""));

            //query to make sure friend exists and get their ID
            prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, firstNameEnd);
            prepStatement.setString(2, lastNameEnd);

            resultSet = prepStatement.executeQuery();

            //check if result set is empty and alert user, otherwise get the ID of the user
            if (!resultSet.next()) {
                throw new Exception("No User Found");
            } else {
                endID = resultSet.getInt(1);
            }

            // show user input (in form of ID's)
            System.out.println(String.format("ID of user: {%d} ID of friend: {%d}", startID, endID));

            //Insert statement for establishing pending friendship
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

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);
            //Set parameters of of insert statement
            prepStatement.setInt(1, startID);
            //execute the insert
            resultSet = prepStatement.executeQuery();
            LinkedList connections = new LinkedList();
            LinkedList currentPath = new LinkedList();
            currentPath.add(startID);
            int degrees = 1;
            int count = 0;
            while (true || !connections.isEmpty()) {
                if (resultSet.next()) {
                    if (resultSet.getInt(1) == endID) {
                        currentPath.add(resultSet.getInt(1));
                        break;
                        //completely found
                    } else if (!connections.contains(resultSet.getInt(1)) && resultSet.getInt(1) != startID) {
                        //add connection to list
                        connections.add(resultSet.getInt(1));
                    }
                    count++;
                }
                //if you are on the last row, remove the last user that did not 
                //work, grab a new userID and get all of their friends
                if (count == numRows && !connections.isEmpty()) {
                    if (currentPath.size() > 1) {
                        currentPath.removeLast();
                        degrees--;
                    }
                    int nextSearch = (int) connections.removeFirst();
                    //get the number of rows for this user
                    prepStatement = connection.prepareStatement(countQuery);
                    prepStatement.setInt(1, nextSearch);
                    resultSet = prepStatement.executeQuery();
                    while (resultSet.next()) {
                        numRows = resultSet.getInt(1);
                    }
                    count = 0;
                    //get the list of friendIDs for the next user
                    prepStatement = connection.prepareStatement(query);
                    prepStatement.setInt(1, nextSearch);
                    resultSet = prepStatement.executeQuery();
                    currentPath.add(nextSearch);

                    degrees++;
                } else if (count == numRows && connections.isEmpty()) {
                    break;
                    //no dice
                }
                if (degrees > 3) {
                    break;
                    //too many degrees
                }
            }

            System.out.println("\nPath for three degrees is ... ");
            for (int i = 0; i < currentPath.size(); i++) {
                System.out.println(currentPath.get(i));
            }
            resultSet.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            switch (errorCode) {
                case 20001:
                    System.out.println("Friendship already pending");
                    break;
                case 20002:
                    System.out.println("Friendship already established");
                    break;
                default:
                    System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
                    break;
            }
        } catch (Exception e) {

            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    // Method to display the top messagers for the past k months and display x results
    // current query treats all messages equally (aka no special consideration for
    // group messages) So if a user sends 1 message to a group of 10 people, that
    // would count as them sending 10 messages
    public void topMessagers() {
        try {
            //initialize input variables for User and Friend info
            int numMonths = 0;
            int numResults = 0;
            String numberOfMonths = null;
            String numberOfResults = null;

            // create a scanner to get user input
            Scanner keyIn = new Scanner(System.in);

            // get the first name of the user and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the number of months: ");
                numberOfMonths = keyIn.nextLine().trim();
            } while (numberOfMonths == null || numberOfMonths.equalsIgnoreCase("") || !Pattern.matches("\\d+", numberOfMonths));

            numMonths = Integer.parseInt(numberOfMonths);
            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the number of results you would like to see: ");
                numberOfResults = keyIn.nextLine().trim().toUpperCase();
            } while (numberOfResults == null || numberOfResults.equalsIgnoreCase("") || !Pattern.matches("\\d+", numberOfResults));

            numResults = Integer.parseInt(numberOfResults);
            //query to make sure user exists and get their ID
            query = "SELECT U.FNAME, U.LNAME, M.MESSAGE_COUNT FROM\n"
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

            System.out.println("\nAfter the insert, data is...\n");
            int counter = 1;
            while (resultSet.next()) {
                System.out.println("Record " + counter + ": "
                        + resultSet.getString(1) + ", "
                        + resultSet.getString(2) + ", "
                        + resultSet.getString(3));
                counter++;
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(String.format("\nSQL Error: %s", e.getMessage()));
        } catch (Exception e) {

            if (e.getMessage().equals("No User Found")) {
                System.out.println("The user name you entered does not exist");
            } else {
                e.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (prepStatement != null) {
                    prepStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: " + e.getMessage());
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
