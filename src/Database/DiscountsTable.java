package Database;

import Main.AuditService;
import OrderDetails.Discount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            AuditService.writeAction("Printed Discounts");
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
            AuditService.writeAction("Added Discount to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getDiscountId(Discount discount) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DiscountID FROM Discounts WHERE name = '" + discount.getName() + "' AND discount = " + discount.getDiscount());
            AuditService.writeAction("Got DiscountID from database");
            if (resultSet.next()) {
                return resultSet.getInt("DiscountID");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public void printDiscountsForMatch(int matchId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Discounts WHERE DiscountID IN (SELECT DiscountID FROM Discounts_Matches WHERE MatchID = " + matchId + ")");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " " + resultSet.getDouble("discount"));
            }
            AuditService.writeAction("Printed Discounts for Match");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Discount> getAllDiscountsInAList() {
        List<Discount> discounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Discounts");
            while (resultSet.next()) {
                discounts.add(new Discount(resultSet.getString("name"), resultSet.getDouble("discount")));
            }
            AuditService.writeAction("Got all Discounts from database");
        } catch (Exception e) {
            System.out.println(e);
        }
        return discounts;
    }
}
