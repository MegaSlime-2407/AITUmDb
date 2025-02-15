package components.film;

import models.Film;

import java.sql.SQLException;
import java.util.List;

public class FilmServicesCont implements IFilmServicesCont {

    private final IFilmServicesRepo repo;
    public FilmServicesCont(final IFilmServicesRepo repo) {
        this.repo = repo;
    }

    public List<Film> getFilms() throws SQLException {
        List<Film> films = repo.getFilms();
        return films;
    }

    public Film getFilmById(int filmId) throws SQLException {
        Film film = repo.getFilmById(filmId);
        return film;
    }
    public String addFilm(Film film) throws SQLException {
        boolean added = repo.addFilm(film);
        return added ? "Film added" : "Film not added";
    }
    public String deleteFilm(int filmId) throws SQLException {
        boolean added = repo.deleteFilm(filmId);
        return added ? "Film deleted" : "Film not deleted";
    }
    public String updateFilm(Film film) throws SQLException {
        boolean added = repo.updateFilm(film);
        return added ? "Film updated" : "Film not updated";
    }
}


