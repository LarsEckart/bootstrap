package kata;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {
    /*
        Calling Add with a negative number will throw an exception “negatives not allowed” - and the negative that was passed.

        if there are multiple negatives, show all of them in the exception message.
     */

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

    @Test
    void negativesNotAllowed() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculate("-1"));
        assertThat(exception.getMessage()).isEqualTo("negatives not allowed: -1");
    }

    @Disabled("Need to refactor first")
    @Test
    void multipleNegativeNumbersNotAllowed() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculate("-1,-2"));
        assertThat(exception.getMessage()).isEqualTo("negatives not allowed: -1, -2");
    }

    private int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }
        String delimiter = ",";
        if (input.startsWith("//")) {
            String[] split = input.split("\n");
            delimiter = String.valueOf(split[0].charAt(2));
            input = split[1];
        }
        if (!input.contains(delimiter)) {
            input += delimiter;
        }
        input = input.replace("\n", delimiter);
        String[] split = input.split(delimiter);
        int result = 0;
        for (String s : split) {
            int i = toInt(s);
            result += i;
        }
        return result;
    }

    private int toInt(String s) {
        int i = Integer.parseInt(s);
        if (i < 0) {
            throw new IllegalArgumentException("negatives not allowed: -1");
        }
        return i;
    }
}
