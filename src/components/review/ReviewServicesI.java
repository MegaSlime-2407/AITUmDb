package components.review;

import models.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewServicesI {
    List<Review> getReviews(int filmId) throws SQLException;
    void leaveReview(int productId, int userId, String description, double rating) throws SQLException;
    void updateRating(int productId) throws SQLException;
}
