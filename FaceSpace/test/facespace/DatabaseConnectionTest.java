package facespace;

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joe Meszar (jwm54@pitt.edu)
 */
public class DatabaseConnectionTest {
    private static DatabaseConnection db = null;
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "poiu0987";
    
    public DatabaseConnectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        db = new DatabaseConnection(URL, USERNAME, PASSWORD);
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        db.closeConnection();
    }
    
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addToGroup method, of class DatabaseConnection.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddToGroup_Normal() throws Exception {
        System.out.println("addToGroup: Testing add 'joe@joe.com' to group 'HISTORY'...");
        
        String userEmail = "joe@joe.com";
        String groupName = "HISTORY";
        
        assertTrue(db.addToGroup(userEmail, groupName));
    }
    @Test
    public void testAddToGroup_Bad_Username() throws Exception {
        System.out.println("addToGroup: Testing add bad username 'noexist@noexist.com' to group 'HISTORY'...");
        
        String userEmail = "noexist@noexist.com";
        String groupName = "HISTORY";
        
        try {
            db.addToGroup(userEmail, groupName);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No User Found"));
        }
    }
    @Test
    public void testAddToGroup_Bad_Group() throws Exception {
        System.out.println("addToGroup: Testing add 'joe@joe.com' to group 'NOEXIST'...");
        
        String userEmail = "joe@joe.com";
        String groupName = "NOEXIST";
        
        try {
            db.addToGroup(userEmail, groupName);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No Group Found"));
        }
    }
    
    /**
     * Test of createGroup method, of class DatabaseConnection.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateGroup_Normal() throws Exception {
        System.out.println("createGroup: Testing creating group 'TESTING1' with member limit '50'...");
        
        String groupName = "TESTING1";
        String description = "TESTING1 GROUP DESCRIPTION";
        int limit = 50;
        
        assertTrue(db.createGroup(groupName, description, limit));
    }
    public void testCreateGroup_Bad_Member_Limit() throws Exception {
        System.out.println("createGroup: Testing creating group 'TESTING2' with invalid member limit '-1'...");
        
        String groupName = "TESTING2";
        String description = "TESTING2 GROUP DESCRIPTION";
        int limit = -1;
        
        assertTrue(db.createGroup(groupName, description, limit));
    }

    /**
     * Test of createUser method, of class DatabaseConnection.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateUser_Normal() throws Exception {
        System.out.println("createUser: Testing creating user 'TEST' 'USER1', email 'TESTUSER1@CS1555.COM', birthday '04-APR-1986'...");
        
        String firstName = "TEST";
        String lastName = "USER1";
        String email = "testuser1@cs1555.com";
        String dateOfBirth = "04-APR-1986";
        
        assertTrue(db.createUser(firstName, lastName, email, dateOfBirth));
    }
    @Test
    public void testCreateUser_Invalid_Date() throws Exception {
        System.out.println("createUser: Testing creating user 'TEST' 'USER1', email 'TESTUSER1@CS1555.COM', birthday '32-APR-1986'...");
        
        String firstName = "TEST";
        String lastName = "USER1";
        String email = "testuser1@cs1555.com";
        String dateOfBirth = "32-APR-1986";
        
        try {
            db.createUser(firstName, lastName, email, dateOfBirth);
        } catch (SQLException e) {
            // ORA-01847: day of month must be between 1 and last day of month 
            assertTrue(e.getSQLState().equals("22008"));
        }
    }
    
    /**
     * Test of displayFriends method, of class DatabaseConnection.
     * 
     * @throws Exception
     */
    @Test
    public void testDisplayFriends_Normal() throws Exception {
        System.out.println("displayFriends: Testing display of all friends for user with email 'joe@joe.com'");
        
        String userEmail = "joe@joe.com";
        
        assertTrue(db.displayFriends(userEmail));
    }
    @Test
    public void testDisplayFriends_Invalid_Username() throws Exception {
        System.out.println("displayFriends: Testing display of all friends for invalid user with email 'invalid@invalid.com'");
        
        String userEmail = "invalid@invalid.com";
        
        try {
            db.displayFriends(userEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No User Found"));
        }
    }

    /**
     * Test of displayMessages method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testDisplayMessages_Normal() throws Exception {
        System.out.println("displayMessages: Testing display of all messages for user with email 'joe@joe.com'");
        
        String userEmail = "joe@joe.com";
        
        assertTrue(db.displayMessages(userEmail));
    }
    
    /**
     * Test of displayNewMessages method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testDisplayNewMessages() throws Exception {
        System.out.println("displayNewMessages: Testing display of all messages for user with email 'joe@joe.com'");
        
        String userEmail = "joe@joe.com";
        
        assertTrue(db.displayNewMessages(userEmail));
    }
    @Test
    public void testDisplayNewMessages_Invalid_Username() throws Exception {
        System.out.println("displayNewMessages: Testing display of all messages for invalid user with email 'invalid@invalid.com'");
        
        String userEmail = "invalid@invalid.com";
        
        try {
            db.displayNewMessages(userEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No User Found"));
        }
    }

    /**
     * Test of dropUser method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testDropUser_Normal() throws Exception {
        System.out.println("dropUser: Testing removal of user with email 'astmarie@cs1555.com'");
        
        String userEmail = "astmarie@cs1555.com";
        
        assertTrue(db.dropUser(userEmail));
    }
    @Test
    public void testDropUser_Invalid_Username() throws Exception {
        System.out.println("dropUser: Testing removal of user with email 'invalid@invalid.com'");
        
        String userEmail = "invalid@invalid.com";
        
        try {
            db.dropUser(userEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No User Found"));
        }
    }
    
    /**
     * Test of establishFriendship method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testEstablishFriendship() throws Exception {
        System.out.println("establishFriendship: Testing establishing friendship between email 'joe@joe.com' and 'fhaffey@cs1555.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "fhaffey@cs1555.com";
        
        assertTrue(db.establishFriendship(userEmail, friendEmail));
    }
    @Test
    public void testEstablishFriendship_Invalid_User_Email() throws Exception {
        System.out.println("establishFriendship: Testing establishing friendship between invalid email 'invalid@invalid.com' and 'kyle@kyle.com'");
        
        String userEmail = "invalid@invalid.com";
        String friendEmail = "kyle@kyle.com";
        
        try {
            db.establishFriendship(userEmail, friendEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No user found matching this userEmail"));
        }
    }
    @Test
    public void testEstablishFriendship_Invalid_Friend_Email() throws Exception {
        System.out.println("establishFriendship: Testing establishing friendship between email 'joe@joe.com' and invalid email 'invalid@invalid.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "invalid@invalid.com";
        
        try {
            db.establishFriendship(userEmail, friendEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("No user found matching this friendEmail"));
        }
    }
    @Test
    public void testEstablishFriendship_Friending_Self() throws Exception {
        System.out.println("establishFriendship: Testing establishing friendship between 'joe@joe.com' and 'joe@joe.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "joe@joe.com";
        
        try {
            db.establishFriendship(userEmail, friendEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("You cannot friend yourself!"));
        }
    }
    @Test
    public void testEstablishFriendship_Already_Pending() throws Exception {
        System.out.println("establishFriendship: Testing establishing friendship between 'joe@joe.com' and 'znuttall@cs1555.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "znuttall@cs1555.com";
        
        try {
            db.establishFriendship(userEmail, friendEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Friendship is already pending"));
        }
    }
    

    /**
     * Test of initiateFriendship method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testInitiateFriendship_Normal() throws Exception {
        System.out.println("initiateFriendship: Testing initiating a friendship request between 'joe@joe.com' and 'dhegarty@cs1555.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "dhegarty@cs1555.com";
        
        assertTrue(db.initiateFriendship(userEmail, friendEmail));
    }
    @Test
    public void testInitiateFriendship_Normal_Create_Friendship() throws Exception {
        System.out.println("initiateFriendship: Testing initiating a friendship request between 'dhegarty@cs1555.com' and 'joe@joe.com'");
        
        String userEmail = "dhegarty@cs1555.com";
        String friendEmail = "joe@joe.com";
        
        assertTrue(db.initiateFriendship(userEmail, friendEmail));
    }
    @Test
    public void testInitiateFriendship_Friend_Self() throws Exception {
        System.out.println("initiateFriendship: Testing initiating a friendship request between 'joe@joe.com' and 'joe@joe.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "joe@joe.com";
        
        try {
            db.initiateFriendship(userEmail, friendEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("You cannot friend yourself!"));
        }
    }
    @Test
    public void testInitiateFriendship_Invalid_User_Email() throws Exception {
        System.out.println("initiateFriendship: Testing initiating a friendship request between 'joe@joe.com' and 'joe@joe.com'");
        
        String userEmail = "joe@joe.com";
        String friendEmail = "joe@joe.com";
        
        try {
            db.initiateFriendship(userEmail, friendEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("You cannot friend yourself!"));
        }
    }
    
    /**
     * Test of listAllGroups method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testListAllGroups() throws Exception {
        System.out.println("listAllGroups: Testing the listing of all groups...");
        
        assertTrue(db.listAllGroups());
    }

    /**
     * Test of listAllUsers method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testListAllUsers() throws Exception {
        System.out.println("listAllUsers: Testing the listing of all users...");
        
        assertTrue(db.listAllUsers());
    }

    /**
     * Test of logInUser method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testLogInUser_Normal() throws Exception {
        System.out.println("logInUser: Testing the login of user with email 'joe@joe.com'");
        String userEmail = "joe@joe.com";
        
        assertTrue(db.logInUser(userEmail));
    }
    @Test
    public void testLogInUser_Invalid_Username() throws Exception {
        System.out.println("logInUser: Testing the login of user with invalid email 'invalid@invalid.com'");
        String userEmail = "invalid@invalid.com";
        
        try {
            db.logInUser(userEmail);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("The user name entered does not exist"));
        }
    }

    /**
     * Test of searchForUser method, of class DatabaseConnection.
     * 
     * @throws java.lang.Exception
     */
    @Test
    public void testSearchForUser() throws Exception {
        System.out.println("searchForUser: Testing search of user with email 'joe@joe.com'");
        String searchTerms = "joe@joe.com";
        
        assertTrue(db.searchForUser(searchTerms));
    }

    /**
     * Test of sendMessageToGroup method, of class DatabaseConnection.
     */
    @Test
    public void testSendMessageToGroup() throws Exception {
        System.out.println("sendMessageToGroup");
        String userEmail = "";
        String groupName = "";
        String messageSubject = "";
        String messageBody = "";
        DatabaseConnection instance = null;
        instance.sendMessageToGroup(userEmail, groupName, messageSubject, messageBody);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessageToUser method, of class DatabaseConnection.
     */
    @Test
    public void testSendMessageToUser() throws Exception {
        System.out.println("sendMessageToUser");
        String userEmail = "";
        String recipEmail = "";
        String messageSubject = "";
        String messageBody = "";
        DatabaseConnection instance = null;
        instance.sendMessageToUser(userEmail, recipEmail, messageSubject, messageBody);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of threeDegrees method, of class DatabaseConnection.
     */
    @Test
    public void testThreeDegrees() throws Exception {
        System.out.println("threeDegrees");
        String startEmail = "";
        String endEmail = "";
        DatabaseConnection instance = null;
        instance.threeDegrees(startEmail, endEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of topMessagers method, of class DatabaseConnection.
     */
    @Test
    public void testTopMessagers() throws Exception {
        System.out.println("topMessagers");
        DatabaseConnection instance = null;
        instance.topMessagers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
