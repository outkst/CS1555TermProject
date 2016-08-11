package facespace;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Kyle Monto (kwm19@pitt.edu) | Joe Meszar (jwm54@pitt.edu)
 *
 * @see
 * <a href="https://github.com/outkst/CS1555TermProject">https://github.com/outkst/CS1555TermProject</a>
 */
public class FaceSpace {
    private static Scanner keyIn;
    
    /**
     * @param args the command line arguments (none)
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to FaceSpaces!");
        DatabaseConnection db = null;
        String url = "", username = "", password = "";
        String firstName = "", lastName = "", userEmail = "", dateOfBirth = "", groupName = "", groupDescription = "";
        String friendEmail = "";
        int membershipLimit = 0;
        
        try {
            // start by getting the username and password
            //      for the database connection
            keyIn = new Scanner(System.in);
            
            // get the database url (default to Pitt db if blank)
            System.out.print("Please enter the URL of the database (leave blank to use Pitt's db): ");
            //url = keyIn.nextLine().toLowerCase();
            if (url.equals("")) { url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass"; }
            
            // DEBUG INFO
            url = "jdbc:oracle:thin:@localhost:1521:xe";
            username = "system";
            password = "poiu0987";
            
            // get the username
            //System.out.print("Please enter the DB username: ");
            //username = keyIn.nextLine();
            
            // get the password
            //System.out.print("Please enter the DB password: ");
            //password = keyIn.nextLine();
            
            // create the database connection instance.
            try {
                db = new DatabaseConnection(url, username, password);
            } catch (SQLException e) {
                printSQLException(e);
                System.exit(0); // EXIT IMMEDIATELY IF CONNECTION NOT ESTABLISHED
            }
            
            int input = -1;
            while (input != 0) {
                // show menu and get user input
                System.out.print("\n\nWHAT WOULD YOU LIKE TO DO? (ENTER 0 TO EXIT)\n\n"
                        + "1 - ADD TO GROUP\t\t7 - DROP USER\t\t\t13 - SEARCH FOR USER\n"
                        + "2 - CREATE GROUP\t\t8 - ESTABLISH FRIENDSHIP\t14 - SEND MESSAGE TO GROUP\n"
                        + "3 - CREATE USER\t\t\t9 - INITIATE FRIENDSHIP\t\t15 - SEND MESSAGE TO USER\n"
                        + "4 - DISPLAY FRIENDS\t\t10 - LIST ALL GROUPS\t\t16 - THREE DEGREES\n"
                        + "5 - DISPLAY MESSAGES\t\t11 - LIST ALL USERS\t\t17 - TOP MESSAGERS\n"
                        + "6 - DISPLAY NEW MESSAGES\t12 - LOG IN USER\n"
                        + "\nEnter command: ");
                input = tryParseInt(keyIn.nextLine()); // returns -1 if bad input
                
                switch (input) {
                    case 0: System.out.println("\nGOODBYE!");
                        break;
                        
                    case 1: System.out.println("\n[ADD TO GROUP]");
                        try {
                            userEmail = getUserEmail("Please enter the user's email address: ");
                            groupName = getUserString("Please enter the name of the group to join: ");

                            db.addToGroup(userEmail, groupName);
                            
                        } catch (SQLException e) {
                            switch (e.getErrorCode()) {
                                case 20000: System.out.println("Group Membership limit is already met"); break;
                                default: printSQLException(e);
                            }
                        }
                        break;
                        
                    case 2: System.out.println("\n[CREATE GROUP]");
                        groupName = getUserString("Please enter a group name: ");
                        groupDescription = getUserString("Please enter a description for the group: ");
                        membershipLimit = getUserNumber("Please enter a membership limit: ");

                        db.createGroup(groupName, groupDescription, membershipLimit);
                        break;
                        
                    case 3: System.out.println("\n[CREATE USER]");
                        firstName = getUserString("Please enter a first name: ");
                        lastName = getUserString("Please enter a last name: ");
                        userEmail = getUserEmail("Please enter a valid email address: ");
                        dateOfBirth = getUserDate("Enter a valid date of birth (DD-MON-YYYY): ");

                        db.createUser(firstName, lastName, userEmail, dateOfBirth);
                        break;
                        
                    case 4: System.out.println("\n[DISPLAY FRIENDS]");
                        userEmail = getUserEmail("Please enter the user's email address: ");
                        
                        db.displayFriends(userEmail);
                        break;
                        
                    case 5: System.out.println("\n[DISPLAY MESSAGES]");
                        db.displayMessages();
                        break;
                        
                    case 6: System.out.println("\n[DISPLAY NEW MESSAGES]");
                        db.displayNewMessages();
                        break;
                        
                    case 7: System.out.println("\n[DROP USER]");
                        db.dropUser();
                        break;
                        
                    case 8: System.out.println("\n[ESTABLISH FRIENDSHIP]");
                        db.establishFriendship();
                        break;
                    
                    case 9: System.out.println("\n[INITIATE FRIENDSHIP]");
                        db.initiateFriendship();
                        break;
                        
                    case 10: System.out.println("\n[LIST ALL GROUPS]");
                        db.listAllGroups();
                        break;
                        
                    case 11: System.out.println("\n[LIST ALL USERS]");
                        db.listAllUsers();
                        break;
                        
                    case 12: System.out.println("\n[LOG IN USER]");
                        db.logInUser();
                        break;
                        
                    case 13: System.out.println("\n[SEARCH FOR USER]");
                        db.searchForUser();
                        break;
                        
                    case 14: System.out.println("\n[SEND MESSAGE TO GROUP]");
                        db.sendMessageToGroup();
                        break;
                        
                    case 15: System.out.println("\n[SEND MESSAGE TO USER]");
                        db.sendMessageToUser();
                        break;
                        
                    case 16: System.out.println("\n[THREE DEGREES]");
                        db.threeDegrees();
                        break;
                        
                    case 17: System.out.println("\n[TOP MESSAGERS]");
                        db.topMessagers();
                        break;
                        
                    default: System.out.println("INVALID INPUT");
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
            
        } catch (Exception e) {
            System.out.println(String.format("\n!! Error: %s", e.getMessage()));
            
        } finally {
            if (db != null) { 
                try {
                    System.out.println("\nClosing connection...");
                    db.closeConnection();    
                } catch (SQLException e) { printSQLException(e); }
            }
        }
    }
    
    
    public static void printSQLException(SQLException e) {
        try {
            while (e != null) {
                int errorCode = e.getErrorCode();
                String sqlState = e.getSQLState();
                String errorMessage = e.getMessage();
                
                // Gets the error number associated with the exception.
                System.err.println("ERROR CODE: " + errorCode);
                
                // Gets the XOPEN SQLstate string. For a JDBC driver error, 
                //  no useful information is returned from this method. For 
                //  a database error, the five-digit XOPEN SQLstate code is 
                //  returned. This method can return null.
                if (sqlState == null || sqlState.isEmpty()) { sqlState = "(empty)"; }
                System.err.println("SQL STATE: " + sqlState.trim().replaceAll("\n", " "));
                
                // Gets the JDBC driver's error message for an error, handled by the driver
                //  or gets the Oracle error number and message for a database error.
                if (errorMessage == null || errorMessage.isEmpty()) { errorMessage = "(empty)"; }
                System.err.println("SQL ERROR MESSAGE: " + errorMessage.trim().replaceAll("\n", " "));

                //Gets the next Exception object in the exception chain.
                e = e.getNextException();
            }   
        } catch (Exception Ex) {
            System.out.println(String.format("\n!! Error: %s", Ex.getMessage()));
        }
    }
    

    /**
     * Gets a valid date from the user input. Normalized to uppercase.
     * 
     * @param message The message to display to the user before getting the input.
     * @return A valid, normalized, date (uppercase, no leading/trailing spaces).
     */
    public static String getUserDate(String message) {
        String userDate;
        do {
            System.out.print(message);
            userDate = keyIn.nextLine().trim().toUpperCase();
        } while (userDate == null || userDate.equalsIgnoreCase("") || !Pattern.matches("[0123]{1}\\d{1}-\\w{3}-\\d{4}", userDate));
        
        return userDate;
    }
    
    
    /**
     * Gets a string from the user input. Normalized to uppercase.
     * 
     * @param message The message to display to the user before getting the input.
     * @return A non-null, non-empty String value (no leading/trailing spaces).
     */
    public static String getUserString(String message) {
        String userString;
        do {
            System.out.print(message);
            userString = keyIn.nextLine().trim().toUpperCase();
        } while (userString == null || userString.equalsIgnoreCase(""));
        
        return userString;
    }
    
    
    /**
     * Gets a valid email address from the user input. Normalized to lowercase.
     * 
     * @param message The message to display to the user before getting the input.
     * @return A valid, normalized, email address (lowercase, no leading/trailing spaces).
     */
    public static String getUserEmail(String message) {
        String userEmail;
        do {
            System.out.print(message);
            userEmail = keyIn.nextLine().trim().toLowerCase();
        } while (userEmail == null || userEmail.equalsIgnoreCase("") || !Pattern.matches("^([a-zA-Z0-9]+([\\.+_-][a-zA-Z0-9]+)*)@(([a-zA-Z0-9]+((\\.|[-]{1,2})[a-zA-Z0-9]+)*)\\.[a-zA-Z]{2,6})$", userEmail));
        
        return userEmail;
    }
    
    
    /**
     * Gets a number from the user input.
     * 
     * @param message The message to display to the user before getting the input.
     * @return A valid integer value.
     */
    public static int getUserNumber(String message) {
        String userNumber;
        do {
            System.out.print(message);
            userNumber = keyIn.nextLine().trim();
        } while (!Pattern.matches("\\d+", userNumber));
        
        return tryParseInt(userNumber);
    }
    
    
    /**
     * Parses an integer value from the given String.
     * 
     * @param value A string value from which to get an integer value.
     * @return The value of the parsed integer from the string; otherwise -1.
     */
    public static int tryParseInt(String value) {  
        int num;
        try {  
            num = Integer.parseInt(value);  
            return num;
         } catch (NumberFormatException e) {  
            return -1;  
         }  
    }
}
