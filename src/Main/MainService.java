package Main;

import MatchDetails.Match;
import MatchDetails.Stadium;
import MatchDetails.StadiumPlace;
import MatchDetails.Stand;
import OrderDetails.Discount;
import Prices.PriceCategory;
import Users.User;

import java.time.LocalDateTime;
import java.util.*;

public class MainService {
    private static Set<User> userList = new HashSet<>();
    static List<Match> matchList = new ArrayList<>();
    private static List<Discount> discountList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public MainService() {
    }

    public void addDiscount(Discount discount) {
        discountList.add(discount);
    }

    public void createMatch(String homeTeam, String awayTeam, Stadium stadium, LocalDateTime date, int spectators, PriceCategory[] priceCategories) {
        Match match = new Match(homeTeam, awayTeam, stadium, date, spectators);

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

        for (int i = 1; i < stands_an[0].getRows_number(); i++) {
            for (int j = 1; j < maximumSeatsPerRow; j++) {
                if (j < stands_an[0].getSeats_per_row()) {
                    places_an[0][i][j] = new StadiumPlace(stadium, stands_an[0], i, j);
                    places_an[1][i][j] = new StadiumPlace(stadium, stands_an[1], i, j);
                }
                places_an[2][i][j] = new StadiumPlace(stadium, stands_an[2], i, j);
                places_an[3][i][j] = new StadiumPlace(stadium, stands_an[3], i, j);
            }
        }
    }

    public void createAccount(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        userList.add(user);
    }

    public void login(Scanner scanner) {
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
    }
}
