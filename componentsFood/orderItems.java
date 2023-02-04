package componentsFood;

public class orderItems {
    
    private final int id;
    private String name;
    private int quantity;

    public orderItems(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
