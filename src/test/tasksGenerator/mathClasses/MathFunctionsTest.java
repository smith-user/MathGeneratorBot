package test.tasksGenerator.mathClasses;

import MathGeneratorBot.tasksGenerator.mathClasses.Fraction;
import MathGeneratorBot.tasksGenerator.mathClasses.MathFunctions;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionsTest {

    @Test
    void testGcd() {
        assertEquals(3, MathFunctions.gcdByEuclidAlgorithm(12, 15));
    }

    @Test
    void testGcdSimple() {
        assertEquals(1, MathFunctions.gcdByEuclidAlgorithm(17, 21));
    }

    @Test
    void testGcdNegative() {
        assertEquals(5, MathFunctions.gcdByEuclidAlgorithm(-15, 10));
    }

    @Test
    void testGcdWithOneZero() {
        assertEquals(10, MathFunctions.gcdByEuclidAlgorithm(0, 10));
    }

    @Test
    void testGcdWithTwoZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathFunctions.gcdByEuclidAlgorithm(0, 0);
        });
    }

    @Test
    void testLeastCommonMultiple() {
        assertEquals(60, MathFunctions.leastCommonMultiple(12, 15));
    }

    @Test
    void testLeastCommonMultipleZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathFunctions.leastCommonMultiple(0, 10);
        });
    }

    @Test
    void testGetRPN() {
        String exp = "4+5*2";
        Object[] actual = MathFunctions.getRPN(exp);
        Object[] expected = {
                new Fraction(4),
                new Fraction(5),
                new Fraction(2),
                '*',
                '+'
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetRPNWithBrackets() {
        String exp = "9-(2+3)*2";
        Object[] actual = MathFunctions.getRPN(exp);
        Object[] expected = {
                new Fraction(9),
                new Fraction(2),
                new Fraction(3),
                '+',
                new Fraction(2),
                '*',
                '-'
        };
        assertArrayEquals(expected, actual);
    }
}
