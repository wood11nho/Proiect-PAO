package MatchDetails;

public class StadiumPlace {
    private static int nextId = 1;
    private int id;
    private Stadium stadium;
    private Stand stand;
    private int row;
    private int seat;
    private boolean isOccupied;

    public StadiumPlace(Stadium stadium, Stand stand, int row, int seat) {
        if (row > stand.getRows_number() || row < 1) {
            throw new IllegalArgumentException("Numarul randului trebuie sa fie intre 1 si " + stand.getRows_number());
        }

        if (seat > stand.getSeats_per_row() || seat < 1) {
            throw new IllegalArgumentException("Numarul locului trebuie sa fie intre 1 si " + stand.getSeats_per_row());
        }

        this.id = nextId++;
        this.stadium = stadium;
        this.stand = stand;
        this.row = row;
        this.seat = seat;
        this.isOccupied = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public void afisare_loc() {
        System.out.println("Locul " + this.getSeat() + " din randul " + this.getRow() + " din tribuna " + this.getStand().getName() + " din stadionul " + this.getStadium().getName());
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
}
