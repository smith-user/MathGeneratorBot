package test.tasksGenerator.mathClasses;

import MathGeneratorBot.tasksGenerator.mathClasses.Fraction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class FractionTest {

    @Test
    void testCreateFraction() {
        Fraction actual = new Fraction(12, -15);
        assertEquals(new Fraction(-4, 5), actual);
    }

    @Test
    void testCreateFractionOnlyWithNumerator() {
        Fraction actual = new Fraction(10);
        assertEquals(new Fraction(10, 1), actual);
    }

    @Test
    void testCreateFractionDenominatorIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Fraction(10, 0));
    }
}
