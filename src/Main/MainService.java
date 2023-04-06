package Main;

import MatchDetails.Match;
import MatchDetails.Stadium;
import MatchDetails.StadiumPlace;
import MatchDetails.Stand;
import OrderDetails.Discount;
import OrderDetails.Order;
import OrderDetails.Ticket;
import Prices.EastWestPrice;
import Prices.PriceCategory;
import Prices.SouthNorthPrice;
import Prices.VipPrice;
import Users.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainService {
    //the admin user
    private final static User admin;
    static List<Match> matchList = new ArrayList<>();
    private static List<Discount> discountList = new ArrayList<>();
    private static Map<Integer, User> userList = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    private static Map<Stadium, StadiumPlace[][][]> stadiumPlaces = new HashMap<>();
    private static User currentUser;

    static {
        try {
            admin = new User(true);
            //add admin to the list
            UUID uuid = UUID.randomUUID();
            int id = uuid.hashCode();
            while (userList.containsKey(id)) {
                uuid = UUID.randomUUID();
                id = uuid.hashCode();
            }
            userList.put(id, admin);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MainService() {
    }

    public void addDiscount(Discount discount) {
        discountList.add(discount);
    }

    public void createMatch(String homeTeam, String awayTeam, Stadium stadium, LocalDateTime date, PriceCategory[] priceCategories) {
        Match match = new Match(homeTeam, awayTeam, stadium, date);

        //show discounts available
        System.out.println("Discounts available: ");

        for (int i = 0; i < discountList.size(); i++) {
            System.out.println(i + ". " + discountList.get(i).getName());
        }

        //choose discounts from the list, from keyboard and add them to the match
        System.out.println("Choose discounts for this match: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        Discount[] discounts = new Discount[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            discounts[i] = discountList.get(Integer.parseInt(inputArray[i]));
        }
        match.setDiscounts(discounts);

        //set prices for the match
        match.setPrices(priceCategories);

        //add match to the list
        matchList.add(match);
    }

    public void setStadiumPlaces(StadiumPlace[][][] places_an, Stadium stadium) {

        Stand[] stands_an = stadium.getStands();

        places_an[0] = new StadiumPlace[stands_an[0].getRows_number()][stands_an[0].getSeats_per_row()];
        places_an[1] = new StadiumPlace[stands_an[1].getRows_number()][stands_an[1].getSeats_per_row()];
        places_an[2] = new StadiumPlace[stands_an[2].getRows_number()][stands_an[2].getSeats_per_row()];
        places_an[3] = new StadiumPlace[stands_an[3].getRows_number()][stands_an[3].getSeats_per_row()];

        int maximumSeatsPerRow = 0;
        for (int i = 0; i < stands_an.length; i++) {
            if (stands_an[i].getSeats_per_row() > maximumSeatsPerRow) {
                maximumSeatsPerRow = stands_an[i].getSeats_per_row();
            }
        }

        int maximumRows = 0;
        for (int i = 0; i < stands_an.length; i++) {
            if (stands_an[i].getRows_number() > maximumRows) {
                maximumRows = stands_an[i].getRows_number();
            }
        }

        for (int i = 1; i < maximumRows; i++) {
            for (int j = 1; j < maximumSeatsPerRow; j++) {
                //i need to verify that both i and j are less than the maximum number of rows and seats per row
                if (i < stands_an[0].getRows_number() && j < stands_an[0].getSeats_per_row()) {
                    places_an[0][i][j] = new StadiumPlace(stadium, stands_an[0], i, j);
                }

                if (i < stands_an[1].getRows_number() && j < stands_an[1].getSeats_per_row()) {
                    places_an[1][i][j] = new StadiumPlace(stadium, stands_an[1], i, j);
                }

                if (i < stands_an[2].getRows_number() && j < stands_an[2].getSeats_per_row()) {
                    places_an[2][i][j] = new StadiumPlace(stadium, stands_an[2], i, j);
                }

                if (i < stands_an[3].getRows_number() && j < stands_an[3].getSeats_per_row()) {
                    places_an[3][i][j] = new StadiumPlace(stadium, stands_an[3], i, j);
                }

            }
        }

        stadiumPlaces.put(stadium, places_an);
        //afisare stadiumPlaces
//        for (int i = 0; i < stadiumPlaces.get(stadium).length; i++) {
//            for (int j = 0; j < stadiumPlaces.get(stadium)[i].length; j++) {
//                for (int k = 0; k < stadiumPlaces.get(stadium)[i][j].length; k++) {
//                    if (stadiumPlaces.get(stadium)[i][j][k] != null) {
//                        System.out.println(stadiumPlaces.get(stadium)[i][j][k].getStand().getName() + " " + stadiumPlaces.get(stadium)[i][j][k].getRow() + " " + stadiumPlaces.get(stadium)[i][j][k].getSeat());
//                    }
//                }
//            }
//        }
    }

    public void createAccount(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        //add user with a random, but not existing id to the list
        UUID uuid = UUID.randomUUID();
        int id = uuid.hashCode();
        while (userList.containsKey(id)) {
            uuid = UUID.randomUUID();
            id = uuid.hashCode();
        }
        userList.put(id, user);
        System.out.println("Account created!");

    }

    public void login(Scanner scanner) {
        System.out.println("Login");
        System.out.print("Enter username: ");
        String usernameLogin = scanner.nextLine();

        System.out.print("Enter password: ");
        String passwordLogin = scanner.nextLine();

        boolean loginSuccessful = false;

        for (Map.Entry<Integer, User> entry : userList.entrySet()) {
            if (entry.getValue().getUsername().equals(usernameLogin) && entry.getValue().checkPassword(passwordLogin)) {
                entry.getValue().setLogged(true);
                loginSuccessful = true;
                System.out.println("Login successful!");
                //set current user
                currentUser = entry.getValue();
            }
        }

        if (!loginSuccessful) {
            System.out.println("Login failed!");
        }
    }

    public boolean isUserLogged() {
        for (Map.Entry<Integer, User> entry : userList.entrySet()) {
            if (entry.getValue().isLogged()) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserAdmin() {
        for (Map.Entry<Integer, User> entry : userList.entrySet()) {
            if (entry.getValue().isLogged() && entry.getValue().isAdmin()) {
                return true;
            }
        }
        return false;
    }

    //i want to know the id of the logged user
    public int getLoggedUserId() {
        for (Map.Entry<Integer, User> entry : userList.entrySet()) {
            if (entry.getValue().isLogged()) {
                return entry.getKey();
            }
        }
        return -1;
    }

    //menu for admin
    public void adminMenu() {
        System.out.println("1. Create match");
        System.out.println("2. Create discount");
        System.out.println("3. Logout");
        System.out.println("4. Exit");

        int input = scanner.nextInt();
        scanner.nextLine();

        switch (input) {
            case 1:
                System.out.println("Create match");
                //create stadium from keyboard
                System.out.println("Enter stadium name: ");
                String stadiumName = scanner.nextLine();
                System.out.println("Enter stadium city: ");
                String stadiumCity = scanner.nextLine();

                Stand[] stands = new Stand[4];
                for (int i = 0; i < 4; i++) {
                    if (i == 0)
                        System.out.println("Configure First Stand");
                    else if (i == 1)
                        System.out.println("Configure Second Stand");
                    else if (i == 2)
                        System.out.println("Configure North Stand(Home Stand)");
                    else System.out.println("Configure South Stand(Away Stand)");

                    System.out.println("Enter stand name: ");
                    String standName = scanner.nextLine();
                    System.out.println("Enter stand rows number: ");
                    int standRowsNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter stand seats per row: ");
                    int standSeatsPerRow = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Is it home stand? (true/false)");
                    boolean isHomeStand = scanner.nextBoolean();
                    scanner.nextLine();
                    stands[i] = new Stand(standName, standRowsNumber * standSeatsPerRow, isHomeStand, standRowsNumber, standSeatsPerRow);
                }
                Stadium stadium = new Stadium(stadiumName, stadiumCity, stands);
                StadiumPlace[][][] places = new StadiumPlace[4][][];

                setStadiumPlaces(places, stadium);
                //create PriceCategory from keyboard
                System.out.println("Enter number of price categories: ");
                int priceCategoriesNumber = scanner.nextInt();
                scanner.nextLine();
                PriceCategory[] priceCategories = new PriceCategory[priceCategoriesNumber];
                for (int i = 0; i < priceCategoriesNumber; i++) {
                    System.out.println("Price Categories Available: ");
                    System.out.println("1. VIP");
                    System.out.println("2. South North");
                    System.out.println("3. East West");
                    System.out.println("Choose price category: ");
                    int priceCategory = scanner.nextInt();
                    scanner.nextLine();
                    switch (priceCategory) {
                        case 1:
                            String name = "VIP";
                            System.out.println("Enter price: ");
                            int price = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Does it have access to private lounge? (true/false)");
                            boolean hasPrivateLounge = scanner.nextBoolean();
                            scanner.nextLine();
                            priceCategories[i] = new VipPrice(name, stands[0], price, hasPrivateLounge);
                            break;
                        case 2:
                            name = "South North";
                            System.out.println("Enter price: ");
                            price = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Is it for home stand? (true/false)");
                            boolean isHomeStand = scanner.nextBoolean();
                            scanner.nextLine();
                            if (isHomeStand)
                                priceCategories[i] = new SouthNorthPrice(name, stands[2], price);
                            else
                                priceCategories[i] = new SouthNorthPrice(name, stands[3], price);
                            break;
                        case 3:
                            name = "East West";
                            System.out.println("Enter price: ");
                            price = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Is it the First Stand or Second Stand? (true/false)");
                            boolean isFirstStand = scanner.nextBoolean();
                            scanner.nextLine();
                            if (isFirstStand)
                                priceCategories[i] = new EastWestPrice(name, stands[0], price);
                            else
                                priceCategories[i] = new EastWestPrice(name, stands[1], price);
                            break;
                    }
                }
                //create match from keyboard
                System.out.println("Enter home team name: ");
                String homeTeamName = scanner.nextLine();
                System.out.println("Enter away team name: ");
                String awayTeamName = scanner.nextLine();
                //enter date but i want it in LocalDateTime format
                System.out.println("Enter date: ");
                String date = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                createMatch(homeTeamName, awayTeamName, stadium, dateTime, priceCategories);
                break;
        }
    }

    public void userMenu() {
        System.out.println("1. Buy ticket");
        System.out.println("2. Logout");
        System.out.println("3. Exit");

        int input = scanner.nextInt();
        scanner.nextLine();

        switch (input) {
            case 1:
                System.out.println("Buy ticket");
                //buy ticket
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

                System.out.print("Enter stand number: ");
                int standNumber = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Price categories available for this stand:");
                for (int i = 0; i < matchList.get(matchNumber - 1).getPrices().length; i++) {
                    if (matchList.get(matchNumber - 1).getPrices()[i].getStand().equals(matchList.get(matchNumber - 1).getStadium().getStands()[standNumber - 1])) {
                        System.out.print(i + 1 + ". ");
                        matchList.get(matchNumber - 1).getPrices()[i].afisare_pret();
                    }
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
                    System.out.println("OrderDetails.Ticket " + (i + 1) + ":");
                    System.out.print("Enter row number: ");
                    rowNumber = scanner.nextInt();
                    System.out.print("Enter seat number: ");
                    seatNumber = scanner.nextInt();

                    //verify if the seat is occupied
                    //first we need to match the stadium from the game you chose with the stadium from the StadiumPlaces map
                    //then we need to match the stand from the game you chose with the stand from the StadiumPlaces map
                    //then we need to match the row and seat from the game you chose with the row and seat from the StadiumPlaces map
                    //if the seat is occupied, we need to ask the user to choose another seat

                    for (Map.Entry<Stadium, StadiumPlace[][][]> entry : stadiumPlaces.entrySet()) {
                        if (entry.getKey().equals(matchList.get(matchNumber - 1).getStadium())) {
                            for (StadiumPlace[][] stadiumPlace : entry.getValue()) {
                                if (stadiumPlace[rowNumber - 1][seatNumber - 1].getStand().equals(matchList.get(matchNumber - 1).getStadium().getStands()[standNumber - 1])) {
                                    if (stadiumPlace[rowNumber - 1][seatNumber - 1].isOccupied()) {
                                        do {
                                            System.out.println("This seat is occupied. Please choose another seat.");
                                            System.out.print("Enter row number: ");
                                            rowNumber = scanner.nextInt();
                                            System.out.print("Enter seat number: ");
                                            seatNumber = scanner.nextInt();
                                        } while (stadiumPlace[rowNumber - 1][seatNumber - 1].isOccupied());
                                    }
                                }
                            }
                        }
                    }

                    //create ticket

                    System.out.println("Discounts available: ");
                    for (int j = 0; j < matchList.get(matchNumber - 1).getDiscounts().length; j++) {
                        System.out.print(j + 1 + ". ");
                        matchList.get(matchNumber - 1).getDiscounts()[j].afisare_discount();
                    }

                    System.out.print("Enter discount number: ");
                    int discountNumber = scanner.nextInt();

                    StadiumPlace stadiumPlace = new StadiumPlace(matchList.get(matchNumber - 1).getStadium(), matchList.get(matchNumber - 1).getStadium().getStands()[standNumber - 1], rowNumber, seatNumber);
                    tickets[i] = new Ticket(matchList.get(matchNumber - 1), stadiumPlace, matchList.get(matchNumber - 1).getPrices()[categoryNumber - 1], matchList.get(matchNumber - 1).getDiscounts()[discountNumber - 1]);
                    //il ocupam si in StadiumPlaces
                    for (Map.Entry<Stadium, StadiumPlace[][][]> entry : stadiumPlaces.entrySet()) {
                        if (entry.getKey().equals(matchList.get(matchNumber - 1).getStadium())) {
                            for (StadiumPlace[][] stadiumPlace1 : entry.getValue()) {
                                if (stadiumPlace1[rowNumber - 1][seatNumber - 1].getStand().equals(matchList.get(matchNumber - 1).getStadium().getStands()[standNumber - 1])) {
                                    stadiumPlace1[rowNumber - 1][seatNumber - 1].setOccupied(true);
                                }
                            }
                        }
                    }
                    //afisare bilet
                    tickets[i].afisare_bilet();
                }

                //create order
                Order order = new Order(tickets);
                System.out.println("Your order has been created.");

                //add order to user
                currentUser.addOrder(order);
                currentUser.afisare_comenzi();
                break;
        }
    }
}
