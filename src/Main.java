import components.admin.AdminAuthServicesRepo;
import components.admin.AdminAuthServicesCont;
import components.admin.IAdminAuthServicesRepo;
import components.admin.IAdminAuthServicesCont;
import components.film.FilmServicesRepo;
import components.film.FilmServicesCont;
import components.film.IFilmServicesRepo;
import components.film.IFilmServicesCont;
import components.menu.Menu;
import components.review.IReviewServicesCont;
import components.review.ReviewServicesRepo;
import components.review.IReviewServicesRepo;
import components.review.ReviewServicesCont;
import components.user.IUserServicesCont;
import components.user.UserServicesRepo;
import components.user.IUserServicesRepo;
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
                Menu menu = getMenu(connection);
                menu.displayMainMenu();
            } else {
                System.out.println("You are not connected to the database.");
            }
        } catch (Exception e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private static Menu getMenu(Connection connection) {
        IAdminAuthServicesRepo adminAuthService = new AdminAuthServicesRepo(connection);
        IAuthServicesRepo authService = new AuthServicesRepo(connection);
        IFilmServicesRepo filmService = new FilmServicesRepo(connection);
        IReviewServicesRepo reviewService = new ReviewServicesRepo(connection);
        IUserServicesRepo userService = new UserServicesRepo(connection);

        IUserServicesCont userServiceCont = new UserServicesCont(userService);
        IFilmServicesCont filmServiceCont = new FilmServicesCont(filmService);
        IReviewServicesCont reviewServiceCont = new ReviewServicesCont(reviewService);
        IAdminAuthServicesCont adminAuthServicesCont = new AdminAuthServicesCont(adminAuthService);
        IAuthServicesCont authServicesCont = new AuthServicesCont(authService);
        Scanner scanner = new Scanner(System.in);
        return new Menu(userServiceCont, filmServiceCont, reviewServiceCont, adminAuthServicesCont, authServicesCont, scanner);
    }
}
