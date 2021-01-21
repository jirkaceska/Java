package cz.muni.fi.pb162.hw01;

/**
 * Defines contract of the infinite sequence.
 * The sequence is not really infinite, it just simulates infinity by defining starting method and incremental rule.
 *
 * @author Marek Sabo
 */
public abstract class InfiniteCellSequence implements Cell {

    @Override
    public abstract String getValue();

    /**
     * The starting value.
     * <p>
     * Calling this method multiple times should always return the same object.
     *
     * @return the first cell of the sequence
     */
    public abstract InfiniteCellSequence startingValue();

    /**
     * This method should contain a formula how to calculate the next element.
     *
     * @return the cell which follows after the current cell according to the sequence
     */
    public abstract InfiniteCellSequence nextValue();

    /**
     * Computes the first n values of the sequence, starting at starting value.
     * <p>
     * Method uses {@link InfiniteCellSequence#startingValue()} and
     * {@link InfiniteCellSequence#nextValue()} to calculate the sequence.
     *
     * @param n the length of the sequence
     * @return sequence array
     */
    public InfiniteCellSequence[] firstNValues(int n) {
        InfiniteCellSequence[] values = new InfiniteCellSequence[n];
        values[0] = startingValue();
        for (int i = 1; i < n; i++) {
            values[i] = values[i - 1].nextValue();
        }
        return values;
    }

    @Override
    public String toString() {
        return getValue();
    }

}
