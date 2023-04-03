import Prices.PriceCategory;
import Stands.Stand;

import java.time.LocalDateTime;
import java.util.*;

public class MainService {
    private static Set<User> userList = new HashSet<>();
    private static List<Match> matchList = new ArrayList<>();
    private static List<Discount> discountList = new ArrayList<>();

    public MainService() {
    }

    public static Set<User> getUserList() {
        return userList;
    }

    public static void setUserList(Set<User> userList) {
        MainService.userList = userList;
    }

    public static List<Match> getMatchList() {
        return matchList;
    }

    public static void setMatchList(List<Match> matchList) {
        MainService.matchList = matchList;
    }

    public static List<Discount> getDiscountList() {
        return discountList;
    }

    public static void setDiscountList(List<Discount> discountList) {
        MainService.discountList = discountList;
    }

    public void addDiscount(Discount discount) {
        discountList.add(discount);
    }

    public void setStadiumStands(Stadium stadium, Stand[] stands) {
        stadium.setStands(stands);
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
}
