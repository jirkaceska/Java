package cz.muni.fi.pb162.hw01.helper;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;

/**
 * This class handles sudoku rules and checks if player can violate the rules.
 *
 * @author Marek Sabo
 */
public class SudokuChecker {

    private Sudoku sudoku;

    /**
     * Creates instance of sudoku checker.
     *
     * @param sudoku the sudoku which is played on
     */
    public SudokuChecker(Sudoku sudoku) {
        this.sudoku = sudoku;
    }
    
    public Sudoku getSudoku() {
        return sudoku;
    }

    /**
     * Checks whether cell c can be inserted without violating rules.
     *
     * @param column column index
     * @param row    row index
     * @param c      cell to be inserted
     * @return true, if no rules are violated, false otherwise
     */
    public boolean canInsert(int column, int row, Cell c) {
        return isEmpty(column, row)
                && isRowValid(row, c)
                && isColumnValid(column, c)
                && isBlockValid(
                column / sudoku.getBlockSize(),
                row / sudoku.getBlockSize(),
                c);
    }

    private boolean isEmpty(int column, int row) {
        return sudoku.getCell(column, row) == null;
    }

    private boolean isRowValid(int row, Cell c) {
        return !ArrayUtils.contains(sudoku.getRow(row), c);
    }

    private boolean isColumnValid(int column, Cell c) {
        return !ArrayUtils.contains(sudoku.getColumn(column), c);
    }

    private boolean isBlockValid(int globalColumn, int globalRow, Cell c) {
        return !ArrayUtils.contains(sudoku.getBlock(globalColumn, globalRow), c);
    }
}
