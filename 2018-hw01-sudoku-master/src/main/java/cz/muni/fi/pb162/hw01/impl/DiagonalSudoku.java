/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;

/**
 * This class represents diagonal NxN sudoku game.
 * In coordinate system, columns represent x values and rows y values.
 * When indexed, columns are always first, rows are second.
 * @author Jiri Ceska
 */
public class DiagonalSudoku extends BasicSudoku implements Sudoku {
    
    /**
     * Create instance of DiagonalSudoku, correct behaviour is guaranteed by DiagonalSudokuChecker
     * @param cells Will be used as sudoku board
     * @param availableValues Only possible values to insert into sudoku
     */
    public DiagonalSudoku(Cell[][] cells, Cell[] availableValues) {
        super(cells, availableValues);
        setSudokuChecker(new DiagonalSudokuChecker(this)); 
    }
}
