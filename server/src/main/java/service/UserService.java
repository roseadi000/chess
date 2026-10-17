package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
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

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        if (da.getUser(registerRequest.username()) == null){
            UserData user = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
            da.createUser(user);

            String authToken = generateToken();
            AuthData auth = new AuthData(registerRequest.username(), authToken);
            da.createAuth(auth);

            return new RegisterResult(registerRequest.username(), authToken);
        }
        else {
            throw new DataAccessException("Not implemented"); //AlreadyTakenException
        }
    }

    public LoginResult login(LoginRequest loginRequest){
        if(da.getUser(loginRequest.username()) != null){
            if(da.getUser(loginRequest.username()).password().equals(loginRequest.password())){
                String authToken = generateToken();
                AuthData auth = new AuthData(loginRequest.username(), authToken);
                da.createAuth(auth);

                return new LoginResult(loginRequest.username(), authToken);
            }
            else {
                throw new RuntimeException("Not implemented"); //IncorrectPasswordException
            }
        }
        else{
            throw new RuntimeException("Not implemented"); //UserNotFound

        }
    }

    public LogoutResult logout(LogoutRequest logoutrequest){
        if(da.getAuth(logoutrequest.authToken()) != null){
            da.deleteAuth(logoutrequest.authToken());

            return new LogoutResult();
        }
        else{
            throw new RuntimeException("Not implemented"); //AuthNotFound
        }
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }
}
