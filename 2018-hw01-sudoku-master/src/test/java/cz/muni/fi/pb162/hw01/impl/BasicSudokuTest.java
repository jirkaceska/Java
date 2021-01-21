package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marek Sabo
 */
@SuppressWarnings("Duplicates")
class BasicSudokuTest {

    private Sudoku sudoku;
    private Cell[][] emptyBoard = {
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null}
    };
    private LetterCell a = new LetterCell('A');
    private LetterCell b = new LetterCell('B');
    private LetterCell c = new LetterCell('C');
    private LetterCell d = new LetterCell('D');

    @BeforeEach
    void setUp() {
        emptyBoard[0][0] = a;
        emptyBoard[3][0] = b;
        emptyBoard[3][1] = c;
        emptyBoard[1][2] = d;
        sudoku = new BasicSudoku(emptyBoard, new Cell[]{a, b, c, d});
    }

    @Test
    void getSize() {
        assertThat(sudoku.getSize()).isEqualTo(4);
    }

    @Test
    void getBlockSize() {
        assertThat(sudoku.getBlockSize()).isEqualTo(2);
    }

    @Test
    void availableValues() {
        assertThat(sudoku.availableValues()).containsExactlyInAnyOrder(a, b, c, d);
    }

    @Test
    void getCell() {
        assertThat(sudoku.getCell(0, 0)).isEqualToComparingFieldByField(a);
        assertThat(sudoku.getCell(3, 0)).isEqualToComparingFieldByField(b);
        assertThat(sudoku.getCell(3, 1)).isEqualToComparingFieldByField(c);
        assertThat(sudoku.getCell(1, 2)).isEqualToComparingFieldByField(d);
    }

    @Test
    void getRow() {
        assertThat(sudoku.getRow(0)).containsExactly(a, null, null, b);
        assertThat(sudoku.getRow(1)).containsExactly(null, null, null, c);
        assertThat(sudoku.getRow(2)).containsExactly(null, d, null, null);
        assertThat(sudoku.getRow(3)).containsExactly(null, null, null, null);
    }

    @Test
    void getColumn() {
        assertThat(sudoku.getColumn(0)).containsExactly(a, null, null, null);
        assertThat(sudoku.getColumn(1)).containsExactly(null, null, d, null);
        assertThat(sudoku.getColumn(2)).containsExactly(null, null, null, null);
        assertThat(sudoku.getColumn(3)).containsExactly(b, c, null, null);
    }

    @Test
    void getBlock() {
        assertThat(sudoku.getBlock(0, 0)).containsExactly(a, null, null, null);
        assertThat(sudoku.getBlock(1, 0)).containsExactly(null, b, null, c);
        assertThat(sudoku.getBlock(0, 1)).containsExactly(null, d, null, null);
        assertThat(sudoku.getBlock(1, 1)).containsExactly(null, null, null, null);
    }

    @DisplayName("Options on filled cells are empty")
    @Test
    void getOptions1() {
        assertThat(sudoku.getOptions(0, 0).length).isEqualTo(0);
        assertThat(sudoku.getOptions(3, 0).length).isEqualTo(0);
        assertThat(sudoku.getOptions(3, 1).length).isEqualTo(0);
        assertThat(sudoku.getOptions(1, 2).length).isEqualTo(0);
    }

    @DisplayName("Options on empty cells")
    @Test
    void getOptions2() {
        assertThat(sudoku.getOptions(2, 2)).contains(a, b, c).doesNotContain(d);
        assertThat(sudoku.getOptions(2, 3)).contains(a, b, c, d);
        assertThat(sudoku.getOptions(1, 1)).contains(b).doesNotContain(a, c, d);
        assertThat(sudoku.getOptions(1, 0)).contains(c).doesNotContain(a, b, d);
    }

    @DisplayName("One hint available")
    @Test
    void getHint1() {
        assertThat(sudoku.getHint(3, 2)).isEqualToComparingFieldByField(a);
        assertThat(sudoku.getHint(1, 1)).isEqualToComparingFieldByField(b);
        assertThat(sudoku.getHint(1, 0)).isEqualToComparingFieldByField(c);
        assertThat(sudoku.getHint(2, 0)).isEqualToComparingFieldByField(d);
    }

    @DisplayName("Hints on occupied cells return null")
    @Test
    void getHint2() {
        assertThat(sudoku.getHint(0, 0)).isNull();
        assertThat(sudoku.getHint(3, 0)).isNull();
        assertThat(sudoku.getHint(3, 1)).isNull();
        assertThat(sudoku.getHint(1, 2)).isNull();
    }

}
