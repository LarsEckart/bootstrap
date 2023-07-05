package kata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerMovementTest {

    @Test
    @DisplayName("Player on beginning location (numbered 0), rolls 7, ends up on location 7")
    void playerCanMove() {
        Players players = Players.of("Horse", "Car");
        Game game = Game.of(players);

        assertThat(game.locationOfCurrentPlayer()).isEqualTo(0);

        game.playerRolled(7);

        assertThat(game.locationOfCurrentPlayer()).isEqualTo(7);
    }
}
