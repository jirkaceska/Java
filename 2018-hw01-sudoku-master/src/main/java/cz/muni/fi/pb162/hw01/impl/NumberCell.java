/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.InfiniteCellSequence;

/**
 * Cell which can contain only number values
 * @author Jiri Ceska
 */
public class NumberCell extends InfiniteCellSequence{
    private int value;
    private static final int STARTING_VALUE = 1;
    
    /**
     * Assign number to this cell (can be null)
     * @param value Value to assign
     */
    public NumberCell(int value) {
        this.value = value;
    }
    
    @Override
    public InfiniteCellSequence startingValue() {
        this.value = STARTING_VALUE;
        return this;
    }
    
    @Override
    public InfiniteCellSequence nextValue() {
        return new NumberCell(value + 1);
    }
    
    @Override
    public String getValue() {
        return String.valueOf(value);
    }
}
