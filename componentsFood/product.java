package componentsFood;

public class product {
    private final int id;
    private String date;
    private String name;
    private float price;
    private boolean active;

    public product(int id, String date, String name, float price, boolean active) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
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
