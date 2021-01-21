package cz.muni.fi.pb162.hw01.impl.cell;

import cz.muni.fi.pb162.hw01.impl.LetterCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Marek Sabo
 */
class LetterCellTest {

    @Test
    void getValue() {
        // given
        LetterCell d = new LetterCell('D');
        // then
        assertThat(d.getValue()).isEqualTo("D");
    }

    @Test
    void startingValueIs1() {
        // given
        LetterCell b = new LetterCell('B');
        // then
        assertThat(b.startingValue().getValue()).isEqualTo("A");
    }

    @DisplayName("Next letter should be incremented")
    @ParameterizedTest
    @MethodSource("charProvider")
    void nextValue(char value) {
        assertThat(
                new LetterCell(value)
                        .nextValue()
                        .getValue()
        ).isEqualTo(Character.toString((char) (value + 1)));

    }

    private static Stream<Character> charProvider() {
        return IntStream.range(0, 26)
                .mapToObj(x -> Character.toString((char)(x % 58 + 65)))
                .map(value -> value.charAt(0));
    }
}
