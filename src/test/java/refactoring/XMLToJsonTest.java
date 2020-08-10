package refactoring;

import java.net.URL;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class XMLToJsonTest {

  @Test
  void approval() throws Exception {
    XMLToJson xmlToJson = new XMLToJson();
    URL url = getClass().getClassLoader().getResource("xmlToJson.xml");

    String json = xmlToJson.getJson(url, "fk:AMM24_fk:AMM24-FM");

    Approvals.verify(json);
  }
}
