package models;
// asd
public class Review {
    private int id;
    private String name;
    private int product_id;
    private int user_id;
    private String description;
    private double rating;

    public Review() {}
    public Review(int id,String name, int product_id, int user_id, String description, double rating) {
        setId(id);
        setName(name);
        setProduct_id(product_id);
        setUser_id(user_id);
        setDescription(description);
        setRating(rating);
    }

    public String toString(){
        return "\nFilm id " + product_id + "\nName" +name + "\nDescription " + description + "\nRating: " + rating;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


}
