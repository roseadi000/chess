package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SQLDataAccess implements DataAccess{

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
                gameData VARCHAR(255) NOT NULL,
                PRIMARY KEY (gameID)
            )""";

    public SQLDataAccess() throws DataAccessException, SQLException {
        configureDatabase();
    }

    @Override
    public void createUser(UserData userData) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public void createAuth(AuthData authData) {

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public Map<Integer, GameData> getGamesList() {
        return Map.of();
    }

    @Override
    public void createGame(GameData gameData) {

    }

    @Override
    public GameData getGame(Integer gameID) {
        return null;
    }

    @Override
    public void updateGame(GameData updatedGame) {

    }

    @Override
    public void clear() {

    }

    private void configureDatabase() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
        createTable(userTableStatement);
        createTable(authTableStatement);
        createTable(gamesTableStatement);
    }

    private void createTable(String statement) throws DataAccessException, SQLException {
        try(var conn = DatabaseManager.getConnection()) {
            updateDatabase(statement, conn);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: Couldn't connect to the database");
        }
    }

    private static void updateDatabase(String statement, Connection conn) throws SQLException {
        try(var createTableStatement = conn.prepareStatement(statement)){
            createTableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(String.format("Error: Invalid Statement: %s", e.getMessage()));
        }
    }
}
