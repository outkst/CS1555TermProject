package facespace;

import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        String username;
        String password;

        // create a scanner to get user input
        Scanner keyIn = new Scanner(System.in);

        // get the username and password
        System.out.println("Please enter DB username: ");
        username = keyIn.next().toLowerCase();
        System.out.println("Please enter DB password: ");
        password = keyIn.next();

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
                firstName = keyIn.next().trim().toUpperCase();
            } while (firstName == null || firstName.equalsIgnoreCase(""));

            // get the lastName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a last name:");
                lastName = keyIn.next().trim().toUpperCase();
            } while (lastName == null || lastName.equalsIgnoreCase(""));

            // get a valid email and normalize (lowercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a valid email address: ");
                email = keyIn.next().trim().toLowerCase();
            } while (email == null || email.equalsIgnoreCase("") || !Pattern.matches("^([a-zA-Z0-9]+([\\.+_-][a-zA-Z0-9]+)*)@(([a-zA-Z0-9]+((\\.|[-]{1,2})[a-zA-Z0-9]+)*)\\.[a-zA-Z]{2,6})$", email));

            // get the date of birth (DOB) and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Enter a valid date of birth (DD-MON-YY): ");
                dateOfBirth = keyIn.next().trim().toUpperCase();
            } while (dateOfBirth == null || dateOfBirth.equalsIgnoreCase("") || !Pattern.matches("[0123]{1}\\d{1}-\\w{3}-\\d{2}", dateOfBirth));

            // show the user input
            System.out.println(String.format("First Name: {%s} LastName: {%s} Email: {%s} DOB: {%s}", firstName, lastName, email, dateOfBirth));

            //Insert query statement
            query = "INSERT INTO USERS(FNAME, LNAME, EMAIL, DOB, LASTLOGIN, DATECREATED) VALUES (?, ?, ?, TO_DATE(?,'DD-MON-YY'), NULL, current_timestamp)";

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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
                firstNameUser = keyIn.next().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.next().trim().toUpperCase();
            } while (lastNameUser.equalsIgnoreCase("") || lastNameUser == null);

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
                firstNameFriend = keyIn.next().trim().toUpperCase();
            } while (firstNameFriend == null || firstNameFriend.equalsIgnoreCase(""));

            // get the last name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's last name: ");
                lastNameFriend = keyIn.next().trim().toUpperCase();
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
                    e.printStackTrace();
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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
                firstNameUser = keyIn.next().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.next().trim().toUpperCase();
            } while (lastNameUser.equalsIgnoreCase("") || lastNameUser == null);

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
                firstNameFriend = keyIn.next().trim().toUpperCase();
            } while (firstNameFriend == null || firstNameFriend.equalsIgnoreCase(""));

            // get the last name of the friend and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter friend's last name: ");
                lastNameFriend = keyIn.next().trim().toUpperCase();
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
                    e.printStackTrace();
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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
                firstNameUser = keyIn.next().trim().toUpperCase();
            } while (firstNameUser == null || firstNameUser.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastNameUser = keyIn.next().trim().toUpperCase();
            } while (lastNameUser.equalsIgnoreCase("") || lastNameUser == null);

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
            int errorCode = e.getErrorCode();
            e.printStackTrace();
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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
                limitS = keyIn.next().trim();

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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
                firstName = keyIn.next().trim().toUpperCase();
            } while (firstName == null || firstName.equalsIgnoreCase(""));

            // get the lastName and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter a last name:");
                lastName = keyIn.next().trim().toUpperCase();
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
                    e.printStackTrace();
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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
                firstName = keyIn.next().trim().toUpperCase();
            } while (firstName == null || firstName.equalsIgnoreCase(""));

            //get the lastName of User and normalize (uppercase with no leading/trailing spaces)
            do {
                System.out.println("Please enter the user's last name: ");
                lastName = keyIn.next().trim().toUpperCase();
            } while (lastName.equalsIgnoreCase("") || lastName == null);

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
                System.out.println("Cannot close Statement. Machine error: " + e.toString());
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
