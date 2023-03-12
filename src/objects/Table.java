package objects;

public class Table {

    private final int table_id;
    private int order_id;
    private Boolean is_empty;

    public Table(int table_id, int order_id, Boolean is_empty) {
        this.table_id = table_id;
        this.order_id = order_id;
        this.is_empty = is_empty;
    }

    public int getID() {
        return table_id;
    }

    public int getOrder() {
        return order_id;
    }
    
    public Boolean isEmpty() {
        return is_empty;
    }
}
