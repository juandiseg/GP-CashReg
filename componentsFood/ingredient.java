package componentsFood;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class ingredient {
    private final int id;
    private int provider_id;
    private String date;
    private String name;
    private float price;
    private int amount;
    private boolean in_inventory;
    private boolean active;

    public ingredient(int id, int provider_id, String date, String name, float price, int amount, boolean in_inventory,
            boolean active) {
        this.id = id;
        this.provider_id = provider_id;
        this.date = date;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.in_inventory = in_inventory;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public int getProviderID() {
        return provider_id;
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

    public int getAmount() {
        return amount;
    }

    public boolean getInInventory() {
        return in_inventory;
    }

    public boolean getActive() {
        return active;
    }

    public String toString() {
        return name;
    }
}
