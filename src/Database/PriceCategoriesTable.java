package Database;

import Main.AuditService;
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

            // Insert into PriceCategories table
            statement.executeUpdate("INSERT INTO PriceCategories (categorytype, standid) VALUES ('" + category_type + "', " + stand_id + ")");

            // Get the id of the newly inserted price category
            ResultSet resultSet = statement.executeQuery("SELECT PriceCategoryID FROM PriceCategories WHERE categorytype = '" + category_type + "' AND standid = " + stand_id);
            if (resultSet.next()) {
                int id = resultSet.getInt("PriceCategoryID");

                // Insert into corresponding prices table based on category_type
                if (category_type.equals("Peluza")) {
                    statement.executeUpdate("INSERT INTO SouthNorthPrices (id, southnorthprice) VALUES (" + id + ", " + price + ")");
                } else if (category_type.equals("Tribuna")) {
                    statement.executeUpdate("INSERT INTO EastWestPrices (id, eastwestprice) VALUES (" + id + ", " + price + ")");
                } else if (category_type.equals("VIP")) {
                    statement.executeUpdate("INSERT INTO VIPPrices (id, vipprice, privateloungeaccess) VALUES (" + id + ", " + price + ", " + private_lounge_access + ")");
                }
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void addPriceCategory(SouthNorthPrice priceCategory) {
        try {
            Statement statement = connection.createStatement();

            // Get the id of the stand from the database
            ResultSet standId = statement.executeQuery("SELECT StandID FROM Stands WHERE name = '" + priceCategory.getStand().getName() + "'");
            if (standId.next()) {
                int standIdValue = standId.getInt("StandID");

                // Insert into PriceCategories table
                statement.executeUpdate("INSERT INTO PriceCategories (categorytype, standid) VALUES ('Peluza', " + standIdValue + ")");
                AuditService.writeAction("Added PriceCategory to database");
                // Get the id of the newly inserted price category
                ResultSet resultSet = statement.executeQuery("SELECT PriceCategoryID FROM PriceCategories WHERE categorytype = 'Peluza' AND standid = " + standIdValue);
                if (resultSet.next()) {
                    int id = resultSet.getInt("PriceCategoryID");

                    // Insert into SouthNorthPrices table
                    statement.executeUpdate("INSERT INTO SouthNorthPrices (id, southnorthprice) VALUES (" + id + ", " + priceCategory.getPrice() + ")");
                    AuditService.writeAction("Added SouthNorthPrice to database");
                }
                resultSet.close();
            }
            standId.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void addPriceCategory(EastWestPrice priceCategory) {
        try {
            Statement statement = connection.createStatement();

            // Get the id of the stand from the database
            ResultSet standId = statement.executeQuery("SELECT StandID FROM Stands WHERE name = '" + priceCategory.getStand().getName() + "'");
            if (standId.next()) {
                int standIdValue = standId.getInt("StandID");

                // Insert into PriceCategories table
                statement.executeUpdate("INSERT INTO PriceCategories (categorytype, standid) VALUES ('Tribuna', " + standIdValue + ")");
                AuditService.writeAction("Added PriceCategory to database");
                // Get the id of the newly inserted price category
                ResultSet resultSet = statement.executeQuery("SELECT PriceCategoryID FROM PriceCategories WHERE categorytype = 'Tribuna' AND standid = " + standIdValue);
                if (resultSet.next()) {
                    int id = resultSet.getInt("PriceCategoryID");

                    // Insert into EastWestPrices table
                    statement.executeUpdate("INSERT INTO EastWestPrices (id, eastwestprice) VALUES (" + id + ", " + priceCategory.getPrice() + ")");
                    AuditService.writeAction("Added EastWestPrice to database");
                }
                resultSet.close();
            }
            standId.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addPriceCategory(VipPrice priceCategory) {
        try {
            Statement statement = connection.createStatement();

            // Get the id of the stand from the database
            ResultSet standId = statement.executeQuery("SELECT StandID FROM Stands WHERE name = '" + priceCategory.getStand().getName() + "'");
            if (standId.next()) {
                int standIdValue = standId.getInt("StandID");

                String categoryType = priceCategory.getName();
                // Insert into PriceCategories table
                statement.executeUpdate("INSERT INTO PriceCategories (categorytype, standid) VALUES ('" + categoryType + "', " + standIdValue + ")");
                AuditService.writeAction("Added PriceCategory to database");
                // Get the id of the newly inserted price category
                ResultSet resultSet = statement.executeQuery("SELECT PriceCategoryID FROM PriceCategories WHERE categorytype = '" + categoryType + "' AND standid = " + standIdValue);
                if (resultSet.next()) {
                    int id = resultSet.getInt("PriceCategoryID");
                    // Insert into VIPPrices table
                    statement.executeUpdate("INSERT INTO VIPPrices (id, vipprice, privateloungeaccess) VALUES (" + id + ", " + priceCategory.getPrice() + ", " + priceCategory.getPrivateLoungeAccess() + ")");
                    AuditService.writeAction("Added VIPPrice to database");
                }
                resultSet.close();
            }
            standId.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateMatchID(PriceCategory priceCategory, int match_id, int stand_id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE PriceCategories SET matchid = " + match_id + " WHERE standid = " + stand_id + " AND categorytype = '" + priceCategory.getName() + "'");
            AuditService.writeAction("Updated match id for PriceCategory");
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printPriceCategoriesForStand(int matchNumber, int standNumber) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PriceCategories WHERE matchid = " + matchNumber + " AND standid = " + standNumber);
            while (resultSet.next()) {
                int id = resultSet.getInt("pricecategoryid");
                String categoryType = resultSet.getString("categorytype");
                int matchId = resultSet.getInt("matchid");
                int standId = resultSet.getInt("standid");
                if (categoryType.equals("Peluza")) {
                    Statement priceStatement = connection.createStatement(); // Create a new Statement object
                    ResultSet price = priceStatement.executeQuery("SELECT southnorthprice FROM SouthNorthPrices WHERE id = " + id);
                    if (price.next()) {
                        System.out.println(id + ". Categoria de pret " + categoryType + " are pretul " + price.getInt("southnorthprice"));
                    }
                    price.close(); // Close the price ResultSet
                    priceStatement.close(); // Close the price Statement
                } else if (categoryType.equals("Tribuna")) {
                    Statement priceStatement = connection.createStatement(); // Create a new Statement object
                    ResultSet price = priceStatement.executeQuery("SELECT eastwestprice FROM EastWestPrices WHERE id = " + id);
                    if (price.next()) {
                        System.out.println(id + ". Categoria de pret " + categoryType + " are pretul " + price.getInt("eastwestprice"));
                    }
                    price.close(); // Close the price ResultSet
                    priceStatement.close(); // Close the price Statement
                } else if (categoryType.equals("VIP2")) {
                    Statement priceStatement = connection.createStatement(); // Create a new Statement object
                    ResultSet price = priceStatement.executeQuery("SELECT vipprice FROM VIPPrices WHERE id = " + id);
                    if (price.next()) {
                        System.out.println(id + ". Categoria de pret " + categoryType + " are pretul " + price.getInt("vipprice") + " si nu are acces la lounge");
                    }
                    price.close(); // Close the price ResultSet
                    priceStatement.close(); // Close the price Statement
                } else if (categoryType.equals("VIP1")) {
                    Statement priceStatement = connection.createStatement(); // Create a new Statement object
                    ResultSet price = priceStatement.executeQuery("SELECT vipprice FROM VIPPrices WHERE id = " + id);
                    if (price.next()) {
                        System.out.println(id + ". Categoria de pret " + categoryType + " are pretul " + price.getInt("vipprice") + " si are acces la lounge");
                    }
                    price.close(); // Close the price ResultSet
                    priceStatement.close(); // Close the price Statement
                }
            }
            resultSet.close(); // Close the resultSet
            statement.close(); // Close the main statement
            AuditService.writeAction("Printed PriceCategories for stand from database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
