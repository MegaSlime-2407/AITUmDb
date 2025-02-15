package components.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServicesRepo implements IUserServicesRepo {
    private final Connection connection;

    public UserServicesRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        List<String> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String user = "ID: " + resultSet.getInt("id") + ", Username: " + resultSet.getString("name");
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public boolean deleteUserById(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql))
            {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");
        }
        return false;
    }
}