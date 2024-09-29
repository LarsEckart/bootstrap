package kata;

import com.spun.util.ArrayUtils;
import java.util.List;
import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.GenericDiffReporter;

public class AraxisReporter extends GenericDiffReporter {

  private static final List<String> TEXT = GenericDiffReporter.TEXT_FILE_EXTENSIONS;
  private static final List<String> IMAGE = GenericDiffReporter.IMAGE_FILE_EXTENSIONS;
  private static final List<String> TEXT_AND_IMAGE = ArrayUtils.combine(TEXT, IMAGE);

  public AraxisReporter() {
    super(
        new DiffInfo("/Applications/Araxis Merge.app/Contents/Utilities/compare", TEXT_AND_IMAGE));
  }
}
