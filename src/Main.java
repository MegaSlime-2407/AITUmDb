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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            if (connection != null) {
                System.out.println("Connected to the database.");
                Menu menu = getMenu();
                menu.displayMainMenu();
            } else {
                System.out.println("You are not connected to the database.");
            }
        } catch (Exception e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static Menu getMenu() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        IAdminAuthServices adminAuthService = new AdminAuthServices(connection);
        IAuthServices authService = new AuthServices(connection);
        IFilmServices filmService = new FilmServices(connection);
        IReviewServices reviewService = new ReviewServices(connection);
        IUserServices userService = new UserServices(connection);
        Scanner scanner = new Scanner(System.in);

        return new Menu(adminAuthService, authService, filmService, reviewService, userService, scanner);
    }
}
