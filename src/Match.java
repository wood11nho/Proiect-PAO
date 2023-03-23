public class Match {
    private String team1;
    private String team2;
    private Stadium stadium;
    private int sold_tickets;
    private PriceCategory[] priceCategories;
    private Discount[] discounts;

    public Match(String team1, String team2, Stadium stadium, int sold_tickets, PriceCategory[] priceCategories, Discount[] discounts) {
        this.team1 = team1;
        this.team2 = team2;
        this.stadium = stadium;
        this.sold_tickets = sold_tickets;
        this.priceCategories = priceCategories;
        this.discounts = discounts;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public int getSoldTickets() {
        return sold_tickets;
    }

    public void setSoldTickets(int sold_tickets) {
        this.sold_tickets = sold_tickets;
    }

    public PriceCategory[] getPrices() {
        return priceCategories;
    }

    public void setPrices(PriceCategory[] priceCategories) {
        this.priceCategories = priceCategories;
    }

    public Discount[] getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Discount[] discounts) {
        this.discounts = discounts;
    }

    public int getIncome() {
        int income = 0;
        for (int i = 0; i < priceCategories.length; i++) {
            income += priceCategories[i].getPrice();
        }
        return income;
    }

    public void printMatchDetails() {
        System.out.println("Meciul dintre " + this.team1 + " si " + this.team2 + " se va juca pe stadionul " + this.stadium.getName() + " din orasul " + this.stadium.getCity());
    }
}
