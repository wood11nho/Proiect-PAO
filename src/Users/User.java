package Users;

import OrderDetails.Order;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class User {
    private String username;
    private String passwordHash;
    private boolean isLogged;
    private Order[] orders;
    private boolean isAdmin;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.isLogged = false;
        this.orders = new Order[0];
        this.isAdmin = false;
    }

    //i also want to add a constructor for admin users
    public User(boolean isAdmin) throws IOException {
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream("C:\\Users\\stoic\\IdeaProjects\\Proiect-PAO\\admin.properties");
        prop.load(input);
        String adminUsername = prop.getProperty("admin.username");
        String adminPassword = prop.getProperty("admin.password");

        this.username = adminUsername;
        this.passwordHash = hashPassword(adminPassword);
        this.isLogged = false;
        this.orders = new Order[0];
        this.isAdmin = true;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String password) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(this.passwordHash);
    }

    public void addOrder(Order order) {
        Order[] newOrders = new Order[this.orders.length + 1];
        for (int i = 0; i < this.orders.length; i++) {
            newOrders[i] = this.orders[i];
        }
        newOrders[this.orders.length] = order;
        this.orders = newOrders;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void afisare_comenzi() {
        for (Order order : orders) {
            order.afisare_comanda();
        }
    }
}