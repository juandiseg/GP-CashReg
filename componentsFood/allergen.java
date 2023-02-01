package componentsFood;

public class allergen {

    private final int id;
    private String name;

    public allergen(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
