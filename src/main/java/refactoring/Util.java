package refactoring;

import java.net.URL;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

class Util {

  public Document getDocument(URL url) throws Exception {
    SAXReader reader = new SAXReader();
    Document document = reader.read(url);
    return document;
  }
}
