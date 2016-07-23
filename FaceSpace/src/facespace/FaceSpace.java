package facespace;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Kyle Monto (kwm19@pitt.edu) | Joe Meszar (jwm54@pitt.edu)
 *
 * @see
 * <a href="https://github.com/outkst/CS1555TermProject">https://github.com/outkst/CS1555TermProject</a>
 */
public class FaceSpace {

    /**
     * @param args the command line arguments (none)
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to FaceSpaces!");
        DatabaseConnection db = null;
        try {
            db = new DatabaseConnection();
            String input = "";
            while (!input.equals("Z")) {
                System.out.print("\nWhat would you like to do? (Enter Z to exit)\n"
                        + "A - Create a User \t\t\t H - Send Group a Message \t\t\t O - Log in User \n"
                        + "B - Initiate a friendship \t\t I - Display Messages for User \t\t\t P - List All Users \n"
                        + "C - Establish a friendship \t\t J - Display New Messages for User \t\t Q - List All Groups \n"
                        + "D - Display User's friends \t\t K - Search for User\n"
                        + "E - Create a Group \t\t\t L - Three Degrees Between Users\n"
                        + "F - Add User to Group \t\t\t M - Top Messagers\n"
                        + "G - Send User a Message \t\t N - Drop a User\n"
                        + "\nEnter command: ");
                Scanner keyIn = new Scanner(System.in);
                input = keyIn.next().toUpperCase();
                switch (input) {
                    case "A":
                        System.out.println("\n[CREATE USER]");
                        db.createUser();
                        break;
                    case "B":
                        System.out.println("\n[INITIATE FRIENDSHIP]");
                        db.initiateFriendship();
                        break;
                    case "C":
                        System.out.println("\n[ESTABLISH FRIENDSHIP]");
                        db.establishFriendship();
                        break;
                    case "D":
                        System.out.println("\n[DISPLAY FRIENDS]");
                        db.displayFriends();
                        break;
                    case "E":
                        System.out.println("\n[CREATE GROUP]");
                        db.createGroup();
                        break;
                    case "F":
                        System.out.println("\n[ADD TO GROUP]");
                        db.addToGroup();
                        break;
                    case "G":
                        System.out.println("\n[SEND MESSAGE TO USER]");
                        db.sendMessageToUser();
                        break;
                    case "H":
                        System.out.println("\n[SEND MESSAGE TO GROUP]");
                        db.sendMessageToGroup();
                        break;
                    case "I":
                        System.out.println("\n[DISPLAY MESSAGES]");
                        db.displayMessages();
                        break;
                    case "J":
                        System.out.println("\n[DISPLAY NEW MESSAGES]");
                        db.displayNewMessages();
                        break;
                    case "K":
                        System.out.println("\n[SEARCH FOR USER]");
                        db.searchForUser();
                        break;
                    case "L":
                        System.out.println("\n[THREE DEGREES]");
                        db.threeDegrees();
                        break;
                    case "M":
                        System.out.println("\n[TOP MESSAGERS]");
                        db.topMessagers();
                        break;
                    case "N":
                        System.out.println("\n[DROP USER]");
                        //dropUser();
                        break;
                    case "O":
                        System.out.println("\n[LOG IN USER]");
                        db.logInUser();
                        break;
                    case "P":
                        System.out.println("\n[LIST ALL USERS]");
                        db.listAllUsers();
                        break;
                    case "Q":
                        System.out.println("\n[LIST ALL GROUPS]");
                        db.listAllGroups();
                        break;
                    default:
                        System.out.println("INVALID INPUT");
                }
            }
        } catch (SQLException e) {

        } finally {
            System.out.println("\nClosing connection...");
            if (db != null) {
                db.closeConnection();
            }
            System.out.println("\nGoodbye!");
        }
    }
}