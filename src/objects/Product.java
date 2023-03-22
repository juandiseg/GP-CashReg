package objects;

public class Product {
    private final int id;
    private String name;
    private float price;
    private boolean active;

    public Product(int id, String name, float price, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
    }

    public int getID() {
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

    public void setActive(boolean active) {
        this.active = active;
    }
}
