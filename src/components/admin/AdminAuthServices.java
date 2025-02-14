package components.admin;

import java.sql.*;

public class AdminAuthServices implements IAdminAuthServices {
    private final Connection connection;

    public AdminAuthServices(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Admin login successful, Welcome, " + username);
                return true;
            } else {
                System.out.println("Wrong username or password for admin.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
