package tasksGenerator.taskTypes.LinearEquation;

import org.junit.jupiter.api.*;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskSolutionException;
import tasksGenerator.mathClasses.Fraction;

import static org.junit.jupiter.api.Assertions.*;

public class LinearEquationManualGeneratorTest {
    static LinearEquationManualGenerator generator;

    @BeforeAll
    static void init() {
        generator = new LinearEquationManualGenerator();
    }

//    @Test
//    void testCreateSolutionGeneralCase() {
//        TaskCondition condition = new LinearEquationTaskCondition(new Fraction(5), new Fraction(-10));
//        String solutionSteps = """
//                5x = 10
//                10 / 5 = 2""";
//        String solutionResult = "x = 2";
//        try {
//            TaskSolution actualSolution = generator.createTaskSolution(condition);
//            assertEquals(solutionSteps, actualSolution.getSolutionSteps());
//            assertEquals(solutionResult, actualSolution.getResult());
//        } catch (TaskSolutionException exc) {
//            fail();
//        }
//    }

//    @Test
//    void testCreateSolutionWithoutRoots() {
//        TaskCondition condition = new LinearEquationTaskCondition(new Fraction(0), new Fraction(-2));
//        String solutionSteps = "0x = 2";
//        String solutionResult = "Корней нет";
//        try {
//            TaskSolution actualSolution = generator.createTaskSolution(condition);
//            assertEquals(solutionSteps, actualSolution.getSolutionSteps());
//            assertEquals(solutionResult, actualSolution.getResult());
//        } catch (TaskSolutionException exc) {
//            fail();
//        }
//    }

//    @Test
//    void testCreateSolutionInfinityNumberOfSolutions() {
//        TaskCondition condition = new LinearEquationTaskCondition(new Fraction(0), new Fraction(0));
//        String solutionSteps = "0x = 0";
//        String solutionResult = "x - любое число";
//        try {
//            TaskSolution actualSolution = generator.createTaskSolution(condition);
//            assertEquals(solutionSteps, actualSolution.getSolutionSteps());
//            assertEquals(solutionResult, actualSolution.getResult());
//        } catch (TaskSolutionException exc) {
//            fail();
//        }
//    }
}
