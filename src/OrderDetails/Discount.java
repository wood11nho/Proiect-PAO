package OrderDetails;

public class Discount {
    private static int nextId = 1;
    private int id;
    private String name;
    private double discount;

    public Discount(String name, double discount) {
        this.id = nextId++;
        this.name = name;
        this.discount = discount;
    }

    public Discount() {

    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    public void afisare_discount() {
        System.out.println("Discountul " + name + " este de " + discount + "%");
    }
}
