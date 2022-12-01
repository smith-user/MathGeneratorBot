package tasksGenerator.wolframAlphaAPI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasksGenerator.MathAPI.wolframAlphaAPI.WolframAlphaAPI;
import tasksGenerator.taskTypes.LinearEquation.LinearEquationManualGenerator;
import tasksGenerator.taskTypes.LinearEquation.LinearEquationWolframSolver;
import tasksGenerator.taskTypes.RationalArithmetic.RationalArithmeticManualGenerator;

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
            RationalArithmeticManualGenerator generator = new RationalArithmeticManualGenerator();
            HashMap<String, ArrayList<String>> result;
            result = alphaAPI.performQuery("12/(1-3/3)");
            result = alphaAPI.performQuery("0/0");
            result = alphaAPI.performQuery("12+(6-5/10*8)/0");
            //result = alphaAPI.performQuery("0x=2");
            for(int i = 0; i < 100; i++)
            {
                result = alphaAPI.performQuery(generator.createTaskCondition().getExpression());
            }

        });
    }
}