package components.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin implements open{
    private int id;
    private String name;
    private String password;

    public Admin(int id, String name, String password) {
        setId(id);
        setName(name);
        setPassword(password);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void getAll() {
        String query = "SELECT * FROM films";
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("All films:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                System.out.println("ID: " + id + ", Title: " + title + ", Director: " + director);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getById(int filmId) {
        String query = "SELECT * FROM films WHERE id = " + filmId;
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                System.out.println("Film found: ID: " + id + ", Title: " + title + ", Director: " + director);
            } else {
                System.out.println("Film not found with ID: " + filmId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
