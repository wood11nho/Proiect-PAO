package OrderDetails;

import java.time.LocalDate;
import java.util.Arrays;

public class Order {
    private static int order_count = 1;
    private String order_number;
    private LocalDate order_date;
    //LISTA DE TICKETE
    private Ticket[] tickets;
    private double total_price;

    public Order(Ticket[] tickets) {
        this.order_number = "ORD" + String.format("%05d", order_count); // generate order number with leading zeroes
        this.order_number = order_number;
        //this.order_date should be system date
        this.order_date = LocalDate.now();
        this.tickets = new Ticket[0];
        this.total_price = 0;

        for (int i = 0; i < tickets.length; i++) {
            addTicket(tickets[i]);
            this.total_price += tickets[i].getPriceCategory().getPrice();
        }

        order_count++;
    }

    public void addTicket(Ticket ticket) {
        tickets = Arrays.copyOf(this.tickets, this.tickets.length + 1);
        tickets[tickets.length - 1] = ticket;
    }
}
