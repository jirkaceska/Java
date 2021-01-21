/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;
import cz.muni.fi.pb162.hw01.helper.ArrayUtils;
import cz.muni.fi.pb162.hw01.helper.SudokuChecker;

/**
 * This class represents basic NxN sudoku game.
 * In coordinate system, columns represent x values and rows y values.
 * When indexed, columns are always first, rows are second.
 * @author Jiri Ceska
 */
public class BasicSudoku implements Sudoku {
    private final Cell[][] cells;
    private final int size;
    private final Cell[] availableValues;
    private SudokuChecker checker = new SudokuChecker(this);
    
    /**
     * Created sudoku board of cells, there might be only availableValues used
     * @param cells Will be used as sudoku board
     * @param availableValues Only possible values to insert into sudoku
     */
    public BasicSudoku(Cell[][] cells, Cell[] availableValues) {
        this.cells = cells;
        size = cells.length;
        this.availableValues = availableValues;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getBlockSize() {
        return (int) Math.sqrt(size);
    }

    @Override
    public Cell[] availableValues() {
        return availableValues;
    }

    @Override
    public Cell getCell(int column, int row) {
        if (!isValidTupleI(column, row, size)) {
            return null;
        }
        return cells[column][row];
    }

    @Override
    public Cell[] getRow(int row) {
        if (!isInRange(row, size)) return null;
        
        Cell[] cellRow = new Cell[size];
        for (int i = 0; i < size; i++) {
            cellRow[i] = cells[i][row];
        }
        return cellRow;
    }

    @Override
    public Cell[] getColumn(int column) {
        if (!isInRange(column, size)) return null;
        return cells[column];
    }

    @Override
    public Cell[] getBlock(int blockColumn, int blockRow) {
        if (!isValidTupleI(blockColumn, blockRow, getBlockSize())) {
            return null;
        }
        Cell[] block = new Cell[size];
        int blockSize = getBlockSize();
        for (int r = 0; r < blockSize; ++r) {
            for (int c = 0; c < blockSize; ++c) {
                block[r*blockSize + c] = 
                        getCell(blockColumn*blockSize + c, 
                                blockRow*blockSize + r);
            }
        }
        return block;
    }

    @Override
    public Cell[] getOptions(int column, int row) {
        if (!isValidTupleI(column, row, size)) {
            return null;
        }
        if (getCell(column, row) != null) {
            return new Cell[0];
        }
        
        Cell[] options = new Cell[size];
        int i = 0;
        for (Cell c : availableValues) {
            if (checker.canInsert(column, row, c)) {
                options[i] = c;
                ++i;
            }
        }
        return options;
    }

    @Override
    public Cell getHint(int column, int row) {
        return ArrayUtils.singleElement(getOptions(column, row)); 
    }

    @Override
    public Cell[][] getAllHints() {
        Cell[][] hints = new Cell[size][size];
        for (int column = 0; column < size; ++column) {
            for (int row = 0; row < size; ++row) {
                hints[column][row] = getHint(column, row);
            }
        }
        return hints;
    }

    @Override
    public boolean putElement(int column, int row, Cell c) {
        if (isValidTupleI(column, row, size) 
                && ArrayUtils.contains(availableValues, c)
                && checker.canInsert(column, row, c)) {
            cells[column][row] = c;
            return true;
        }
        return false;
    }

    /**
     * Check if number is in range <0, topBoundary)
     * @param i Number to check
     * @param topBoundary Top boundary of interval
     * @return True if number is in desired interval, false otherwise
     */
    public static boolean isInRange(int i, int topBoundary) {
        return 0 <= i && i < topBoundary;
    }
    
    /**
     * Check if tuple (i,j) can be safely used to indexing sudoku board
     * @param i Column index to check
     * @param j Row index to check
     * @param topBoundary Maximum of indexes
     * @return True if tuple can be used as index, false otherwise
     */
    public static boolean isValidTupleI(int i, int j, int topBoundary) {
        return isInRange(i, topBoundary) && isInRange(j, topBoundary);
    }
    
    /**
     * Set checker which will observe rules of sudoku 
     * @param checker 
     */
    protected void setSudokuChecker(SudokuChecker checker) {
        this.checker = checker;
    }
}
