package kata;

import com.spun.util.markdown.table.MarkdownTable;
import org.lambda.query.Queryable;

import java.util.List;

class Monopoly {

    private Places[] board = {
            Places.GO,
            Places.MediterraneanAvenue,
            Places.CommunityChest,
            Places.BalticAvenue,
            Places.IncomeTax,
            Places.ReadingRailroad};
    Queryable<Player> players = new Queryable<>(Player.class);

    public Monopoly(int playerCount) {
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    @Override
    public String toString() {
        MarkdownTable markdownTable = new MarkdownTable();

        for (var square : board) {
            // get players on square, if there are players, add it to md column, if not
            List<Player> players = getPlayersOnSquare(square);
            markdownTable.addRow(square.name(), players);
        }
        String output = markdownTable.toMarkdown();
        for (var player : players) {
            output += player.details() + "\n";
        }
        return output;
    }

    private List<Player> getPlayersOnSquare(Places square) {
        return players.where(p -> p.location() == square.location());
    }

}
