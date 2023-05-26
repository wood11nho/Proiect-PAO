package Database;

import Main.AuditService;
import MatchDetails.Match;
import MatchDetails.Stadium;
import MatchDetails.Stand;
import OrderDetails.Discount;
import Prices.EastWestPrice;
import Prices.PriceCategory;
import Prices.SouthNorthPrice;
import Prices.VipPrice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                int matchId = resultSet.getInt("matchid");
                String team1 = resultSet.getString("team1");
                String team2 = resultSet.getString("team2");
                int stadiumId = resultSet.getInt("stadiumid");
                int soldTickets = resultSet.getInt("soldtickets");
                String date = resultSet.getString("datetime");
                // get data from Stadiums table about the stadium, using stadiumid
                Statement stadiumStatement = connection.createStatement(); // Create a new Statement object
                ResultSet stadium = stadiumStatement.executeQuery("SELECT * FROM Stadiums WHERE StadiumID = " + stadiumId);
                if (stadium.next()) {
                    String stadiumName = stadium.getString("name");
                    String stadiumCity = stadium.getString("city");
                    int stadiumCapacity = stadium.getInt("capacity");
                    System.out.println(matchId + ". " + team1 + " vs " + team2 + " at " + stadiumName + " in " + stadiumCity + " with capacity of " + stadiumCapacity + " on " + date + " with " + soldTickets + " tickets sold");
                }
                stadium.close(); // Close the stadium ResultSet
                stadiumStatement.close(); // Close the stadium Statement
            }
            resultSet.close(); // Close the resultSet
            statement.close(); // Close the main statement
            AuditService.writeAction("Printed Matches");
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
            // Get ID of stadium from database
            ResultSet stadiumID = statement.executeQuery("SELECT StadiumID FROM Stadiums WHERE name = '" + match.getStadium().getName() + "'");
            if (stadiumID.next()) {
                int stadiumIdValue = stadiumID.getInt("StadiumID");

                // Insert into Matches table
                statement.executeUpdate("INSERT INTO Matches (team1, team2, stadiumid, soldtickets, datetime) VALUES ('" + match.getTeam1() + "', '" + match.getTeam2() + "', " + stadiumIdValue + ", " + match.getSoldTickets() + ", '" + match.getDate() + "')");
            }
            stadiumID.close();
            statement.close();
            AuditService.writeAction("Added Match to database");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public int getMatchId(String homeTeamName, String awayTeamName, Stadium stadium, LocalDateTime dateTime) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MatchID FROM Matches WHERE team1 = ? AND team2 = ? AND stadiumid = ? AND datetime = ?");
            statement.setString(1, homeTeamName);
            System.out.println(homeTeamName);
            statement.setString(2, awayTeamName);
            System.out.println(awayTeamName);

            // Get stadium id from database
            PreparedStatement stadiumStatement = connection.prepareStatement("SELECT StadiumID FROM Stadiums WHERE name = ?");
            stadiumStatement.setString(1, stadium.getName());
            ResultSet stadiumID = stadiumStatement.executeQuery();

            if (stadiumID.next()) {
                int stadiumIdValue = stadiumID.getInt("StadiumID");

                // Set stadium id and datetime parameters for the query
                statement.setInt(3, stadiumIdValue);
                System.out.println(stadiumIdValue);
                statement.setString(4, dateTime.toString());
                System.out.println(dateTime.toString());

                // Execute the query
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    AuditService.writeAction("Got MatchID from database");
                    return resultSet.getInt("MatchID");
                }
            }
            stadiumID.close();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }


    public int getMatchId(Match match) {
        try {
            Statement statement = connection.createStatement();
            //get stadium id from database
            ResultSet stadiumID = statement.executeQuery("SELECT id FROM Stadiums WHERE name = '" + match.getStadium().getName() + "'");
            //get match id from database
            ResultSet resultSet = statement.executeQuery("SELECT id FROM Matches WHERE team1 = '" + match.getTeam1() + "' AND team2 = '" + match.getTeam2() + "' AND stadiumid = " + stadiumID + " AND datetime = '" + match.getDate() + "'");
            if (resultSet.next()) {
                AuditService.writeAction("Got MatchID from database");
                return resultSet.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public int getNumberOfMatches() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Matches");
            if (resultSet.next()) {
                AuditService.writeAction("Got number of matches from database");
                return resultSet.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public int getStadiumIdOfMatch(int matchId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT stadiumid FROM Matches WHERE matchid = " + matchId);
            if (resultSet.next()) {
                AuditService.writeAction("Got StadiumID of match from database");
                return resultSet.getInt("stadiumid");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public List<Match> getAllMatchesInAList() {
        List<Match> matches = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Matches");
            while (resultSet.next()) {
                int matchId = resultSet.getInt("matchid");
                String team1 = resultSet.getString("team1");
                String team2 = resultSet.getString("team2");
                int stadiumId = resultSet.getInt("stadiumid");
                int soldTickets = resultSet.getInt("soldtickets");
                String date = resultSet.getString("datetime");

                // get data from Stadiums table about the stadium, using stadiumid
                Statement stadiumStatement = connection.createStatement();
                ResultSet stadium = stadiumStatement.executeQuery("SELECT * FROM Stadiums WHERE StadiumID = " + stadiumId);
                if (stadium.next()) {
                    String stadiumName = stadium.getString("name");
                    String stadiumCity = stadium.getString("city");
                    Stand[] stands = new Stand[4];
                    PriceCategory[] priceCategories = new PriceCategory[7];
                    Discount[] discounts = new Discount[4];
                    Statement standsStatement = connection.createStatement();
                    ResultSet standsResultSet = standsStatement.executeQuery("SELECT * FROM Stands WHERE stadiumid = " + stadiumId);
                    int i = 0;
                    int j = 0;
                    while (standsResultSet.next()) {
                        int standId = standsResultSet.getInt("standid");
                        String standName = standsResultSet.getString("name");
                        int standCapacity = standsResultSet.getInt("capacity");
                        boolean home_stand = standsResultSet.getBoolean("ishomestand");
                        int rowsnumber = standsResultSet.getInt("rowsnumber");
                        int seatsperrow = standsResultSet.getInt("seatsperrow");
                        stands[i] = new Stand(standName, standCapacity, home_stand, rowsnumber, seatsperrow);

                        // Fetch data from PriceCategories ResultSet and store it in variables
                        int priceCategoryPrice = 0;
                        boolean privateLoungeAccess = false;
                        String priceCategoryName = "";

                        // Create a new Statement for executing queries inside the inner loop
                        Statement priceStatement = connection.createStatement();
                        ResultSet priceCategoriesResultSet = priceStatement.executeQuery("SELECT * FROM PriceCategories WHERE standid = " + standId + " AND matchid = " + matchId);

                        while (priceCategoriesResultSet.next()) {
                            int priceCategoryId = priceCategoriesResultSet.getInt("pricecategoryid");
                            priceCategoryName = priceCategoriesResultSet.getString("categorytype");
//                            System.out.println(priceCategoryName + " " + priceCategoryId);
                            if (priceCategoryName.equals("VIP1") || priceCategoryName.equals("VIP2")) {
                                Statement vipStatement = connection.createStatement();
                                ResultSet vipresultset = vipStatement.executeQuery("SELECT * FROM VIPPrices WHERE id = " + priceCategoryId);
                                if (vipresultset.next()) {
                                    privateLoungeAccess = vipresultset.getBoolean("privateloungeaccess");
                                    priceCategoryPrice = vipresultset.getInt("vipprice");
                                }
                                vipresultset.close(); // Close the VIPPrices ResultSet
                            } else if (priceCategoryName.equals("Peluza")) {
                                Statement SouthNorthStatement = connection.createStatement();
                                ResultSet SouthNorthResultSet = SouthNorthStatement.executeQuery("SELECT * FROM SouthNorthPrices WHERE id = " + priceCategoryId);
                                if (SouthNorthResultSet.next()) {
                                    priceCategoryPrice = SouthNorthResultSet.getInt("SouthNorthPrice");
                                }
                                SouthNorthResultSet.close(); // Close the SouthNorthPrices ResultSet
                            } else if (priceCategoryName.equals("Tribuna")) {
                                Statement EastWestStatement = connection.createStatement();
                                ResultSet EastWestResultSet = EastWestStatement.executeQuery("SELECT * FROM EastWestPrices WHERE id = " + priceCategoryId);
                                if (EastWestResultSet.next()) {
                                    priceCategoryPrice = EastWestResultSet.getInt("EastWestPrice");
                                }
                                EastWestResultSet.close(); // Close the EastWestPrices ResultSet
                            }
                            if (priceCategoryId >= priceCategories.length) {
                                priceCategoryId -= priceCategories.length;
                            }
                            priceCategories[priceCategoryId] = createPriceCategory(priceCategoryName, stands[i], priceCategoryPrice, privateLoungeAccess);
                            j++;
                        }
                        priceCategoriesResultSet.close(); // Close the priceCategoriesResultSet
                        priceStatement.close(); // Close the priceStatement
                        i++;
                    }
                    standsStatement.close(); // Close the standsStatement
                    standsResultSet.close(); // Close the standsResultSet

                    stadium.close(); // Close the stadium ResultSet
                    stadiumStatement.close(); // Close the stadium Statement

                    Statement discountsStatement = connection.createStatement();
                    ResultSet discountsResultSet = discountsStatement.executeQuery("SELECT * FROM Discounts_Matches WHERE matchid = " + matchId);
                    int k = 0;
                    while (discountsResultSet.next()) {
                        int discountId = discountsResultSet.getInt("discountid");
                        Statement discountStatement = connection.createStatement();
                        ResultSet discountResultSet = discountStatement.executeQuery("SELECT * FROM Discounts WHERE discountid = " + discountId);
                        if (discountResultSet.next()) {
                            String discountName = discountResultSet.getString("name");
                            double discountPercentage = discountResultSet.getDouble("discount");
                            discounts[k] = new Discount(discountName, discountPercentage);
                        }
                        discountResultSet.close(); // Close the discountResultSet
                        discountStatement.close(); // Close the discountStatement
                        k++;
                    }
                    discountsResultSet.close(); // Close the discountsResultSet
                    discountsStatement.close(); // Close the discountsStatement

                    matches.add(new Match(matchId, team1, team2, new Stadium(stadiumName, stadiumCity, stands), soldTickets, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                    matches.get(matches.size() - 1).setPrices(priceCategories);
                    matches.get(matches.size() - 1).setDiscounts(discounts);
                }
            }
            resultSet.close(); // Close the resultSet
            statement.close(); // Close the main statement
            AuditService.writeAction("Got all matches from database");
            //afisare detalii meci 1
//            matches.get(0).printMatchDetails();
            return matches;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private PriceCategory createPriceCategory(String priceCategoryName, Stand stand, int price, boolean privateLoungeAccess) {
        if (priceCategoryName.equals("VIP1") || priceCategoryName.equals("VIP2")) {
            return new VipPrice(priceCategoryName, stand, price, privateLoungeAccess);
        } else if (priceCategoryName.equals("Peluza")) {
            return new SouthNorthPrice(priceCategoryName, stand, price);
        } else if (priceCategoryName.equals("Tribuna")) {
            return new EastWestPrice(priceCategoryName, stand, price);
        }
        return null;
    }

}
