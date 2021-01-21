package cz.muni.fi.pb162.hw01.helper;

import cz.muni.fi.pb162.hw01.Sudoku;
import cz.muni.fi.pb162.hw01.impl.LetterCell;
import cz.muni.fi.pb162.hw01.impl.NumberCell;
import cz.muni.fi.pb162.hw01.impl.SudokuBuilder;

/**
 * @author Marek Sabo
 */
public final class SudokuSample {

    private static final NumberCell NUMBER_TYPE = new NumberCell(42);
    private static final LetterCell LETTER_TYPE = new LetterCell('X');

    private SudokuSample() {
    }

    /**
     * Creates basic sudoku:
     * <pre>
     * {@code
     * |=====|=====|
     * | 1 _ | _ _ |
     * | _ _ | _ 3 |
     * |=====|=====|
     * | _ 4 | 2 _ |
     * | _ _ | _ _ |
     * |=====|=====|
     * }
     * </pre>
     *
     * @return 4x4 basic numeric sudoku
     */
    public static Sudoku createSmallSudoku() {
        return new SudokuBuilder(4, NUMBER_TYPE)
                .cell(0, 0, new NumberCell(1))
                .cell(3, 1, new NumberCell(3))
                .cell(1, 2, new NumberCell(4))
                .cell(2, 2, new NumberCell(2))
                .build();
    }

    /**
     * Creates diagonal sudoku:
     * <pre>
     * {@code
     * |=====|=====|
     * | A _ | _ B |
     * | _ _ | _ C |
     * |=====|=====|
     * | _ D | _ _ |
     * | _ _ | _ _ |
     * |=====|=====|
     * }
     * </pre>
     *
     * @return 4x4 diagonal letter sudoku
     */
    public static Sudoku createSmallDiagonalLetterSudoku() {
        return new SudokuBuilder(4, LETTER_TYPE)
                .cell(0, 0, new LetterCell('A'))
                .cell(3, 0, new LetterCell('B'))
                .cell(3, 1, new LetterCell('C'))
                .cell(1, 2, new LetterCell('D'))
                .diagonal(true)
                .build();
    }

    /**
     * Creates basic sudoku:
     * <pre>
     * {@code
     * |=======|=======|=======|
     * | 6 _ _ | _ 1 8 | _ _ 3 |
     * | _ 7 _ | _ 9 _ | _ _ 8 |
     * | _ 8 _ | _ _ _ | 9 4 _ |
     * |=======|=======|=======|
     * | 8 _ _ | _ _ _ | _ 1 5 |
     * | 1 _ _ | 6 5 2 | _ _ 7 |
     * | 2 5 _ | _ _ _ | _ _ 4 |
     * |=======|=======|=======|
     * | _ 1 6 | _ _ _ | _ 8 _ |
     * | 7 _ _ | _ 6 _ | _ 5 _ |
     * | 5 _ _ | 3 2 _ | _ _ 6 |
     * |=======|=======|=======|
     * }
     * </pre>
     *
     * @return 9x9 basic numeric sudoku
     */
    public static Sudoku createBigSudoku() {
        return new SudokuBuilder(9, NUMBER_TYPE)
                .cell(0, 0, new NumberCell(6))
                .cell(4, 0, new NumberCell(1))
                .cell(5, 0, new NumberCell(8))
                .cell(8, 0, new NumberCell(3))
                .cell(1, 1, new NumberCell(7))
                .cell(4, 1, new NumberCell(9))
                .cell(8, 1, new NumberCell(8))
                .cell(1, 2, new NumberCell(8))
                .cell(6, 2, new NumberCell(9))
                .cell(7, 2, new NumberCell(4))

                .cell(0, 3, new NumberCell(8))
                .cell(7, 3, new NumberCell(1))
                .cell(8, 3, new NumberCell(5))
                .cell(0, 4, new NumberCell(1))
                .cell(3, 4, new NumberCell(6))
                .cell(4, 4, new NumberCell(5))
                .cell(5, 4, new NumberCell(2))
                .cell(8, 4, new NumberCell(7))
                .cell(0, 5, new NumberCell(2))
                .cell(1, 5, new NumberCell(5))
                .cell(8, 5, new NumberCell(4))

                .cell(1, 6, new NumberCell(1))
                .cell(2, 6, new NumberCell(6))
                .cell(7, 6, new NumberCell(8))
                .cell(0, 7, new NumberCell(7))
                .cell(4, 7, new NumberCell(6))
                .cell(7, 7, new NumberCell(5))
                .cell(0, 8, new NumberCell(5))
                .cell(3, 8, new NumberCell(3))
                .cell(4, 8, new NumberCell(2))
                .cell(8, 8, new NumberCell(6))
                .build();
    }

}
