package Users;

import OrderDetails.Order;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username;
    private String passwordHash;
    private boolean isLogged;
    private Order[] orders;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.isLogged = false;
        this.orders = new Order[0];
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
}