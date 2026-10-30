package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLDataAccess implements DataAccess{

    enum inputTypes {
        USERNAME,
        PASSWORD,
        EMAIL,
        GAME_NAME
    }

    private final String userTableStatement =
            """
                CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )""";

    private final String authTableStatement =
            """
                CREATE TABLE IF NOT EXISTS auths (
                authToken VARCHAR(255) NOT NULL,
                username VARCHAR(255) NOT NULL,
                PRIMARY KEY (authToken)
            )""";

    private final String gamesTableStatement =
            """
                CREATE TABLE IF NOT EXISTS games (
                gameID INT NOT NULL,
                whiteUsername VARCHAR(255) DEFAULT NULL,
                blackUsername VARCHAR(255) DEFAULT NULL,
                gameName VARCHAR(255) NOT NULL,
                gameData TEXT NOT NULL,
                PRIMARY KEY (gameID)
            )""";

    public SQLDataAccess() throws DataAccessException, SQLException {
        configureDatabase();
    }

    @Override
    public void createUser(UserData userData) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            if(sanitizeInput(userData.username(), inputTypes.USERNAME)){
                if(sanitizeInput(userData.password(), inputTypes.PASSWORD)){
                    String passwordHashed = hashPassword(userData.password());
                    if(sanitizeInput(userData.email(), inputTypes.EMAIL)){
                        var statement = "INSERT INTO users (username, password, email) VALUES(?, ?, ?)";
                        updateDatabase(statement, List.of(userData.username(), passwordHashed, userData.email()), conn);
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException(String.format("Error: Invalid Statement: %s", e.getMessage()));
        }
    }

    @Override
    public UserData getUser(String username) throws SQLException, DataAccessException {
        try(var conn = DatabaseManager.getConnection()){
            if(sanitizeInput(username, inputTypes.USERNAME)){
                var statement = "SELECT username, password, email FROM users WHERE username=?";
                try(var getUserStatement = conn.prepareStatement(statement)){
                    getUserStatement.setString(1, username);
                    try(var response = getUserStatement.executeQuery()){
                        return readGetUser(response);
                    }
                }

            }
        } catch (SQLException e) {
            throw new SQLException(String.format("Error: Invalid Statement: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            if(sanitizeInput(authData.username(), inputTypes.USERNAME)){
                var statement = "INSERT INTO auths (authToken, username) VALUES(?, ?)";
                updateDatabase(statement, List.of(authData.authToken(), authData.username()), conn);
            }
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            var statement = "SELECT authToken, username FROM auths WHERE authToken=?";
            try(var getAuthStatement = conn.prepareStatement(statement)){
                getAuthStatement.setString(1, authToken);
                try(var response = getAuthStatement.executeQuery()){
                    return readGetAuth(response);
                }
            }
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            var statement = "DELETE FROM auths WHERE authToken=?";
            updateDatabase(statement, List.of(authToken), conn);
        }
    }

    @Override
    public Map<Integer, GameData> getGamesList() throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gameData FROM games";
            try(var getGameStatement = conn.prepareStatement(statement)){
                try(var response = getGameStatement.executeQuery()){
                    return readGetGamesList(response);
                }
            }
        }
    }

    @Override
    public void createGame(GameData gameData) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            if(sanitizeInput(gameData.gameName(), inputTypes.GAME_NAME)){
                var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, gameData) VALUES(?, ?, ?, ?, ?)";
                updateDatabase(statement, List.of(gameData.gameID(), gameData.whiteUsername(),
                        gameData.blackUsername(), gameData.gameName(), gameData.game()), conn);
            }
        }
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()){
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, gameData FROM games WHERE gameID=?";
            try(var getGameStatement = conn.prepareStatement(statement)){
                getGameStatement.setInt(1, gameID);
                try(var response = getGameStatement.executeQuery()){
                    return readGetGame(response);
                }
            }
        }
    }

    @Override
    public void updateGame(GameData updatedGame) {

    }

    @Override
    public void clear() throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()) {
            var usersStatement = "TRUNCATE TABLE users";
            var authsStatement = "TRUNCATE TABLE auths";
            var gamesStatement = "TRUNCATE TABLE games";

            updateDatabase(usersStatement, List.of(), conn);
            updateDatabase(authsStatement, List.of(), conn);
            updateDatabase(gamesStatement, List.of(), conn);

        } catch (DataAccessException e) {
            throw new DataAccessException("Error: Couldn't connect to the database");
        } catch (SQLException e) {
            throw new SQLException(String.format("Error: Invalid Statement: %s", e.getMessage()));
        }

    }

    private void configureDatabase() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        createTable(userTableStatement);
        createTable(authTableStatement);
        createTable(gamesTableStatement);
    }

    private void createTable(String statement) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()) {
            updateDatabase(statement, List.of(), conn);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: Couldn't connect to the database");
        }
    }

    private static void updateDatabase(String statement, List<Object> parms, Connection conn) throws SQLException {
        try(var createTableStatement = conn.prepareStatement(statement)){
            if(!parms.isEmpty()){
                for(int i = 0; i < parms.size(); i++){
                    var parm = parms.get(i);
                    if(parm instanceof String){
                        createTableStatement.setString(i + 1, (String) parm);
                    }
                    else if(parm instanceof Integer){
                        createTableStatement.setInt(i + 1, (Integer) parm);
                    }
                    else if(parm instanceof ChessGame){
                        String game = new Gson().toJson(parm);
                        createTableStatement.setString(i + 1, game);
                    }
                }
            }
            createTableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(String.format("Error: Invalid Statement: %s", e.getMessage()));
        }
    }

    private boolean sanitizeInput(String input, inputTypes type){
        if(type == inputTypes.USERNAME){
            return input.matches("\\w+");
        }
        else if(type == inputTypes.PASSWORD){
            return input.matches("\\w+[!@#$%_]*");
        }
        else if(type == inputTypes.EMAIL){
            return input.matches("\\w+@\\w+\\.\\w+");
        }
        else if(type == inputTypes.GAME_NAME){
            return input.matches("\\w+");
        }
        else {
            return false;
        }
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private UserData readGetUser(ResultSet result) throws SQLException {
        String username = null;
        String password = null;
        String email = null;

        while(result.next()) {
            username = result.getString("username");
            password = result.getString("password");
            email = result.getString("email");
        }

        if(username != null) {
            return new UserData(username, password, email);
        }
        else {
            return null;
        }
    }

    private AuthData readGetAuth(ResultSet result) throws SQLException {
        String authToken = null;
        String username = null;

        while(result.next()) {
            authToken = result.getString("authToken");
            username = result.getString("username");
        }

        if(authToken != null) {
            return new AuthData(authToken, username);
        }
        else {
            return null;
        }
    }
    private GameData readGetGame(ResultSet result) throws SQLException {
        Integer gameID = null;
        String whiteUsername = null;
        String blackUsername = null;
        String gameName = null;
        String game = null;

        while(result.next()) {
            gameID = result.getInt("gameID");
            whiteUsername = result.getString("whiteUsername");
            blackUsername = result.getString("blackUsername");
            gameName = result.getString("gameName");
            game = result.getString("gameData");
        }

        if(gameID != null) {
            ChessGame chessGame = new Gson().fromJson(game, ChessGame.class);
            return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
        }
        else {
            return null;
        }
    }

    private Map<Integer, GameData> readGetGamesList(ResultSet result) throws SQLException {
        Map<Integer, GameData> games = new HashMap<>();

        Integer gameID = null;
        String whiteUsername = null;
        String blackUsername = null;
        String gameName = null;
        String game = null;

        while(result.next()) {
            gameID = result.getInt("gameID");
            whiteUsername = result.getString("whiteUsername");
            blackUsername = result.getString("blackUsername");
            gameName = result.getString("gameName");
            game = result.getString("gameData");

            if(gameID != null) {
                ChessGame chessGame = new Gson().fromJson(game, ChessGame.class);
                GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
                games.put(gameID, gameData);
            }
        }

        return games;


    }
}


