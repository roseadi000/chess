package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.JoinGameResult;
import result.ListGamesResult;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    void listGames() {
        assertDoesNotThrow(() -> {
            DataAccess da = new MemoryDataAccess();
            AuthData userAuth = createUser(da);

            GameService gameService = new GameService(da);
            ChessGame chessGame = new ChessGame();
            GameData game = new GameData(1, "Rex", "Fives", "501st", chessGame);
            da.createGame(game);

            ListGamesRequest listGamesRequest = new ListGamesRequest(userAuth.authToken());
            ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);
            ListGamesResult listGamesResultExpected = new ListGamesResult(Map.of(1, game));

            assertEquals(listGamesResultExpected, listGamesResult);
        });
    }

    @Test
    void createGame() {
        assertDoesNotThrow(() -> {
            DataAccess da = new MemoryDataAccess();
            AuthData userAuth = createUser(da);

            GameService gameService = new GameService(da);
            ChessGame chessGame = new ChessGame();
            int gameID = new Random(45).nextInt(10000);
            GameData game = new GameData(gameID, null, null, "501st", chessGame);
            CreateGameRequest createGameRequest = new CreateGameRequest(userAuth.authToken(), "501st");

            ListGamesResult createGameResult = gameService.createGame(createGameRequest);
            ListGamesResult listGamesResultExpected = new ListGamesResult(Map.of(game.gameID(), game));

            assertEquals(listGamesResultExpected, createGameResult);
        });
    }

    @Test
    void joinGame() {
        assertDoesNotThrow(() -> {
            DataAccess da = new MemoryDataAccess();
            AuthData userAuth = createUser(da);

            GameService gameService = new GameService(da);
            ChessGame chessGame = new ChessGame();
            GameData game = new GameData(1, null, "Fives", "501st", chessGame);
            da.createGame(game);
            GameData gameExpected = new GameData(1, "Rex", "Fives", "501st", chessGame);

            JoinGameRequest joinGameRequest = new JoinGameRequest(userAuth.authToken(), "WHITE", 1);
            JoinGameResult joinGameResult = gameService.joinGame(joinGameRequest);
            JoinGameResult joinGameResultExpected = new JoinGameResult();

            assertEquals(joinGameResultExpected, joinGameResult);

            ListGamesRequest listGamesRequest = new ListGamesRequest(userAuth.authToken());
            ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);
            ListGamesResult listGamesResultExpected = new ListGamesResult(Map.of(1, gameExpected));

            assertEquals(listGamesResultExpected, listGamesResult);
        });
    }

    private static AuthData createUser(DataAccess da) {
        UserService userService = new UserService(da);
        UserData user = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(user);
        AuthData auth = new AuthData("123456", "Rex");
        da.createAuth(auth);

        return auth;
    }
}