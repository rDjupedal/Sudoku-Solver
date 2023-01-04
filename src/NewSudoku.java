public class NewSudoku {

    public static void main(String args[]) {
        System.out.println("New Sudoku started");

        int[] board1 = {
                9, 0, 0, 4, 1, 5, 0, 7, 8,
                1, 8, 5, 7, 0, 0, 0, 0, 9,
                3, 0, 0, 0, 0, 8, 5, 6, 0,
                0, 1, 0, 3, 9, 0, 7, 4, 0,
                0, 0, 0, 1, 0, 0, 0, 3, 0,
                0, 0, 3, 0, 0, 4, 0, 9, 0,
                5, 0, 1, 6, 7, 0, 0, 8, 0,
                8, 0, 0, 0, 0, 9, 6, 0, 0,
                6, 4, 0, 0, 0, 1, 2, 5, 0
        };

        int[] board2 = {
                0, 0, 0, 0, 0, 0, 0, 7, 0,
                1, 0, 5, 0, 3, 6, 4, 0, 9,
                0, 7, 4, 9, 0, 0, 5, 0, 0,
                0, 0, 0, 0, 0, 5, 8, 0, 0,
                0, 1, 0, 3, 0, 0, 0, 0, 5,
                7, 0, 0, 0, 0, 8, 1, 0, 0,
                6, 4, 0, 0, 0, 0, 0, 0, 7,
                0, 3, 0, 6, 2, 0, 0, 8, 4,
                0, 0, 0, 5, 9, 0, 0, 0, 3
        };

        Board board = new Board(board2);

        System.out.println("\n\n\tBefore solving");
        board.printBoard();

        Game game = new Game(board);
        game.solve();

        System.out.println("\n\n\tAfter solving");
        board.printBoard();

//        board.getCell(6, 3).print();
//        board.getCell(6, 4).print();
//        board.getCell(6, 5).print();

//        for (int t = 0; t < 9; t++) {
//            System.out.println("TEST BOX " + t);
//            for (Cell c : board.getBox(t)) {
//                c.print();
//            }
//        }


    }
}
