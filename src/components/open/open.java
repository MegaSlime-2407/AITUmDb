package components.open;

import models.Review;

import java.util.List;

public interface open {
    List<Review> getAllReviewsByUserId(int userId);
}
