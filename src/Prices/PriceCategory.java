package Prices;

import Stands.Stand;

public abstract class PriceCategory {
    protected String name;
    protected Stand stand;

    public PriceCategory(String name, Stand stand) {
        this.name = name;
        this.stand = stand;
    }

    public String getName() {
        return name;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public abstract int getPrice();

    public abstract void afisare_pret();
}