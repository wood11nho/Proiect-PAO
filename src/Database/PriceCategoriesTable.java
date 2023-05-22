package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PriceCategoriesTable {
    private Connection connection;

    public PriceCategoriesTable() {
        connection = MyJDBC.getConnection();
    }

    public void printPriceCategories() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PriceCategories");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addPriceCategory(String name, String category_type, int stand_id, int match_id, int price, boolean private_lounge_access) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO PriceCategories (name, category_type, standid, matchid) VALUES ('" + name + "', '" + category_type + "', " + stand_id + ", " + match_id + ")");
            // get the id of the newly inserted price category
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PriceCategories WHERE name = '" + name + "'");
            resultSet.next();
            int id = resultSet.getInt("id");
            if (category_type.equals("Peluza")) {
                statement.executeUpdate("INSERT INTO SouthNorthPrices (id, price) VALUES (" + id + ", " + price + ")");
            } else if (category_type.equals("Tribuna")) {
                statement.executeUpdate("INSERT INTO EastWestPriecs (id, price) VALUES (" + id + ", " + price + ")");
            } else if (category_type.equals("VIP")) {
                statement.executeUpdate("INSERT INTO VIPPrices (id, price, private_lounge_access) VALUES (" + id + ", " + price + ", " + private_lounge_access + ")");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
