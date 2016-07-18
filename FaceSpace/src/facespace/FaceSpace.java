package facespace;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Kyle Monto (kwm19@pitt.edu) | Joe Meszar (jwm54@pitt.edu)
 *
 * @see <a href="https://github.com/outkst/CS1555TermProject">https://github.com/outkst/CS1555TermProject</a>
 */
public class FaceSpace {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to FaceSpaces!");
        DatabaseConnection db = null;
        try {
            db = new DatabaseConnection();
            String input = "";
            while (!input.equals("Q")) {
                System.out.println("What would you like to do? (Enter q to exit)\n"
                        + "A - Create a User \t\t\t H - Send Group a Message \t\t\t O - Log in User \n"
                        + "B - Initiate a friendship \t\t I - Display Messages for User \n"
                        + "C - Establish a friendship \t\t J - Display New Messages for User\n"
                        + "D - Display User's friends \t\t K - Search for User\n"
                        + "E - Create a Group \t\t\t L - Three Degrees Between Users\n"
                        + "F - Add User to Group \t\t\t M - Top Messagers\n"
                        + "G - Send User a Message \t\t N - Drop a User\n");
                Scanner keyIn = new Scanner(System.in);
                input = keyIn.next().toUpperCase();
                switch (input) {
                    case "A":
                        System.out.println("createUser");
                        db.createUser();
                        break;
                    case "B":
                        System.out.println("initiateFriendship");
                        db.initiateFriendship();
                        break;
                    case "C":
                        System.out.println("establishFriendship");
                        db.establishFriendship();
                        break;
                    case "D":
                        System.out.println("displayFriends");
                        db.displayFriends();
                        break;
                    case "E":
                        System.out.println("createGroup");
                        db.createGroup();
                        break;
                    case "F":
                        System.out.println("addToGroup");
                        db.addToGroup();
                        break;
                    case "G":
                        System.out.println("establishFriendship");
//                sendMessageToUser();
                        break;
                    case "H":
                        System.out.println("sendMessageToGroup");
//                sendMessageToGroup();
                        break;
                    case "I":
                        System.out.println("displayMessages");
//                displayMessages();
                        break;
                    case "J":
                        System.out.println("displayNewMessages");
//                displayNewMessages();
                        break;
                    case "K":
                        System.out.println("searchForUser");
//                searchForUser();
                        break;
                    case "L":
                        System.out.println("threeDegrees");
//                threeDegrees();
                        break;
                    case "M":
                        System.out.println("topMessagers");
//                topMessagers();
                        break;
                    case "N":
                        System.out.println("dropUser");
//                dropUser();
                        break;
                    case "O":
                        System.out.println("logInUser");
                        db.logInUser();
                }
            }
        } catch (SQLException sQLException) {

        } finally {
            System.out.println("closing connection");
             db.closeConnection();
        }
    }
}
