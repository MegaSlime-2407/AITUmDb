package components.open;

import models.Film;
import models.Review;

import java.sql.SQLException;
import java.util.List;

public interface open {
    List<Film> getFilms() throws SQLException;

    Film getFilmById() throws SQLException;

    List<Review> getAllReviewsByUserId() throws SQLException;
}
