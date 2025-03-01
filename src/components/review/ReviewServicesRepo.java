package components.review;

import models.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReviewServicesRepo implements IReviewServices {
    private final Connection connection;

    public ReviewServicesRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Review> getReviews(int filmId) throws SQLException {
        String sql = "SELECT * FROM usersreviews WHERE product_id = ?";
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("id"));
                review.setProduct_id(resultSet.getInt("product_id"));
                review.setUser_id(resultSet.getInt("user_id"));
                review.setDescription(resultSet.getString("description"));
                review.setRating(resultSet.getDouble("rating"));
                reviews.add(review);
            }
        }
        return reviews;
    }
    @Override
    public List<Review> getAllReviewsByUserID() throws SQLException{
        String sql = "SELECT ur.*, m.name AS movie_name "+
                "FROM usersreviews ur "+
                "JOIN films m ON ur.product_id = m.filmid " +
                "WHERE ur.user_id=?";
        List<Review> reviews = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user id:");
        int userId = Integer.parseInt(scanner.nextLine());
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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

    @Override
    public boolean leaveReview(int productId, int userId, String description, double rating) throws SQLException {
        String sql = "INSERT INTO usersreviews (product_id, user_id, description, rating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, rating);
            preparedStatement.executeUpdate();
            updateRating(productId);
        }
        return false;
    }

    @Override
    public boolean updateRating(int productId) throws SQLException {
        String query = "UPDATE films SET rating = ? WHERE filmid = ?";
        String query2 = "SELECT rating FROM usersreviews WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
            preparedStatement.setInt(1, productId);
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
                    preparedStatement2.setInt(2, productId);
                    preparedStatement2.executeUpdate();
                }
            }
        }
        return false;
    }
}