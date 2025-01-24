import components.user.UserServices;
import components.admin.AdminService;
import models.Film;
import models.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/aitumdb";
        String user = "postgres";
        String password = "0000";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database.");
            AdminService adminService = new AdminService(connection);
            UserServices userServices = new UserServices(connection);
            Scanner scanner = new Scanner(System.in);

            boolean running = true;
            while (running) {
                System.out.println("\n--- MENU ---");
                System.out.println("1) Check all movies");
                System.out.println("1.1) Choose and check all reviews of a specific movie");
                System.out.println("2) Login as user/admin");
                System.out.println("3) Exit from session");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        List<Film> films = adminService.getFilms();
                        System.out.println("\n--- All Movies ---");
                        for (Film film : films) {
                            System.out.println(film);
                        }
                        break;

                    case "1.1":
                        System.out.print("Enter the ID of the movie to check reviews: ");
                        int filmId = Integer.parseInt(scanner.nextLine());
                        List<Review> reviews = userServices.getReviews(filmId);
                        System.out.println("\n--- Reviews ---");
                        for (Review review : reviews) {
                            System.out.println(review);
                        }
                        break;

                    case "2":
                        System.out.println("Login as:");
                        System.out.println("1) Admin");
                        System.out.println("2) User");
                        System.out.print("Choose an option: ");
                        String userType = scanner.nextLine();

                        if (userType.equals("1")) {
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
                                            List<Film> allMovies = adminService.getFilms();
                                            System.out.println("\n--- All Movies ---");
                                            for (Film film : allMovies) {
                                                System.out.println(film);
                                            }
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
                        } else if (userType.equals("2")) {
                            if (userServices.login()) {
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
                                            int productId = Integer.parseInt(scanner.nextLine());
                                            System.out.print("Enter your user ID: ");
                                            int userId = Integer.parseInt(scanner.nextLine());
                                            userServices.leaveReview(productId, userId);
                                            break;
                                        case "2":
                                            List<Film> movies = userServices.getFilms();
                                            System.out.println("\n--- All Movies ---");
                                            for (Film film : movies) {
                                                System.out.println(film);
                                            }
                                            break;
                                        case "3":
                                            userMenu = false;
                                            break;
                                        default:
                                            System.out.println("Invalid choice. Try again.");
                                    }
                                }
                            }
                        } else {
                            System.out.println("Invalid user type. Try again.");
                        }
                        break;

                    case "3":
                        running = false;
                        System.out.println("Exiting session. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection error:");
            e.printStackTrace();
        }
    }
}
