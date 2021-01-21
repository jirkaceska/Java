/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.InfiniteCellSequence;

/**
 * Cell which can contain only letter values
 * @author Jiri Ceska
 */
public class LetterCell extends InfiniteCellSequence {
    private char value;
    private static final char STARTING_VALUE = 'A';
    
    /**
     * Assign letter to this cell (can be null)
     * @param value Value to assign
     */
    public LetterCell(char value) {
        this.value = value;
    }
    
    @Override
    public InfiniteCellSequence startingValue() {
        this.value = STARTING_VALUE;
        return this;
    }

    @Override
    public InfiniteCellSequence nextValue() {
        int nextCharValue = (int) value + 1;
        return new LetterCell((char) nextCharValue);
    }
    
    @Override
    public String getValue() {
        return String.valueOf(value);
    }
}
