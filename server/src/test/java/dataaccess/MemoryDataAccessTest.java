package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDataAccessTest {

    private UserData createUser(MemoryDataAccess da){
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");
        da.createUser(newUser);

        return newUser;
    }
    private AuthData createAuth(MemoryDataAccess da){
        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);

        return newAuth;
    }
    private GameData createGame(MemoryDataAccess da, Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){
        GameData newGame = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
        da.createGame(newGame);

        return newGame;

    }

    @Test
    void getUser() {
        var da = new MemoryDataAccess();
        UserData newUser = createUser(da);

        assertEquals(newUser, da.getUser(newUser.username()));
    }
    @Test
    void getUserNotInList() {
        var da = new MemoryDataAccess();
        UserData newUser = createUser(da);

        assertNull(da.getUser("Cody"));
    }
    @Test
    void getUserEmpty() {
        var da = new MemoryDataAccess();

        assertNull(da.getUser("Rex"));
    }

    @Test
    void getAuth() {
        var da = new MemoryDataAccess();
        AuthData newAuth = createAuth(da);

        assertEquals(newAuth, da.getAuth(newAuth.authToken()));
    }
    @Test
    void getAuthNotInList() {
        var da = new MemoryDataAccess();
        AuthData newAuth = createAuth(da);

        assertNull(da.getAuth("654320"));
    }
    @Test
    void getAuthEmpty() {
        var da = new MemoryDataAccess();

        assertNull(da.getAuth("123456"));
    }

    @Test
    void deleteAuth() {
        var da = new MemoryDataAccess();
        AuthData newAuth = createAuth(da);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        da.deleteAuth(newAuth.authToken());
        assertNull(da.getAuth(newAuth.authToken()));

    }

    @Test
    void getGame(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);

        assertEquals(newGame, da.getGame(newGame.gameID()));
    }
    @Test
    void getGameNotInList(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);

        assertNull(da.getGame(2));
    }
    @Test
    void getGameEmpty(){
        var da = new MemoryDataAccess();

        assertNull(da.getGame(1));
    }

    @Test
    void getListGames(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);

        assertEquals(listGames, da.getGamesList());
    }
    @Test
    void getListGamesEmpty(){
        var da = new MemoryDataAccess();
        Map<Integer, GameData> listGames = Map.of();

        assertEquals(listGames, da.getGamesList());
    }

    @Test
    void updateGame(){
        var da = new MemoryDataAccess();
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "", "Fives", "501st", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);
        assertEquals(listGames, da.getGamesList());

        GameData joinedGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);
        Map<Integer, GameData> joinedListGames = Map.of(1, joinedGame);

        assertEquals(joinedListGames, da.getGamesList());



    }

    @Test
    void clear() {
        var da = new MemoryDataAccess();
        UserData newUser = createUser(da);
        assertEquals(newUser, da.getUser(newUser.username()));

        AuthData newAuth = createAuth(da);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da, 1, "Rex", "Cody", "Clones", newChessGame);
        assertEquals(newGame, da.getGame(newGame.gameID()));


        da.clear();
        assertNull(da.getUser(newUser.username()));
        assertNull(da.getAuth(newAuth.authToken()));
        assertNull(da.getGame(newGame.gameID()));
    }
}