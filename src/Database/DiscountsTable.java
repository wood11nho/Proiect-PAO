package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DiscountsTable {
    private Connection connection;

    public DiscountsTable() {
        connection = MyJDBC.getConnection();
    }

    public void printDiscounts() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Discounts");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addDiscount(String name, double discount, int match_id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Discounts (name, discount, matchid) VALUES ('" + name + "', " + discount + ", " + match_id + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
