package kata;

import java.util.Objects;

final class Player {
    private final String name;
    private int balance;

    Player(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void addMoney(int amount) {
        balance += amount;
    }

    public int balance() {
        return balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Player) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player[" +
                "name=" + name + ']';
    }
}
