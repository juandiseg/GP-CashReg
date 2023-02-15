package objects;

public class orderItems {
    
    private final int id;
    private product product;
    private int quantity;

    public orderItems(int id, product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
    }

    public product getProduct() {
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
