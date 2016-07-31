package facespace;

import java.sql.SQLException;
import java.text.ParseException;
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
        
        if (args.length < 1) {
            throw new IllegalArgumentException("\n\nPlease provide necessary parameters:\n"
                    + "\t-user \tRun the program in user mode\n"
                    + "\t-test \tRun the program in test mode\n"
                    + "\nEXAMPLE: java facespace.FaceSpace -user\n");
        }
        
        if (args[0].equals("-user")) {
            System.out.println("user");
            try {
                db = new DatabaseConnection();
                int input = -1;
                while (input != 0) {
                    System.out.print("\nWhat would you like to do? (Enter 0 to exit)\n"
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
                        case 0:
                            break;
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
        } else if (args[0].equals("-test")) {
            System.out.println("testing");
            
            try {
                db = new DatabaseConnection();
                testingFunction(db);
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
                
            } finally {
                System.out.println("\nClosing connection...");
                if (db != null) {
                    db.closeConnection();
                }
                System.out.println("\nGoodbye!");
            }
        }
    }

    private static void testingFunction(DatabaseConnection db) throws ParseException {
        //testing createUser
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION createUser CREATING 50 NEW USERS]");
        //general case
        createNewUsersTest(db);
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION listAllUsers DISPLAYING ALL USERS]\n");
        db.listAllUsers();
        //edge cases
        //create user that already exists
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION createUser CREATING USER THAT ALREADY EXISTS]");
        db.createUserTest("JASON", "TOMEI", "jtomei@cs1555.com", "08-MAY-1995");

        
        //testing initiate friendship
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION initiateFriendship INITIATING 10 FRIENDSHIPS]");
        //general case
        initiateFriendships(db);
        //edge cases
        //initiate the same friendship
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION initiateFriendship TRYING TO INITIATE FRIENDSHIP THAT ALREADY IS PENDING]");
        db.initiateFriendshipTest("esheeran@cs1555.com", "saguillon@cs1555.com");
        //initiate friendship with self
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION initiateFriendship TRYING TO INITIATE FRIENDSHIP WITH SELF]");
        db.initiateFriendshipTest("esheeran@cs1555.com", "esheeran@cs1555.com");
        //later try to initiate an established friendship

        
        //testing establish friendship
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION establishFriendship ESTABLISHING 10 FRIENDSHIPS]");
        //general case
        establishFriendship(db);
        //edge cases
        //establish a friendship that already exists
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION establishFriendship TRYING TO ESTABLISH FRIENDSHIP THAT ALREADY IS ESTABLISHED]");
        db.establishFriendshipTest("saguillon@cs1555.com", "esheeran@cs1555.com");
        //establish friendship with self
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION initiateFriendship TRYING TO ESTABLISH FRIENDSHIP WITH SELF]");
        db.initiateFriendshipTest("esheeran@cs1555.com", "esheeran@cs1555.com");

        
        //testing display friends
        //general case
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION displayFriends DISPLAYING FRIENDS FOR Jason Tomei]");
        db.displayFriendsTest("jtomei@cs1555.com");

        
        //testing create group
        //general case
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION createGroup CREATING 5 GROUPS]");
        createGroups(db);
        db.listAllGroups();
        //edge case
        //create group that already exists
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION createGroup CREATING GROUP THAT ALREADY EXISTS]");
        db.createGroupTest("GROUP1", "DESCRIPTION1", "1");
        //create group with negative or zero membership
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION createGroup CREATING GROUP WITH INVALID MEMBERSHIP LIMIT '-1']");
        db.createGroupTest("GROUP6", "DESCRIPTION6", "-1");
        db.listAllGroups();
        
        
        //add users to capacity of group
        //testing addToGroup
        //general case
        //add 10 users to group
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION addUserToGroup ADDING 10 USERS TO GROUPS]");
        usersToGroups(db);
        
        //max out membership limit
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION addUserToGroup ATTEMPT TO ADD USER TO FULL GROUP]");
        db.addToGroupTest("saguillon@cs1555.com", "GROUP1");

        
        //testing sendMessageToUser
        //general case
        //send 15 messages
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION sendMessageToUser SENDING 15 MESSAGES USER-TO-USER]");
        sendMessagesUser(db);
        //edge cases
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION sendMessageToUser SENDING 1 MESSAGE USER-TO-USER TO NON-EXISTENT USER]");
        db.sendMessageToUserTest("joe@joe.com", "nonexistent@noexist.com", "This is a test subject", "This message will not be sent");
        
        //testing sendMessageToGroup
        //general case
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION sendMessageToGroup SENDING 5 MESSAGES USER-TO-GROUP]");
        sendMessagesGroup(db);
        //edge cases

        
        //testing displayMessages
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION displayMessages DISPLAYING MESSAGES FOR 'Elaine Sheeran']");
        db.displayMessagesTest("esheeran@cs1555.com");

        //login elaine sheeran
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION logInUser LOGGING IN USER 'Elaine Sheeran']");
        db.logInUserTest("esheeran@cs1555.com");
        
        //send new message to elaine sheeran
        db.sendMessageToUserTest("saguillon@cs1555.com", "esheeran@cs1555.com", "NEW MESSAGE", "New message since you logged in");

        
        //testing displayNewMessages
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION displayNewMessages DISPLAYING NEW MESSAGES FOR 'Elaine Sheeran']");
        db.displayNewMessagesTest("esheeran@cs1555.com");

        
        //testing searchForUser
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION searchForUser SEARCHING FOR KNOWN USER JOE MESZAR USING SEARCH TERMS: 'Joe Meszar']");
        db.searchForUserTest("Joe Meszar");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION searchForUser SEARCHING FOR KNOWN USER JOE MESZAR USING SEARCH TERMS: 'joe@joe.com']");
        db.searchForUserTest("joe@joe.com");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION searchForUser SEARCHING FOR UNKNOWN USER BRUCE LEE USING TERMS: 'Bruce Lee']");
        db.searchForUserTest("Bruce Lee");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION searchForUser SEARCHING FOR UNKNOWN USER BRUCE LEE USING TERMS: 'brucelee@jeetkune.do']");
        db.searchForUserTest("brucelee@jeetkune.do");
        
        
        //testing threeDegrees
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION threeDegrees WITH KNOWN LINK BETWEEN Joe Meszar AND HIMSELF]");
        db.threeDegreesTest("joe@joe.com", "joe@joe.com");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION threeDegrees WITH KNOWN LINK BETWEEN Joe Meszar AND Kyle Monto]");
        db.threeDegreesTest("joe@joe.com", "kyle@kyle.com");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION threeDegrees WITH KNOWN LINK BETWEEN Joe Meszar AND Melisa Finchum]");
        db.threeDegreesTest("joe@joe.com", "mfinchum@cs1555.com");

        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION threeDegrees WITH KNOWN LINK BETWEEN Joe Meszar AND Jason Tomei]");
        db.threeDegreesTest("joe@joe.com", "jtomei@cs1555.com");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION threeDegrees WITH UNKNOWN LINK BETWEEN Joe Meszar AND Shelton Sgro]");
        db.threeDegreesTest("joe@joe.com", "ssgro@cs1555.com");
        
        
        //testing topMessagers
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION topMessagers WITH NUMBEROFMONTHS '1' AND RESULTS '3']");
        db.topMessagersTest("1", "3");
        
        //testing topMessagers
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION topMessagers WITH NUMBEROFMONTHS '3' AND RESULTS '5']");
        db.topMessagersTest("3", "5");
        
        //testing topMessagers
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION topMessagers WITH NUMBEROFMONTHS '7' AND RESULTS '7']");
        db.topMessagersTest("7", "7");
        
        //testing topMessagers
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION topMessagers WITH NUMBEROFMONTHS '9' AND RESULTS '10']");
        db.topMessagersTest("9", "10");
        
        
        //testing drop users
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION dropUser REMOVING USER Elaine Sheeran]");
        db.dropUserTest("esheeran@cs1555.com");
        System.out.println("\n!! SEARCHING FOR NEWLY REMOVED USER Elaine Sheeran !!");
        db.searchForUserTest("esheeran@cs1555.com");
        System.out.println("\n!! DISPLAYING FRIENDS FOR NEWLY REMOVED USER Elaine Sheeran !!");
        db.displayFriendsTest("esheeran@cs1555.com");
        System.out.println("\n!! DISPLAYING MESSAGES FOR NEWLY REMOVED USER Elaine Sheeran !!");
        db.displayMessagesTest("esheeran@cs1555.com");
        System.out.println("\n!! DISPLAYING MESSAGES FOR USER THAT SENT MESSAGE TO Elaine Sheeran !!");
        db.displayMessagesTest("saguillon@cs1555.com");
        
        System.out.println("\n*******************************************************\n"
                + "[TESTING FUNCTION dropUser REMOVING ALL TEST USERS]");
        dropUsersTest(db);
        
        System.out.println("\n*******************************************************\n"
                + "[REMOVING ALL TEST GROUPS]");
        dropGroupsTest(db);
    }

    private static void createNewUsersTest(DatabaseConnection db) {
        db.createUserTest("Elaine", "Sheeran", "esheeran@cs1555.com", "10-JAN-1994");
        db.createUserTest("Sharri", "Aguillon", "saguillon@cs1555.com", "11-JAN-1994");
        db.createUserTest("Coy", "Kitts", "ckitts@cs1555.com", "12-JAN-1994");
        db.createUserTest("Reid", "Salerno", "rsalerno@cs1555.com", "13-JAN-1994");
        db.createUserTest("Omar", "Desousa", "odesousa@cs1555.com", "14-JAN-1994");
        db.createUserTest("Max", "Niday", "mniday@cs1555.com", "15-JAN-1994");
        db.createUserTest("Josie", "Hugo", "jhugo@cs1555.com", "16-JAN-1994");
        db.createUserTest("Tera", "Gayhart", "tgayhart@cs1555.com", "17-JAN-1994");
        db.createUserTest("Tiana", "Heimer", "theimer@cs1555.com", "18-JAN-1994");
        db.createUserTest("Keely", "Cypher", "kcypher@cs1555.com", "19-JAN-1994");
        db.createUserTest("Dorris", "Tegeler", "dtegeler@cs1555.com", "20-JAN-1994");
        db.createUserTest("Joeann", "Cutshaw", "jcutshaw@cs1555.com", "21-JAN-1994");
        db.createUserTest("Alaina", "Authement", "aauthement@cs1555.com", "22-JAN-1994");
        db.createUserTest("Natasha", "Fick", "nfick@cs1555.com", "23-JAN-1994");
        db.createUserTest("Ingrid", "Pettus", "ipettus@cs1555.com", "24-JAN-1994");
        db.createUserTest("Marchelle", "Mccardell", "mmccardell@cs1555.com", "25-JAN-1994");
        db.createUserTest("Sabina", "Tengan", "stengan@cs1555.com", "26-JAN-1994");
        db.createUserTest("Sara", "Eckhoff", "seckhoff@cs1555.com", "27-JAN-1994");
        db.createUserTest("Marquerite", "Goolsby", "mgoolsby@cs1555.com", "28-FEB-1994");
        db.createUserTest("Carlton", "Drone", "cdrone@cs1555.com", "10-FEB-1994");
        db.createUserTest("Britney", "Whitmer", "bwhitmer@cs1555.com", "11-FEB-1994");
        db.createUserTest("Zita", "Castello", "zcastello@cs1555.com", "12-FEB-1994");
        db.createUserTest("Sharon", "Adam", "sadam@cs1555.com", "13-FEB-1994");
        db.createUserTest("Rubin", "Lodi", "rlodi@cs1555.com", "14-FEB-1994");
        db.createUserTest("Theodora", "Conigliaro", "tconigliaro@cs1555.com", "15-FEB-1994");
        db.createUserTest("Nia", "Roesler", "nroesler@cs1555.com", "16-FEB-1994");
        db.createUserTest("Charlotte", "Marotta", "cmarotta@cs1555.com", "17-FEB-1994");
        db.createUserTest("Valentina", "Cervone", "vcervone@cs1555.com", "18-FEB-1994");
        db.createUserTest("Thomasena", "Bible", "tbible@cs1555.com", "19-FEB-1994");
        db.createUserTest("Pamela", "You", "pyou@cs1555.com", "20-FEB-1994");
        db.createUserTest("Heide", "Lang", "hlang@cs1555.com", "21-FEB-1994");
        db.createUserTest("Audria", "Dorfman", "adorfman@cs1555.com", "22-FEB-1994");
        db.createUserTest("Jon", "Rawlins", "jrawlins@cs1555.com", "23-FEB-1994");
        db.createUserTest("Cristal", "Mcpartland", "cmcpartland@cs1555.com", "24-FEB-1994");
        db.createUserTest("Kazuko", "Shortridge", "kshortridge@cs1555.com", "25-FEB-1994");
        db.createUserTest("Ida", "Chilcott", "ichilcott@cs1555.com", "26-FEB-1994");
        db.createUserTest("Niesha", "Andino", "nandino@cs1555.com", "27-FEB-1994");
        db.createUserTest("Tam", "Molder", "tmolder@cs1555.com", "10-MAR-1994");
        db.createUserTest("Lavina", "Duncan", "lduncan@cs1555.com", "11-MAR-1994");
        db.createUserTest("Celestine", "Juneau", "cjuneau@cs1555.com", "12-MAR-1994");
        db.createUserTest("Emilee", "Simonson", "esimonson@cs1555.com", "13-MAR-1994");
        db.createUserTest("Gabriel", "Esters", "gesters@cs1555.com", "14-MAR-1994");
        db.createUserTest("Brittaney", "Hirt", "bhirt@cs1555.com", "15-MAR-1994");
        db.createUserTest("Jeanett", "Mclellan", "jmclellan@cs1555.com", "16-MAR-1994");
        db.createUserTest("Bobbi", "Vallarta", "bvallarta@cs1555.com", "17-MAR-1994");
        db.createUserTest("Drucilla", "Rodrigue", "drodrigue@cs1555.com", "18-MAR-1994");
        db.createUserTest("Latonia", "Mounce", "lmounce@cs1555.com", "19-MAR-1994");
        db.createUserTest("Gary", "Sundstrom", "gsundstrom@cs1555.com", "20-MAR-1994");
        db.createUserTest("Michelina", "Garrick", "mgarrick@cs1555.com", "21-MAR-1994");
        db.createUserTest("Sheilah", "Sarkis", "ssarkis@cs1555.com", "22-MAR-1994");
    }
    
    private static void dropUsersTest(DatabaseConnection db) {
        db.dropUserTest("saguillon@cs1555.com");
        db.dropUserTest("ckitts@cs1555.com");
        db.dropUserTest("rsalerno@cs1555.com");
        db.dropUserTest("odesousa@cs1555.com");
        db.dropUserTest("mniday@cs1555.com");
        db.dropUserTest("jhugo@cs1555.com");
        db.dropUserTest("tgayhart@cs1555.com");
        db.dropUserTest("theimer@cs1555.com");
        db.dropUserTest("kcypher@cs1555.com");
        db.dropUserTest("dtegeler@cs1555.com");
        db.dropUserTest("jcutshaw@cs1555.com");
        db.dropUserTest("aauthement@cs1555.com");
        db.dropUserTest("nfick@cs1555.com");
        db.dropUserTest("ipettus@cs1555.com");
        db.dropUserTest("mmccardell@cs1555.com");
        db.dropUserTest("stengan@cs1555.com");
        db.dropUserTest("seckhoff@cs1555.com");
        db.dropUserTest("mgoolsby@cs1555.com");
        db.dropUserTest("cdrone@cs1555.com");
        db.dropUserTest("bwhitmer@cs1555.com");
        db.dropUserTest("zcastello@cs1555.com");
        db.dropUserTest("sadam@cs1555.com");
        db.dropUserTest("rlodi@cs1555.com");
        db.dropUserTest("tconigliaro@cs1555.com");
        db.dropUserTest("nroesler@cs1555.com");
        db.dropUserTest("cmarotta@cs1555.com");
        db.dropUserTest("vcervone@cs1555.com");
        db.dropUserTest("tbible@cs1555.com");
        db.dropUserTest("pyou@cs1555.com");
        db.dropUserTest("hlang@cs1555.com");
        db.dropUserTest("adorfman@cs1555.com");
        db.dropUserTest("jrawlins@cs1555.com");
        db.dropUserTest("cmcpartland@cs1555.com");
        db.dropUserTest("kshortridge@cs1555.com");
        db.dropUserTest("ichilcott@cs1555.com");
        db.dropUserTest("nandino@cs1555.com");
        db.dropUserTest("tmolder@cs1555.com");
        db.dropUserTest("lduncan@cs1555.com");
        db.dropUserTest("cjuneau@cs1555.com");
        db.dropUserTest("esimonson@cs1555.com");
        db.dropUserTest("gesters@cs1555.com");
        db.dropUserTest("bhirt@cs1555.com");
        db.dropUserTest("jmclellan@cs1555.com");
        db.dropUserTest("bvallarta@cs1555.com");
        db.dropUserTest("drodrigue@cs1555.com");
        db.dropUserTest("lmounce@cs1555.com");
        db.dropUserTest("gsundstrom@cs1555.com");
        db.dropUserTest("mgarrick@cs1555.com");
        db.dropUserTest("ssarkis@cs1555.com");
    }

    private static void initiateFriendships(DatabaseConnection db) {
        //first 10 pairs for the creation of 50 users
        db.initiateFriendshipTest("esheeran@cs1555.com", "saguillon@cs1555.com");
        db.initiateFriendshipTest("ckitts@cs1555.com", "rsalerno@cs1555.com");
        db.initiateFriendshipTest("odesousa@cs1555.com", "mniday@cs1555.com");
        db.initiateFriendshipTest("mniday@cs1555.com", "jhugo@cs1555.com");
        db.initiateFriendshipTest("tgayhart@cs1555.com", "theimer@cs1555.com");
        db.initiateFriendshipTest("kcypher@cs1555.com", "dtegeler@cs1555.com");
        db.initiateFriendshipTest("jcutshaw@cs1555.com", "aauthement@cs1555.com");
        db.initiateFriendshipTest("nfick@cs1555.com", "ipettus@cs1555.com");
        db.initiateFriendshipTest("mmccardell@cs1555.com", "stengan@cs1555.com");
        db.initiateFriendshipTest("seckhoff@cs1555.com", "mgoolsby@cs1555.com");
    }

    private static void establishFriendship(DatabaseConnection db) {
        //same friendships as initiateFriendships but in reverse
        db.establishFriendshipTest("saguillon@cs1555.com", "esheeran@cs1555.com");
        db.establishFriendshipTest("rsalerno@cs1555.com", "ckitts@cs1555.com");
        db.establishFriendshipTest("mniday@cs1555.com", "odesousa@cs1555.com");
        db.establishFriendshipTest("jhugo@cs1555.com", "mniday@cs1555.com");
        db.establishFriendshipTest("theimer@cs1555.com", "tgayhart@cs1555.com");
        db.establishFriendshipTest("dtegeler@cs1555.com", "kcypher@cs1555.com");
        db.establishFriendshipTest("aauthement@cs1555.com", "jcutshaw@cs1555.com");
        db.establishFriendshipTest("ipettus@cs1555.com", "nfick@cs1555.com");
        db.establishFriendshipTest("stengan@cs1555.com", "mmccardell@cs1555.com");
        db.establishFriendshipTest("mgoolsby@cs1555.com", "seckhoff@cs1555.com");
    }

    private static void createGroups(DatabaseConnection db) {
        //generic groups with memberlimit string on end
        db.createGroupTest("GROUP1", "DESCRIPTION1", "1");
        db.createGroupTest("GROUP2", "DESCRIPTION2", "2");
        db.createGroupTest("GROUP3", "DESCRIPTION3", "3");
        db.createGroupTest("GROUP4", "DESCRIPTION4", "4");
        db.createGroupTest("GROUP5", "DESCRIPTION5", "5");
    }
     
    private static void dropGroupsTest(DatabaseConnection db) {
        db.dropGroupTest("GROUP1");
        db.dropGroupTest("GROUP2");
        db.dropGroupTest("GROUP3");
        db.dropGroupTest("GROUP4");
        db.dropGroupTest("GROUP5");
    }

    private static void usersToGroups(DatabaseConnection db) {
        //add users to groups just created to max out their membership limits
        db.addToGroupTest("esheeran@cs1555.com", "GROUP1");
        db.addToGroupTest("ckitts@cs1555.com", "GROUP2");
        db.addToGroupTest("odesousa@cs1555.com", "GROUP2");
        db.addToGroupTest("mniday@cs1555.com", "GROUP3");
        db.addToGroupTest("tgayhart@cs1555.com", "GROUP3");
        db.addToGroupTest("kcypher@cs1555.com", "GROUP3");
        db.addToGroupTest("jcutshaw@cs1555.com", "GROUP4");
        db.addToGroupTest("nfick@cs1555.com", "GROUP4");
        db.addToGroupTest("mmccardell@cs1555.com", "GROUP4");
        db.addToGroupTest("seckhoff@cs1555.com", "GROUP4");
        db.addToGroupTest("kshortridge@cs1555.com", "GROUP5");
        db.addToGroupTest("ichilcott@cs1555.com", "GROUP5");
        db.addToGroupTest("nandino@cs1555.com", "GROUP5");
        db.addToGroupTest("tmolder@cs1555.com", "GROUP5");
        db.addToGroupTest("lduncan@cs1555.com", "GROUP5");
    }

    private static void sendMessagesUser(DatabaseConnection db) throws ParseException {
        //15 message with users created during testing
        //current_timestamp
        db.sendMessageToUserTest("saguillon@cs1555.com", "esheeran@cs1555.com", "AM", "Am if number no up period regard sudden better");
        db.sendMessageToUserTest("rsalerno@cs1555.com", "ckitts@cs1555.com", "DECISIVELY", "Decisively surrounded all admiration and not you");
        db.sendMessageToUserTest("mniday@cs1555.com", "odesousa@cs1555.com", "OUT", "Out particular sympathize not favourable introduced insipidity but ham");
        db.sendMessageToUserTest("jhugo@cs1555.com", "mniday@cs1555.com", "RATHER", "Rather number can and set praise");
        db.sendMessageToUserTest("theimer@cs1555.com", "tgayhart@cs1555.com", "DISTRUSTS", "Distrusts an it contented perceived attending oh");
        db.sendMessageToUserTest("dtegeler@cs1555.com", "kcypher@cs1555.com", "THOROUGHLY", "Thoroughly estimating introduced stimulated why but motionless");
        db.sendMessageToUserTest("aauthement@cs1555.com", "jcutshaw@cs1555.com", "SENTIMENTS", "Sentiments two occasional affronting solicitude travelling and one contrasted");
        db.sendMessageToUserTest("ipettus@cs1555.com", "nfick@cs1555.com", "FORTUNE", "Fortune day out married parties");
        db.sendMessageToUserTest("stengan@cs1555.com", "mmccardell@cs1555.com", "HAPPINESS", "Happiness remainder joy but earnestly for off");
        db.sendMessageToUserTest("mgoolsby@cs1555.com", "seckhoff@cs1555.com", "TOOK", "Took sold add play may none him few");
        db.sendMessageToUserTest("esheeran@cs1555.com", "saguillon@cs1555.com", "IF", "If as increasing contrasted entreaties be");
        db.sendMessageToUserTest("ckitts@cs1555.com", "rsalerno@cs1555.com", "NOW", "Now summer who day looked our behind moment coming");
        db.sendMessageToUserTest("odesousa@cs1555.com", "mniday@cs1555.com", "PAIN", "Pain son rose more park way that");
        db.sendMessageToUserTest("mniday@cs1555.com", "jhugo@cs1555.com", "AN", "An stairs as be lovers uneasy");
        db.sendMessageToUserTest("tgayhart@cs1555.com", "theimer@cs1555.com", "PASTURE", "Pasture he invited mr company shyness");
    }

    private static void sendMessagesGroup(DatabaseConnection db) throws ParseException {
        db.sendMessageToGroupTest("esheeran@cs1555.com", "GROUP1", "TEST1", "This is the TEST1 message");
        db.sendMessageToGroupTest("odesousa@cs1555.com", "GROUP2", "TEST2", "This is the TEST2 message");
        db.sendMessageToGroupTest("kcypher@cs1555.com", "GROUP3", "TEST3", "This is the TEST3 message");
        db.sendMessageToGroupTest("mmccardell@cs1555.com", "GROUP4", "TEST4", "This is the TEST4 message");
        db.sendMessageToGroupTest("odesousa@cs1555.com", "GROUP5", "TEST5", "This is the TEST5 message");
    }
}
