package handler;

import com.google.gson.Gson;
import dataaccess.*;
import request.ClearRequest;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import io.javalin.http.Context;
import result.*;
import service.UserService;

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
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(403);
        } catch (FieldEmptyException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(400);
        }
        catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(500);
        }

    }

    public void loginHandler(Context ctx) {
        LoginRequest login = serializer.fromJson(ctx.body(), LoginRequest.class);

        try{
            LoginResult loginResult = userService.login(login);
            ctx.result(serializer.toJson(loginResult));
        } catch (IncorrectLoginException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(401);
        } catch (FieldEmptyException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(400);
        }
        catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(500);
        }
    }

    public void logoutHandler(Context ctx) {
        LogoutRequest logout = new LogoutRequest(ctx.header("authorization"));

        try{
            LogoutResult logoutResult = userService.logout(logout);
            ctx.result(serializer.toJson(logoutResult));
        } catch (InvalidAuthException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(401);
        } catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(500);
        }
    }

    public void clearHandler(Context ctx){
        ClearRequest clearRequest = new ClearRequest();

        try {
            ClearResult clearResult = userService.clear(clearRequest);
            ctx.result(serializer.toJson(clearResult));
        } catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(500);
        }
    }
}
