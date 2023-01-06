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

        int[] board3 = {
                0, 0, 2, 1, 0, 0, 0, 6, 0,
                5, 6, 0, 3, 0, 0, 0, 0, 7,
                0, 0, 8, 0, 0, 5, 0, 0, 0,
                0, 0, 0, 0, 1, 0, 0, 0, 8,
                6, 3, 0, 0, 0, 9, 0, 1, 0,
                0, 2, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 7, 0, 0, 4, 0, 0,
                9, 1, 0, 0, 0, 3, 0, 8, 0,
                0, 0, 5, 0, 0, 0, 0, 0, 0
        };

        Board board = new Board(board3);

        System.out.println("\n\n\tBefore solving");
        System.out.println(board);
//        board.printBoard();

        Game game = new Game(board);
        game.solve();

        System.out.println("\n\n\tAfter solving");
        System.out.println(board);
//        board.printBoard();

        System.out.println("Correct solution? " + board.isCorrect());

    }
}
