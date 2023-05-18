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
            System.out.println(url);
            String username = properties.getProperty("database.username");
            System.out.println(username);
            String password = properties.getProperty("database.password");
            System.out.println(password);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
}
