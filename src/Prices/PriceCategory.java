package Prices;

import MatchDetails.Stand;

public abstract class PriceCategory {
    protected static int nextId = 1;
    protected int id;
    protected String name;
    protected Stand stand;

    public PriceCategory(String name, Stand stand) {
        this.id = nextId++;
        this.name = name;
        this.stand = stand;
    }

    public int getId() {
        return id;
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
