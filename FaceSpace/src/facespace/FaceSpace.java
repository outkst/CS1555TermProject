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
            int input = 0;
            while (input != 18) {
                System.out.print("\nWhat would you like to do? (Enter 18 to exit)\n"
                        + "1 - Create a User \t\t\t 8 - Send Group a Message \t\t\t 15 - Log in User \n"
                        + "2 - Initiate a friendship \t\t 9 - Display Messages for User \t\t\t 16 - List All Users \n"
                        + "3 - Establish a friendship \t\t 10 - Display New Messages for User \t\t 17 - List All Groups \n"
                        + "4 - Display User's friends \t\t 11 - Search for User\n"
                        + "5 - Create a Group \t\t\t 12 - Three Degrees Between Users\n"
                        + "6 - Add User to Group \t\t\t 13 - Top Messagers\n"
                        + "7 - Send User a Message \t\t 14 - Drop a User\n"
                        + "\nEnter command: ");
                Scanner keyIn = new Scanner(System.in);
                input = keyIn.nextInt();
                switch (input) {
                    case 1:
                        System.out.println("\n[CREATE USER]");
                        db.createUser();
                        break;
                    case 2:
                        System.out.println("\n[INITIATE FRIENDSHIP]");
                        db.initiateFriendship();
                        break;
                    case 3:
                        System.out.println("\n[ESTABLISH FRIENDSHIP]");
                        db.establishFriendship();
                        break;
                    case 4:
                        System.out.println("\n[DISPLAY FRIENDS]");
                        db.displayFriends();
                        break;
                    case 5:
                        System.out.println("\n[CREATE GROUP]");
                        db.createGroup();
                        break;
                    case 6:
                        System.out.println("\n[ADD TO GROUP]");
                        db.addToGroup();
                        break;
                    case 7:
                        System.out.println("\n[SEND MESSAGE TO USER]");
                        db.sendMessageToUser();
                        break;
                    case 8:
                        System.out.println("\n[SEND MESSAGE TO GROUP]");
                        db.sendMessageToGroup();
                        break;
                    case 9:
                        System.out.println("\n[DISPLAY MESSAGES]");
                        db.displayMessages();
                        break;
                    case 10:
                        System.out.println("\n[DISPLAY NEW MESSAGES]");
                        db.displayNewMessages();
                        break;
                    case 11:
                        System.out.println("\n[SEARCH FOR USER]");
                        db.searchForUser();
                        break;
                    case 12:
                        System.out.println("\n[THREE DEGREES]");
                        db.threeDegrees();
                        break;
                    case 13:
                        System.out.println("\n[TOP MESSAGERS]");
                        db.topMessagers();
                        break;
                    case 14:
                        System.out.println("\n[DROP USER]");
                        db.dropUser();
                        break;
                    case 15:
                        System.out.println("\n[LOG IN USER]");
                        db.logInUser();
                        break;
                    case 16:
                        System.out.println("\n[LIST ALL USERS]");
                        db.listAllUsers();
                        break;
                    case 17:
                        System.out.println("\n[LIST ALL GROUPS]");
                        db.listAllGroups();
                        break;
                    case 18:
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
