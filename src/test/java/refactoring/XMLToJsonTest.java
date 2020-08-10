package refactoring;

import java.net.URL;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.BeyondCompareMacReporter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@UseReporter(BeyondCompareMacReporter.class)
class XMLToJsonTest {

  @Test
  void approval() throws Exception {
    verifyXmlToJson("fk:AMM24_fk:AMM24-FM");
  }

  @Test
  void approval_root() throws Exception {
    verifyXmlToJson("/");
  }

  @Disabled("didn't work yet")
  @Test
  void approval_trnum() throws Exception {
    verifyXmlToJson("fk:AMM24-00-00-02_ft:loetr");
  }

  private void verifyXmlToJson(String xPathString) throws Exception {
    XMLToJson xmlToJson = new XMLToJson();
    URL url = getClass().getClassLoader().getResource("xmlToJson.xml");

    String json = xmlToJson.getJson(url, xPathString);

    Approvals.verifyJson(json);
  }
}
