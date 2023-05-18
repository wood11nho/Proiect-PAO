package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StandsTable {
    private Connection connection;

    public StandsTable() {
        connection = MyJDBC.getConnection();
    }

    public void printStands() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Stands");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStand(String name, int capacity, int remaining_capacity, boolean home_stand, int rows_number, int seats_per_row) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Stands (name, capacity, remainingcapacity, ishomestand, rowsnumber, seatsperrow) VALUES ('" + name + "', " + capacity + ", " + remaining_capacity + ", " + home_stand + ", " + rows_number + ", " + seats_per_row + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
