package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.List;
import java.util.Map;

public interface DataAccess {
    void createUser(UserData userData);
    UserData getUser(String username);

    void createAuth(AuthData authData);
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);

    Map<Integer, GameData> getGamesList();
    void createGame(GameData gameData);
    GameData getGame(Integer gameID);
    void updateGame(GameData updatedGame);


    void clear();
}
