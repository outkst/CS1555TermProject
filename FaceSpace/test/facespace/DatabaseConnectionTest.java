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

    /**
     * Test of createUser method, of class DatabaseConnection.
     * @throws java.lang.Exception
     */
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
     */
    @Test
    public void testDisplayFriends() throws Exception {
        System.out.println("displayFriends");
        String userEmail = "";
        DatabaseConnection instance = null;
        instance.displayFriends(userEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayMessages method, of class DatabaseConnection.
     */
    @Test
    public void testDisplayMessages() throws Exception {
        System.out.println("displayMessages");
        String userEmail = "";
        DatabaseConnection instance = null;
        instance.displayMessages(userEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayNewMessages method, of class DatabaseConnection.
     */
    @Test
    public void testDisplayNewMessages() throws Exception {
        System.out.println("displayNewMessages");
        String userEmail = "";
        DatabaseConnection instance = null;
        instance.displayNewMessages(userEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dropUser method, of class DatabaseConnection.
     */
    @Test
    public void testDropUser() throws Exception {
        System.out.println("dropUser");
        String userEmail = "";
        DatabaseConnection instance = null;
        instance.dropUser(userEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of establishFriendship method, of class DatabaseConnection.
     */
    @Test
    public void testEstablishFriendship() throws Exception {
        System.out.println("establishFriendship");
        String userEmail = "";
        String friendEmail = "";
        DatabaseConnection instance = null;
        instance.establishFriendship(userEmail, friendEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initiateFriendship method, of class DatabaseConnection.
     */
    @Test
    public void testInitiateFriendship() throws Exception {
        System.out.println("initiateFriendship");
        String userEmail = "";
        String friendEmail = "";
        DatabaseConnection instance = null;
        instance.initiateFriendship(userEmail, friendEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllGroups method, of class DatabaseConnection.
     */
    @Test
    public void testListAllGroups() throws Exception {
        System.out.println("listAllGroups");
        DatabaseConnection instance = null;
        instance.listAllGroups();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listAllUsers method, of class DatabaseConnection.
     */
    @Test
    public void testListAllUsers() throws Exception {
        System.out.println("listAllUsers");
        DatabaseConnection instance = null;
        instance.listAllUsers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logInUser method, of class DatabaseConnection.
     */
    @Test
    public void testLogInUser() throws Exception {
        System.out.println("logInUser");
        String userEmail = "";
        DatabaseConnection instance = null;
        instance.logInUser(userEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchForUser method, of class DatabaseConnection.
     */
    @Test
    public void testSearchForUser() throws Exception {
        System.out.println("searchForUser");
        String searchTerms = "";
        DatabaseConnection instance = null;
        instance.searchForUser(searchTerms);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
