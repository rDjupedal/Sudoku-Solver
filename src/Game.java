import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Sudoku game
*/

public class Game {

    private Board board;

    public Game(Board board) {
        this.board = board;
    }

    public void solve() {
        solve_csp();
        solveByNum();
    }

    private void solveByNum() {

        // ROWS
        for (int r = 0; r < 9; r++) {

            // First get all cells on this row
            ArrayList<Cell> rowList = board.getRow(r);

            // Now check what numbers fit
            for (int num = 0; num < 9; num++) {
                ArrayList<Cell> fits = new ArrayList<>();
                for (Cell cell : rowList) {
                    if (cell.contains(num)) {
                        // The number fits into this cell. Let's note it.
                        fits.add(cell);
                    }
                }

                // We found only one cell in which this number fits
                if (fits.size() == 1) fits.get(0).setKnownValue(num);

                // The number fits in several cells.. lets see which other numbers also fit in those
                if (fits.size() > 1) {
                    Set<Integer> set = new HashSet<>();
                    for (Cell c : fits) {
                        for (int val : c.getValues()) set.add(val);
                    }

                    if (fits.size() == set.size()) {
                        // We found the same number of unique possible numbers as the number of cells and
                        // can therefore conclude that these numbers will not fit into other cells.
                        for (Cell cell : rowList) {
                            if (!fits.contains(cell)) {
                                for (int n : set) cell.removeValue(n);
                            }
                        }
                    }

                }

            }

        }


//        ArrayList<Cell> unknownCells = board.getUnknownCells();
//
//        for (int value = 1; value < 10; value++) {
//
//            //
//            for (int row = 0; row < 9; row++) {
//                // Get all cells on current row
//                ArrayList<Cell> rowList = board.getRow(row);
//
//            }
//
//
//
//        }

        // Each cell that aren't solved
//        for (Cell cell: board.getUnknownCells()) {
//
//            // Num 1-9
//            for (int value = 1; value < 10; value++) {
//
//                if (!cell.contains(value)) break;
//
//                // Check if current num already exist on row
//                for (Cell c : board.getRow(cell)) {
//                    if (c.)
//                }
//
//            }
//
//        }

    }

    private void solve_csp() {

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
