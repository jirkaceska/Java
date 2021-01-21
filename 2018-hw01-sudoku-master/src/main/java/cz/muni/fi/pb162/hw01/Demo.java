package cz.muni.fi.pb162.hw01;

import cz.muni.fi.pb162.hw01.helper.ArrayUtils;
import cz.muni.fi.pb162.hw01.helper.Formatter;
import cz.muni.fi.pb162.hw01.helper.SudokuSample;
import cz.muni.fi.pb162.hw01.impl.LetterCell;
import cz.muni.fi.pb162.hw01.impl.NumberCell;

import java.util.Scanner;

/**
 * Runnable class for running sudoku game.
 *
 * @author Marek Sabo
 */
public class Demo {

    private static Sudoku sudoku;

    // FIXME: You can modify this method to check that everything is working correctly.
    private static Sudoku createSudokuSample() {
        return SudokuSample.createBigSudoku();
    }

    /**
     * The CLI program, interactive, multiple commands can be used.
     * <p>
     * To list all commands and their description, use command <b>help</b>.
     *
     * @param args arguments are ignored
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        sudoku = createSudokuSample();
        Formatter.prettyPrint(sudoku);

        while (!isSolved(sudoku)) {
            try {
                System.out.println("Command:");
                applyCommand(in.nextLine());
                Formatter.prettyPrint(sudoku);
            } catch (NullPointerException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("Exception caught, ignoring.");
            }
        }
        System.out.println("Solved.");
    }

    private static boolean isSolved(Sudoku sudoku) {
        for (int i = 0; i < sudoku.getSize(); i++) {
            if (ArrayUtils.contains(sudoku.getRow(i), null)) return false;
        }
        return true;
    }

    private static void applyCommand(String command) {
        String[] params = command.split(" ");
        applyCommandWithParams(params);
        System.out.println();
    }

    private static void applyCommandWithParams(String[] params) {
        switch (params[0]) {
            case "help":
                applyHelp();
                break;
            case "put":
                applyPut(params);
                break;
            case "hint":
                applyHint(params);
                break;
            case "hints":
                Formatter.prettyPrint(sudoku.getAllHints());
                break;
            case "put-hints":
                hintsAsMoves();
                break;
            case "options":
                applyOptions(params);
                break;
            case "new":
                sudoku = createSudokuSample();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private static void applyHelp() {
        System.out.println("Available commands:");
        System.out.println("put <number> <number> <char> - inserts char into the coordinates");
        System.out.println("hint <number> <number> - gives hint of the cell");
        System.out.println("hints - gives hints");
        System.out.println("put-hints - applies all hints as moves");
        System.out.println("options <number> <number> - prints available numbers");
        System.out.println("new - creates new sudoku");
        System.out.println("exit - finishes the game");
    }

    private static void applyPut(String[] params) {
        boolean wasPut = sudoku.putElement(
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2]),
                parseCell(params[3])
        );
        if (!wasPut) System.out.println("Cell cannot be put there according to sudoku rules.");
    }

    private static Cell parseCell(String s) {
        try {
            return new NumberCell(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return new LetterCell(s.charAt(0));
        }
    }

    private static void applyHint(String[] params) {
        Cell hint = sudoku.getHint(
                Integer.parseInt(params[1]),
                Integer.parseInt(params[2])
        );
        if (hint == null) {
            System.out.println("No hint available.");
        } else {
            System.out.println(hint.getValue());
        }
    }

    private static void applyOptions(String[] params) {
        Formatter.prettyPrint(
                sudoku.getOptions(
                        Integer.parseInt(params[1]),
                        Integer.parseInt(params[2])
                )
        );
    }

    private static void hintsAsMoves() {
        Cell[][] allHints = sudoku.getAllHints();

        Formatter.prettyPrint(allHints);

        for (int i = 0; i < sudoku.getSize(); i++) {
            for (int j = 0; j < sudoku.getSize(); j++) {
                Cell hint = allHints[i][j];
                if (hint != null) sudoku.putElement(i, j, hint);
            }
        }
    }

}
