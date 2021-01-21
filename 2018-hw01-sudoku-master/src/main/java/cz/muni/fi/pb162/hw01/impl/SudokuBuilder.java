/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.InfiniteCellSequence;
import cz.muni.fi.pb162.hw01.Sudoku;
import cz.muni.fi.pb162.hw01.helper.ArrayUtils;

/**
 *
 * @author Jiri Ceska
 */
public class SudokuBuilder {
    private final int size;
    private final Cell[][] cells;
    private final Cell[] availableValues;
    private boolean isDiagonal = false;
    
    /**
     * Creates instance of builder to make Sudoku creation easier
     * @param size Created sudoku will have dimension size x size
     * @param cellType Type of cells of sudoku
     */
    public SudokuBuilder(int size, InfiniteCellSequence cellType) {
        this.size = size;
        cells = new Cell[size][size];
        this.availableValues = cellType.firstNValues(size);
    }
    
    /**
     * Put cell to the board iff it has correct type nad both indexes are valid
     * @param column Index of column
     * @param row Index of row
     * @param c Cell to insert
     * @return Reference to current object
     */
    public SudokuBuilder cell(int column, int row, Cell c) {
        if (BasicSudoku.isValidTupleI(column, row, size) 
                && ArrayUtils.contains(availableValues, c)) {
            cells[column][row] = c;        
        }
        return this;
    }
    
    /**
     * Set SudokuBuilder to build DiagonalSudoku when true, otherwise BasicSudoku will be built
     * @param isDiagonal Whether builder should build DiagonalSudoku
     * @return Reference to current object
     */
    public SudokuBuilder diagonal(boolean isDiagonal) {
        this.isDiagonal = isDiagonal;
        return this;
    }
    
    /**
     * Create new instace of DiagonalSudoku/BasicSudoku
     * @return Successfully built sudoku
     */
    public Sudoku build() {
        return isDiagonal
                ? new DiagonalSudoku(cells, availableValues)
                : new BasicSudoku(cells, availableValues);
    }
}
