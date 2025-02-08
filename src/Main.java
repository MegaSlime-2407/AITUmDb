import components.admin.AdminAuthServices;
import components.admin.IAdminAuthServices;
import components.film.FilmServices;
import components.film.IFilmServices;
import components.menu.Menu;
import components.review.ReviewServices;
import components.review.IReviewServices;
import components.user.UserServices;
import components.user.IUserServices;
import components.utils.AuthServices;
import components.utils.IAuthServices;
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
                IAdminAuthServices adminAuthService = new AdminAuthServices(connection);
                IAuthServices authService = new AuthServices(connection);
                IFilmServices filmService = new FilmServices(connection);
                IReviewServices reviewService = new ReviewServices(connection);
                IUserServices userService = new UserServices(connection);
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