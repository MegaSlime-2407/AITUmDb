package components.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServices implements IUserServices {
    private final Connection connection;

    public UserServices(Connection connection) {
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
    public void deleteUserById(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        String delete = "DELETE FROM usersreviews WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             PreparedStatement deleteStmt = connection.prepareStatement(delete)) {
            pstmt.setInt(1, userId);
            deleteStmt.setInt(1, userId);
            pstmt.executeUpdate();
            deleteStmt.executeUpdate();
            System.out.println("User deleted successfully.");
        }
    }
}