package tasksGenerator.taskTypes.RationalArithmetic;

import tasksGenerator.mathClasses.MathFunctions;

import java.util.Objects;

final public class RationalArithmeticManualGenerator extends RationalArithmeticGenerator {

    private static final int maxNumber = 10;
    private static final int maxDegree = 2;
    private static final int minUnitCount = 3;
    private static final int maxUnitCount = 5;
    private static final Character[] operations =
            RationalArithmeticTaskCondition.getOperationsWithPriorities().keySet().toArray(new Character[0]);

    @Override
    public RationalArithmeticTaskCondition createTaskCondition() {
        String expression = generateUnit(1, false);
        expression = expression.substring(1, expression.length() - 1);
        return new RationalArithmeticTaskCondition(expression);
    }

    /**
     *
     * @param depth вложенность скобок для части выражения - unit (0 - это само выражение)
     * @param generateOperator {@code true} если нужно генерировать знак операции перед первым значением
     *                                     данной части выражения, {@code false} если знак операции
     *                                     генерировать не нужно.
     * @return строка как часть выражения - число или выражние в скобках.
     */
    private String generateUnit(int depth, boolean generateOperator) {
        StringBuilder expression = new StringBuilder();
        boolean exprInBrackets = MathFunctions.intRandomUnsigned(5) % depth == 0;
        Character operator = ' ';
        if (generateOperator)
        {
            operator = operations[MathFunctions.intRandomUnsigned(1000) % operations.length];
            if (exprInBrackets)
                while (Objects.equals(operator, '^'))
                    operator = operations[MathFunctions.intRandomUnsigned(1000) % operations.length];
            expression.append(operator);
        }
        if (exprInBrackets)
        {
            expression.append("(");
            int elementsCount = Math.max(2, MathFunctions.intRandomUnsigned(maxUnitCount - minUnitCount) + minUnitCount + 1 - depth);
            for(int i = 0; i < elementsCount; i++)
                expression.append(generateUnit(depth + 1, i != 0));
            expression.append(")");
        }
        else {
            int number;
            if (operator == '^') {
                number = 2 + MathFunctions.intRandomUnsigned(maxDegree - 2);
            } else {
                number = MathFunctions.intRandomUnsignedNonZero(maxNumber);
            }
            expression.append(number);
        }
        return expression.toString();
    }
}
