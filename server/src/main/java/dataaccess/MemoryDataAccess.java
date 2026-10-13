package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryDataAccess implements DataAccess{

    //username, userData
    private Map<String, UserData> users = new HashMap<>();

    @Override
    public void createUser(String username, String password, String email) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public UserData getUser(String username) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Not implemented");
    }
}
