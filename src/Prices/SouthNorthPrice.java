package Prices;

import MatchDetails.Stand;

public class SouthNorthPrice extends PriceCategory {
    private int price;

    public SouthNorthPrice(String name, Stand stand, int price) {
        super(name, stand);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void afisare_pret() {
        System.out.println("Pretul unui bilet la " + name + " este " + price);
    }
}
