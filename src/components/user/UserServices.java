package components.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServices {
    private Connection connection;

    public UserServices(Connection connection) {
        this.connection = connection;
    }
    public void register(String username, String password) {
        if (isUserExist(username)) {
            System.out.println("User already exists"); }
            else {
            addUserToDatabase(username, password);
            System.out.println("User registered successfully.");
        }
    }

    private boolean isUserExist(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement) = connection.prepareStatement(query)) {
    preparedStatement.setString(1, username);
    ResultSet resultSet = preparedStatement.executeQuery();
    if ( resulSet.next()) {
        return resultSet.getInt(1) > 0;
    }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }
    }

    private void addUserToDatabase(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        }
catch (SQLException e) {
            e.printStackTrace();

}
    }
}
















