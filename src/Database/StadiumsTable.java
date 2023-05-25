package Database;

import Main.AuditService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StadiumsTable {
    private Connection connection;

    public StadiumsTable() {
        connection = MyJDBC.getConnection();
    }

    public void printStadiums() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Stadiums");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            AuditService.writeAction("Printed Stadiums");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStadium(String name, String city, int capacity) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Stadiums (name, city, capacity) VALUES ('" + name + "', '" + city + "', " + capacity + ")");
            AuditService.writeAction("Added Stadium to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
