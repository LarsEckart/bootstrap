package refactoring;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.BeyondCompareMacReporter;
import org.junit.jupiter.api.Test;

@UseReporter(BeyondCompareMacReporter.class)
class PathMapTest {

  @Test
  void test_initialization() {
    Approvals.verify(XMLToJson.pathMap);
  }
}
