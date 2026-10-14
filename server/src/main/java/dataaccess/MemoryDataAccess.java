package dataaccess;

import kotlin.NotImplementedError;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDataAccess implements DataAccess{

    private Map<String, UserData> users = new HashMap<>();
    private Map<String, AuthData> auths = new HashMap<>();
    private Map<Integer, GameData> games = new HashMap<>();

    @Override
    public void createUser(UserData userData) {
        users.put(userData.username(), userData);

    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public void createAuth(AuthData authData){
        auths.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuth(String authToken){
        return auths.get(authToken);
    }

    @Override
    public Map<Integer, GameData> getGamesList(){
        return games;
    }

    @Override
    public void createGame(GameData gameData){
        games.put(gameData.gameID(), gameData);
    }

    @Override
    public GameData getGame(Integer gameID){
        return games.get(gameID);
    }

    @Override
    public void updateGame(GameData updatedGame){
        games.replace(updatedGame.gameID(), updatedGame);
    }

    @Override
    public void deleteAuth(String authToken){
        auths.remove(authToken);
    }



    @Override
    public void clear() {
        users.clear();
        auths.clear();
    }
}
