package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import request.RegisterRequest;
import io.javalin.http.Context;
import result.RegisterResult;
import service.UserService;

import javax.xml.crypto.Data;
import java.util.Map;

/*public class UserHandler {

    private final DataAccess da;
    private Gson serializer = new Gson();
    private UserService userService = new UserService(da);

    public UserHandler(DataAccess da){
        this.da = da;
    }

    public String registerHandler(Context registerBody) {

        RegisterRequest register = serializer.fromJson(registerBody.body(), RegisterRequest.class);

        try{
           RegisterResult registerResult = userService.register(register);
            return "Success";
        } catch (Exception e) {
            return "Error";
        }

    }
}*/
