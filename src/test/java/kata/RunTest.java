package kata;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    strict = true,
    monochrome = true,
    plugin = {"pretty", "junit:/tmp/output"},
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunTest {
  // this is the adapter/bridge code
  // between cucumber jvm and junit
  // you do not need to edit this
}
