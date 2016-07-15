/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facespace;

import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author kylemonto
 */
public class DatabaseConnection {

    private static Connection connection; //used to hold the jdbc connection to the DB
    private Statement statement; //used to create an instance of the connection
    private PreparedStatement prepStatement; //used to create a prepared statement, that will be later reused
    private ResultSet resultSet; //used to hold the result of your query (if one
    // exists)
    private String query;  //this will hold the query we are using

    public DatabaseConnection() throws SQLException {

        //to run in netbeans need to add ojbdc6.jar to project libraries
        String username = "";
        String password = "";

        Scanner keyIn = new Scanner(System.in);
        System.out.println("Please enter DB username: ");
        username = keyIn.next().toLowerCase();
        System.out.println("Please enter DB password: ");
        password = keyIn.next();
        try {
            // Register the oracle driver.  
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            //This is the location of the database.  This is the database in oracle
            //provided to the class
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

            //create a connection to DB on class3.cs.pitt.edu
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception Ex) {
            System.out.println("Error connecting to database.  Machine Error: "
                    + Ex.toString());
        } finally {
            /*
             * NOTE: the connection should be created once and used through out the whole project;
             * Is very expensive to open a connection therefore you should not close it after every operation on database
             */
            //Moved the connection to its only method and is inside of a finally block 
            //in the main program
//		connection.close();
        }

    }

    public void createUser() {
        try {
            //initialize input variables
            String firstName = "";
            String lastName = "";
            String email = "";
            String dateOfBirth = "";

            //get the firstName
            Scanner keyIn = new Scanner(System.in);
            System.out.println("Please enter a first name: ");
            firstName = keyIn.next().trim();
            while (firstName.equalsIgnoreCase("") || firstName == null || !Pattern.matches("[a-zA-Z]+", firstName)) {
                System.out.println("Please enter a first name:");
                firstName = keyIn.next().trim();
            }
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();

            //get the lastName
            System.out.println("Please enter a last name: ");
            lastName = keyIn.next().trim();
            while (lastName.equalsIgnoreCase("") || lastName == null || !Pattern.matches("[a-zA-Z]+", lastName)) {
                System.out.println("Please enter a last name:");
                lastName = keyIn.next().trim();
            }
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

            //get the Email
            System.out.println("Please enter an email: ");
            email = keyIn.next().trim();
            while (email.equalsIgnoreCase("") || email == null || !Pattern.matches("\\w+@\\w+\\.\\w+", email)) {
                System.out.println("Invalid email please re-enter: ");
                email = keyIn.next().trim();
            }

            //get the DOB
            System.out.println("Please enter a date of birth DD-MON-YY: ");
            dateOfBirth = keyIn.next().trim().toUpperCase();
            while (dateOfBirth.equalsIgnoreCase("") || dateOfBirth == null || !Pattern.matches("[0123]{1}\\d{1}-\\w{3}-\\d{2}", dateOfBirth)) {
                System.out.println("Invalid dateOfBirth please re-enter (DD-MON-YY): ");
                dateOfBirth = keyIn.next().trim().toUpperCase();
            }
            System.out.println("First Name: " + firstName + " LastName: " + lastName + " email: " + email + " DOB" + dateOfBirth);

            //Insert query statement
            query = "INSERT INTO USERS(FNAME, LNAME, EMAIL, DOB, LASTLOGIN, DATECREATED) VALUES (?, ?, ?, TO_DATE(?,'DD-MON-YY'), NULL, current_timestamp)";

            //Create the prepared statement
            prepStatement = connection.prepareStatement(query);

            //Set parameters of of insert statement
            prepStatement.setString(1, firstName);
            prepStatement.setString(2, lastName);
            prepStatement.setString(3, email);
            prepStatement.setString(4, dateOfBirth);

            //execute the insert
            prepStatement.executeUpdate();

            query = "Select * FROM USERS";

            statement = connection.createStatement(); //create an instance

            resultSet = statement.executeQuery(query);

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

    public void initiateFriendship() {
        try {
            //initialize input variables
            String firstNameUser = "";
            String lastNameUser = "";
            int userID = 0;
            String firstNameFriend = "";
            String lastNameFriend = "";
            int friendID = 0;

            //get the firstName of User
            Scanner keyIn = new Scanner(System.in);
            System.out.println("Please enter the first name of the first user: ");
            firstNameUser = keyIn.next().trim();
            while (firstNameUser.equalsIgnoreCase("") || firstNameUser == null || !Pattern.matches("[a-zA-Z]+", firstNameUser)) {
                System.out.println("Please enter a first name:");
                firstNameUser = keyIn.next().trim();
            }
            firstNameUser = firstNameUser.substring(0, 1).toUpperCase() + firstNameUser.substring(1).toLowerCase();

            //get the lastName of User
            System.out.println("Please enter a last name: ");
            lastNameUser = keyIn.next().trim();
            while (lastNameUser.equalsIgnoreCase("") || lastNameUser == null || !Pattern.matches("[a-zA-Z]+", lastNameUser)) {
                System.out.println("Please enter a last name:");
                lastNameUser = keyIn.next().trim();
            }
            lastNameUser = lastNameUser.substring(0, 1).toUpperCase() + lastNameUser.substring(1).toLowerCase();

            //query to make sure user exists and get there ID
            query = "SELECT ID FROM USERS WHERE FNAME = ? AND LNAME = ?";

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

            //get the firstName of Friend
            System.out.println("Please enter the first name of the second user: ");
            firstNameFriend = keyIn.next().trim();
            while (firstNameFriend.equalsIgnoreCase("") || firstNameFriend == null || !Pattern.matches("[a-zA-Z]+", firstNameFriend)) {
                System.out.println("Please enter a first name:");
                firstNameFriend = keyIn.next().trim();
            }
            firstNameFriend = firstNameFriend.substring(0, 1).toUpperCase() + firstNameFriend.substring(1).toLowerCase();

            //get the lastName of Friend
            System.out.println("Please enter a last name: ");
            lastNameFriend = keyIn.next().trim();
            while (lastNameFriend.equalsIgnoreCase("") || lastNameFriend == null || !Pattern.matches("[a-zA-Z]+", lastNameFriend)) {
                System.out.println("Please enter a last name:");
                lastNameFriend = keyIn.next().trim();
            }
            lastNameFriend = lastNameFriend.substring(0, 1).toUpperCase() + lastNameFriend.substring(1).toLowerCase();

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

            System.out.println("ID of user: " + userID + " ID of friend: " + friendID);

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
            if (errorCode == 20001) {
                System.out.println("Friendship already pending");
            } else if (errorCode == 20002) {
                System.out.println("Friendship already established");
            } else {
                e.printStackTrace();
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
