package Database;

import Prices.EastWestPrice;
import Prices.PriceCategory;
import Prices.SouthNorthPrice;
import Prices.VipPrice;

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

    public void addPriceCategory(String category_type, int stand_id, int price, boolean private_lounge_access) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO PriceCategories (category_type, standid) VALUES ('" + category_type + "', " + stand_id + ")");
            // get the id of the newly inserted price category
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PriceCategories WHERE category_type = '" + category_type + "' AND standid = " + stand_id);
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

    public void addPriceCategory(SouthNorthPrice priceCategory) {
        try {
            Statement statement = connection.createStatement();
            // get the id of the stand from the database
            ResultSet standId = statement.executeQuery("SELECT id FROM Stands WHERE name = '" + priceCategory.getStand().getName() + "'");
            standId.next();
            statement.executeUpdate("INSERT INTO PriceCategories (category_type, standid) VALUES ('Peluza', " + standId.getInt("id") + ")");
            // get the id of the newly inserted price category
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PriceCategories WHERE category_type = 'Peluza' AND standid = " + standId.getInt("id"));
            resultSet.next();
            int id = resultSet.getInt("id");
            statement.executeUpdate("INSERT INTO SouthNorthPrices (id, price) VALUES (" + id + ", " + priceCategory.getPrice() + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addPriceCategory(EastWestPrice priceCategory) {
        try {
            Statement statement = connection.createStatement();
            // get the id of the stand from the database
            ResultSet standId = statement.executeQuery("SELECT id FROM Stands WHERE name = '" + priceCategory.getStand().getName() + "'");
            standId.next();
            statement.executeUpdate("INSERT INTO PriceCategories (category_type, standid) VALUES ('Tribuna', " + standId.getInt("id") + ")");
            // get the id of the newly inserted price category
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PriceCategories WHERE category_type = 'Tribuna' AND standid = " + standId.getInt("id"));
            resultSet.next();
            int id = resultSet.getInt("id");
            statement.executeUpdate("INSERT INTO EastWestPrices (id, price) VALUES (" + id + ", " + priceCategory.getPrice() + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addPriceCategory(VipPrice priceCategory) {
        try {
            Statement statement = connection.createStatement();
            // get the id of the stand from the database
            ResultSet standId = statement.executeQuery("SELECT id FROM Stands WHERE name = '" + priceCategory.getStand().getName() + "'");
            standId.next();
            statement.executeUpdate("INSERT INTO PriceCategories (category_type, standid) VALUES ('VIP', " + standId.getInt("id") + ")");
            // get the id of the newly inserted price category
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PriceCategories WHERE category_type = 'VIP' AND standid = " + standId.getInt("id"));
            resultSet.next();
            int id = resultSet.getInt("id");
            statement.executeUpdate("INSERT INTO VIPPrices (id, price, private_lounge_access) VALUES (" + id + ", " + priceCategory.getPrice() + ", " + priceCategory.getPrivateLoungeAccess() + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMatchID(PriceCategory priceCategory, int match_id) {
        try {
            Statement statement = connection.createStatement();
            //get stand id of the price category
            ResultSet standId = statement.executeQuery("SELECT standid FROM PriceCategories WHERE category_type = '" + priceCategory.getName() + "'");
            //get id of the price category
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PriceCategories WHERE category_type = '" + priceCategory.getName() + "' AND standid = " + standId.getInt("standid"));
            resultSet.next();
            int id = resultSet.getInt("id");
            //update match id
            statement.executeUpdate("UPDATE PriceCategories SET matchid = " + match_id + " WHERE id = " + id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
