package components.menu;

import components.user.User;
import components.user.UserServices;
import components.admin.AdminService;
import components.open.OpenServices;
import models.Film;
import models.Review;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private AdminService adminService;
    private UserServices userServices;
    private OpenServices openServices;

    public Menu(AdminService adminService, UserServices userServices, OpenServices openServices, Scanner scanner) {
        this.adminService = adminService;
        this.userServices = userServices;
        this.scanner = scanner;
    }

    public void displayMainMenu() throws SQLException {
        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Check all movies");
            System.out.println("2) Login as user/admin");
            System.out.println("3) Exit from session");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayMovies();
                    break;
                case "2":
                    loginMenu();
                    break;
                case "3":
                    running = false;
                    System.out.println("Exiting session. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayMovies() throws SQLException {
        List<Film> films = openServices.getFilms();
        System.out.println("\n--- All Movies ---");
        for (Film film : films) {
            System.out.println(film);
        }

        System.out.print("\nEnter the ID of the movie to check reviews (or 0 to go back): ");
        int filmId = Integer.parseInt(scanner.nextLine());

        if (filmId != 0) {
            displayReviews(filmId);
        } else {
            System.out.println("Returning to the main menu...");
        }
    }

    private void displayReviews(int filmId) throws SQLException {
        List<Review> reviews = openServices.getReviews(filmId);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for this movie.");
        } else {
            System.out.println("\n--- Reviews ---");
            for (Review review : reviews) {
                System.out.println(review);
            }
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
                System.out.println("\n--- Admin components.menu.Menu ---");
                System.out.println("1) Add movie");
                System.out.println("2) Get all movies");
                System.out.println("3) Update movie");
                System.out.println("4) Get all reviwes from specific user") ;
                System.out.println("5) Delete movie");
                System.out.println("6) Get all users");
                System.out.println("7) Logout");
                System.out.print("Choose an option: ");
                String adminChoice = scanner.nextLine();
                switch (adminChoice) {
                    case "1":
                        adminService.addFilm();
                        break;
                    case "2":
                        List<Film> allMovies = openServices.getFilms();
                        System.out.println("\n--- All Movies ---");
                        for (Film film : allMovies) {
                            System.out.println(film);
                        }
                        break;
                    case "3":
                        adminService.updateFilm();
                        break;
                    case "4":
                        List<Review> allReviews = adminService.getAllReviewsByUserId();
                        System.out.println("\n--- All Reviews ---");
                        for ( Review review : allReviews) {
                            System.out.println(review);
                        }
                        break;
                    case "5":
                        adminService.deleteFilm();
                        break;
                    case "6":
                        List<String> allUsers = adminService.getAllUsers();
                        System.out.println("\n--- All Users ---");
                        for (String user : allUsers) {
                            System.out.println(user);
                        }
                    case "7":
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
                System.out.println("\n--- User components.menu.Menu ---");
                System.out.println("1) Leave a review");
                System.out.println("2) Check all movies");
                System.out.println("3) Logout");
                System.out.print("Choose an option: ");
                String userChoice = scanner.nextLine();
                switch (userChoice) {
                    case "1":
                        System.out.print("Enter the ID of the movie to review: ");
                        int filmId = Integer.parseInt(scanner.nextLine());
                        userServices.leaveReview(filmId, userId);
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


}