import components.user.UserServices;
import components.admin.AdminService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

            Menu menu = new Menu(adminService, userServices, scanner);
            menu.displayMainMenu();

        } catch (SQLException e) {
            System.out.println("Connection error:");
            e.printStackTrace();
        }
    }
}