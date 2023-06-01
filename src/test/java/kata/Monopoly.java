package kata;

import com.spun.util.markdown.table.MarkdownTable;
import org.lambda.query.Queryable;

import java.util.List;

class Monopoly {

    private Place[] board = {
            Place.GO,
            Place.MediterraneanAvenue,
            Place.CommunityChest,
            Place.BalticAvenue,
            Place.IncomeTax,
            Place.ReadingRailroad};
    Queryable<Player> players = new Queryable<>(Player.class);
    public int currentPlayer = 0;

    public Monopoly(int playerCount) {
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    Player getPlayer(int index) {
        return players.get(index);
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
        output += "current player: " + (currentPlayer + 1);
        return output;
    }

    private List<Player> getPlayersOnSquare(Place square) {
        return players.where(p -> p.location() == square.location());
    }

    public Turn move(int spaces) {
        Player player = players.get(currentPlayer);
        player.move(spaces);
        doAutomaticActions(player);
        return new Turn(player, this);
    }

    private void doAutomaticActions(Player player) {
        Place place = board[player.location()];
        Player owner = getOwner(place);
        if (owner != null && owner != player) {
            int rent = place.getRent(owner.getPropertyGroupImprovements(place));

            owner.money += rent;
            player.money -= rent;
        }
    }

    private Player getOwner(Place place) {
        return players.first(p -> p.properties.contains(place));
    }

    public boolean isPropertyAvailable(int location) {
        return true;
    }

    public Place getProperty(int location) {
        return board[location];
    }

    public void endTurn() {
        this.currentPlayer = (this.currentPlayer + 1) % players.size();
    }
}
