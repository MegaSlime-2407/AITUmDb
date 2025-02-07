package components.open;

import models.Film;
import models.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OpenServices {
    private Connection connection;

    public OpenServices(Connection connection) {
        this.connection = connection;
    }
    public List<Film> getFilms() throws SQLException {
        String sql = "SELECT * FROM films";
        List<Film> films = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                film.setFilm_id(resultSet.getInt("filmid"));
                film.setFilm_title(resultSet.getString("name"));
                film.setFilm_genre(resultSet.getString("genre"));
                film.setFilm_rating(resultSet.getDouble("rating"));
                film.setFilm_description(resultSet.getString("description"));
                films.add(film);
            }
        }
        return films;
    }
    public Film getFilmById() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film id: ");
        String id = scanner.nextLine();
        String sql = "SELECT * FROM films WHERE filmid = ?";
        Film film = new Film();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                film.setFilm_id(resultSet.getInt("filmid"));
                film.setFilm_title(resultSet.getString("name"));
                film.setFilm_genre(resultSet.getString("genre"));
                film.setFilm_rating(resultSet.getDouble("rating"));
                film.setFilm_description(resultSet.getString("description"));
            }
        }
        return film;
    }

    public List<Review> getReviews(int film_id) throws SQLException {
        String sql = "SELECT * FROM usersreviews WHERE product_id = ?";
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, film_id);
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
}
