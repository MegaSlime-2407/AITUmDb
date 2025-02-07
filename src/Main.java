import components.admin.AdminAuthServices;
import components.admin.AdminAuthServicesI;
import components.film.FilmServices;
import components.film.FilmServicesI;
import components.menu.Menu;
import components.review.ReviewServices;
import components.review.ReviewServicesI;
import components.user.UserServices;
import components.user.UserServicesI;
import components.utils.AuthServices;
import components.utils.AuthServicesI;
import components.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connected to the database.");
                AdminAuthServicesI adminAuthService = new AdminAuthServices(connection);
                AuthServicesI authService = new AuthServices(connection);
                FilmServicesI filmService = new FilmServices(connection);
                ReviewServicesI reviewService = new ReviewServices(connection);
                UserServicesI userService = new UserServices(connection);
                Scanner scanner = new Scanner(System.in);

                Menu menu = new Menu(adminAuthService,authService, filmService, reviewService, userService, scanner);
                menu.displayMainMenu();
            } else {
                System.out.println("You are not connected to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Connection error:");
            e.printStackTrace();
        }
    }
}