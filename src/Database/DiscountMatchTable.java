package Database;

import Main.AuditService;
import MatchDetails.Match;
import OrderDetails.Discount;

import java.sql.Connection;

public class DiscountMatchTable {
    private Connection connection;

    public DiscountMatchTable() {
        connection = MyJDBC.getConnection();
    }

    public void addManyToMany(int discountId, int matchId) {
        try {
            connection.createStatement().executeUpdate("INSERT INTO discounts_matches (discountId, matchId) VALUES (" + discountId + ", " + matchId + ")");
            AuditService.writeAction("Added DiscountMatch to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addManyToMany(Discount discount, Match match) {
        try {
            //get id of discount from database
            int discountId = new DiscountsTable().getDiscountId(discount);
            //get id of match from database
            int matchId = new MatchesTable().getMatchId(match);
            //add to database
            connection.createStatement().executeUpdate("INSERT INTO discounts_matches (discountId, matchId) VALUES (" + discountId + ", " + matchId + ")");
            AuditService.writeAction("Added DiscountMatch to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
