package components.user;

import components.review.IReviewServices;
import components.review.IReviewServicesCont;
import models.Review;
import models.User;

import java.sql.SQLException;
import java.util.List;

public class UserServicesCont implements IUserServicesCont {
    private final IUserServices repo;

    public UserServicesCont(final IUserServices repo) {
        this.repo = repo;
    }

    public List<String> getAllUsers() throws SQLException {
        List<String> users = repo.getAllUsers();
        return users;
    }

    public String deleteUserById(int userId) throws SQLException {
        boolean added = repo.deleteUserById(userId);
        return added ? "User deleted" : "User not deleted";
    }
}
