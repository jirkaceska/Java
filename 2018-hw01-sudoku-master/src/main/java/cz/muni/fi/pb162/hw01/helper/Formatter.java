package cz.muni.fi.pb162.hw01.helper;

import cz.muni.fi.pb162.hw01.Cell;
import cz.muni.fi.pb162.hw01.Sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Utility class used for formatting purposes and printing on standard output.
 *
 * @author Marek Sabo
 */
@SuppressWarnings("Convert2MethodRef")
public final class Formatter {

    private Formatter() {
    }

    /**
     * Prints content of array on standard output.
     * Nulls are represented with _.
     * <p>
     * F.e., array of length four with only B at second position is
     * {@code _ B _ _}
     *
     * @param cells array to be formatted and printed
     */
    public static void prettyPrint(Cell[] cells) {
        System.out.println(prettyString(cells));
    }

    /**
     * Prints content of array on standard output.
     * Nulls are represented with _.
     * <p>
     * F.e., array of length 4x4:
     * <pre>
     * {@code
     * _ C D _
     * _ B A _
     * _ _ _ A
     * C _ _ D
     * }
     * </pre>
     *
     * @param cells array to be formatted and printed
     */
    public static void prettyPrint(Cell[][] cells) {
        System.out.println(prettyString(cells));
    }

    /**
     * Prints content of sudoku on standard output.
     * Nulls are represented with _.
     * Square blocks are represented with lines.
     * <p>
     * F.e., sudoku 4x4:
     * <pre>
     * {@code
     * |=====|=====|
     * | 1 _ | _ _ |
     * | _ _ | _ 3 |
     * |=====|=====|
     * | _ 4 | 2 _ |
     * | _ _ | _ _ |
     * |=====|=====|
     * }
     * </pre>
     *
     * @param sudoku printed and formatted sudoku
     */
    public static void prettyPrint(Sudoku sudoku) {
        System.out.println(prettyString(sudoku));
    }

    /**
     * Same as {@link Formatter#prettyPrint(Sudoku)}}, but returns {@code String}.
     * @param sudoku sudoku to be formatted
     * @return sudoku in a readable format
     */
    public static String prettyString(Sudoku sudoku) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sudoku.getSize(); i++) {
            if (i % sudoku.getBlockSize() == 0) thickLine(sb, sudoku.getBlockSize());
            buildWithBlocks(i, sudoku, sb);
        }
        thickLine(sb, sudoku.getBlockSize());
        newLine(sb);
        return sb.toString();
    }

    private static String prettyString(Cell[] cells) {
        return Arrays.stream(cells)
                .map(e -> printableCell(e))
                .collect(Collectors.joining(" "));
    }

    private static String prettyString(Cell[][] cells) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            for (Cell[] cell : cells) {
                sb.append(printableCell(cell[i])).append(" ");
            }
            newLine(sb);
        }
        return sb.toString();
    }

    private static String printableCell(Cell c) {
        return c == null ? "_" : c.getValue();
    }

    private static void buildWithBlocks(int row, Sudoku sudoku, StringBuilder sb) {
        for (int i = 0; i < sudoku.getSize(); i++) {
            if (i % sudoku.getBlockSize() == 0) sb.append("| ");
            sb.append(printableCell(sudoku.getCell(i, row))).append(" ");
        }
        newLine(sb.append("|"));
    }

    private static void thickLine(StringBuilder sb, int length) {
        String s = "|" + repeat(length, "==") + "=";
        newLine(sb.append(repeat(length, s)).append("|"));
    }

    private static void newLine(StringBuilder sb) {
        sb.append(System.lineSeparator());
    }

    private static String repeat(int n, String s) {
        return String.join("", Collections.nCopies(n, s));
    }

}
