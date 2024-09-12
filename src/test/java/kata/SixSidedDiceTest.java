package kata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class SixSidedDiceTest {

  private SixSidedDice sixSidedDice;

  @BeforeEach
  void setUp() {
    sixSidedDice = new SixSidedDice();
  }

  @RepeatedTest(10_000)
  void check_value_distribution() {
    int[] results = new int[6];
    SixSidedDice sixSidedDice1 = new SixSidedDice();

    for (int i = 0; i < 10_000; i++) {
      int result = sixSidedDice1.roll().asInt();
      results[result - 1]++;
    }

    for (int i = 0; i < 6; i++) {
      assertThat(results[i]).isBetween(1450, 1850);
    }
  }
}
