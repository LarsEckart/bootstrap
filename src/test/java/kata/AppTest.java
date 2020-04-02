package kata;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {
    // "" -> 0
    // "1" -> 1
    // "1,2" -> 3

    @Test
    void my_first_test() {
        assertThat("4" + "2").isEqualTo("42");
    }
}
