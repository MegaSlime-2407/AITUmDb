package models;

public class films {
    private int film_id;
    private String film_title;
    private String film_genre;
    private double film_rating;
    private String film_description;

    public films(String title, String genre, double rating, String description) {
        setFilm_title(title);
        setFilm_genre(genre);
        setFilm_rating(rating);
        setFilm_description(description);
    }

    public int getFilm_id() {
        return film_id;
    }
    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }
    public String getFilm_title() {
        return film_title;
    }
    public void setFilm_title(String film_title) {
        this.film_title = film_title;
    }
    public String getFilm_genre() {
        return film_genre;
    }
    public void setFilm_genre(String film_genre) {
        this.film_genre = film_genre;
    }
    public double getFilm_rating() {
        return film_rating;
    }
    public void setFilm_rating(double film_rating) {
        this.film_rating = film_rating;
    }
    public String getFilm_description() {
        return film_description;
    }
    public void setFilm_description(String film_description) {
        this.film_description = film_description;
    }
}
