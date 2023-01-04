/**
 * Sudoku game
*/

public class Game {

    private Board board;

    public Game(Board board) {
        this.board = board;
    }

    public void solve() {

        int possibilites = board.possibilites();
        boolean loop = true;

        // Repeat as long as the number of possibilites is decreasing
        while(loop) {

            // For each cell
            for (Cell cell : board.getCells()) {

                // Skip cells that are already known
                if (cell.known()) continue;

                // Check row
                for (Cell c : board.getRow(cell)) {
                    // Removes possible value from cell if another cell on the same row has this value as known
                    if (c.known() && cell.contains(c.knownValue())  && !cell.known()) cell.removeValue(c.knownValue());
                }

                // Check col
                for (Cell c : board.getCol(cell)) {
                    // Removes possible value from cell if another cell on the same col has this value as known
                    if (c.known() && cell.contains(c.knownValue())  && !cell.known()) cell.removeValue(c.knownValue());
                }

                // Check 3x3 box
                for (Cell c : board.getBox(cell)) {
                    // Removes possible value from cell if another cell in the same box has this value as known
                    if (c.known() && cell.contains(c.knownValue()) && !cell.known()) {
                        cell.removeValue(c.knownValue());
                    }
                }

            }

            if (possibilites == board.possibilites()) loop = false;
            else possibilites = board.possibilites();

            System.out.println(possibilites);
        }
    }


}
