package cz.muni.fi.pb162.hw01;

/**
 * This class represents casual NxN sudoku game.
 * <p>
 * Column and row indexing starts at 0.
 * In coordinate system, columns represent x values and rows y values.
 * When indexed, columns are always first, rows are second.
 * <p>
 * The following sudoku has size 4x4, block size is 2, there are 16 cells.
 * <p>
 * Empty cells are represented by {@code null} value.
 * <p>
 * Four cells are non-empty (not null):
 * <ul>
 * <li>A on position [0, 0]</li>
 * <li>B on position [3, 0]</li>
 * <li>C on position [3, 1]</li>
 * <li>D on position [1, 2]</li>
 * </ul>
 * <pre>
 * {@code
 * |=====|=====|
 * | A _ | _ B |
 * | _ _ | _ C |
 * |=====|=====|
 * | _ D | _ _ |
 * | _ _ | _ _ |
 * |=====|=====|
 * }
 * </pre>
 * Sudoku sample has 4 blocks (indexed with global rows and columns):
 * <ul>
 * <li>block [0, 0] has cell A</li>
 * <li>block [1, 0] has cells B and C</li>
 * <li>block [0, 1] has cell D</li>
 * <li>block [1, 1] has no cells</li>
 * </ul>
 * <p>
 * The interface also mentions options, which are meant to be options which can be put
 * into empty cell without breaking any sudoku rules.
 * <p>
 * Rules are casual sudoku rules: every row, column and block must have unique cells.
 * <p>
 * For example, [2, 2] has 3 options: A, B or C.
 * D cannot be put there, because it violates row rule.
 * <p>
 * Hints are options with exactly one option.
 * <p>
 * For example, [1, 1] has hint B.
 * Cell A violates block (n.[0, 0]) rule, C violates row (n.1) rule, D violates column (n.1) rule.
 * Thus, the only option, hint, is B.
 * <p>
 * Detailed information can be found in particular methods.
 *
 * @author Marek Sabo
 */
public interface Sudoku {

    /**
     * Dimension of the sudoku grid.
     *
     * @return positive square number
     */
    int getSize();

    /**
     * Size of every sudoku block.
     *
     * @return positive square root of {@link Sudoku#getSize()}
     */
    int getBlockSize();

    /**
     * Returns array of possible inserted values in an ascending order.
     * If this method is called multiple times, it returns reference to the same array.
     * <p>
     * F.e. [1, 2, 3, 4] or ['A', 'B', 'C', 'D'].
     *
     * @return array of possible inserted values in ascending order
     */
    Cell[] availableValues();

    /**
     * The cell at the specific position.
     *
     * @param column cell column index
     * @param row    cell row index
     * @return cell at the coordinates, null if is empty
     */
    Cell getCell(int column, int row);

    /**
     * The row at the specific position.
     *
     * @param row row index
     * @return row cells from left to right
     */
    Cell[] getRow(int row);

    /**
     * The column at the specific position.
     *
     * @param column column index
     * @return column cells from up to down
     */
    Cell[] getColumn(int column);

    /**
     * Returns cells in the specific block.
     * Global columns and rows of the cell are calculated as the quotients of the block size.
     * <p>
     * F.e. column / blockSize = globalColumn.
     * <p>
     * Global columns and rows can be also seen as numbered blocks (starting from zero).
     * <p>
     * Note that there are {@link Sudoku#getSize()} blocks and every block has also size cells.
     *
     * @param globalColumn block column index
     * @param globalRow    block rox index
     * @return cells in the block from left to right, from top to bottom
     */
    Cell[] getBlock(int globalColumn, int globalRow);

    /**
     * Computes all options available which can be put at specific column and cell
     * and do not break any of the sudoku rules.
     * <p>
     * Can contain nulls.
     * <p>
     * If the cell is already filled, it returns an empty array.
     *
     * @param column column index
     * @param row    row index
     * @return array containing all cells which can be inserted into chosen position
     */
    Cell[] getOptions(int column, int row);

    /**
     * This method is similar to {@link Sudoku#getOptions(int, int)} method.
     * <p>
     * If there is only one option available, it returns that cell.
     * <p>
     * Otherwise, if there are more or zero options, or the cell is already occupied, returns null.
     *
     * @param column column index
     * @param row    row index
     * @return cell which can be inserted into (empty) position, null otherwise
     */
    Cell getHint(int column, int row);

    /**
     * Returns all hints for the whole sudoku game.
     * Every value contains the result of {@link Sudoku#getHint(int, int)} method.
     *
     * @return hints for every position in the sudoku
     */
    Cell[][] getAllHints();

    /**
     * Insert a cell into the sudoku.
     *
     * @param column column index
     * @param row    row index
     * @param c      element to be put
     * @return true, if cell was inserted successfully, false if some sudoku rule was violated
     */
    boolean putElement(int column, int row, Cell c);

}
