package kata;

import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@UseReporter(IntelliJReporter.class)
class AppTest {

    @Test
    void testBasicPlay() {
        StoryBoard story = new StoryBoard();
        Skyjo skyjo = new Skyjo(new Player("Alice"), new Player("Bob"));
        story.addFrame(skyjo.toString());
        skyjo.startGame();
        story.addFrame(skyjo.toString());
        skyjo.flipInitialCards();
        story.addFrame(skyjo.toString());

        Approvals.verify(story);
    }
}
