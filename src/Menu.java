import components.user.UserServices;
import components.admin.AdminService;
import models.Film;
import models.Review;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private AdminService adminService;
    private UserServices userServices;

    public Menu(AdminService adminService, UserServices userServices, Scanner scanner) {
        this.adminService = adminService;
        this.userServices = userServices;
        this.scanner = scanner;
    }

    public void displayMainMenu() throws SQLException {
        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Check all movies");
            System.out.println("1.1) Choose and check all reviews of a specific movie");
            System.out.println("2) Login as user/admin");
            System.out.println("3) Exit from session");
            System.out.println("4) Register as a user");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayMovies();
                    break;
                case "1.1":
                    displayReviews();
                    break;
                case "2":
                    loginMenu();
                    break;
                case "3":
                    running = false;
                    System.out.println("Exiting session. Goodbye!");
                    break;
                case "4": // ✅ Вызываем меню регистрации
                    registerMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayMovies() throws SQLException {
        List<Film> films = adminService.getFilms();
        System.out.println("\n--- All Movies ---");
        for (Film film : films) {
            System.out.println(film);
        }
    }

    private void displayReviews() throws SQLException {
        System.out.print("Enter the ID of the movie to check reviews: ");
        int filmId = Integer.parseInt(scanner.nextLine());
        List<Review> reviews = userServices.getReviews(filmId);
        System.out.println("\n--- Reviews ---");
        for (Review review : reviews) {
            System.out.println(review);
        }
    }

    private void loginMenu() throws SQLException {
        System.out.println("Login as:");
        System.out.println("1) Admin");
        System.out.println("2) User");
        System.out.print("Choose an option: ");
        String userType = scanner.nextLine();

        if (userType.equals("1")) {
            adminLoginMenu();
        } else if (userType.equals("2")) {
            userLoginMenu();
        } else {
            System.out.println("Invalid user type. Try again.");
        }
    }

    private void adminLoginMenu() throws SQLException {
        if (adminService.login()) {
            boolean adminMenu = true;
            while (adminMenu) {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1) Add movie");
                System.out.println("2) Get all movies");
                System.out.println("3) Update movie");
                System.out.println("4) Delete movie");
                System.out.println("5) Logout");
                System.out.print("Choose an option: ");
                String adminChoice = scanner.nextLine();
                switch (adminChoice) {
                    case "1":
                        adminService.addFilm();
                        break;
                    case "2":
                        displayMovies();
                        break;
                    case "3":
                        adminService.updateFilm();
                        break;
                    case "4":
                        adminService.deleteFilm();
                        break;
                    case "5":
                        adminMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void userLoginMenu() throws SQLException {
        int userId = userServices.login();
        if (userId != 0) {
            boolean userMenu = true;
            while (userMenu) {
                System.out.println("\n--- User Menu ---");
                System.out.println("1) Leave a review");
                System.out.println("2) Check all movies");
                System.out.println("3) Logout");
                System.out.print("Choose an option: ");
                String userChoice = scanner.nextLine();
                switch (userChoice) {
                    case "1":
                        leaveReview(userId);
                        break;
                    case "2":
                        displayMovies();
                        break;
                    case "3":
                        userMenu = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private void leaveReview(int userId) throws SQLException {
        System.out.print("Enter the ID of the movie to review: ");
        int productId = Integer.parseInt(scanner.nextLine());
        userServices.leaveReview(productId, userId);
    }


    private void registerMenu() throws SQLException {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        userServices.register(username, password);
    }
}
