package dataaccess;

import kotlin.NotImplementedError;
import model.UserData;

public class UserMemoryDAO implements UserDAO{

    @Override
    public void createUser(String username, String password, String email) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public UserData getUser(String username) {
        throw new RuntimeException("Not implemented");
    }
}
