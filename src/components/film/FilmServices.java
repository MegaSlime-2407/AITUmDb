package components.film;

import models.Film;
import components.observers.Observer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmServices implements IFilmServices {
    private final Connection connection;
    private final List<Observer> observers = new ArrayList<>();

    public FilmServices(Connection connection) {
        this.connection = connection;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(int filmId) {
        for (Observer observer : observers) {
            observer.update(filmId);
        }
    }

    @Override
    public List<Film> getFilms() throws SQLException {
        return List.of();
    }

    @Override
    public Film getFilmById(int filmId) throws SQLException {
        return null;
    }

    @Override
    public void addFilm(Film film) throws SQLException {
        String sql = "INSERT INTO films (name, genre, rating, description) VALUES (?, ?, 0, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, film.getFilm_title());
            pstmt.setString(2, film.getFilm_genre());
            pstmt.setString(3, film.getFilm_description());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int filmId = generatedKeys.getInt(1);
                notifyObservers(filmId);
            }

            System.out.println("Film added successfully");
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
            notifyObservers(film.getFilm_id());
            System.out.println("Film updated successfully");
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
            notifyObservers(filmId);
            System.out.println("Film deleted successfully");
        }
    }
}

