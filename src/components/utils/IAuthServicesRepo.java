package components.utils;

import java.sql.SQLException;

public interface IAuthServicesRepo {
    int login(String username, String password) throws SQLException;
    int register(String username, String password) throws SQLException;
    boolean isUserExist(String username) throws SQLException;
    void addUserToDatabase(String username, String password) throws SQLException;
}
