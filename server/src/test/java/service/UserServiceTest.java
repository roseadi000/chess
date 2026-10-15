package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.LogoutResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void register() {
            DataAccess da = new MemoryDataAccess();
            UserService userService = new UserService(da);
            RegisterRequest registerRequest = new RegisterRequest("rex@501st.com", "Rex", "forTheRepublic");
            RegisterResult registerResult = userService.register(registerRequest);
            RegisterResult registerResultExpected = new RegisterResult("Rex", registerResult.authToken()); //change authToken later

            assertEquals(registerResultExpected, registerResult);
    }

    @Test
    void login() {
        DataAccess da = new MemoryDataAccess();
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);

        LoginRequest loginRequest = new LoginRequest("Rex", "forTheRepublic");
        LoginResult loginResult = userService.login(loginRequest);
        LoginResult loginResultExpected = new LoginResult("Rex", loginResult.authToken()); //replace authToken

        assertEquals(loginResultExpected, loginResult);
    }

    @Test
    void logout() {
        DataAccess da = new MemoryDataAccess();
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);
        AuthData auth = new AuthData("Rex", "123456");
        da.createAuth(auth);

        LogoutRequest logoutRequest = new LogoutRequest(auth.authToken());
        LogoutResult logoutResult = userService.logout(logoutRequest);
        LogoutResult logoutResultExpected = new LogoutResult();

        assertEquals(logoutResultExpected, logoutResult);

    }
}