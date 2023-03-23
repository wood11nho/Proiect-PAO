public class Ticket {
    private Match match;
    private StadiumPlace place;
    private Price price;
    private Discount discount;

    public Ticket(Match match, StadiumPlace place, Price price, Discount discount) {
        this.match = match;
        this.place = place;
        this.price = price;
        this.discount = discount;
    }

    public Match getMatch() {
        return match;
    }

    public StadiumPlace getPlace() {
        return place;
    }

    public Price getPrice() {
        return price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public int getFinalPrice() {
        return (int) (price.getPrice() * discount.getDiscount());
    }
}
