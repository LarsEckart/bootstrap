package kata;

import com.spun.util.servlets.ValidationError;
import org.lambda.query.Queryable;

class Player {
    private final String name;
    public Queryable<Place> properties = new Queryable<>(Place.class);
    private int location;
    public int money = 1500;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int location() {
        return location;
    }

    public String details() {
        return this.name + " ($" + money + ") " + properties.join(", ", p -> p.name());
    }

    public void move(int spaces) {
        location += spaces;
    }

    public int getMoney() {
        return money;
    }
}