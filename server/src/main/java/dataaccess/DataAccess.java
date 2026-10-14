package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.List;

public interface DataAccess {
    void createUser(UserData userData);
    UserData getUser(String username);

    void createAuth(AuthData authData);
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);

    List<GameData> getGamesList();
    void createGame(String gameName);
    void joinGame(String gameID);


    void clear();
}
