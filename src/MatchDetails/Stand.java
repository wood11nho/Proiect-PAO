package MatchDetails;

public class Stand {
    private static int nextId = 1;
    private int id;
    private String name;
    private int capacity;
    private int remaining_capacity;
    private boolean home_stand;
    private int rows_number;
    private int seats_per_row;

    public Stand(String name, int capacity, boolean home_stand, int rows_number, int seats_per_row) {
        this.id = nextId++;
        this.name = name;
        this.capacity = capacity;
        this.remaining_capacity = capacity;
        this.home_stand = home_stand;
        this.rows_number = rows_number;
        this.seats_per_row = seats_per_row;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isHome_stand() {
        return home_stand;
    }

    public void setHome_stand(boolean home_stand) {
        this.home_stand = home_stand;
    }

    public int getRows_number() {
        return rows_number;
    }

    public int getSeats_per_row() {
        return seats_per_row;
    }

    public void afisare_stand() {
        System.out.println("Tribuna " + this.name + " are o capacitate de " + this.remaining_capacity + " locuri si este " + (this.home_stand ? "tribuna de acasa" : "tribuna de oaspeti"));
    }

    public int getRemainingCapacity() {
        return remaining_capacity;
    }

    public int isHomeStand() {
        return home_stand ? 1 : 0;
    }

    public int getRowsNumber() {
        return rows_number;
    }

    public int getSeatsPerRow() {
        return seats_per_row;
    }
}
