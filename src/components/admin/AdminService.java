package components.admin;
import models.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import javax.sql.DataSource;

public class AdminService {
    private Connection connection;

    public AdminService(Connection connection) {
        this.connection = connection;
    }

    public void addFilm() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film title: ");
        String title = scanner.nextLine();
        System.out.print("Enter film genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter film description: ");
        String description = scanner.nextLine();

        String sql = "INSERT INTO films (name, genre, rating, description) VALUES (?, ?, 0, ?)";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setString(3, description);

            pstmt.executeUpdate();
            System.out.println("Film added successfully");
        }
    }

    public void deleteFilm() throws SQLException {
        String sql = "DELETE FROM films WHERE filmid = ?";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film id: ");
        String id = scanner.nextLine();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setInt(1, Integer.parseInt(id));

            pstmt.executeUpdate();
            System.out.println("Film deleted successfully");
        }
    }
}