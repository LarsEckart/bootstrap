package kata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {

  @Test
  void my_first_test() {
    assertThat("4" + "2").isEqualTo("42");
  }
}
