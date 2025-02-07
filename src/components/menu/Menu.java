package components.menu;

import components.admin.AdminAuthServicesI;
import components.film.FilmServicesI;
import components.review.ReviewServicesI;
import components.user.UserServicesI;
import components.utils.AuthServicesI;
import models.Film;
import models.Review;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final AdminAuthServicesI adminAuthServices;
    private final AuthServicesI authService;
    private final FilmServicesI filmService;
    private final ReviewServicesI reviewService;
    private final UserServicesI userService;
    private final Scanner scanner;

    public Menu(AdminAuthServicesI adminAuthServices, AuthServicesI authService, FilmServicesI filmService, ReviewServicesI reviewService, UserServicesI userService, Scanner scanner) {
        this.adminAuthServices = adminAuthServices;
        this.authService = authService;
        this.filmService = filmService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.scanner = scanner;
    }

    public void displayMainMenu() throws SQLException {
        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Check all movies");
            System.out.println("2) Login as user/admin");
            System.out.println("3) Exit from session");
            System.out.println("4) Register as a user");
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
                case "4":
                    System.out.print("Enter a username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter a password: ");
                    String password = scanner.nextLine();
                    authService.register(username, password);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayMovies() throws SQLException {
        List<Film> films = filmService.getFilms();
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
        List<Review> reviews = reviewService.getReviews(filmId);
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
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (adminAuthServices.login(username, password)) {
            boolean adminMenu = true;
            while (adminMenu) {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1) Add movie");
                System.out.println("2) Get all movies");
                System.out.println("3) Update movie");
                System.out.println("4) Get all reviews from specific user");
                System.out.println("5) Delete movie");
                System.out.println("6) Get all users");
                System.out.println("7) Logout");
                System.out.print("Choose an option: ");
                String adminChoice = scanner.nextLine();
                switch (adminChoice) {
                    case "1":
                        System.out.print("Enter film title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter film genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("Enter film description: ");
                        String description = scanner.nextLine();
                        Film film = new Film(title, genre, 0, description);
                        filmService.addFilm(film);
                        break;
                    case "2":
                        List<Film> allMovies = filmService.getFilms();
                        System.out.println("\n--- All Movies ---");
                        for (Film f : allMovies) {
                            System.out.println(f);
                        }
                        break;
                    case "3":
                        System.out.print("Enter film ID: ");
                        int filmId = Integer.parseInt(scanner.nextLine());
                        Film filmToUpdate = filmService.getFilmById(filmId);
                        if (filmToUpdate != null) {
                            System.out.println("Current title: " + filmToUpdate.getFilm_title());
                            System.out.print("Enter new title (leave blank to keep current): ");
                            String newTitle = scanner.nextLine();
                            if (!newTitle.isEmpty()) {
                                filmToUpdate.setFilm_title(newTitle);
                            }

                            System.out.println("Current genre: " + filmToUpdate.getFilm_genre());
                            System.out.print("Enter new genre (leave blank to keep current): ");
                            String newGenre = scanner.nextLine();
                            if (!newGenre.isEmpty()) {
                                filmToUpdate.setFilm_genre(newGenre);
                            }

                            System.out.println("Current description: " + filmToUpdate.getFilm_description());
                            System.out.print("Enter new description (leave blank to keep current): ");
                            String newDescription = scanner.nextLine();
                            if (!newDescription.isEmpty()) {
                                filmToUpdate.setFilm_description(newDescription);
                            }

                            filmService.updateFilm(filmToUpdate);
                        } else {
                            System.out.println("No film found with the given ID.");
                        }
                        break;
                    case "4":
                        System.out.print("Enter user id: ");
                        int userId = Integer.parseInt(scanner.nextLine());
                        List<Review> allReviews = reviewService.getReviews(userId);
                        System.out.println("\n--- All Reviews ---");
                        for (Review review : allReviews) {
                            System.out.println(review);
                        }
                        break;
                    case "5":
                        System.out.print("Enter film id: ");
                        int filmIdToDelete = Integer.parseInt(scanner.nextLine());
                        filmService.deleteFilm(filmIdToDelete);
                        break;
                    case "6":
                        List<String> allUsers = userService.getAllUsers();
                        System.out.println("\n--- All Users ---");
                        for (String user : allUsers) {
                            System.out.println(user);
                        }
                        break;
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
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        int userId = authService.login(username, password);
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
                        System.out.print("Enter the ID of the movie to review: ");
                        int filmId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter the review description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter the review rating: ");
                        double rating = Double.parseDouble(scanner.nextLine());
                        reviewService.leaveReview(filmId, userId, description, rating);
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