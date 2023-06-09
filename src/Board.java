import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Sudoku board
 */
public class Board {

    private ArrayList<Cell> cells = new ArrayList<>();

    public Board(int[] newCells) {

        for (int i = 0; i < newCells.length; i++) {
            int row = i / 9;
            int col = i % 9;

            if (newCells[i] == 0) {
                // Creating a cell with an unknown value
                cells.add(new Cell(row, col));
            } else {
                // Creating a Cell with a known value
                cells.add(new Cell(row, col, newCells[i]));
            }

        }
    }

    public Board() {

    }

    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public ArrayList<Cell> getUnknownCells() {
        ArrayList<Cell> unknown = new ArrayList<>();
        for (Cell c : this.cells) {
            if (!c.known()) unknown.add(c);
        }
        return unknown;
    }

    public Cell getCell(int r, int c) {
        for (Cell cell : this.cells) {
            if (cell.getRow() == r && cell.getCol() == c) return cell;
        }
        System.out.println("Error in Board::getCell(): cell not found");
        return null;
    }

    public ArrayList<Cell> getRow(Cell cell) {
        return this.getRow(cell.getRow());
    }

    public ArrayList<Cell> getRow(int row) {
        ArrayList<Cell> rowList = new ArrayList<>();
        for (Cell c : this.cells) {
            if (c.getRow() == row) rowList.add(c);
        }
        return rowList;
    }

    public ArrayList<Cell> getCol(Cell cell) {
        return this.getCol(cell.getCol());
    }

    public ArrayList<Cell> getCol(int col) {
        ArrayList<Cell> colList = new ArrayList<>();
        for (Cell c : this.cells) {
            if (c.getCol() == col) colList.add(c);
        }
        return colList;
    }


    public ArrayList<Cell> getBox(int box) {

        return this.getBox(new Cell(3 * (box / 3), 3 * (box % 3)));
    }

    public ArrayList<Cell> getBox(Cell cell) {
        ArrayList<Cell> boxList = new ArrayList<>();

        // Get box
        int boxR = cell.getRow() / 3;
        int boxC = cell.getCol() / 3;

        for (Cell c : this.cells) {
            if (c.getRow() / 3 == boxR && c.getCol() / 3 == boxC) boxList.add(c);
        }

        return boxList;
    }

    public void printBoard() {

        System.out.println("-----------------------");
        System.out.println("Unknown cells: " + this.unknownCells());
        System.out.println("Possibilites: " + this.possibilites());

        System.out.println("-------------------------");
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0) System.out.print("| ");
                if (this.getCell(r, c).known()) System.out.print(this.getCell(r, c).knownValue() + " ");
                else System.out.print("_ ");

            }
            if (r % 3 == 2) System.out.println("|\n|-----------------------|");
            else System.out.println("|");
        }
    }

    public int unknownCells() {
        int x = 0;
        for (Cell c : this.cells) {
            if (!c.known()) x++;
        }
        return x;
    }

    public int possibilites() {
        int x = 0;
        for (Cell c : this.cells) {
            x += c.getValues().size();
        }
        return x;
    }

    /**
     * Checks if the game is finished and correct
     * @return
     */
    public boolean solved() {

        for (Cell cell : getCells()) {
            if (!cell.known()) return false;
        }

        return isCorrect();
    }

    /**
     * Checks whether the whole board has correct known values
     * @return
     */
    public boolean isCorrect() {

        // ROWS & CELLS & BOXES
        for (int x = 0; x < 9; x++) {
            if (!this.checkCells(this.getRow(x))) return false;
            if (!this.checkCells(this.getCol(x))) return false;
            if (!this.checkCells(this.getBox(x))) return false;
        }

        // Check if any cell is empty
        for (Cell cell : this.getCells()) {
            if (cell.getValues().isEmpty()) return false;
        }

        return true;
    }

    /**
     * Checks whether a list of cells contains known values 1-9
     * @param cells
     * @return
     */
    private boolean checkCells(ArrayList<Cell> cells) {

        Set<Integer> set = new HashSet<>();
        for (Cell c : cells) {
            if (c.known()) {
                if (!set.add(c.knownValue())) return false;
            }
            // Uncomment to only accept finished games
            //else return false;
        }

        return true;
    }

    public int[][] getSimpleBoard() {
        int[][] board = new int[9][9];

        for (Cell cell : this.getCells()) {
            int row = cell.getRow();
            int col = cell.getCol();
            int num = (cell.known()) ? cell.knownValue() : 0;
            board[row][col] = num;
        }

        return board;
    }

    public Board copy() {

        Board copy = new Board();

        for (Cell oldCell : this.getCells()) {

            int index = 0;
            int[] values = new int[oldCell.getValues().size()];

            for (int num : oldCell.getValues()) {
//                System.out.println("Setting index " + index + " to " + num);
                values[index] = num;
                index++;
            }

            Cell newCell = new Cell(oldCell.getRow(), oldCell.getCol(), values);

            copy.addCell(newCell);
        }

        return copy;
    }

    public String toString() {

        StringBuilder str = new StringBuilder();

//        str.append("-----------------------");
        str.append("Unknown cells: " + this.unknownCells());
        str.append("\nPossibilites: " + this.possibilites());

        str.append("\n-------------------------\n");
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0) str.append("| ");
                if (this.getCell(r, c).known()) str.append(this.getCell(r, c).knownValue() + " ");
                else str.append("_ ");

            }
            if (r % 3 == 2) str.append("|\n|-----------------------|\n");
            else str.append("|\n");
        }

        return str.toString();
    }

}
