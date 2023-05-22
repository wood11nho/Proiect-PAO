package Database;

import MatchDetails.Match;
import MatchDetails.Stadium;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

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

    public void addMatch(Match match) {
        try {
            Statement statement = connection.createStatement();
            //get ID of stadium from database
            ResultSet stadiumID = statement.executeQuery("SELECT id FROM Stadiums WHERE name = '" + match.getStadium().getName() + "'");
            statement.executeUpdate("INSERT INTO Matches (team1, team2, stadiumid, soldtickets, datetime) VALUES ('" + match.getTeam1() + "', '" + match.getTeam2() + "', " + stadiumID + ", " + match.getSoldTickets() + ", '" + match.getDate() + "')");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getMatchId(String homeTeamName, String awayTeamName, Stadium stadium, LocalDateTime dateTime) {
        try {
            Statement statement = connection.createStatement();
            //get stadium id from database
            ResultSet stadiumID = statement.executeQuery("SELECT id FROM Stadiums WHERE name = '" + stadium.getName() + "'");
            //get match id from database
            ResultSet resultSet = statement.executeQuery("SELECT id FROM Matches WHERE team1 = '" + homeTeamName + "' AND team2 = '" + awayTeamName + "' AND stadiumid = " + stadiumID + " AND datetime = '" + dateTime + "'");
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }
}
