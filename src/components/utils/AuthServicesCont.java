package components.utils;

import models.User;

import java.sql.SQLException;

public class AuthServicesCont implements IAuthServices {
    private final IAuthServices repo;
    public AuthServicesCont(final IAuthServices repo) {
        this.repo = repo;
    }
    public int login(String username, String password) throws SQLException {
        if(repo == null){
            throw new NullPointerException("Repo is null");
        }
        return repo.login(username, password);
    }

    @Override
    public int register(String username, String password) throws SQLException {
        User user = new User(username, password);
        int newUser = repo.register(username, password);
        return newUser;


    }

    @Override
    public boolean isUserExist(String username) throws SQLException {
        if(repo == null){
            throw new NullPointerException("Repo is null");
        }
       return repo.isUserExist(username);
    }

    @Override
    public void addUserToDatabase(String username, String password) throws SQLException {
        if(repo == null){
            throw new NullPointerException("Repo is null");
        }
        repo.addUserToDatabase(username, password);

    }
}
