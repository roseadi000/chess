package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDataAccessTest {

    @Test
    void getUser() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser("Rex", "forTheRepublic", "rex@501st.com");
        assertEquals(newUser, da.getUser(newUser.username()));
    }

    @Test
    void clear() {
    }
}