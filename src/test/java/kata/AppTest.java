package kata;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {
    // "1,2" -> 3

    @Test
    void emptyStringReturnsZero() {
        assertThat(calculate("")).isEqualTo(0);
    }

    @Test
    void singleNumberReturnsSameNumber() {
        assertThat(calculate("1")).isEqualTo(1);
    }

    @Test
    void twoNumbersReturnsSum() {
        assertThat(calculate("1,2")).isEqualTo(3);
    }

    private int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        if (input.contains(",")) {
            String[] split = input.split(",");
            return Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
        }
        return 1;
    }
}
