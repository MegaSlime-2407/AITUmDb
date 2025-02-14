package components.film;

import models.Film;

import java.sql.SQLException;
import java.util.List;

public interface IFilmServicesCont {
    List<Film> getFilms() throws SQLException;
    Film getFilmById(int filmId) throws SQLException;
    String addFilm(Film film) throws SQLException;
    String deleteFilm(int filmId) throws SQLException;
    String updateFilm(Film film) throws SQLException;
}
