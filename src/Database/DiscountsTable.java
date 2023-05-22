package Database;

import OrderDetails.Discount;

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

    public void addDiscount(String name, double discount) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Discounts (name, discount) VALUES ('" + name + "', " + discount + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addDiscount(Discount discount) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Discounts (name, discount) VALUES ('" + discount.getName() + "', " + discount.getDiscount() + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMatchID(Discount discount, int matchID) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Discounts SET matchid = " + matchID + " WHERE name = '" + discount.getName() + "'");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
