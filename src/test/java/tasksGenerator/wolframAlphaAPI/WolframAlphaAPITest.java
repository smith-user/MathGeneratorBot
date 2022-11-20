package tasksGenerator.wolframAlphaAPI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WolframAlphaAPITest {

    static WolframAlphaAPI alphaAPI;

    @BeforeAll
    static void init() {
        alphaAPI = WolframAlphaAPI.instance();
    }

    @Test
    void instance() {
    }

    @Test
    void performQuery() {
        assertDoesNotThrow(() -> {
            HashMap<String, ArrayList<String>> result = alphaAPI.performQuery("x^2 -5x + 6 = 0");
            System.out.println(result);
        });
    }
}