package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MatchesTable {
    private Connection connection;

    public MatchesTable() {
        connection = MyJDBC.getConnection();
    }

    public void printMatches() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Matches");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("team1") + " vs " + resultSet.getString("team2") + " on " + resultSet.getString("datetime"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addMatch(String team1, String team2, int stadium_id, int sold_tickets, String date) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Matches (team1, team2, stadiumid, soldtickets, datetime) VALUES ('" + team1 + "', '" + team2 + "', " + stadium_id + ", " + sold_tickets + ", '" + date + "')");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
