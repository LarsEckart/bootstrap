package kata;

import com.spun.util.markdown.table.MarkdownTable;

import java.util.ArrayList;
import java.util.List;

class Monopoly {

    private Places[] board = {Places.GO};
    List<Player> players = new ArrayList<>();

    public Monopoly(int playerCount) {
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    @Override
    public String toString() {
        MarkdownTable markdownTable = new MarkdownTable();

        for (var square : board) {
            markdownTable.addRow(square.name());
        }
        return markdownTable.toMarkdown();
    }
}
