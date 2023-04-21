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

    public Cell(int row, int col, int[] values) {
        this.row = row;
        this.col = col;
        for(int i = 0; i < values.length; i++) {
            this.values.add(values[i]);
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
            return -1;
        }
    }

    public int getRow() { return this.row; }
    public int getCol() { return this.col; }

    public ArrayList<Integer> getValues() { return this.values; }

    public ArrayList<Integer> removeValue(int r) {

        if (this.values.contains(r)) this.values.remove((Integer) r);

        return this.getValues();
    }

    public void addValue(int value) {
        this.values.add(value);
    }

    public void removeValueIndex(int index) {
        if (this.values.size() >= index + 1 && index != 0) values.remove(index);
        else System.out.println("Error in removeValueIndex() no such index");
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
        System.out.print("(" + this.row + "," + this.col + ")  { ");
        for (Integer value : this.values) {
            System.out.print(value + " ");
        }
        System.out.println("}");
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(" + this.row + "," + this.col + ")  { ");
        for (Integer value : this.values) {
            str.append(value + " ");
        }
//        str.append("}\n");
        str.append("}");
        return str.toString();
    }

    public boolean equals(Object o) {

        Cell cell2 = (Cell) o;

        if (this.getRow() != cell2.getRow() || this.getCol() != cell2.getCol()) return false;
        if (this.values.size() == cell2.values.size()) {

            for (int val : this.values) {
                if (!cell2.getValues().contains(val)) return false;
            }

        } else return false;


        return true;
    }

    public int compareTo(Cell b) {

        if (this.getValues().size() > b.getValues().size()) return 1;
        if (this.getValues().size() < b.getValues().size()) return -1;
//        if (this.getValues().size() == b.getValues().size()) return 0;

//        return this.getValues().size() > b.getValues().size() ? 1 : -1;
        else return 0;
    }
}
