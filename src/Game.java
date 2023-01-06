import java.util.*;

/**
 * Sudoku game
*/

public class Game {

    private Board board;
    ArrayList<Guess> guesses = new ArrayList<>();

    public Game(Board board) {
        this.board = board;
    }

    public void solve() {

        boolean loop = true;
        int iter = 0;

        while (loop) {

            // Neither method was able to decrease number of possibilities
            if (!solve_csp() && !solveByNum()) {

                // Game finished
                if (board.solved()) {
                    System.out.println("Iterations: " + iter);
                    return;
                }

                loop = false;

                //Make guess
                makeGuess();
                while (true) {
                    if (board.solved()) return;
                    if (!solve_csp() && !solveByNum()) {
                        if (!board.solved() || !board.isCorrect()) undoLastGuess();
                        else loop = false;
                    }
                    makeGuess();
                }
            }

            iter++;
        }
    }


    private boolean bruteForce(Board b) {

        if (b.solved()) {
            System.out.println("-------SOLVED!-------");
            System.out.println(b);
            return true;
        }


        // Check if possibilites still exists
        int poss = 0;
        for (Cell c : b.getUnknownCells()) {
            for (int n : c.getValues()) {
                poss++;
            }
        }

        if (!b.isCorrect() || b.getUnknownCells().isEmpty() || poss < 1) {
            System.out.println("Board correct:" + b.isCorrect() + " UnknwonCells left?:" + !b.getUnknownCells().isEmpty() + " possibilites:" + poss);
            return false;
        }

        System.out.println("Board correct");

        for (Cell cell : b.getUnknownCells()) {
            for (int num : cell.getValues()) {

                Board b2 = b.copy();
                Cell changeCell = b2.getCell(cell.getRow(), cell.getCol());
                changeCell.removeValue(num);

                changeCell.print();
                System.out.println("(" + changeCell.getRow() + "," + changeCell.getCol() + ") remove " + num);
                if (bruteForce(b2)) {
                    System.out.println("Remove ok");
                    return true;
                }

                //Backtrack
                System.out.println("Backtracking.. Putting back " + num + " to (" + changeCell.getRow() + "," + changeCell.getCol() + ")");
                changeCell.addValue(num);
                changeCell.print();

            }
        }

        System.out.println("bruteforce finished");
        System.out.println("correct? " + b.isCorrect());
        System.out.println(b);

        return false;

    }



    private void undoLastGuess() {
        Guess undoGuess = this.guesses.get(this.guesses.size() - 1);
        undoGuess.undo();
        this.guesses.remove(undoGuess);
    }


    /**
     * Guesses a number which it removes from a cell
     * @return The Cell and the number that was removed
     */
    private Guess makeGuess() {
        Random rnd = new Random();
        Cell cell = null;
        int lowP = 81;

        // Find the cell with the least number of possible numbers
        for (Cell c : board.getUnknownCells()) {
            if (c.getValues().size() < lowP && !c.getValues().isEmpty()) {
                cell = c;
                lowP = c.getValues().size();
            }
        }
        if (cell == null) return null;

        // Guess a value to remove for the cell
        int removeValue = cell.getValues().get(rnd.nextInt(cell.getValues().size()-1));

        System.out.println("----------------");
        System.out.print("GUESS: Removing number " + removeValue + " from cell ");
        cell.print();
        cell.removeValue(removeValue);
        cell.print();

        Guess newG = new Guess(cell, removeValue);
        this.guesses.add(newG);
        return newG;
    }

    private boolean solveByNum() {
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

    private boolean solve_csp() {

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
