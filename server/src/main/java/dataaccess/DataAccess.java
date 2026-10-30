package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DataAccess {
    void createUser(UserData userData) throws DataAccessException, SQLException;
    UserData getUser(String username) throws SQLException, DataAccessException;

    void createAuth(AuthData authData) throws DataAccessException, SQLException;
    AuthData getAuth(String authToken) throws DataAccessException, SQLException;
    void deleteAuth(String authToken) throws DataAccessException, SQLException;

    Map<Integer, GameData> getGamesList();
    void createGame(GameData gameData) throws DataAccessException, SQLException;
    GameData getGame(Integer gameID) throws DataAccessException, SQLException;
    void updateGame(GameData updatedGame);


    void clear() throws DataAccessException, SQLException;
}
