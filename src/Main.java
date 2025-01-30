import components.user.UserServices;
import components.admin.AdminService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if(connection != null) {
                System.out.println("Connected to the database.");
                AdminService adminService = new AdminService(connection);
                UserServices userServices = new UserServices(connection);
                Scanner scanner = new Scanner(System.in);

                Menu menu = new Menu(adminService, userServices, scanner);
                menu.displayMainMenu();
            }
            else System.out.printf("You are not connected to the database.%n");

        } catch (SQLException e) {
            System.out.println("Connection error:");
            e.printStackTrace();
        }
    }
}
