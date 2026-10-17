package service;

import dataaccess.AlreadyTakenException;
import dataaccess.DataAccess;
import dataaccess.IncorrectLoginException;
import dataaccess.InvalidAuthException;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.*;
import result.*;

import java.util.UUID;

public class UserService {

    private final DataAccess da;
    public UserService(DataAccess da) {
        this.da = da;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException {
        if (da.getUser(registerRequest.username()) == null){
            UserData user = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
            da.createUser(user);

            String authToken = generateToken();
            AuthData auth = new AuthData(registerRequest.username(), authToken);
            da.createAuth(auth);

            return new RegisterResult(registerRequest.username(), authToken);
        }
        else {
            throw new AlreadyTakenException("The username is already in use");
        }
    }

    public LoginResult login(LoginRequest loginRequest) throws IncorrectLoginException {
        if(da.getUser(loginRequest.username()) != null){
            if(da.getUser(loginRequest.username()).password().equals(loginRequest.password())){
                String authToken = generateToken();
                AuthData auth = new AuthData(loginRequest.username(), authToken);
                da.createAuth(auth);

                return new LoginResult(loginRequest.username(), authToken);
            }
        }
        throw new IncorrectLoginException("The username or password was incorrect");
    }

    public LogoutResult logout(LogoutRequest logoutrequest) throws InvalidAuthException {
        if(da.getAuth(logoutrequest.authToken()) != null){
            da.deleteAuth(logoutrequest.authToken());

            return new LogoutResult();
        }
        else{
            throw new InvalidAuthException("No Authorization Token found. Please log in");
        }
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
