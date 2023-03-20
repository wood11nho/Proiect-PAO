public class Match {
    private String team1;
    private String team2;
    private Stadium stadium;
    private int sold_tickets;
    private Price[] prices;
    private Discount[] discounts;

    public Match(String team1, String team2, Stadium stadium, int sold_tickets, Price[] prices, Discount[] discounts) {
        this.team1 = team1;
        this.team2 = team2;
        this.stadium = stadium;
        this.sold_tickets = sold_tickets;
        this.prices = prices;
        this.discounts = discounts;
    }
}
