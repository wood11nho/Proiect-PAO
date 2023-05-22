package Database;

import MatchDetails.Stadium;
import MatchDetails.Stand;

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

    public void addStand(String name, int capacity, int remaining_capacity, boolean home_stand, int rows_number, int seats_per_row, int stadium_id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Stands (name, capacity, remainingcapacity, ishomestand, rowsnumber, seatsperrow, stadiumid) VALUES ('" + name + "', " + capacity + ", " + remaining_capacity + ", " + home_stand + ", " + rows_number + ", " + seats_per_row + ", " + stadium_id + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStand(Stand stand) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Stands (name, capacity, remainingcapacity, ishomestand, rowsnumber, seatsperrow) VALUES ('" + stand.getName() + "', " + stand.getCapacity() + ", " + stand.getRemainingCapacity() + ", " + stand.isHomeStand() + ", " + stand.getRowsNumber() + ", " + stand.getSeatsPerRow() + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStadiumId(Stand stand, Stadium stadium) {
        try {
            Statement statement = connection.createStatement();
            int stadiumId = statement.executeUpdate("SELECT id FROM Stadiums WHERE name = '" + stadium.getName() + "'");
            statement.executeUpdate("UPDATE Stands SET stadiumid = " + stadiumId + " WHERE name = '" + stand.getName() + "'");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
