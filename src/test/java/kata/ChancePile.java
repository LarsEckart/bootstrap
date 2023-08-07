package kata;

import java.util.Arrays;
import java.util.Stack;

public class ChancePile implements AutomaticAction {

    public static ChancePile INSTANCE = new ChancePile();

    private final Stack<ChanceCard> cards = new Stack<>();

    private ChancePile() {
        cards.push(new ChanceCard.BankCard());
    }

    public static AutomaticAction get() {
        return INSTANCE;
    }

    public static void stack(ChanceCard... chanceCard) {
        INSTANCE.cards.clear();
        Arrays.stream(chanceCard).forEach(item -> INSTANCE.cards.add(0, item));
    }

    public static int size() {
        return INSTANCE.cards.size();
    }

    @Override
    public void execute(Player player, Monopoly monopoly, Place place) {
        ChanceCard card = cards.pop();
        card.execute(player, monopoly, place);
    }
}
