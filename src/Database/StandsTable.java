package Database;

import Main.AuditService;
import MatchDetails.Stadium;
import MatchDetails.Stand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            AuditService.writeAction("Printed Stands");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printStands(int stadium_id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Stands WHERE stadiumid = " + stadium_id);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            AuditService.writeAction("Printed Stands from stadium with id " + stadium_id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStand(String name, int capacity, int remaining_capacity, boolean home_stand, int rows_number, int seats_per_row, int stadium_id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Stands (name, capacity, remainingcapacity, ishomestand, rowsnumber, seatsperrow, stadiumid) VALUES ('" + name + "', " + capacity + ", " + remaining_capacity + ", " + home_stand + ", " + rows_number + ", " + seats_per_row + ", " + stadium_id + ")");
            AuditService.writeAction("Added Stand to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStand(Stand stand) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Stands (name, capacity, remainingcapacity, ishomestand, rowsnumber, seatsperrow) VALUES ('" + stand.getName() + "', " + stand.getCapacity() + ", " + stand.getRemainingCapacity() + ", " + stand.isHomeStand() + ", " + stand.getRowsNumber() + ", " + stand.getSeatsPerRow() + ")");
            AuditService.writeAction("Added Stand to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStadiumId(Stand stand, Stadium stadium) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT StadiumID FROM Stadiums WHERE name = '" + stadium.getName() + "'");

            if (resultSet.next()) { // Move the cursor to the first row
                int stadium_id = resultSet.getInt("StadiumID");
                statement.executeUpdate("UPDATE Stands SET stadiumid = " + stadium_id + " WHERE name = '" + stand.getName() + "'");
            }

            AuditService.writeAction("Updated stadium id for stand " + stand.getName());
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getStandId(Stand stand) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT StandID FROM Stands WHERE name = '" + stand.getName() + "'");

            if (resultSet.next()) { // Move the cursor to the first row
                int stand_id = resultSet.getInt("StandID");
                resultSet.close();
                statement.close();
                AuditService.writeAction("Got stand id for stand " + stand.getName());
                return stand_id;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
}
