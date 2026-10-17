package service;

import dataaccess.*;
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
        assertDoesNotThrow(() -> {
            DataAccess da = new MemoryDataAccess();
            UserService userService = new UserService(da);
            RegisterRequest registerRequest = new RegisterRequest("rex@501st.com", "Rex", "forTheRepublic");
            RegisterResult registerResult = userService.register(registerRequest);
            RegisterResult registerResultExpected = new RegisterResult("Rex", registerResult.authToken()); //change authToken later

            assertEquals(registerResultExpected, registerResult);
        });
    }
    @Test
    void registerThrowsError() {
            DataAccess da = new MemoryDataAccess();
            UserService userService = new UserService(da);
            UserData currentUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
            da.createUser(currentUser);
            RegisterRequest registerRequest = new RegisterRequest("rex@501st.com", "Rex", "forTheRepublic");

            assertThrows(AlreadyTakenException.class, () -> {
                userService.register(registerRequest);
            });
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> {
        DataAccess da = new MemoryDataAccess();
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);

        LoginRequest loginRequest = new LoginRequest("Rex", "forTheRepublic");
        LoginResult loginResult = userService.login(loginRequest);
        LoginResult loginResultExpected = new LoginResult("Rex", loginResult.authToken());

        assertEquals(loginResultExpected, loginResult);
        });
    }

    @Test
    void loginUsernameNotFound() {
            DataAccess da = new MemoryDataAccess();
            UserService userService = new UserService(da);
            UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
            da.createUser(user);

            LoginRequest loginRequest = new LoginRequest("Cody", "forTheRepublic");

            assertThrows(IncorrectLoginException.class, () -> {userService.login(loginRequest);});
    }

    @Test
    void loginIncorrectPassword() {
        DataAccess da = new MemoryDataAccess();
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);

        LoginRequest loginRequest = new LoginRequest("Rex", "forTheRepublix");

        assertThrows(IncorrectLoginException.class, () -> {userService.login(loginRequest);});
    }

    @Test
    void logout() {
        assertDoesNotThrow(() -> {
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
        });

    }

    @Test
    void logoutInvalidAuth() {
        DataAccess da = new MemoryDataAccess();
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);
        AuthData auth = new AuthData("Rex", "123456");
        da.createAuth(auth);

        LogoutRequest logoutRequest = new LogoutRequest("12");
        assertThrows(InvalidAuthException.class, () -> {userService.logout(logoutRequest);});
    }
}

