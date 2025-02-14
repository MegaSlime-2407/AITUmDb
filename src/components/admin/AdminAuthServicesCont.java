package components.admin;

public class AdminAuthServicesCont implements IAdminAuthServices {
    private final IAdminAuthServices repo;
    public AdminAuthServicesCont(final IAdminAuthServices repo) {
        this.repo = repo;
    }

    public boolean login(String username, String password){
        if(repo == null){
            throw new NullPointerException("Repo is null");
        }
        return repo.login(username, password);
    }

}
