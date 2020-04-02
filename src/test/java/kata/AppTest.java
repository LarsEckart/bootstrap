package kata;

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
        assertThrows(IllegalArgumentException.class, () -> calculate("-1"));
        // check exception message
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
        if (input.contains(delimiter) || input.contains("\n")) {
            input = input.replace("\n", delimiter);
            String[] split = input.split(delimiter);
            int result = 0;
            for (String s : split) {
                int i = Integer.parseInt(s);
                if (i < 0) {
                    throw new IllegalArgumentException();
                }
                result += i;
            }
            return result;
        }
        return Integer.parseInt(input);
    }
}
