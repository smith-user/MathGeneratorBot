package test.tasksGenerator.taskTypes.LinearEquation;

import MathGeneratorBot.tasksGenerator.taskTypes.LinearEquation.LinearEquationManualGenerator;
import org.junit.jupiter.api.*;

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
