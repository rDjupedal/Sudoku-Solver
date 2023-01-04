import java.util.ArrayList;

/**
 * Sudoku cell
 */
public class Cell {

    private int row, col;

    private ArrayList<Integer> values = new ArrayList<>();

    /**
     * Constructor for cell with unknown value
     * @param row
     * @param col
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        for (int i = 1; i < 10; i++) {
            this.values.add(i);
        }
    }

    /**
     * Constructor for Cell with known value
     * @param row
     * @param col
     * @param knownValue
     */
    public Cell(int row, int col, int knownValue) {
        this.row = row;
        this.col = col;
        this.values.add(knownValue);
    }

    public boolean known() {
        return this.values.size() == 1;
    }

    public int knownValue() {
        if (this.known()) {
            return this.values.get(0);
        }
        else {
            System.out.println("value not known!");
            return -2;
        }
    }

    public int getRow() { return this.row; }
    public int getCol() { return this.col; }

    public ArrayList<Integer> getValues() { return this.values; }

    public ArrayList<Integer> removeValue(int r) {
//        System.out.println("removing value " + r + " from cell " + this.row + ", " + this.col);
        for (Integer value : this.values) {
//            if (value == r) this.values.remove(r);
            if (this.values.contains(value)) {
                this.values.remove(this.values.indexOf(r));
                break;
            }
        }
        return this.getValues();
    }

    public boolean contains(int value) {
        for (int a : this.values) {
            if (a == value) return true;
        }
        return false;
    }

    public void setKnownValue(int value) {
        this.values.clear();
        this.values.add(value);
    }

    public void print() {
        System.out.print(this.row + ", " + this.col + ": ");
        for (Integer value : this.values) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

}
