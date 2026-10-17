package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import io.javalin.*;
import handler.UserHandler;

public class Server {

    private final Javalin javalin;

    public Server() {
        DataAccess da = new MemoryDataAccess();
        UserHandler userHandler = new UserHandler(da);
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", ctx -> ctx.result("{}"));
        javalin.post("/user", ctx -> userHandler.registerHandler(ctx));
        javalin.post("/session", ctx -> userHandler.loginHandler(ctx));
        javalin.delete("/session", ctx -> userHandler.logoutHandler(ctx));

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
