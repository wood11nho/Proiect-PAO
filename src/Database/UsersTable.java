package Database;

import Main.AuditService;
import Users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UsersTable {
    private Connection connection;

    public UsersTable() {
        connection = MyJDBC.getConnection();
    }

    public void printUsers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            AuditService.writeAction("Printed Users");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Users (Username, PasswordHash, isLogged, isAdmin) VALUES ('" + user.getUsername() + "', '" + user.getPasswordHash() + "', " + user.isLogged() + ", " + user.isAdmin() + ")");
            AuditService.writeAction("Added User to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Map<Integer, User> getAllUsersInAMap() {
        Map<Integer, User> users = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("Username"), resultSet.getString("PasswordHash"), resultSet.getBoolean("isLogged"), resultSet.getBoolean("isAdmin"));
                UUID uuid = UUID.randomUUID();
                int id = uuid.hashCode();
                while (users.containsKey(id)) {
                    uuid = UUID.randomUUID();
                    id = uuid.hashCode();
                }
                users.put(id, user);
            }
            AuditService.writeAction("Loaded Users from database");
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    public void setLoggedUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Users SET isLogged = " + user.isLogged() + " WHERE Username = '" + user.getUsername() + "'");
            AuditService.writeAction("Updated User as logged in the database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setUnloggedUser(User user) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Users SET isLogged = " + user.isLogged() + " WHERE Username = '" + user.getUsername() + "'");
            AuditService.writeAction("Updated User as logged out in the database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setAllUsersOffline() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Users SET isLogged = false");
            AuditService.writeAction("Updated all Users as logged out in the database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
