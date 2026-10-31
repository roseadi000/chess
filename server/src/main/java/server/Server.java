package server;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import dataaccess.SQLDataAccess;
import handler.GameHandler;
import io.javalin.*;
import handler.UserHandler;

import java.sql.SQLException;

public class Server {

    private final Javalin javalin;

    public Server() {
        DataAccess da;

        try {
            da = new SQLDataAccess();
        } catch (SQLException | DataAccessException e) {
            da = new MemoryDataAccess();
        }
        UserHandler userHandler = new UserHandler(da);
        GameHandler gameHandler = new GameHandler(da);
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", ctx -> userHandler.clearHandler(ctx));
        javalin.post("/user", ctx -> userHandler.registerHandler(ctx));
        javalin.post("/session", ctx -> userHandler.loginHandler(ctx));
        javalin.delete("/session", ctx -> userHandler.logoutHandler(ctx));
        javalin.get("/game", ctx -> gameHandler.listGamesHandler(ctx));
        javalin.post("/game", ctx -> gameHandler.createGameHandler(ctx));
        javalin.put("/game", ctx -> gameHandler.joinGameHandler(ctx));

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
