package tasksGenerator.taskTypes.RationalArithmetic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tasksGenerator.TasksGenerator;
import tasksGenerator.exceptions.TaskConditionException;
import tasksGenerator.exceptions.TaskSolutionException;

import static tasksGenerator.mathClasses.MathFunctions.*;

import java.util.Objects;

final public class RationalArithmeticManualGenerator extends RationalArithmeticGenerator {

    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());
    private static final int maxNumber = 7;
    private static final int maxDegree = 2;
    private static final int minUnitCount = 3;
    private static final int maxUnitCount = 4;
    private static final int maxDepth = 2;
    private static final Character[] operations =
            RationalArithmeticTaskCondition.getOperationsWithPriorities().keySet().toArray(new Character[0]);

    @Override
    public RationalArithmeticTaskCondition createTaskCondition() {
        logger.traceEntry();
        String expression = generateUnit(1, false);
        logger.debug("Сгенерируемое выражение: \"{}\"", expression);
        expression = expression.substring(1, expression.length() - 1);
        return logger.traceExit(new RationalArithmeticTaskCondition(expression));
    }

    /**
     * Генерирует часть арифметического выражения.
     * @param depth вложенность скобок для части выражения - unit (0 - это само выражение)
     * @param generateOperator {@code true} если нужно генерировать знак операции перед первым значением
     *                                     данной части выражения, {@code false} если знак операции
     *                                     генерировать не нужно.
     * @return строка как часть выражения - число или выражние в скобках.
     */
    private String generateUnit(int depth, boolean generateOperator) {
        StringBuilder expression = new StringBuilder();
        boolean exprInBrackets = depth <= maxDepth && intRandomUnsigned(1000) % depth == 0;
        Character operator = ' ';
        if (generateOperator)
        {
            operator = operations[intRandomUnsigned(1000) % operations.length];
            if (exprInBrackets)
                while (Objects.equals(operator, '^'))
                    operator = operations[intRandomUnsigned(1000) % operations.length];
            expression.append(operator);
        }
        if (exprInBrackets)
        {
            expression.append("(");
            int elementsCount = Math.max(2, intRandomUnsigned(maxUnitCount - minUnitCount) + minUnitCount + 1 - depth);
            for(int i = 0; i < elementsCount; i++)
                expression.append(generateUnit(depth + 1, i != 0));
            expression.append(")");
        }
        else {
            int number;
            if (operator == '^') {
                number = 2 + intRandomUnsigned(maxDegree - 2);
            } else {
                number = intRandomUnsignedNonZero(maxNumber);
            }
            expression.append(number);
        }
        return expression.toString();
    }
}
