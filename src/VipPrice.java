public class VipPrice extends PriceCategory {
    private int price;

    public VipPrice(String name, Stand stand, int price) {
        super(name, stand);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void afisare_pret() {
        System.out.println("Pretul categoriei de pret " + this.getName() + " situat in " + this.getStand().getName() + " este " + this.getPrice() + " lei");
    }
}
