package kata;

import java.util.Random;

class AlternativeFourPlayerVersion {

  public void play(int diceCount) {
    Random random = new Random();
    int sum = 0;
    if (diceCount == 4) {
      for (int i = 0; i < diceCount; i++) {
        sum += random.nextInt(6) + 1;
      }
    } else {
      sum = random.nextInt(6) + 1 + random.nextInt(6);
    }
    if (diceCount == 4) {
      if (sum >= 14) {
        System.out.println("You win!");
      } else {
        System.out.println("You lose!");
      }
    } else {
      if (sum > 7) {
        System.out.println("You win!");
      } else {
        System.out.println("You lose!");
      }
    }
  }
}
