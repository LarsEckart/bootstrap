package kata;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {
    // “//;\n1;2” -> 3

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

    @Test
    void customDelimiterInFirstLine() {
        assertThat(calculate("//;\n1;2")).isEqualTo(3);
    }

    private int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        if (input.startsWith("//")) {
            String[] split = input.split("\n");
            input = split[1].replace(String.valueOf(split[0].charAt(2)), ",");
        }
        if (input.contains(",") || input.contains("\n")) {
            input = input.replace("\n", ",");
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
