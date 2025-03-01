package models;

public class Admin {
    private int id;
    private String name;
    private String password;

    public Admin(int id, String name, String password) {
        setId(id);
        setName(name);
        setPassword(password);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
