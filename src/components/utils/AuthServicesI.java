package components.utils;

import java.sql.SQLException;

public interface AuthServicesI {
    int login(String username, String password) throws SQLException;
    void register(String username, String password) throws SQLException;
}
