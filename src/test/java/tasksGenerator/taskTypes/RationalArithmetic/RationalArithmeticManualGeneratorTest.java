package tasksGenerator.taskTypes.RationalArithmetic;

import org.junit.jupiter.api.*;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.mathClasses.MathFunctions;

import static org.junit.jupiter.api.Assertions.*;

public class RationalArithmeticManualGeneratorTest {
    static RationalArithmeticManualGenerator generator;
    @BeforeAll
    static void init() {
        generator = new RationalArithmeticManualGenerator();
    }

    @Test
    void testCreateTaskCondition() {
        for(int i = 0; i < 100; i++) {
            assertDoesNotThrow( () -> {
                MathFunctions.getRPN(generator.createTaskCondition().getExpression());
            });
        }
    }

//    @Test
//    void testCreateSolutionGeneralCase() {
//        TaskCondition condition = new RationalArithmeticTaskCondition("10+(5-6)*2");
//        String solutionSteps = """
//                1. 5 - 6 = -1
//                2. -1 * 2 = -2
//                3. 10 + -2 = 8""";
//        String solutionResult = "8";
//        try {
//            TaskSolution actualSolution = generator.createTaskSolution(condition);
//            assertEquals(solutionSteps, actualSolution.getSolutionSteps());
//            assertEquals(solutionResult, actualSolution.getResult());
//        } catch (TaskSolutionException exc) {
//            fail();
//        }
//    }
}
