package objects;

public class OrderItems {
    
    private final int id;
    private Product product;
    private int quantity;

    public OrderItems(int id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return product.getName();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
