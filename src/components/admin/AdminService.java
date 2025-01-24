package components.admin;
import models.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

public class AdminService {
    private DataSource dataSource;

    public AdminService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addFilm(Film film) throws SQLException {
        String sql = "INSERT INTO film (name, genre, rating, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, film.getFilm_title());
            pstmt.setString(2, film.getFilm_genre());
            pstmt.setDouble(3, film.getFilm_rating());
            pstmt.setString(4, film.getFilm_description());

            pstmt.executeUpdate();
        }
    }

    public void deleteFilm(Film film) throws SQLException {
        String sql = "DELETE FROM film WHERE film_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, film.getFilm_id());

            pstmt.executeUpdate();
        }
    }
}