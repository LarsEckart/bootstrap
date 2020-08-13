package refactoring;

import com.spun.util.StringUtils;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * fk--folder key,dk--doc key -->value is key string
 * dt--doc type,ft--folder type -->both have 19 key options:
 * bom tr amend history help info loeid cust cust0-cust9 custhist
 * folder type only seen as "history" in toc,why ??do we use other ones? -- it only has one child
 * doc type only seen two "tr" and "history" in toc??-- it only has one child
 * node with "folder type="history"" is the only child of it parent either "<doc type="tr" key="xxx" trnum="xxx"....." or "<doc type="tr"  trnum="xxx""
 * <folder type="history"  could has more then one doc children .e.g. title="History of AMM31-32-00-720-807" in 700/amm
 *
 *
 *
 * folder element has the follwing to identify itself:
 * 	1, key
 * 	2, type="history",  in this case, folder is the only child of doc element with type ="tr"?????
 *
 * doc element has the following to identify itself
 * 	1, key
 * 	2, type="tr" trnum="xxxxx"
 * 	3, type="history", in this case, doc isthe only child of a folder element???
 *
 *
 *
 * the return json format likes following:
 * [
 * { "data" : "A node", "children" , "state" : "open" },
 * { "data" : "Only child", "state" : "closed" },
 *	"Ajax node"
 *	]
 */
public class XMLToJson {
  public static final Map<String, String> pathMap;

  static {
    Map<String, String> aMap = new HashMap<String, String>();
    aMap.put("fk", "folder[@key");
    aMap.put("ft", "folder[@type");
    aMap.put("fth", "folder[@type='history'");
    aMap.put("dk", "doc[@key");
    aMap.put("dt", "doc[@type");
    aMap.put("dth", "doc[@type='history'");
    aMap.put("dtrn", "doc[@trnum");
    pathMap = Collections.unmodifiableMap(aMap);
  }

  Util util = new Util();

  /*
   * @param url the path to TOC.xml
   * @param xPathString the short format searched node path
   * @throws DocumentException
   *
   * sample xPathString : "fk:AMM24_fk:AMM24-FM_dk"
   */
  @SuppressWarnings({"unchecked"})
  public String getJson(URL url, String xPathString) throws Exception {

    var innerJson =
        getNode(url, xPathString)
            .elements()
            .stream()
            .map(element -> convertToJson(xPathString, element))
            .collect(Collectors.joining(","));
    return "[" + innerJson + "]";
  }

  private Element getNode(URL url, String xPathString) throws Exception {
    Document TOCDoc = util.getDocument(url);
    Element node = null;
    if (xPathString.equals("/")) {
      node = TOCDoc.getRootElement();
    } else {
      String realXPathString = pathMapping(xPathString);
      System.out.println(realXPathString);
      node = (Element) TOCDoc.selectSingleNode(realXPathString);
    }
    return node;
  }

  private String convertToJson(String xPathString, Element elem) {

    String eleName = elem.getName();
    Boolean hasChildren = false;
    if ((elem.elements().size() > 0)) {
      hasChildren = true;
      // current element has children itself, state shoud be "closed"

    }
    List<Attribute> list = elem.attributes();
    String titleAttrContent = elem.attributeValue("title");
    String fileAttrContent = elem.attributeValue("file");
    if ("doc".equals(eleName)) {
      return convertDoc(xPathString, elem, hasChildren, list, titleAttrContent, fileAttrContent);
    } else if ("folder".equals(eleName)) {
      return convertFolder(xPathString, elem, list, titleAttrContent, fileAttrContent);
    } else {
      String jsonString = "";
      return jsonString;
    }
  }

  private String convertDoc(String xPathString, Element elem, Boolean hasChildren,
      List<Attribute> list, String titleAttrContent, String fileAttrContent) {
    String jsonString = "";
    // doc element always has "file" attribute

    for (Attribute attribute : list) {
      jsonString = jsonString.concat("{");
      String attrName = attribute.getName();
      // each one has to have "data" line, "attr" line "state" line and "children" line
      jsonString = jsonString.concat("'data':'").concat(titleAttrContent).concat("',");
      if ("key".equals(attrName)) {
        jsonString += convertKey(xPathString, elem, fileAttrContent);
        break;
      } else if ("trnum".equals(attrName)) {
        jsonString += convertTrnum(xPathString, elem, fileAttrContent);
        break;
      }
    }
    if (hasChildren) {
      // state set up as "closed" and no need to set up "children" field
      jsonString = jsonString.concat(",'state':'closed'");
    }
    jsonString = jsonString.concat("}");
    return jsonString;
  }

  private String convertTrnum(String xPathString, Element elem, String fileAttrContent) {
    String result = "";
    String trnumContent = elem.attributeValue("trnum");
    result =
        result
            .concat("'attr':{'id':'")
            .concat(xPathString)
            .concat("_dtrn:")
            .concat(trnumContent)
            .concat("','file':'")
            .concat(fileAttrContent)
            .concat("'}");
    return result;
  }

