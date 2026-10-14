package service;

import dataaccess.DataAccess;
import request.LoginRequest;
import request.*;
import result.*;

public class UserService {

    private final DataAccess da;
    public UserService(DataAccess da) {
        this.da = da;
    }

    public RegisterResult register(RegisterRequest registerRequest){
        throw new RuntimeException("Not implemented");
    }

    public LoginResult login(LoginRequest loginRequest){
        throw new RuntimeException("Not implemented");
    }

    public LoginResult logout(LogoutRequest logoutrequest){
        throw new RuntimeException("Not implemented");
    }
}
