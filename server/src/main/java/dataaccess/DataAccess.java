package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DataAccess {
    void createUser(UserData userData) throws DataAccessException, SQLException, InvalidInputException;
    UserData getUser(String username) throws SQLException, DataAccessException, InvalidInputException;

    void createAuth(AuthData authData) throws DataAccessException, SQLException, InvalidInputException;
    AuthData getAuth(String authToken) throws DataAccessException, SQLException;
    void deleteAuth(String authToken) throws DataAccessException, SQLException;

    Map<Integer, GameData> getGamesList() throws DataAccessException, SQLException;
    void createGame(GameData gameData) throws DataAccessException, SQLException, InvalidInputException;
    GameData getGame(Integer gameID) throws DataAccessException, SQLException;
    void updateGame(GameData updatedGame) throws DataAccessException, SQLException;


    void clear() throws DataAccessException, SQLException;
}
