package kata;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void testEquals() {
    EqualsVerifier.simple().forClass(Card.class).withIgnoredFields("flipped").verify();
  }
}
