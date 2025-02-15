package components.review;

import models.Review;

import java.sql.SQLException;
import java.util.List;

public interface IReviewServicesRepo {
    List<Review> getReviews(int filmId) throws SQLException;
    boolean leaveReview(int productId, int userId, String description, double rating) throws SQLException;
    boolean updateRating(int productId) throws SQLException;
    List<Review> getAllReviewsByUserID() throws SQLException;
}
