/**
 * Wrapper class to hold a guess cell + removed number(s)
 */
public class Guess {

    private Cell cell;
    private int num;

    public Guess(Cell cell, int num) {
        this.cell = cell;
        this.num = num;
        this.cell.removeValue(num);
    }

    public void undo() {
        this.cell.addValue(this.num);
    }



    public Cell getCell() { return this.cell; }
    public int getnum() { return this.num; }
}
