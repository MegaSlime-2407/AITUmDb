package components.user;

import java.sql.SQLException;
import java.util.List;

public interface UserServicesI {
    List<String> getAllUsers() throws SQLException;
    void deleteUserById(int userId) throws SQLException;
}
