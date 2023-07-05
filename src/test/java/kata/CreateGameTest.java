package kata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateGameTest {

    @Test
    @DisplayName("Create a game with two players named Horse and Car.")
    void my_first_test() {
        Game game = Game.of(Players.of("Horse", "Car"));

        assertThat(game.canBePlayed()).isTrue();
    }

    @Test
    @DisplayName("Create game with < 2 players. When attempting to play the game, it will fail.")
    void notEnoughPlayers() {
        Game game = Game.of(Players.of("Horse"));

        assertThat(game.canBePlayed()).isFalse();
    }

    @Test
    @DisplayName("Create game with > 8 players. When attempting to play the game, it will fail.")
    void tooManyPlayers() {
        Game game = Game.of(Players.of("Horse", "Car", "Boat", "Plane", "Bike", "Train", "Bus", "Truck", "Scooter"));

        assertThat(game.canBePlayed()).isFalse();
    }

    @Test
    void creating100GamesThenBothHorseAndCarWillAtSomePointBeFirstPlayer() {
        int carFirst = 0;
        int horseFirst = 0;
        for (int i = 0; i < 100; i++) {
            Game game = Game.of(Players.of("Horse", "Car"));
            String firstPlayer = game.whoseTurnIsIt();
            if (firstPlayer.equals("Car")) {
                carFirst++;
            }
            if (firstPlayer.equals("Horse")) {
                horseFirst++;
            }
        }
        int numberOfTimesThatCarStarted = carFirst;
        int numberOfTimesThatHorseStarted = horseFirst;
        Assertions.assertAll(
                () -> assertThat(numberOfTimesThatCarStarted).isPositive(),
                () -> assertThat(numberOfTimesThatHorseStarted).isPositive());
    }
}
