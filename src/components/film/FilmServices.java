package components.film;

import models.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmServices implements IFilmServices {
    private final Connection connection;

    public FilmServices(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Film> getFilms() throws SQLException {
        String sql = "SELECT * FROM films";
        List<Film> films = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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

    @Override
    public Film getFilmById(int filmId) throws SQLException {
        String sql = "SELECT * FROM films WHERE filmid = ?";
        Film film = new Film();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                film.setFilm_id(resultSet.getInt("filmid"));
                film.setFilm_title(resultSet.getString("name"));
                film.setFilm_genre(resultSet.getString("genre"));
                film.setFilm_rating(resultSet.getDouble("rating"));
                film.setFilm_description(resultSet.getString("description"));
            }
        }
        return film;
    }

    @Override
    public void addFilm(Film film) throws SQLException {
        String sql = "INSERT INTO films (name, genre, rating, description) VALUES (?, ?, 0, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, film.getFilm_title());
            pstmt.setString(2, film.getFilm_genre());
            pstmt.setString(3, film.getFilm_description());
            pstmt.executeUpdate();
            System.out.println("Film added successfully");
        }
    }

    @Override
    public void deleteFilm(int filmId) throws SQLException {
        String sql = "DELETE FROM films WHERE filmid = ?";
        String sql2 = "DELETE FROM usersreviews WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
            pstmt.setInt(1, filmId);
            pstmt2.setInt(1, filmId);
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            System.out.println("Film deleted successfully");
        }
    }

    @Override
    public void updateFilm(Film film) throws SQLException {
        String sql = "UPDATE films SET name = ?, genre = ?, description = ? WHERE filmid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, film.getFilm_title());
            pstmt.setString(2, film.getFilm_genre());
            pstmt.setString(3, film.getFilm_description());
            pstmt.setInt(4, film.getFilm_id());
            pstmt.executeUpdate();
            System.out.println("Film updated successfully");
        }
    }
}
