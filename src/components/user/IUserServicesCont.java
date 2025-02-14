package components.user;

import java.sql.SQLException;
import java.util.List;

public interface IUserServicesCont {
    List<String> getAllUsers() throws SQLException;
    String deleteUserById(int userId) throws SQLException;
}
