package components.utils;

import models.User;

import java.sql.*;

public class AuthServicesRepo implements IAuthServices {
    private final Connection connection;

    public AuthServicesRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int login(String username, String password) {
        String sql = "SELECT id, password FROM users WHERE name=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");
                if (PasswordUtils.verifyPassword(password, storedHash)) {
                    System.out.println("Login successful, Welcome, " + username);
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Wrong name or password, try again");
        return 0;
    }

    @Override
    public int register(String username, String password) throws SQLException {
        if (isUserExist(username)) {
            System.out.println("User already exists.");
        } else {
            addUserToDatabase(username, password);
            System.out.println("User registered successfully.");
        }
        return 0;
    }

    public boolean isUserExist(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }

    public void addUserToDatabase(String username, String password) throws SQLException {
        String query = "INSERT INTO users (name, password) VALUES (?, ?)";
        String hashedPassword = PasswordUtils.hashPassword(password);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
        }
    }

    public boolean register(User user) {
        return false;
    }
}

