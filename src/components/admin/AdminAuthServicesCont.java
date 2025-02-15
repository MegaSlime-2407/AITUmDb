package components.admin;

public class AdminAuthServicesCont implements IAdminAuthServicesCont {
    private final IAdminAuthServicesRepo repo;
    public AdminAuthServicesCont(final IAdminAuthServicesRepo repo) {
        this.repo = repo;
    }

    public boolean login(String username, String password){
        if(repo == null){
            throw new NullPointerException("Repo is null");
        }
        return repo.login(username, password);
    }

}
