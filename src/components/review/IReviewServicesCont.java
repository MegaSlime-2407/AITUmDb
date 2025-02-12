package components.review;

import models.Review;

import java.sql.SQLException;
import java.util.List;

public interface IReviewServicesCont {
    List <Review> getReviews(int filmId) throws SQLException;
    List<Review> getAllReviewsByUserID() throws SQLException;
    String leaveReview(int productId, int userId, String description, double rating) throws SQLException;
    String updateRating(int productId) throws SQLException;
}
