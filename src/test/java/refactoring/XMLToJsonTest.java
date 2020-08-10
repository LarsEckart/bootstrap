package refactoring;

import java.net.URL;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.BeyondCompareMacReporter;
import org.junit.jupiter.api.Test;

@UseReporter(BeyondCompareMacReporter.class)
class XMLToJsonTest {

  @Test
  void approval() throws Exception {
    XMLToJson xmlToJson = new XMLToJson();
    URL url = getClass().getClassLoader().getResource("xmlToJson.xml");

    String json = xmlToJson.getJson(url, "fk:AMM24_fk:AMM24-FM");

    Approvals.verify(json);
  }
}
