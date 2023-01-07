import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Solver {

    public Solver() {};

    public Board solve(Board board) {

        while (solve_csp(board) || solveByNum(board)) {
            System.out.println(board.unknownCells() + " / " + board.possibilites());
        }

        // Solve functions have no effect any longer

        // Is the game solved?
        if (board.solved()) {
            return board;
        }

        // Is the game still legal and have empty cells?
        if (board.isCorrect() && !board.getUnknownCells().isEmpty()) {
            Board newBoard = board.copy();
            // Make a guess to the new board
            makeGuess(newBoard);
            System.out.println("test");
            if (solve(newBoard) != null) return newBoard;
            else return board;
        }

        return null;
    }

    private void makeGuess(Board board) {

        Cell cell = null;
        int lowP = 81;

        // Find the cell with the least number of possible numbers
        for (Cell c : board.getUnknownCells()) {
            if (c.getValues().size() < lowP) {
                lowP = c.getValues().size();
                cell = c;
            }
        }

        if (cell == null) System.out.println("Error in makeGuess(). No empty cell!");

        int remove = cell.getValues().get(0);

        System.out.println("----------------");
        System.out.print("GUESS: Removing number " + remove + " from cell ");
        cell.print();
        cell.removeValue(remove);
        cell.print();

    }

    /**
     * Checks numbers 1-9 for each row, col, box if they can fit to only each cell
     * @param board
     * @return Successful decreasing alternatives
     */
    private boolean solveByNum(Board board) {
        int poss = board.possibilites();

        // ROWS
        for (int r = 0; r < 9; r++) {
            removeByNum(board.getRow(r));
        }

        // COLS
        for (int c = 0; c < 9; c++) {
            removeByNum(board.getCol(c));
        }

        // BOXES
        for (int box = 0; box < 9; box++) {
            removeByNum(board.getBox(box));
        }

        return (poss > board.possibilites());
    }

    private void removeByNum(ArrayList<Cell> cells) {
        // Now check what numbers fit
        for (int num = 0; num < 9; num++) {
            ArrayList<Cell> fits = new ArrayList<>();
            for (Cell cell : cells) {
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
                    for (Cell cell : cells) {
                        if (!fits.contains(cell)) {
                            for (int n : set) cell.removeValue(n);
                        }
                    }
                }

            }

        }

    }

    private boolean solve_csp(Board board) {

        int poss = board.possibilites();

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

        return (poss > board.possibilites());
    }
}
