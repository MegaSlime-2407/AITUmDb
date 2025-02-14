package components.film;

import models.Film;

import java.sql.SQLException;
import java.util.List;

public interface IFilmServices {
    List<Film> getFilms() throws SQLException;
    Film getFilmById(int filmId) throws SQLException;
    boolean addFilm(Film film) throws SQLException;
    boolean deleteFilm(int filmId) throws SQLException;
    boolean updateFilm(Film film) throws SQLException;
}
