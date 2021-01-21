/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.helper.ArrayUtils;
import cz.muni.fi.pb162.hw01.helper.SudokuChecker;

/**
 * This class handles sudoku rules and checks if player can violate the rules
 * @author Jiri Ceska
 */
public class DiagonalSudokuChecker extends SudokuChecker {
    
    /**
     * Creates instance of diagonal sudoku checker.
     * @param sudoku The sudoku which is played on
     */
    public DiagonalSudokuChecker(DiagonalSudoku sudoku) {
        super(sudoku);
    }
    
    @Override
    public boolean canInsert(int column, int row, Cell c) {
        return super.canInsert(column, row, c) && isDiagonalValid(column, row, c);
    }
    
    /**
     * If indexes are on diagonal, check if cell doesn't violate diagonal rules
     * @param column Index of column
     * @param row Index of row
     * @param c Cell to check
     * @return True if cell doesn't violate diagonal rules, false otherwise
     */
    private boolean isDiagonalValid(int column, int row, Cell c) {
        if (column == row) {
            return !ArrayUtils.contains(getPrimaryDiagonal(), c);
        }
        if (column == getSudoku().getSize() - 1 - row) {
            return !ArrayUtils.contains(getSecondaryDiagonal(), c);
        }
        return true;
    }
    
    /**
     * Get cells which are on primary diagonal (from top left to bottom right)
     * @return Cells on primary diagonal
     */
    private Cell[] getPrimaryDiagonal() {
        int size = getSudoku().getSize();
        Cell[] primaryDiagonal = new Cell[size];
        
        for (int i = 0; i < size; ++i) {
            primaryDiagonal[i] = getSudoku().getCell(i, i);
        }
        return primaryDiagonal;
    }
    
    /**
     * Get cells which are on secondary diagonal (from bottom left to top right)
     * @return Cells on secondary diagonal
     */
    private Cell[] getSecondaryDiagonal() {
        int size = getSudoku().getSize();
        Cell[] secondaryDiagonal = new Cell[size];
        
        for (int i = 0; i < size; ++i) {
            secondaryDiagonal[i] = getSudoku().getCell(i, size - i - 1);
        }
        return secondaryDiagonal;
    }
}
