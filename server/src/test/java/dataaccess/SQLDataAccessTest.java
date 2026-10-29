package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SQLDataAccessTest {

    private static SQLDataAccess da;

    private UserData createUser(SQLDataAccess da){
        UserData newUser = new UserData("Rex", "forTheRepublic", "rex@501st.com");

        assertDoesNotThrow(() -> {
        da.createUser(newUser);
        });
        return newUser;

    }
    private AuthData createAuth(SQLDataAccess da){
        AuthData newAuth = new AuthData("123456", "Rex");
        da.createAuth(newAuth);

        return newAuth;
    }
    private GameData createGame(SQLDataAccess da, Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){
        GameData newGame = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
        da.createGame(newGame);

        return newGame;

    }

    @BeforeAll
    public static void createDatabase() throws SQLException, DataAccessException {
        try{
            da = new SQLDataAccess();
        } catch (SQLException e) {
            throw new SQLException(String.format("Error: Invalid Statement: %s", e.getMessage()));
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: Couldn't connect to the database");
        }
    }

    @Test
    void clear() {
        assertDoesNotThrow(() -> {
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
        });
    }

    @Test
    void getUser() {
        assertDoesNotThrow(() -> {
        UserData newUser = createUser(da);

        assertEquals(newUser.username(), da.getUser(newUser.username()).username());
        assertTrue(BCrypt.checkpw(newUser.password(), da.getUser(newUser.username()).password()));
        assertEquals(newUser.email(), da.getUser(newUser.username()).email());

        da.clear();
        });
    }

    @Test
    void getUserNotInList() {
        assertDoesNotThrow(() -> {
        assertNull(da.getUser("Cody"));
        });
    }
    @Test
    void getUserEmpty() {
        assertDoesNotThrow(() -> {
        assertNull(da.getUser("Rex"));
        });
    }

    @Test
    void getAuth() {
        assertDoesNotThrow(() -> {
        AuthData newAuth = createAuth(da);

        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        da.clear();
        });
    }
    @Test
    void getAuthNotInList() {
        assertNull(da.getAuth("654320"));
    }
    @Test
    void getAuthEmpty() {
        assertNull(da.getAuth("123456"));
    }

    @Test
    void deleteAuth() {
        assertDoesNotThrow(() -> {
        AuthData newAuth = createAuth(da);
        assertEquals(newAuth, da.getAuth(newAuth.authToken()));

        da.deleteAuth(newAuth.authToken());
        assertNull(da.getAuth(newAuth.authToken()));

        da.clear();
        });
    }

    @Test
    void getGame(){
        assertDoesNotThrow(() -> {
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);

        assertEquals(newGame, da.getGame(newGame.gameID()));

        da.clear();
        });
    }
    @Test
    void getGameNotInList(){
        assertDoesNotThrow(() -> {
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);

        assertNull(da.getGame(2));

        da.clear();
        });
    }
    @Test
    void getGameEmpty(){
        assertNull(da.getGame(1));
    }

    @Test
    void getListGames(){
        assertDoesNotThrow(() -> {
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "Rex", "Fives", "501st", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);

        assertEquals(listGames, da.getGamesList());

        da.clear();
        });
    }
    @Test
    void getListGamesEmpty(){
        assertDoesNotThrow(() -> {
        Map<Integer, GameData> listGames = Map.of();

        assertEquals(listGames, da.getGamesList());

        da.clear();
        });
    }

    @Test
    void updateGame(){
        assertDoesNotThrow(() -> {
        ChessGame newChessGame = new ChessGame();
        GameData newGame = createGame(da,1, "", "Fives", "501st", newChessGame);
        Map<Integer, GameData> listGames = Map.of(1, newGame);
        assertEquals(listGames, da.getGamesList());

        GameData joinedGame = new GameData(1, "Rex", "Fives", "501st", newChessGame);
        da.updateGame(joinedGame);
        Map<Integer, GameData> joinedListGames = Map.of(1, joinedGame);

        assertEquals(joinedListGames, da.getGamesList());

        da.clear();
        });
    }
}