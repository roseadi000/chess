package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDataAccessTest {

    @Test
    void getUser() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);

        assertEquals(newUser, da.getUser(newUser.username()));
    }

    @Test
    void getAuth() {
        var da = new MemoryDataAccess();
        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);

        assertEquals(newAuth, da.getAuth(newAuth.authToken()));
    }

    @Test
    void deleteAuth() {
        var da = new MemoryDataAccess();
        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        da.deleteAuth(newAuth.authToken());
        assertNull(da.getAuth(newAuth.authToken()));

    }

    @Test
    void getGame(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = new GameData(1, "Rex", "Cody", "Clones", newChessGame);
        da.createGame(newGame);

        assertEquals(newGame, da.getGame(newGame.gameID()));
    }

    @Test
    void getListGames(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = new GameData(1, "Rex", "Cody", "Clones", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);
        da.createGame(newGame);

        assertEquals(listGames, da.getGamesList());
    }

    @Test
    void updateGame(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = new GameData(1, "", "Cody", "Clones", newChessGame);
        GameData joinedGame = new GameData(1, "Rex", "Cody", "Clones", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);
        da.createGame(newGame);
        assertEquals(listGames, da.getGamesList());

        Map<Integer, GameData> joinedListGames = Map.of(1, joinedGame);
        da.updateGame(joinedGame);

        assertEquals(joinedListGames, da.getGamesList());



    }

    @Test
    void clear() {
        var da = new MemoryDataAccess();
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);
        assertEquals(newUser, da.getUser(newUser.username()));

        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        ChessGame newChessGame = new ChessGame();
        GameData newGame = new GameData(1, "Rex", "Cody", "Clones", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);
        da.createGame(newGame);
        assertEquals(listGames, da.getGamesList());

        da.clear();
        assertNull(da.getUser(newUser.username()));
        assertNull(da.getAuth(newAuth.authToken()));
        assertNull(da.getGamesList());
    }
}