  private String convertKey(String xPathString, Element elem, String fileAttrContent) {
    String keyContent = elem.attributeValue("key");
    return String.format("'attr':{'id':'%s_dk:%s','file':'%s'}", xPathString, keyContent, fileAttrContent);
  }

  private String convertFolder(String xPathString, Element elem, List<Attribute> list,
      String titleAttrContent, String fileAttrContent) {
    String jsonString = "";
    jsonString = jsonString.concat("{");
    for (Attribute attribute : list) {
      String attrName = attribute.getName();
      jsonString = jsonString.concat("'data':'").concat(titleAttrContent).concat("',");
      if ("key".equals(attrName)) {
        String keyContent = elem.attributeValue("key");
        jsonString =
            jsonString
                .concat("'attr':{'id':'")
                .concat(xPathString)
                .concat("_fk:")
                .concat(keyContent)
                .concat("'}");
        if (fileAttrContent != null) {
          jsonString = jsonString.concat("','file':'").concat(fileAttrContent).concat("'}");
        }

        break;
      } else if ("type".equals(attrName)) {
        String typeContent = elem.attributeValue("type");
        if ("history".equals(typeContent)) {
          jsonString = jsonString.concat("'attr':{'id':'").concat(xPathString).concat("_fth,");
        }
        break;
      }
    }
    jsonString = jsonString.concat("}");
    return jsonString;
  }

  /*
   * post string looks like : "fk:LOETR_dtrn:TR12-118_fth_dth"
   * it represents the inner doc elemnet:
   * <folder key="LOETR" type="loetr" title="List of Effective TRs" file="loetr.html">
   *		<doc type="tr" trnum="TR12-118" trdate="May 07/2012" title="[TR12-118] TASK AMM12-31-00-660-806 - Inspection and Removal of De-Hydrated Anti-Icing Fluid inside the Flight Control Surfaces" file="TR12-118.pdf" refloc="AMM12-31-00-660-806">
   * 			<folder type="history" title="History of AMM12-31-00-660-806">
   *   			<doc title="TASK 12-31-00-660-806 - Inspection and Removal of De-Hydrated Anti-Icing Fluid inside the Flight Control Surfaces" file="AMM12-31-00-660-806.pdf" type="history" refloc="AMM12-31-00-660-806"/>
   * </folder>
   * the xpath string should be:
   * folder[@key="LOETR"]/doc[@trnum="TR12-118"]/folder[@type="history"]/doc[@type="history"]
   *
   *
   * the String : "fk:AMM24_fk:AMM24-FM_dk:CTOC-24"
   * it represents the inner doc with attribute file="CTOC-24.pdf"
   * the string : "fk:AMM24_fk:AMM24-00-00_fk:AMM24-00-00-02_dk:AMM24-00-00-700-801" represents
   * <folder key="AMM24" title="CH 24 - Electrical Power">
   *		<folder key="AMM24-FM" title="Front Matter">
   * 			<doc key="CTOC-24" title="Table of Contents" file="CTOC-24.pdf"/>
   *		</folder>
   *		<folder key="AMM24-00-00" title="24-00-00 - General">
   * 			<folder key="AMM24-00-00-02" title="General - Maintenance Practices">
   *   			<doc key="AMM24-00-00-700-801" title="TASK 24-00-00-700-801 - AC Power, DC Power and Battery Maintenance Practice Recommendations" file="AMM24-00-00-700-801.pdf"/>
   *
   * it can be even optimized as :
   * "fk:AMM24_fk:00-00_fk:02_dk:AMM24-00-00-700-801"
   * if the inner key fully include the previous key, omit it, otherwise use full string
   * the xpath string should be:
   * folder[@key="AMM24"]/folder[@key="AMM24-00-00"]/folder[@key="AMM24-00-00-02"]/doc[@key="AMM24-00-00-700-801"]
   *
   * if shortXPath is ?? which means the query based on the root of the document
   *
   *
   */
  public static String pathMapping(String shortXPath) throws Exception {
    if ("".equals(shortXPath)) {
      return "//toc";
    }

    String expanded = "//";
    String[] pairs = shortXPath.split("_");
    for (String pair : pairs) {
      if (!expanded.endsWith("/")) {
        expanded += "/";
      }
      expanded += expandPath(pair);
    }
    return expanded;
  }

  private static String expandPath(String shortPathPair) throws Exception {
    System.out.println(shortPathPair);
    String[] split = shortPathPair.split("\\:");
    if (split.length == 2) {
      String key = split[0];
      String path = pathMap.get(key);
      String value = split[1];
      if (!StringUtils.isNonZero(path)) {
        throw new Exception("no mapping found");
      }
      return String.format("%s='%s']", path, value);
    }
    return "";
  }

  public static void main(String[] args) throws Exception {
    XMLToJson x2j = new XMLToJson();
    String test = "fk:AMM24_fk:AMM24-FM";

    test = "";
    System.out.println(
        x2j.getJson(new URL("http://localhost:8080/WebNavSpring/q400/amm/toc.xml"), test));
    // System.out.println(x2j.pathMapping(test));

  }
}
