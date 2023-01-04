import java.util.ArrayList;

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

    public ArrayList<Cell> getCells() {
        return this.cells;
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
//        ArrayList<Cell> rowList = new ArrayList<>();
//        for (Cell c : this.cells) {
//            if (c.getRow() == cell.getRow()) rowList.add(c);
//        }
//        return rowList;
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
        ArrayList<Cell> colList = new ArrayList<>();
        for (Cell c : this.cells) {
            if (c.getCol() == cell.getCol()) colList.add(c);
        }
        return colList;
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
                else System.out.print("? ");

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

}
