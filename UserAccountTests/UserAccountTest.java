import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserAccountTest {
    UserAccount testUser;
    UserAccount testUser1;
    UserAccount testUserFromTxtFile;
    Ticket testTicket;

    @Before
    public void setUp(){
        testUser = new UserAccount("test", "testpw", 20);
        testUser1 = new UserAccount("othertest", "p", 30);
        testUserFromTxtFile = new UserAccount("testuser1", "password", 220);
        //testTicket = new Ticket(flight, localdatetime, false);
    }

    @Test
    public void getMiles() {
        assertEquals(testUser.getMiles(), 20);
        assertEquals(testUser1.getMiles(), 30);
    }

    @Test
    public void getTickets() {
    }

    @Test
    public void getUserAccountArray() {
        assertEquals(UserAccount.getUserAccountArray("userAccountsTest.txt").get(0).toString(), testUserFromTxtFile.toString());
    }

    @Test
    public void thisUser() {
        assertFalse(testUser.thisUser("test", "test1"));
        assertTrue(testUser.thisUser("test", "testpw"));
    }
}