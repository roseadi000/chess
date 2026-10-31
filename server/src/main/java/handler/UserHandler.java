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
        } catch (Exception e) {
            exceptionHandler(ctx, e);
        }

    }

    public void loginHandler(Context ctx) {
        LoginRequest login = serializer.fromJson(ctx.body(), LoginRequest.class);

        try{
            LoginResult loginResult = userService.login(login);
            ctx.result(serializer.toJson(loginResult));
        }
        catch (Exception e) {
            exceptionHandler(ctx, e);
        }
    }

    public void logoutHandler(Context ctx) {
        LogoutRequest logout = new LogoutRequest(ctx.header("authorization"));

        try{
            LogoutResult logoutResult = userService.logout(logout);
            ctx.result(serializer.toJson(logoutResult));
        } catch (Exception e) {
            exceptionHandler(ctx, e);
        }
    }

    public void clearHandler(Context ctx){
        ClearRequest clearRequest = new ClearRequest();

        try {
            ClearResult clearResult = userService.clear(clearRequest);
            ctx.result(serializer.toJson(clearResult));
        } catch (Exception e) {
            exceptionHandler(ctx, e);
        }
    }

    private void exceptionHandler(Context ctx, Exception e){
        if(e instanceof AlreadyTakenException) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(403);
        }
        else if(e instanceof FieldEmptyException) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(400);
        }
        else if(e instanceof InvalidInputException) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(422);
        }
        else if(e instanceof IncorrectLoginException) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(401);
        }
        else if (e instanceof InvalidAuthException) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(401);
        }
        else{
            ErrorResult errorResult = new ErrorResult(String.format("Error: %s", e.getMessage()));
            ctx.result(serializer.toJson(errorResult));
            ctx.status(500);
        }
    }
}
