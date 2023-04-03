import Prices.PriceCategory;

public class Ticket {
    private Match match;
    private StadiumPlace place;
    private PriceCategory priceCategory;
    private Discount discount;

    public Ticket(Match match, StadiumPlace place, PriceCategory priceCategory, Discount discount) {
        this.match = match;
        this.place = place;
        this.priceCategory = priceCategory;
        this.discount = discount;
    }

    public Match getMatch() {
        return match;
    }

    public StadiumPlace getPlace() {
        return place;
    }

    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    public Discount getDiscount() {
        return discount;
    }

    public double getFinalPrice() {
        return this.getPriceCategory().getPrice() - this.getDiscount().getDiscount();
    }

    public void afisare_bilet() {
        System.out.println("Biletul pentru meciul " + this.getMatch().getTeam1() + " - " + this.getMatch().getTeam2() + " din stadionul " + this.getMatch().getStadium().getName() + " in sectiunea " + this.getPlace().getStand().getName() + " randul " + this.getPlace().getRow() + " locul " + this.getPlace().getSeat() + " cu pretul " + this.getPriceCategory().getPrice() + " lei cu discountul " + this.getDiscount().getDiscount() + " are pretul final de " + this.getFinalPrice() + " lei");
    }
}
