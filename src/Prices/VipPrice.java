package Prices;

import Stands.Stand;

public class VipPrice extends PriceCategory {
    private int price;
    private boolean privateLoungeAccess;

    public VipPrice(String name, Stand stand, int price, boolean privateLoungeAccess) {
        super(name, stand);
        this.price = price;
        this.privateLoungeAccess = privateLoungeAccess;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void afisare_pret() {
        System.out.println("Pretul unui bilet la Tribuna 1 VIP este " + price);
    }
}
