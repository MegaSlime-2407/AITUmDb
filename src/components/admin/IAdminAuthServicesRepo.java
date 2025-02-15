package components.admin;

public interface IAdminAuthServicesRepo {
    boolean login(String username, String password);
}
