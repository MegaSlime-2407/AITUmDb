package models;

public class User {
    private int id;
    private String name;
    private String password;
    public User(String name, String password) {
        setId(id);
        setName(name);
        setPassword(password);
    }
    public String toString() {
        return "ID: "+ id + "\nName" + name;
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
