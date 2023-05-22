package Main;

import static Main.MainService.scanner;

public class Main {
    public static void main(String[] args) {

        MainService mainService = new MainService();

//        mainService.addDiscount(new Discount("Student", 0.5));
//        mainService.addDiscount(new Discount("Copil", 0.25));
//        mainService.addDiscount(new Discount("Pensionar", 0.25));
//        mainService.addDiscount(new Discount("Familie", 0.75));
//        mainService.addDiscount(new Discount("Niciun Discount", 0));
//
//        DiscountsTable discountsTable = new DiscountsTable();
//        discountsTable.addDiscount("Student", 0.5);
//        discountsTable.addDiscount("Copil", 0.25);
//        discountsTable.addDiscount("Pensionar", 0.25);
//        discountsTable.addDiscount("Familie", 0.75);
//        discountsTable.addDiscount("Niciun Discount", 0);
//
//        Stand[] stands_an = new Stand[4];
//        stands_an[0] = new Stand("Tribuna 1", 13500, true, 61, 226);
//        stands_an[1] = new Stand("Tribuna 2", 13500, true, 61, 226);
//        stands_an[2] = new Stand("Peluza Nord", 14280, true, 61, 239);
//        stands_an[3] = new Stand("Peluza Sud", 14280, false, 61, 239);
//
//        Stadium arena_nationala = new Stadium("Arena Nationala", "Bucuresti", stands_an);
//
//        StadiumsTable stadiumsTable = new StadiumsTable();
//        stadiumsTable.addStadium("Arena Nationala", "Bucuresti");
//
//        StandsTable standsTable = new StandsTable();
//        standsTable.addStand("Tribuna 1", 13500, 13500, true, 61, 226, 1);
//        standsTable.addStand("Tribuna 2", 13500, 13500, true, 61, 226, 1);
//        standsTable.addStand("Peluza Nord", 14280, 14280, true, 61, 239, 1);
//        standsTable.addStand("Peluza Sud", 14280, 14280, false, 61, 239, 1);
//
//        StadiumPlace[][][] places_an = new StadiumPlace[4][][];
//        mainService.setStadiumPlaces(places_an, arena_nationala);
//
//        PriceCategory[] pricesmatch1 = new PriceCategory[6];
//        pricesmatch1[0] = new SouthNorthPrice("Peluza Catalin Hildan", stands_an[2], 15);
//        pricesmatch1[1] = new SouthNorthPrice("Peluza Oaspeti", stands_an[3], 20);
//        pricesmatch1[2] = new EastWestPrice("Tribuna 2", stands_an[1], 30);
//        pricesmatch1[3] = new EastWestPrice("Tribuna 1", stands_an[0], 60);
//        pricesmatch1[4] = new VipPrice("VIP1", stands_an[0], 200, true);
//        pricesmatch1[5] = new VipPrice("VIP2", stands_an[0], 100, false);
//
//        PriceCategoriesTable priceCategoriesTable = new PriceCategoriesTable();
//        priceCategoriesTable.addPriceCategory("Peluza Catalin Hildan", "Peluza", 3, 15, false);
//        priceCategoriesTable.addPriceCategory("Peluza Oaspeti", "Peluza", 4, 20, false);
//        priceCategoriesTable.addPriceCategory("Tribuna 2", "Tribuna", 2, 30, false);
//        priceCategoriesTable.addPriceCategory("Tribuna 1", "Tribuna", 1, 60, false);
//        priceCategoriesTable.addPriceCategory("VIP1", "VIP", 1, 200, true);
//        priceCategoriesTable.addPriceCategory("VIP2", "VIP", 1, 100, false);

//        mainService.createMatch("Dinamo", "FCSB", arena_nationala, LocalDateTime.of(2021, 5, 5, 20, 30), pricesmatch1);

        System.out.println("Hello, welcome to 43-3ckets! Here you can buy tickets for your favorite matches!");
        //Let's create an account
        mainService.createAccount(scanner);

        //Let's login
        boolean loggedIn = false;
        while (!loggedIn) {
            mainService.login(scanner);
            if (mainService.isUserLogged()) {
                loggedIn = true;
            }
        }

        //verify if you are admin
        if (mainService.isUserAdmin()) {
            mainService.adminMenu();
            //afisare meciuri
        } else {
            mainService.userMenu();
        }
    }
}