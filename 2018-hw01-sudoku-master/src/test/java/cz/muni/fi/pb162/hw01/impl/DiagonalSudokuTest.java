package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;
import cz.muni.fi.pb162.hw01.helper.Formatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marek Sabo
 */
@SuppressWarnings("Duplicates")
class DiagonalSudokuTest {

    private Sudoku sudoku;
    private Cell[][] emptyBoard = {
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null},
            new Cell[]{null, null, null, null}
    };
    private NumberCell n1 = new NumberCell(1);
    private NumberCell n2 = new NumberCell(2);
    private NumberCell n3 = new NumberCell(3);
    private NumberCell n4 = new NumberCell(4);

    @BeforeEach
    void setUp() {
        emptyBoard[0][0] = n1;
        emptyBoard[3][0] = n2;
        emptyBoard[3][1] = n3;
        emptyBoard[1][2] = n4;
        sudoku = new DiagonalSudoku(emptyBoard, new Cell[]{n1, n2, n3, n4});
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
        assertThat(sudoku.availableValues()).containsExactlyInAnyOrder(n1, n2, n3, n4);
    }

    @Test
    void getCell() {
        assertThat(sudoku.getCell(0, 0)).isEqualToComparingFieldByField(n1);
        assertThat(sudoku.getCell(3, 0)).isEqualToComparingFieldByField(n2);
        assertThat(sudoku.getCell(3, 1)).isEqualToComparingFieldByField(n3);
        assertThat(sudoku.getCell(1, 2)).isEqualToComparingFieldByField(n4);
    }

    @Test
    void getRow() {
        assertThat(sudoku.getRow(0)).containsExactly(n1, null, null, n2);
        assertThat(sudoku.getRow(1)).containsExactly(null, null, null, n3);
        assertThat(sudoku.getRow(2)).containsExactly(null, n4, null, null);
        assertThat(sudoku.getRow(3)).containsExactly(null, null, null, null);
    }

    @Test
    void getColumn() {
        assertThat(sudoku.getColumn(0)).containsExactly(n1, null, null, null);
        assertThat(sudoku.getColumn(1)).containsExactly(null, null, n4, null);
        assertThat(sudoku.getColumn(2)).containsExactly(null, null, null, null);
        assertThat(sudoku.getColumn(3)).containsExactly(n2, n3, null, null);
    }

    @Test
    void getBlock() {
        assertThat(sudoku.getBlock(0, 0)).containsExactly(n1, null, null, null);
        assertThat(sudoku.getBlock(1, 0)).containsExactly(null, n2, null, n3);
        assertThat(sudoku.getBlock(0, 1)).containsExactly(null, n4, null, null);
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
        assertThat(sudoku.getOptions(2, 2)).contains(n2, n3).doesNotContain(n1, n4);
        assertThat(sudoku.getOptions(2, 3)).contains(n1, n2, n3, n4);
        assertThat(sudoku.getOptions(1, 1)).contains(n2).doesNotContain(n1, n3, n4);
        assertThat(sudoku.getOptions(1, 0)).contains(n3).doesNotContain(n1, n2, n4);
    }

    @DisplayName("One hint available")
    @Test
    void getHint1() {
        assertThat(sudoku.getHint(3, 2)).isEqualToComparingFieldByField(n1);
        assertThat(sudoku.getHint(1, 1)).isEqualToComparingFieldByField(n2);
        assertThat(sudoku.getHint(1, 0)).isEqualToComparingFieldByField(n3);
        assertThat(sudoku.getHint(2, 0)).isEqualToComparingFieldByField(n4);
    }

    @DisplayName("Hints on occupied cells return null")
    @Test
    void getHint2() {
        assertThat(sudoku.getHint(0, 0)).isNull();
        assertThat(sudoku.getHint(3, 0)).isNull();
        assertThat(sudoku.getHint(3, 1)).isNull();
        assertThat(sudoku.getHint(1, 2)).isNull();
    }

    @DisplayName("Put element on correct positions")
    @Test
    void putElement1() {
        assertThat(sudoku.putElement(1, 3, n1)).isTrue();
        assertThat(sudoku.putElement(1, 0, n3)).isTrue();
        assertThat(sudoku.putElement(2, 3, n4)).isTrue();
        assertThat(sudoku.putElement(1, 1, n2)).isTrue();

        // check that elements were actually added
        assertThat(sudoku.getRow(0)).containsExactly(n1, n3, null, n2);
        assertThat(sudoku.getRow(1)).containsExactly(null, n2, null, n3);
        assertThat(sudoku.getRow(2)).containsExactly(null, n4, null, null);
        assertThat(sudoku.getRow(3)).containsExactly(null, n1, n4, null);
    }

    @DisplayName("Put element on occupied positions")
    @Test
    void putElement2() {
        assertThat(sudoku.putElement(0, 0, n2)).isFalse();
        assertThat(sudoku.putElement(3, 0, n3)).isFalse();
        assertThat(sudoku.putElement(3, 1, n1)).isFalse();
        assertThat(sudoku.putElement(1, 2, n4)).isFalse();

        checkSudokuNotChanged(); // check that sudoku stayed the same
    }

    private void checkSudokuNotChanged() {
        assertThat(sudoku.getRow(0)).containsExactly(n1, null, null, n2);
        assertThat(sudoku.getRow(1)).containsExactly(null, null, null, n3);
        assertThat(sudoku.getRow(2)).containsExactly(null, n4, null, null);
        assertThat(sudoku.getRow(3)).containsExactly(null, null, null, null);
    }
}
