package OrderDetails;

import java.time.LocalDate;
import java.util.Arrays;

public class Order {
    private static int order_count = 1;
    private int id;
    private String order_number;
    private LocalDate order_date;
    //LISTA DE TICKETE
    private Ticket[] tickets;
    private double total_price;

    public Order(Ticket[] tickets) {
        this.id = order_count;
        this.order_number = "ORD" + String.format("%05d", order_count); // generate order number with leading zeroes
        this.order_number = order_number;
        //this.order_date should be system date
        this.order_date = LocalDate.now();
        this.tickets = new Ticket[0];
        this.total_price = 0;

        for (int i = 0; i < tickets.length; i++) {
            addTicket(tickets[i]);
            this.total_price += tickets[i].getFinalPrice();
        }

        order_count++;
    }

    public void addTicket(Ticket ticket) {
        tickets = Arrays.copyOf(this.tickets, this.tickets.length + 1);
        tickets[tickets.length - 1] = ticket;
    }

    public void afisare_comanda() {
        System.out.println("Comanda cu numarul " + this.order_number + " din data de " + this.order_date + " are urmatoarele bilete:");
        for (Ticket ticket : tickets) {
            ticket.afisare_bilet();
        }
        System.out.println("Pretul total al comenzii este " + this.total_price + " lei");
    }
}
