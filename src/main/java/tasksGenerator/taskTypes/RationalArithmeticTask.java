package tasksGenerator.taskTypes;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.mathClasses.Fraction;
import tasksGenerator.mathClasses.MathFunctions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class RationalArithmeticTask extends TaskType {

    protected static final Map<Character, Integer> operationsWithPriorities = Map.of(
            '+', 0,
            '-', 0,
            '*', 1,
            '/', 1,
            '^', 2
    );
    protected static final int maxNumber = 10;
    protected static final int maxDegree = 2;
    protected static final int minUnitCount = 3;
    protected static final int maxUnitCount = 5;

    private static final Character[] operations = operationsWithPriorities.keySet().toArray(new Character[0]);

    public RationalArithmeticTask() {
        while(true) {
            try {
                condition = generate();
                solution = solve();
                break;
            } catch (ArithmeticException | IllegalArgumentException e) {
                continue;
            }
        }
    }

    @Override
    protected TaskCondition generate() {
        String expression = generateUnit(1, false);
        expression = expression.substring(1, expression.length() - 1);
        return new TaskCondition("Вычислить значение выражения: ", expression);
    }

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
            switch (operator) {
                case '^' -> number = 2 + MathFunctions.intRandomUnsigned(maxDegree - 2);
                case '/' -> number = MathFunctions.intRandomUnsignedNonZero(maxNumber);
                default -> number = MathFunctions.intRandomUnsigned(maxNumber);
            }
            expression.append(number);
        }
        return expression.toString();
    }

    @Override
    protected TaskSolution solve() {
        ArrayList<String> steps = new ArrayList<>();
        int stepNum = 1;
        Object[] rpn = MathFunctions.getRPN(condition.getExpression(), operationsWithPriorities);
        Stack<Fraction> stack = new Stack<>();
        for (Object o : rpn) {
            Fraction fraction;
            if (o instanceof Fraction) {
                fraction = (Fraction) o;
            } else if (o instanceof Character) {
                Fraction[] operands = new Fraction[2];
                for (int j = 0; j < operands.length; j++) {
                    if (!stack.empty())
                        operands[j] = stack.pop();
                    else
                        throw new IllegalArgumentException("Неверное выражение: %s".formatted(condition.getExpression()));
                }
                fraction = calculateNewValue(operands[1], operands[0], (Character) o);
                steps.add("%d. %s %s %s = %s".formatted(
                                stepNum++,
                                operands[1].getString(),
                                (Character) o,
                                operands[0].getString(),
                                fraction.getString()
                        )
                );
            } else
                throw new IllegalArgumentException("Неверное выражение: %s".formatted(condition.getExpression()));
            stack.push(fraction);
        }

        Fraction result;
        if(stack.size() == 1)
            result = stack.pop();
        else
            throw new IllegalArgumentException("Неверное выражение: %s".formatted(condition.getExpression()));
        return new TaskSolution(String.join("\n", steps), result.getString());
    }

    private static Fraction calculateNewValue(Fraction fstOperand, Fraction sndOperand, char operator)
            throws IllegalArgumentException
    {
        return switch (operator) {
            case '+' -> fstOperand.add(sndOperand);
            case '-' -> fstOperand.sub(sndOperand);
            case '*' -> fstOperand.mult(sndOperand);
            case '^' -> {
                double num = sndOperand.toDouble();
                if (Math.abs(num - (int)num) > 10e-6)
                    throw new IllegalArgumentException("Возведение числа в не целую степень: %s ^ %s".formatted(
                            fstOperand.getString(),
                            sndOperand.getString())
                    );
                yield fstOperand.pow((int)sndOperand.toDouble());
            }
            case '/' -> {
                if (Objects.equals(sndOperand.getString(), "0"))
                    throw new IllegalArgumentException("Деление на ноль: %s / %s".formatted(
                            fstOperand.getString(), sndOperand.getString()));
                yield fstOperand.div(sndOperand);
            }
            default -> throw new IllegalArgumentException("Неверный оператор: %c".formatted(operator));
        };
    }
}

