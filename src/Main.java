import components.menu.Menu;
import components.open.OpenServices;
import components.user.UserServices;
import components.admin.AdminService;
import java.sql.Connection;
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
                OpenServices openServices = new OpenServices(connection);
                Scanner scanner = new Scanner(System.in);

                Menu menu = new Menu(adminService, userServices,openServices, scanner);
                menu.displayMainMenu();
            }
            else System.out.printf("You are not connected to the database.%n");

        } catch (SQLException e) {
            System.out.println("Connection error:");
            e.printStackTrace();
        }
    }
}