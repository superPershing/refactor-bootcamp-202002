package cc.xpbootcamp.warmup.fibonacci;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FibGeneratorTest {
    private FibGenerator fibGenerator;

    @BeforeEach
    void setUp() {
        fibGenerator = new FibGenerator();
    }

    @Test
    void should_return_1_when_calculate_given_position_is_1() {
        assertThat(fibGenerator.calculate(1L)).isEqualTo(1L);
    }

    @Test
    void should_return_1_when_calculate_given_position_is_2() {
        assertThat(fibGenerator.calculate(2L)).isEqualTo(1L);
    }

    @Test
    void should_return_2_when_calculate_given_position_is_3() {
        assertThat(fibGenerator.calculate(3L)).isEqualTo(2L);
    }

    @Test
    void should_return_3_when_calculate_given_position_is_4() {
        assertThat(fibGenerator.calculate(4L)).isEqualTo(3L);
    }

    @Test
    void should_return_55_when_calculate_given_position_is_10() {
        assertThat(fibGenerator.calculate(10L)).isEqualTo(55L);
    }

    @Test
    void should_return_12586269025L_when_calculate_given_position_is_50() {
        assertThat(fibGenerator.calculate(50L)).isEqualTo(12586269025L);
    }

    @Test
    void should_throw_exception_when_calculate_given_position_is_negative() {
        Assertions.assertThrows(GivenPositionIsNegativeException.class, () -> {
            fibGenerator.calculate(-1L);
        });

    }
}
