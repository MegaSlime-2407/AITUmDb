package components.admin;
import models.Film;
import models.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService   {
    private Connection connection;

    public AdminService(Connection connection) {
        this.connection = connection;
    }
    public boolean login() throws SQLException {
        String sql = "SELECT * FROM admin where name=? and password=?";
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
                return true;
            }
            else {
                System.out.println("Wrong name or password, try again");
                return false;
            }
        }
    }

    public void addFilm() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film title: ");
        String title = scanner.nextLine();
        System.out.print("Enter film genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter film description: ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO films (name, genre, rating, description) VALUES (?, ?, 0, ?)";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setString(3, description);

            pstmt.executeUpdate();
            System.out.println("Film added successfully");
        }
    }

    public void deleteFilm() throws SQLException {
        String sql = "DELETE FROM films WHERE filmid = ?";
        String sql2 = "DELETE FROM usersreviews WHERE product_id = ?";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film id: ");
        String id = scanner.nextLine();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setInt(1, Integer.parseInt(id));

            pstmt.setInt(1, Integer.parseInt(id));

            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            System.out.println("Film deleted successfully");
        }
    }
        public void updateFilm() throws SQLException {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter film ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            String fetchSql = "SELECT * FROM films WHERE filmid = ?";
            Film film = null;

            try (PreparedStatement fetchStmt = connection.prepareStatement(fetchSql)) {
                fetchStmt.setInt(1, id);
                ResultSet resultSet = fetchStmt.executeQuery();

                if (resultSet.next()) {
                    film = new Film();
                    film.setFilm_id(resultSet.getInt("filmid"));
                    film.setFilm_title(resultSet.getString("name"));
                    film.setFilm_genre(resultSet.getString("genre"));
                    film.setFilm_description(resultSet.getString("description"));
                } else {
                    System.out.println("No film found with the given ID.");
                    return;
                }
            }

            System.out.println("Current title: " + film.getFilm_title());
            System.out.print("Enter new title (leave blank to keep current): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                film.setFilm_title(newTitle);
            }

            System.out.println("Current genre: " + film.getFilm_genre());
            System.out.print("Enter new genre (leave blank to keep current): ");
            String newGenre = scanner.nextLine();
            if (!newGenre.isEmpty()) {
                film.setFilm_genre(newGenre);
            }


            System.out.println("Current description: " + film.getFilm_description());
            System.out.print("Enter new description (leave blank to keep current): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                film.setFilm_description(newDescription);
            }

            // Update the film in the database
            String updateSql = "UPDATE films SET name = ?, genre = ?, rating = 0, description = ? WHERE filmid = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setString(1, film.getFilm_title());
                updateStmt.setString(2, film.getFilm_genre());
                updateStmt.setString(3, film.getFilm_description());
                updateStmt.setInt(4, film.getFilm_id());

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Film updated successfully!");
                } else {
                    System.out.println("No changes were made.");
                }
            }
        }

    public List<Review> getAllReviewsByUserId() throws SQLException {
        String sql = "SELECT ur.*, m.name AS movie_name " +
                "FROM usersreviews ur " +
                "JOIN films m ON ur.product_id = m.filmid " +
                "WHERE ur.user_id = ?";
        String sql1 = "SELECT * FROM usersreviews WHERE user_id = ?";
        List<Review> reviews = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user id: ");
        int userId = Integer.parseInt(scanner.nextLine());
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("id"));
                review.setProduct_id(resultSet.getInt("product_id"));
                review.setUser_id(resultSet.getInt("user_id"));
                review.setDescription(resultSet.getString("description"));
                review.setRating(resultSet.getDouble("rating"));
                review.setName(resultSet.getString("movie_name"));
                reviews.add(review);
            }
        }
        return reviews;
    }
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


    public void deleteUserByID() throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        String delete = "DELETE FROM usersreviews WHERE user_id = ?";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user id: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (PreparedStatement deleteStmt = connection.prepareStatement(delete)) {
                pstmt.setInt(1, userId);
                int rowsAffected = pstmt.executeUpdate();
                deleteStmt.setInt(1, userId);

                if (rowsAffected > 0) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("No user found with the given ID.");
                }
            }
        }
    }

    public void deleteReview() throws SQLException {
        String sql = "DELETE FROM usersreviews WHERE id = ?";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter review id: ");
        String id = scanner.nextLine();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setInt(1, Integer.parseInt(id));

            pstmt.executeUpdate();
            System.out.println("Review deleted successfully");
        }
    }
}