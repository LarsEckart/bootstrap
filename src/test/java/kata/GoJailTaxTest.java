package kata;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GoJailTaxTest {

    @Test
    void whenPlayerLandsOnGoTheirBalanceIncreasesBy200() {
        Game game = Game.of(Players.of("Horse", "Car"));

        for (int i = 0; i < 6; i++) {
            game.playerRolled(6);
            game.endTurn();
            game.playerRolled(6);
            game.endTurn();
        }

        game.playerRolled(4);

        assertThat(game.balanceOfCurrentPlayer()).isEqualTo(200);
    }

    @Test
    void whenPlayerGoesOverGoTheirBalanceIncreasesBy200() {
        Game game = Game.of(Players.of("Horse", "Car"));

        for (int i = 0; i < 6; i++) {
            game.playerRolled(6);
            game.endTurn();
            game.playerRolled(6);
            game.endTurn();
        }

        game.playerRolled(6);

        assertThat(game.balanceOfCurrentPlayer()).isEqualTo(200);
    }

    @Test
    void whenPlayerLandsOnNormalFieldTheirBalanceStaysTheSame() {
        Game game = Game.of(Players.of("Horse", "Car"));

        game.playerRolled(4);

        assertThat(game.balanceOfCurrentPlayer()).isEqualTo(0);
    }
}
