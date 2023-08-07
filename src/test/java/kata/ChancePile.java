package kata;

import java.util.Stack;

public class ChancePile implements AutomaticAction {

    public static ChancePile INSTANCE = new ChancePile();

    private Stack<ChanceCard> cards = new Stack<>();

    private ChancePile() {
        cards.push(new ChanceCard.BankCard());
    }

    public static AutomaticAction get() {
        return INSTANCE;
    }

    @Override
    public void execute(Player player, Monopoly monopoly, Place place) {
        ChanceCard card = cards.pop();
        card.execute(player, monopoly, place);
    }
}
