package kata;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@UseReporter(AraxisReporter.class)
class AppTest {

  @Test
  void my_first_test() {
    Approvals.verify("Hello World1");
  }
}
