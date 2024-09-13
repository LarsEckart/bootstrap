package kata;

import java.util.Random;

class AlternativeVersion {

  public void play() {
    Random random = new Random();
    int sum = random.nextInt(6) + 1 + random.nextInt(6);
    if (sum > 7) {
      System.out.println("You win!");
    } else {
      System.out.println("You lose!");
    }
  }
}
