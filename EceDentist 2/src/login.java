import entity.User;
import homepage.HomePage;
import adminpage.AdminPage;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class login extends JFrame {
    private JTextField tfusername;
    private JTextField pfpassword;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel loginPanel;
    private JPanel panel1;
    private JLabel icon;
    private User user;
    public login() {
        add(loginPanel);
        setSize(600,350);
        setLocationRelativeTo(this);
        setContentPane(loginPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Login System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        loginButton.addActionListener(e ->  {
            if(tfusername.getText().equals("admin") && pfpassword.getText().equals("admin123")){
                new AdminPage().setVisible(true);
                dispose();
            }else {
                if (getAuthenticatedUser(new User(
                        tfusername.getText(),
                        pfpassword.getText()
                ))) {
                    new HomePage().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(login.this, "Email or Password Invalid", "Try again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        registerButton.addActionListener(e -> {
            if(registerUser(
                    new User (
                            tfusername.getText(),
                            pfpassword.getText()
                    )
            )){
                new HomePage().setVisible(true);
                dispose();
            }else{
                JOptionPane.showMessageDialog(login.this, "Email or Password Invalid", "Try again", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private boolean getAuthenticatedUser(User user) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean registerUser ( User user){
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users (username, password) values (?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        login loginForm = new login();
        User user = loginForm.user;

        if (user != null) {
            System.out.println("Successful Authentication of: " + user.username);
        }
        else {
            System.out.println("Authentication canceled");
        }
    }
}


