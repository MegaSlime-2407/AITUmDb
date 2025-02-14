import components.admin.AdminAuthServicesRepo;
import components.admin.AdminAuthServicesCont;
import components.admin.IAdminAuthServices;
import components.film.FilmServicesRepo;
import components.film.FilmServicesCont;
import components.film.IFilmServices;
import components.menu.Menu;
import components.review.ReviewServicesRepo;
import components.review.IReviewServices;
import components.review.ReviewServicesCont;
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


                IUserServices userServiceCont = new UserServicesCont(userService);
                IFilmServices filmServiceCont = new FilmServicesCont(filmService);
                IReviewServices reviewServiceCont = new ReviewServicesCont(reviewService);
                IAdminAuthServices adminAuthServicesCont = new AdminAuthServicesCont(adminAuthService);
                IAuthServices authServicesCont = new AuthServicesCont(authService);

                Scanner scanner = new Scanner(System.in);

                Menu menu = new Menu(userServiceCont, filmServiceCont, reviewServiceCont, adminAuthServicesCont, authServicesCont, scanner);
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
