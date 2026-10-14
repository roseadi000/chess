package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDataAccessTest {

    @Test
    void getUser() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);

        assertEquals(newUser, da.getUser(newUser.username()));
    }

    @Test
    void getAuth() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);
        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);

        assertEquals(newAuth, da.getAuth(newAuth.authToken()));
    }

    @Test
    void deleteAuth() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);
        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        da.deleteAuth(newAuth.authToken());
        assertNull(da.getAuth(newAuth.authToken()));

    }

    @Test
    void clear() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);
        assertEquals(newUser, da.getUser(newUser.username()));

        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        da.clear();
        assertNull(da.getUser(newUser.username()));
        assertNull(da.getAuth(newAuth.authToken()));
    }
}