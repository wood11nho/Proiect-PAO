public abstract class Price {
    private String name;

    public Price(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getPrice();

}
