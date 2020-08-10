package refactoring;

import com.spun.util.ObjectUtils;
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

  @Test
  void pathMapping() throws Exception {
    String[] shortPaths = {"",
        "fk:AMM24_fk:AMM24-FM",
        "dk:AMM24",
        "dt:AMM24",
        "dth:AMM24",
        "dtrn:AMM24",
        "fk:AMM24",
        "ft:AMM24",
        "fth:AMM24",
        "dk:AMM24_dt:AMM24_dth:AMM24_dtrn:AMM24_fk:AMM24_ft:AMM24_fth:AMM24",
        "unknown:AMM24",
        "unknown:AMM24_ft:any"
    };
    Approvals.verifyAll(
        "path maps",
        shortPaths,
        s -> {
          try {
            return String.format("%s -> %s", s, new XMLToJson().pathMapping(s));
          } catch (Throwable e) {
            return s + " -> " + e.getClass() +" " + e.getMessage();
          }
        });
  }
}
