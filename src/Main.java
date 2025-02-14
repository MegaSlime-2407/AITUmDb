import components.admin.AdminAuthServices;
import components.admin.AdminAuthServicesCont;
import components.admin.IAdminAuthServices;
import components.admin.IAdminAuthServicesCont;
import components.film.FilmServices;
import components.film.FilmServicesCont;
import components.film.IFilmServices;
import components.film.IFilmServicesCont;
import components.menu.Menu;
import components.review.IReviewServicesCont;
import components.review.ReviewServices;
import components.review.IReviewServices;
import components.review.ReviewServicesCont;
import components.user.IUserServicesCont;
import components.user.UserServices;
import components.user.IUserServices;
import components.user.UserServicesCont;
import components.utils.*;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            if (connection != null) {
                System.out.println("Connected to the database.");

                IAdminAuthServices adminAuthService = new AdminAuthServices(connection);
                IAuthServices authService = new AuthServices(connection);
                IFilmServices filmService = new FilmServices(connection);
                IReviewServices reviewService = new ReviewServices(connection);
                IUserServices userService = new UserServices(connection);

                IUserServicesCont userServiceCont = new UserServicesCont(userService);
                IFilmServicesCont filmServiceCont = new FilmServicesCont(filmService);
                IReviewServicesCont reviewServiceCont = new ReviewServicesCont(reviewService);
                IAdminAuthServicesCont adminAuthServicesCont = new AdminAuthServicesCont(adminAuthService);
                IAuthServicesCont authServicesCont = new AuthServicesCont(authService);

                Scanner scanner = new Scanner(System.in);

                Menu menu = new Menu(userServiceCont, filmServiceCont, reviewServiceCont, adminAuthServicesCont, authServicesCont, scanner);
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
