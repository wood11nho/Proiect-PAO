public class Main {
    public static void main(String[] args) {

        Stand[] stands_an = new Stand[4];
        stands_an[0] = new Stand("Tribuna 1", 13500, true, 62, 218);
        stands_an[1] = new Stand("Tribuna 2", 13500, true, 62, 218);
        stands_an[2] = new Stand("Peluza Nord", 14300, true, 62, 231);
        stands_an[3] = new Stand("Peluza Sud", 14300, false, 62, 231);
        Stadium arena_nationala = new Stadium("Arena Nationala", "Bucuresti", 55600, stands_an);

        Price[] pricesmatch1 = new Price[5];
        Discount[] discountsmatch1 = new Discount[4];
        pricesmatch1[0] = new SouthNorthPrice("Peluza Catalin Hildan", 15);
        pricesmatch1[1] = new SouthNorthPrice("Peluza Oaspeti", 20);
        pricesmatch1[2] = new EastWestPrice("Tribuna 2", 30);
        pricesmatch1[3] = new EastWestPrice("Tribuna 1", 60);
        pricesmatch1[4] = new VipPrice("VIP", 100);
        discountsmatch1[0] = new Discount("Student", 0.5);
        discountsmatch1[1] = new Discount("Copil", 0.25);
        discountsmatch1[2] = new Discount("Pensionar", 0.25);
        discountsmatch1[3] = new Discount("Familie", 0.75);

        Match match1 = new Match("FC Botosani", "FC Dinamo", arena_nationala, 0, pricesmatch1, discountsmatch1);

        match1.printMatchDetails();
    }
}