package entity;
import javax.swing.*;
public class User {
    public String username;
    public String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User (JTextField tfusername, JTextField pfpassword) {
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
