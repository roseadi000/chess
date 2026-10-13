package dataaccess;

import model.UserData;

public interface DataAccess {
    void createUser(String username, String password, String email);
    UserData getUser(String username);

    void clear();
}
