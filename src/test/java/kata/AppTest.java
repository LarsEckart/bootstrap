package kata;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {
    // the following input is ok: “1\n2,3” (will equal 6)

    @Test
    void emptyStringReturnsZero() {
        assertThat(calculate("")).isEqualTo(0);
    }

    @Test
    void singleNumberReturnsSameNumber() {
        assertThat(calculate("1")).isEqualTo(1);
        assertThat(calculate("2")).isEqualTo(2);
    }

    @Test
    void twoNumbersReturnsSum() {
        assertThat(calculate("1,2")).isEqualTo(3);
    }

    @Test
    void multipleNumbersReturnsSum() {
        assertThat(calculate("1,2,3")).isEqualTo(6);
    }

    @Test
    void newlineIsAlsoAValidNumberSeparator() {
        assertThat(calculate("1\n2,3")).isEqualTo(6);
    }

    private int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        if (input.contains(",")) {
            String[] split = input.split(",");
            int result = 0;
            for (String s : split) {
                result += Integer.parseInt(s);
            }
            return result;
        }
        return Integer.parseInt(input);
    }
}
