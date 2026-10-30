package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.*;
import result.*;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {

    private final DataAccess da;
    public UserService(DataAccess da) {
        this.da = da;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, FieldEmptyException, SQLException, DataAccessException {
        if(registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            throw new FieldEmptyException("Error: username, password, or email cannot be empty");
        }

        if (da.getUser(registerRequest.username()) == null){
            UserData user = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
            da.createUser(user);

            String authToken = generateToken();
            AuthData auth = new AuthData(authToken, registerRequest.username());
            da.createAuth(auth);

            return new RegisterResult(registerRequest.username(), authToken);
        }
        else {
            throw new AlreadyTakenException("Error: The username is already in use");
        }
    }

    public LoginResult login(LoginRequest loginRequest) throws IncorrectLoginException, FieldEmptyException, SQLException, DataAccessException {
        if(loginRequest.username() == null || loginRequest.password() == null){
            throw new FieldEmptyException("Error: username or password cannot be empty");
        }

        if(da.getUser(loginRequest.username()) != null){
            if(da.getUser(loginRequest.username()).password().equals(loginRequest.password())){
                String authToken = generateToken();
                AuthData auth = new AuthData(authToken, loginRequest.username());
                da.createAuth(auth);

                return new LoginResult(loginRequest.username(), authToken);
            }
        }
        throw new IncorrectLoginException("Error: The username or password was incorrect");
    }

    public LogoutResult logout(LogoutRequest logoutrequest) throws InvalidAuthException, SQLException, DataAccessException {
        if(da.getAuth(logoutrequest.authToken()) != null){
            da.deleteAuth(logoutrequest.authToken());

            return new LogoutResult();
        }
        else{
            throw new InvalidAuthException("Error: No Authorization Token found. Please log in");
        }
    }

    public ClearResult clear(ClearRequest clearRequest) throws SQLException, DataAccessException {
        da.clear();
        return new ClearResult();
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
