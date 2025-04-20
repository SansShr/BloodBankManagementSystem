package model;

public class User {
    private int id;
    private String username;
    private String role;

    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getRole() { return role; }
    public String getUsername() { return username; }
    public int getId() { return id; }
}
