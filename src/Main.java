import Prices.EastWestPrice;
import Prices.PriceCategory;
import Prices.SouthNorthPrice;
import Prices.VipPrice;
import Stands.Stand;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static Set<User> userList = new HashSet<>();
    private static List<Match> matchList = new ArrayList<>();
    public static void main(String[] args) {

        MainService mainService = new MainService();

        mainService.addDiscount(new Discount("Student", 0.5));
        mainService.addDiscount(new Discount("Copil", 0.25));
        mainService.addDiscount(new Discount("Pensionar", 0.25));
        mainService.addDiscount(new Discount("Familie", 0.75));
        mainService.addDiscount(new Discount("Niciun Discount", 0));

        Stand[] stands_an = new Stand[4];
        stands_an[0] = new Stand("Tribuna 1", 13500, true, 62, 218);
        stands_an[1] = new Stand("Tribuna 2", 13500, true, 62, 218);
        stands_an[2] = new Stand("Peluza Nord", 14300, true, 62, 231);
        stands_an[3] = new Stand("Peluza Sud", 14300, false, 62, 231);

        Stadium arena_nationala = new Stadium("Arena Nationala", "Bucuresti", 55600);
        mainService.setStadiumStands(arena_nationala, stands_an);

        PriceCategory[] pricesmatch1 = new PriceCategory[6];
        pricesmatch1[0] = new SouthNorthPrice("Peluza Catalin Hildan", stands_an[2], 15);
        pricesmatch1[1] = new SouthNorthPrice("Peluza Oaspeti", stands_an[3], 20);
        pricesmatch1[2] = new EastWestPrice("Tribuna 2", stands_an[1], 30);
        pricesmatch1[3] = new EastWestPrice("Tribuna 1", stands_an[0], 60);
        pricesmatch1[4] = new VipPrice("VIP", stands_an[0], 200, true);
        pricesmatch1[5] = new VipPrice("VIP", stands_an[0], 100, false);

        mainService.createMatch("FC Dinamo", "FCSB", arena_nationala, LocalDateTime.of(2023, 5, 5, 20, 30), 0, pricesmatch1);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user1 = new User(username, password);

        userList.add(user1);

        //Let's login
        System.out.println("Login");
        System.out.print("Enter username: ");
        String usernameLogin = scanner.nextLine();

        System.out.print("Enter password: ");
        String passwordLogin = scanner.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(usernameLogin) && user.checkPassword(passwordLogin)) {
                System.out.println("Login successful!");
                user.setLogged(true);
            } else {
                System.out.println("Login failed!");
            }
        }

        //Let's buy some tickets
        System.out.println("Buy tickets");
        System.out.print("Enter number of tickets: ");
        int numberOfTickets = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Matches available:");
        for (int i = 0; i < matchList.size(); i++) {
            System.out.print(i + 1 + ". ");
            matchList.get(i).printMatchDetails();
        }

        System.out.print("Enter match number: ");
        int matchNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Stands available:");
        for (int i = 0; i < matchList.get(matchNumber - 1).getStadium().getStands().length; i++) {
            System.out.print(i + 1 + ". ");
            matchList.get(matchNumber - 1).getStadium().getStands()[i].afisare_stand();
        }

        System.out.println("Price categories available:");
        for (int i = 0; i < matchList.get(matchNumber - 1).getPrices().length; i++) {
            System.out.print(i + 1 + ". ");
            matchList.get(matchNumber - 1).getPrices()[i].afisare_pret();
        }

        System.out.println("Choose the category of tickets you want to buy:");
        System.out.print("Enter category number: ");
        int categoryNumber = scanner.nextInt();

        //Choose your seat:
        if (numberOfTickets > 1) {
            System.out.println("Choose your seats:");
        } else {
            System.out.println("Choose your seat:");
        }

        int rowNumber = 0, seatNumber = 0;

        Ticket[] tickets = new Ticket[numberOfTickets];

        for (int i = 0; i < numberOfTickets; i++) {
            System.out.println("Ticket " + (i + 1) + ":");
            System.out.print("Enter row number: ");
            rowNumber = scanner.nextInt();
            System.out.print("Enter seat number: ");
            seatNumber = scanner.nextInt();

            //verify if the seat is occupied

            StadiumPlace stadiumPlace = new StadiumPlace(matchList.get(matchNumber - 1).getStadium(), matchList.get(matchNumber - 1).getPrices()[categoryNumber - 1].getStand(), rowNumber, seatNumber);
            stadiumPlace.setOccupied(true);

            tickets[i] = new Ticket(matchList.get(matchNumber - 1), stadiumPlace, matchList.get(matchNumber - 1).getPrices()[categoryNumber - 1], matchList.get(matchNumber - 1).getDiscounts()[4]);
            //afisare bilet
            tickets[i].afisare_bilet();
        }

        //create order
        Order order = new Order(tickets);
    }
}