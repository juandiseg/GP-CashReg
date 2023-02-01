package componentsFood;

public class product_ingredients {
    private final int productID;
    private final int ingredient_id;
    private String date;
    private int amount;

    public product_ingredients(int productID, int ingredient_id, String date, int amount) {
        this.productID = productID;
        this.ingredient_id = ingredient_id;
        this.date = date;
        this.amount = amount;
    }

    public int getProductID() {
        return productID;
    }

    public int getIngredientID() {
        return ingredient_id;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }
}
