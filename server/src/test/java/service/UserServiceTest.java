package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import request.ClearRequest;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import result.ClearResult;
import result.LoginResult;
import result.LogoutResult;
import result.RegisterResult;

import java.sql.SQLException;

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
    void registerThrowsError() throws SQLException, DataAccessException {
        DataAccess da = new MemoryDataAccess();
        UserService userService = createUserService(da);
        RegisterRequest registerRequest = new RegisterRequest("rex@501st.com", "Rex", "forTheRepublic");

            assertThrows(AlreadyTakenException.class, () -> {
                userService.register(registerRequest);
            });
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> {
        DataAccess da = new MemoryDataAccess();
        UserService userService = createUserService(da);

        LoginRequest loginRequest = new LoginRequest("Rex", "forTheRepublic");
        LoginResult loginResult = userService.login(loginRequest);
        LoginResult loginResultExpected = new LoginResult("Rex", loginResult.authToken());

        assertEquals(loginResultExpected, loginResult);
        });
    }

    @Test
    void loginUsernameNotFound() {
        DataAccess da = new MemoryDataAccess();
        UserService userService = createUserService(da);

        LoginRequest loginRequest = new LoginRequest("Cody", "forTheRepublic");

        assertThrows(IncorrectLoginException.class, () -> {userService.login(loginRequest);});
    }

    @Test
    void loginIncorrectPassword() {
        DataAccess da = new MemoryDataAccess();
        UserService userService = createUserService(da);

        LoginRequest loginRequest = new LoginRequest("Rex", "forTheRepublix");

        assertThrows(IncorrectLoginException.class, () -> {userService.login(loginRequest);});
    }

    @Test
    void logout() {
        assertDoesNotThrow(() -> {
        DataAccess da = new MemoryDataAccess();
            UserService userService = createUserService(da);
            AuthData auth = new AuthData("123456", "Rex");
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
        UserService userService = createUserService(da);
        AuthData auth = new AuthData("123456", "Rex");
        da.createAuth(auth);

        LogoutRequest logoutRequest = new LogoutRequest("12");
        assertThrows(InvalidAuthException.class, () -> {userService.logout(logoutRequest);});
    }

    @Test
    void clear() {
        assertDoesNotThrow(() -> {
        DataAccess da = new MemoryDataAccess();
        UserService userService = createUserService(da);

        ClearRequest clearRequest = new ClearRequest();
        ClearResult clearResult = userService.clear(clearRequest);
        ClearResult clearResultExpected = new ClearResult();

        assertEquals(clearResultExpected, clearResult);
        });
    }

    private static UserService createUserService(DataAccess da) {
        assertDoesNotThrow(() -> {
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);
        return userService;
        });

        return null;
    }
}

