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

    @Test
    @DisplayName("Player on location numbered 39, rolls 6, ends up on location 5")
    void playerCanMoveAroundTheBoard() {
        Players players = Players.of("Horse", "Car");
        Game game = Game.of(players);

        game.playerRolled(6);
        game.playerRolled(6);
        game.playerRolled(6);
        game.playerRolled(6);
        game.playerRolled(6);
        game.playerRolled(6);
        game.playerRolled(3);
        assertThat(game.locationOfCurrentPlayer()).isEqualTo(39);

        game.playerRolled(6);
        assertThat(game.locationOfCurrentPlayer()).isEqualTo(5);
    }
}
