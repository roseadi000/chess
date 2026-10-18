package handler;

import dataaccess.*;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.JoinGameRequestBody;
import request.ListGamesRequest;
import result.CreateGameResult;
import result.ErrorResult;
import result.JoinGameResult;
import result.ListGamesResult;
import service.GameService;
import io.javalin.http.Context;
import com.google.gson.Gson;

public class GameHandler {

    private final DataAccess da;
    private final GameService gameService;
    private Gson serializer = new Gson();

    public GameHandler(DataAccess da){
        this.da = da;
        this.gameService = new GameService(da);
    }

    public void listGamesHandler(Context ctx){
        ListGamesRequest listGamesRequest = new ListGamesRequest(ctx.header("authorization"));

        try{
            ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);
            ctx.result(serializer.toJson(listGamesResult));
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

    public void createGameHandler(Context ctx){
        String gameName = ctx.body();
        String authToken = ctx.header("authorization");
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken, gameName);

        try{
            CreateGameResult createGameResult = gameService.createGame(createGameRequest);
            ctx.result(serializer.toJson(createGameResult));
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

    public void joinGameHandler(Context ctx){
        JoinGameRequestBody joinGameRequestBody = serializer.fromJson(ctx.body(), JoinGameRequestBody.class);
        String authToken = ctx.header("authorization");
        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken,
                joinGameRequestBody.playerColor(), joinGameRequestBody.gameID());

        try{
            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);
            ctx.result(serializer.toJson(joinGameResult));
        } catch (InvalidAuthException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(401);
        } catch (GameNotFoundException | InvalidColorException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(400);
        } catch (ColorIsFullException e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(403);
        } catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            ctx.result(serializer.toJson(errorResult));
            ctx.status(500);
        }
    }
}
