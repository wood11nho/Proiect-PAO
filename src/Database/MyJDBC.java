package Database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MyJDBC {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            FileInputStream input = new FileInputStream("C:\\Users\\stoic\\IdeaProjects\\Proiect-PAO\\database.properties");
            properties.load(input);
            String url = properties.getProperty("database.url");
            String username = properties.getProperty("database.username");
            String password = properties.getProperty("database.password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public void deleteAll() {
        try {
            Connection connection = getConnection();
            connection.createStatement().executeUpdate("DELETE FROM discounts");
            connection.createStatement().executeUpdate("DELETE FROM discounts_matches");
            connection.createStatement().executeUpdate("DELETE FROM matches");
            connection.createStatement().executeUpdate("DELETE FROM eastwestprices");
            connection.createStatement().executeUpdate("DELETE FROM southnorthprices");
            connection.createStatement().executeUpdate("DELETE FROM vipprices");
            connection.createStatement().executeUpdate("DELETE FROM pricecategories");
            connection.createStatement().executeUpdate("DELETE FROM stadiums");
            connection.createStatement().executeUpdate("DELETE FROM stands");
            connection.createStatement().executeUpdate("DELETE FROM users");
            connection.createStatement().executeUpdate("ALTER TABLE discounts AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE discounts_matches AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE matches AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE eastwestprices AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE southnorthprices AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE vipprices AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE pricecategories AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE stadiums AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE stands AUTO_INCREMENT = 1");
            connection.createStatement().executeUpdate("ALTER TABLE users AUTO_INCREMENT = 1");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
