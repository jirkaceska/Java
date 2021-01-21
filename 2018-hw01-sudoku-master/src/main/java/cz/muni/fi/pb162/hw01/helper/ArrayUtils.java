package cz.muni.fi.pb162.hw01.helper;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.impl.NumberCell;

/**
 * Utility class.
 *
 * @author Marek Sabo
 */
public final class ArrayUtils {

    public static final Cell[] EMPTY_ARRAY = new Cell[]{};

    private ArrayUtils() {
    }

    /**
     * Searches for {@link NumberCell} in input array.
     * If array is empty, returns true.
     *
     * @param board to be searched for numbers
     * @return true if it contains at least one {@link NumberCell}
     */
    public static boolean containsNumbers(Cell[][] board) {
        for (Cell[] cells : board) {
            for (Cell c : cells) {
                if (c != null) return (c instanceof NumberCell);
            }
        }
        return true;
    }

    /**
     * Makes an intersection of three arrays.
     * <p>
     * {@code
     * [1, 2, 3, null] & [4, 2, 1, null] & [1, 5, null, null] = [1, null, null, null] }
     *
     * @param c1 first array
     * @param c2 second array
     * @param c3 third array
     * @return array with cells which are available in all three input arrays
     */
    public static Cell[] intersect(Cell[] c1, Cell[] c2, Cell[] c3) {
        return intersect(intersect(c1, c2), c3);
    }

    /**
     * Makes an intersection of two input arrays.
     * The result array has the length of the c1 array.
     *
     * @param c1 first array
     * @param c2 second array
     * @return array with cells available in both input arrays
     */
    private static Cell[] intersect(Cell[] c1, Cell[] c2) {
        Cell[] intersectedArray = new Cell[c1.length];
        for (int i = 0; i < c1.length; i++) {
            if (contains(c2, c1[i])) intersectedArray[i] = c1[i];
        }
        return intersectedArray;
    }

    /**
     * Makes a difference c1 - c2.
     * <p>
     * {@code
     * [1, 2, 3, 4] - [3, null, 1, null] = [null, 2, null, 4] }
     *
     * @param c1 array which is subtracted (some cells are in the result)
     * @param c2 subtracting array (cells are not in the result)
     * @return array of length c1 with elements which are in c1 but not in c2
     */
    public static Cell[] difference(Cell[] c1, Cell[] c2) {
        Cell[] subtractedArray = new Cell[c1.length];
        for (int i = 0; i < c1.length; i++) {
            if (!contains(c2, c1[i])) subtractedArray[i] = c1[i];
        }
        return subtractedArray;
    }

    /**
     * Searches an array for value wanted cell.
     *
     * @param cells  array to be searched
     * @param wanted cell which value is searched for
     * @return true if cells array contains value of wanted element
     */
    public static boolean contains(Cell[] cells, Cell wanted) {
        for (Cell c : cells) {
            if (c == null) {
                if (wanted == null) return true;
            } else {
                if (c.hasSameValueAs(wanted)) return true;
            }
        }
        return false;
    }

    /**
     * Reduces array to a single element, if the array has exactly
     * one not-null cell.
     *
     * @param cells array, from which the cell could be extracted
     * @return cell is array contains only not-null cell, null otherwise
     */
    public static Cell singleElement(Cell[] cells) {
        Cell foundCell = null;
        for (Cell c : cells) {
            if (c != null) {
                if (foundCell != null) return null;
                else foundCell = c;
            }
        }
        return foundCell;
    }

}
