package MatchDetails;

import java.util.Arrays;

public class Stadium {
    private static int nextId = 1;
    private int id;
    private String name;
    private String city;
    private int capacity;
    private Stand[] stands;

    public Stadium(String name, String city, Stand[] stands) {
        this.id = nextId++;
        this.name = name;
        this.city = city;
        this.stands = new Stand[0];

        for (Stand stand : stands) {
            this.adaugare_stand(stand);
        }

        this.capacity = 0;
        for (Stand stand : stands) {
            this.capacity += stand.getCapacity();
        }
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getCapacity() {
        return capacity;
    }

    public Stand[] getStands() {
        return stands;
    }

    public void setStands(Stand[] stands) {
        this.stands = stands;
    }

    public void adaugare_stand(Stand stand) {
        stands = Arrays.copyOf(this.stands, this.stands.length + 1);
        stands[stands.length - 1] = stand;
    }

    public void afisare_stadion() {
        System.out.println("Stadionul " + this.name + " din orasul " + this.city + " are o capacitate de " + this.capacity + " locuri");
        System.out.println("Stadionul are urmatoarele tribune:");
        for (Stand stand : stands) {
            stand.afisare_stand();
        }
    }
}
