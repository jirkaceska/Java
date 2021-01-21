package cz.muni.fi.pb162.hw01;

/**
 * Defines contract of the smallest Sudoku unit.
 *
 * @author Marek Sabo
 */
public interface Cell {

    /**
     * Returns the content of the element in a string representation.
     *
     * @return string representation of the value
     */
    String getValue();

    /**
     * Method compares the values of the cells and returns true if are the same,
     * false otherwise.
     * <p>
     * Simply said, this method replaces the use of {@link Object#equals(Object)} method.
     * <p>
     * Equals method is not used due to the lack of students knowledge and can be ignored.
     *
     * @param that other value which it {@code this} object compared to
     * @return true if cells are same, false otherwise
     */
    default boolean hasSameValueAs(Cell that) {
        if (that == null) return false;
        return this.getValue().equals(that.getValue());
    }

}
