package test.tasksGenerator.wolframAlphaAPI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import mathGeneratorBot.tasksGenerator.mathAPI.wolframAlphaAPI.WolframAlphaAPI;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WolframAlphaAPITest {

    static WolframAlphaAPI alphaAPI;

    /**
     * Инициализация объекта {@link WolframAlphaAPI}.
     */
    @BeforeAll
    static void init() {
        alphaAPI = WolframAlphaAPI.instance();
    }

    /**
     * Проверка решения арифметической задачи.
     */
    @Test
    void performQueryArithmetic() {
        String input = "12+3*4-5";
        assertDoesNotThrow(() -> {
            HashMap<String, ArrayList<String>> result = alphaAPI.performQuery(input);
            assertTrue(result.containsKey("Results"));
            assertEquals("19", result.get("Results").get(0));
            assertEquals(
                    "Simplify the following:\n12 + 3×4 - 5\n3×4 = 12:\n12 + 12 - 5\n | 1 | 2\n+ | 1 | 2\n" +
                    " | 2 | 4:\n24 - 5\n | 1 | 14\n | | \n- | | 5\n | 1 | 9:\nAnswer: | \n | 19", result.get("Results").get(1));
            assertTrue(result.containsKey("Input"));
            assertEquals("12 + 3×4 - 5", result.get("Input").get(0));
        });
    }

    /**
     * Проверка решения квадратного уравнения.
     */
    @Test
    void performQueryQuadraticEquation() {
        String input = "x^2-5x+6=0";
        assertDoesNotThrow(() -> {
            HashMap<String, ArrayList<String>> result = alphaAPI.performQuery(input);
            assertTrue(result.containsKey("Results"));
            assertEquals("x = 2", result.get("Results").get(0));
            assertEquals("x = 3", result.get("Results").get(1));
            assertEquals(
                    "Solve for x:\nx^2 - 5 x + 6 = 0\nThe left hand side factors into a product with two terms:\n" +
                    "(x - 3) (x - 2) = 0\nSplit into two equations:\nx - 3 = 0 or x - 2 = 0\nAdd 3 to both sides:\n" +
                    "x = 3 or x - 2 = 0\nAdd 2 to both sides:\nAnswer: | \n | x = 3 or x = 2",
                    result.get("Results").get(2));
            assertTrue(result.containsKey("Input interpretation"));
            assertEquals("solve x^2 - 5 x + 6 = 0", result.get("Input interpretation").get(0));
        });
    }
}