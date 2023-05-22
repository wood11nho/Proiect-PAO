package Prices;

import MatchDetails.Stand;

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
        if (privateLoungeAccess)
            System.out.println("Pretul unui bilet la " + name + " este " + price + " si include acces la lounge-ul privat");
        else
            System.out.println("Pretul unui bilet la " + name + " este " + price + " si nu include acces la lounge-ul privat");
    }

    public boolean getPrivateLoungeAccess() {
        return privateLoungeAccess;
    }
}
