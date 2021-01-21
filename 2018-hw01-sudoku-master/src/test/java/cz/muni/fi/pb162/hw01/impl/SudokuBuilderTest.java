package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;
import cz.muni.fi.pb162.hw01.helper.Formatter;
import cz.muni.fi.pb162.hw01.helper.SudokuSample;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marek Sabo
 */
class SudokuBuilderTest {

    private Cell n1 = new NumberCell(1);
    private Cell n2 = new NumberCell(2);
    private Cell n3 = new NumberCell(3);
    private Cell n4 = new NumberCell(4);

    private Cell[][] board = {
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null}
    };

    @Test
    void createSmallSudoku1() {
        board[0][0] = n1;
        board[2][2] = n2;
        board[3][1] = n3;
        board[1][2] = n4;
        Sudoku smallSudoku = SudokuSample.createSmallSudoku();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i == 0 && j == 0) || (i == 2 && j == 2) || (i == 3 && j == 1) || (i == 1 && j == 2)) {
                    assertThat(smallSudoku.getCell(i, j)).isEqualToComparingFieldByField(board[i][j]);
                } else {
                    assertThat(smallSudoku.getCell(i, j)).isNull();
                }
            }
        }
    }

    @Test
    void createSmallSudoku2() {
        assertSodukuWithString(
                SudokuSample.createSmallSudoku(),
                "|=====|=====|\n" +
                        "| 1 _ | _ _ |\n" +
                        "| _ _ | _ 3 |\n" +
                        "|=====|=====|\n" +
                        "| _ 4 | 2 _ |\n" +
                        "| _ _ | _ _ |\n" +
                        "|=====|=====|\n\n");
    }

    @Test
    void createSmallDiagonalLetterSudoku() {
        assertSodukuWithString(
                SudokuSample.createSmallDiagonalLetterSudoku(),
                "|=====|=====|\n" +
                        "| A _ | _ B |\n" +
                        "| _ _ | _ C |\n" +
                        "|=====|=====|\n" +
                        "| _ D | _ _ |\n" +
                        "| _ _ | _ _ |\n" +
                        "|=====|=====|\n\n");
    }

    @Test
    void createBigSudoku() {
        assertSodukuWithString(
                SudokuSample.createBigSudoku(),
                "|=======|=======|=======|\n" +
                        "| 6 _ _ | _ 1 8 | _ _ 3 |\n" +
                        "| _ 7 _ | _ 9 _ | _ _ 8 |\n" +
                        "| _ 8 _ | _ _ _ | 9 4 _ |\n" +
                        "|=======|=======|=======|\n" +
                        "| 8 _ _ | _ _ _ | _ 1 5 |\n" +
                        "| 1 _ _ | 6 5 2 | _ _ 7 |\n" +
                        "| 2 5 _ | _ _ _ | _ _ 4 |\n" +
                        "|=======|=======|=======|\n" +
                        "| _ 1 6 | _ _ _ | _ 8 _ |\n" +
                        "| 7 _ _ | _ 6 _ | _ 5 _ |\n" +
                        "| 5 _ _ | 3 2 _ | _ _ 6 |\n" +
                        "|=======|=======|=======|\n\n");
    }

    private void assertSodukuWithString(Sudoku sudoku, String expectedString) {
        assertThat(Formatter.prettyString(sudoku))
                .isEqualTo(expectedString.replace("\n", System.lineSeparator()));
    }

}
