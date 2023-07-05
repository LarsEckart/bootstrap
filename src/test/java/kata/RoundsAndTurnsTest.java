package kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoundsAndTurnsTest {

    @Test
    void afterFirstPlayerNextTurnIsSecondPlayer() {
        Game game = Game.of(Players.ofFixedOrder("Horse", "Car"));
        Player firstPlayer = game.whoseTurnIsIt();

        game.playerRolled(1);
        game.endTurn();

        assertThat(game.whoseTurnIsIt().name()).isEqualTo("Car");
    }

    @Test
    void afterSecondPlayerNextTurnIsAgainFirstPlayer() {
        Game game = Game.of(Players.ofFixedOrder("Horse", "Car"));
        Player firstPlayer = game.whoseTurnIsIt();

        game.playerRolled(1);
        game.endTurn();
        game.playerRolled(1);
        game.endTurn();

        assertThat(game.whoseTurnIsIt().name()).isEqualTo("Horse");
    }
}
