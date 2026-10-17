package handler;

import com.google.gson.Gson;
import dataaccess.AlreadyTakenException;
import dataaccess.DataAccess;
import dataaccess.IncorrectLoginException;
import dataaccess.InvalidAuthException;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import io.javalin.http.Context;
import result.LoginResult;
import result.LogoutResult;
import result.RegisterResult;
import service.UserService;

import javax.xml.crypto.Data;
import java.util.Map;

public class UserHandler {

    private final DataAccess da;
    private Gson serializer = new Gson();
    private UserService userService;

    public UserHandler(DataAccess da){
        this.da = da;
        this.userService = new UserService(da);
    }

    public void registerHandler(Context ctx) {

        RegisterRequest register = serializer.fromJson(ctx.body(), RegisterRequest.class);

        try{
           RegisterResult registerResult = userService.register(register);
            ctx.result(serializer.toJson(registerResult));
        } catch (AlreadyTakenException e) {
            ctx.result(e.getMessage());
            ctx.status(401);
        }
        catch (Exception e) {
            ctx.result(e.getMessage());
            ctx.status(500);
        }

    }

    public void loginHandler(Context ctx) {
        LoginRequest login = serializer.fromJson(ctx.body(), LoginRequest.class);

        try{
            LoginResult loginResult = userService.login(login);
            ctx.result(serializer.toJson(loginResult));
        } catch (IncorrectLoginException e) {
            ctx.result(e.getMessage());
            ctx.status(401);
        } catch (Exception e) {
            ctx.result(e.getMessage());
            ctx.status(500);
        }
    }

    public void logoutHandler(Context ctx) {
        LogoutRequest logout = new LogoutRequest(ctx.header("authorization"));

        try{
            LogoutResult logoutResult = userService.logout(logout);
            ctx.result(serializer.toJson(logoutResult));
        } catch (InvalidAuthException e) {
            ctx.result(e.getMessage());
            ctx.status(401);
        } catch (Exception e) {
            ctx.result(e.getMessage());
            ctx.status(500);
        }
    }
}
