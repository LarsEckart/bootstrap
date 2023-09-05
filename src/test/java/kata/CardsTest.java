package kata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsTest {

    @Test
    void flipping_a_card() {
        Cards cards = new Cards();
        for (int i = 0; i < 12; i++) {
            cards.add(new Card(1));
        }
        cards.flip(1, 1);
        assertEquals("""
                <1><X1X><X1X><X1X>
                <X1X><X1X><X1X><X1X>
                <X1X><X1X><X1X><X1X>
                """, cards.toString());
    }

    @Test
    void flipping_another_card() {
        Cards cards = new Cards();
        for (int i = 0; i < 12; i++) {
            cards.add(new Card(1));
        }
        cards.flip(2, 2);
        assertEquals("""
                <X1X><X1X><X1X><X1X>
                <X1X><1><X1X><X1X>
                <X1X><X1X><X1X><X1X>
                """, cards.toString());
    }

}
