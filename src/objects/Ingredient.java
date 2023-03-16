package objects;

public class Ingredient {
    private final int id;
    private String date;
    private String name;
    private boolean inventory;

    public Ingredient(int id, String date, String name, boolean inventory) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.inventory = inventory;
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

    public boolean getInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = inventory;
    }
}
