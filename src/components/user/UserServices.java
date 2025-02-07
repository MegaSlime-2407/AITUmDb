package components.user;

import models.Film;
import models.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserServices {
    private Connection connection;
    public UserServices(Connection connection) {
        this.connection = connection;
    }

    public int login() throws SQLException {
        String sql = "SELECT * FROM users where name=? and password=?";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Login successful, Welcome, " + username);
                return resultSet.getInt("id");
            }
            else {
                System.out.println("Wrong name or password, try again");
                return 0;
            }
        }
    }

    public void updateRating(int product_id) throws SQLException {
        String query = "UPDATE films SET rating = ? WHERE filmid = ?";
        String query2 = "SELECT rating FROM usersreviews WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
            preparedStatement.setInt(1, product_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            double total = 0;
            int count = 0;
            while (resultSet.next()) {
                total += resultSet.getDouble("rating");
                count++;
            }
            if (count > 0) {
                double averageRating = total / count;
                try (PreparedStatement preparedStatement2 = connection.prepareStatement(query)) {
                    preparedStatement2.setDouble(1, averageRating);
                    preparedStatement2.setInt(2, product_id);
                    preparedStatement2.executeUpdate();
                }
            }
        }
    }

    public void leaveReview(int product_id, int user_id) throws SQLException {
        String query = "INSERT into usersreviews(product_id, user_id, description, rating) VALUES (?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the review description: ");
            preparedStatement.setString(3, scanner.nextLine());
            System.out.println("Please enter the review rating: ");
            preparedStatement.setDouble(4, Double.parseDouble(scanner.next()));
            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.executeUpdate();
            updateRating(product_id);
        }
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
















