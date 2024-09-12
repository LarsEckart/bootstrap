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
    var dice = new AlwaysSameDiceResultDice(1);
    var diceGame = new DiceGame(dice);

    GameResult result = diceGame.play();

    assertThat(result).isEqualTo(new GameResult(1));
  }

  @Test
  void when_two_dice_rolled_then_positive_number_returned() {
    var dice = new AlwaysSameDiceResultDice(1);
    var dice2 = new AlwaysSameDiceResultDice(3);
    var diceGame = new DiceGame(List.of(dice, dice2));

    GameResult result = diceGame.play();

    assertThat(result).isEqualTo(new GameResult(4));
  }

  @Test
  void when_two_dice_rolled_and_sum_larger_than_7_then_we_win() {
    var dice = new AlwaysSameDiceResultDice(4);
    var dice2 = new AlwaysSameDiceResultDice(6);
    var diceGame = new DiceGame(List.of(dice, dice2));

    GameResult result = diceGame.play();

    assertThat(result.hasWon()).isTrue();
  }

  @Test
  void when_two_dice_rolled_and_sum_less_than_7_then_we_lose() {
    var dice = new AlwaysSameDiceResultDice(4);
    var dice2 = new AlwaysSameDiceResultDice(1);
    var diceGame = new DiceGame(List.of(dice, dice2));

    GameResult result = diceGame.play();

    assertThat(result.hasWon()).isFalse();
  }

  private static class AlwaysSameDiceResultDice extends SixSidedDice {

    public int result;

    public AlwaysSameDiceResultDice(int result1) {
      result = result1;
    }

    @Override
    public DiceResult roll() {
      return new DiceResult(result);
    }
  }
}
