package components.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServices {
    private Connection connection;

    public UserServices(Connection connection) {
        this.connection = connection;
    }
    public void register(String username, String password) throws SQLException {
        if (isUserExist(username)) {
            System.out.println("User already exists"); }
            else {
            addUserToDatabase(username, password);
            System.out.println("User registered successfully.");
        }
    }

    private boolean isUserExist(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    preparedStatement.setString(1, username);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
        return resultSet.getInt(1) > 0;
    }
    }
        return false;
    }

    private void addUserToDatabase(String username, String password) {
        String query = "INSERT INTO users (name, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        }
catch (SQLException e) {
            e.printStackTrace();

}
    }
}
















