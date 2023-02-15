package objects;

public class product {
    private final int id;
    private String name;
    private float price;
    private boolean active;

    public product(int id, String name, float price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public boolean getActive() {
        return active;
    }

    public String toString() {
        return name;
    }
}
