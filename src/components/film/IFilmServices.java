package components.film;

import models.Film;

import java.sql.SQLException;
import java.util.List;

public interface IFilmServices {
    List<Film> getFilms() throws SQLException;
    Film getFilmById(int filmId) throws SQLException;
    void addFilm(Film film) throws SQLException;
    void deleteFilm(int filmId) throws SQLException;
    void updateFilm(Film film) throws SQLException;
}
