package objects;

public class OrderMenus {
    
    private final int id;
    private Menu menu;
    private int quantity;

    public OrderMenus(int id, Menu menu, int quantity) {
        this.id = id;
        this.menu = menu;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return menu.getName();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
