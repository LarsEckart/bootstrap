package kata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {

  // DO we need a Cup that contains 2 Dice?

  @Test
  void when_dice_rolled_then_positive_number_returned() {
    SixSidedDice dice = new SixSidedDice();
    var diceGame = new DiceGame(dice);

    int result = diceGame.play();

    assertThat(result).isPositive();
  }

  @Test
  void when_two_dice_rolled_then_positive_number_returned() {
    SixSidedDice dice = new SixSidedDice();
    SixSidedDice dice2 = new SixSidedDice();
    var diceGame = new DiceGame(List.of(dice, dice2));

    int result = diceGame.play();

    assertThat(result).isPositive();
  }
}
