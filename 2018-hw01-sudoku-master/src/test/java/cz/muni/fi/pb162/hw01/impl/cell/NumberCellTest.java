package cz.muni.fi.pb162.hw01.impl.cell;

import cz.muni.fi.pb162.hw01.impl.NumberCell;
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
class NumberCellTest {

    @Test
    void getValue() {
        // given
        NumberCell numberFour = new NumberCell(4);
        // then
        assertThat(numberFour.getValue()).isEqualTo("4");
    }

    @Test
    void startingValueIs1() {
        // given
        NumberCell numberCell = new NumberCell(4);
        // then
        assertThat(numberCell.startingValue().getValue()).isEqualTo("1");
    }

    @DisplayName("Next value should be incremented")
    @ParameterizedTest
    @MethodSource("intProvider")
    void nextValue(int value) {
        assertThat(
                new NumberCell(value)
                        .nextValue()
                        .getValue()
        ).isEqualTo(String.valueOf(value + 1));

    }

    private static Stream<Integer> intProvider() {
        return IntStream.range(1, 31).boxed();
    }
}
