import java.util.Arrays;

public class Stadium {
    private String name;
    private String city;
    private int capacity;
    private Stand[] stands;

    public Stadium(String name, String city, int capacity, Stand[] stands) {
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.stands = new Stand[0];

        for (Stand stand : stands) {
            this.adaugare_stand(stand);
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
