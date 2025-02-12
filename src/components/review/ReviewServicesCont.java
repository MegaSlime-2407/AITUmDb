package components.review;

import models.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewServicesCont implements IReviewServicesCont{
    private final IReviewServices repo;
    public ReviewServicesCont(final IReviewServices repo) {
        this.repo = repo;
    }
    public String getReviews(int filmId) throws SQLException {
        List<Review> reviews = repo.getReviews(filmId);
        StringBuilder sb = new StringBuilder();
        for (Review review : reviews) {
            sb.append(review.toString()).append("\n");
        }
        return sb.toString();
    }
    public List <Review> getAllReviewsByUserID() throws SQLException{

        List<Review> reviews = repo.getAllReviewsByUserID();
        return reviews;
    }
    public String leaveReview(int productId, int userId, String description, double rating) throws SQLException {
        boolean added = repo.leaveReview(productId, userId, description, rating);
        return added ? "Review added" : "Review not added";
    }
    public String updateRating(int productId) throws SQLException {
        boolean added = repo.updateRating(productId);
        return added ? "Review updated" : "Review not updated";
    }
}
