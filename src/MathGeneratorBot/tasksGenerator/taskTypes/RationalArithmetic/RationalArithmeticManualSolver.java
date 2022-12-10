package MathGeneratorBot.tasksGenerator.taskTypes.RationalArithmetic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import MathGeneratorBot.tasksGenerator.exceptions.TaskSolutionException;
import MathGeneratorBot.tasksGenerator.mathClasses.Fraction;
import MathGeneratorBot.tasksGenerator.mathClasses.MathFunctions;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import static MathGeneratorBot.tasksGenerator.taskTypes.RationalArithmetic.RationalArithmeticTaskSolution.SpecialCases.*;

final public class RationalArithmeticManualSolver extends RationalArithmeticSolver{
    private static final Logger logger = LogManager.getLogger(RationalArithmeticManualSolver.class.getName());
    @Override
    public RationalArithmeticTaskSolution createTaskSolution(RationalArithmeticTaskCondition condition) throws TaskSolutionException {
        logger.traceEntry("{}", condition);
        ArrayList<String> steps = new ArrayList<>();
        int stepNum = 1;
        Object[] rpn;
        try {
            rpn = MathFunctions.getRPN(condition.getExpression(), RationalArithmeticTaskCondition.getOperationsWithPriorities());
        } catch (IllegalArgumentException exc) {
            logger.catching(exc);
            throw logger.throwing(new TaskSolutionException(exc.getMessage()));
        }
        Stack<Fraction> stack = new Stack<>();
        for (Object o : rpn) {
            Fraction fraction;
            if (o instanceof Fraction) {
                fraction = (Fraction) o;
            } else if (o instanceof Character) {
                Fraction[] operands = new Fraction[2];
                for (int j = 0; j < operands.length; j++) {
                    operands[j] = stack.pop();
                }
                try{
                    fraction = calculateNewValue(operands[1], operands[0], (Character) o);
                } catch (IllegalArgumentException exc) {
                    return logger.traceExit(
                            new RationalArithmeticTaskSolution(
                                    String.join("\n", steps),
                                    DIVISION_BY_ZERO, operands[1].getString(), operands[0].getString()));
                }
                steps.add("%d. %s %s %s = %s".formatted(
                                stepNum++,
                                operands[1].getString(),
                                (Character) o,
                                operands[0].getString(),
                                fraction.getString()
                        )
                );
            } else {
                throw logger.throwing(new TaskSolutionException("Неверное выражение: %s".formatted(condition.getExpression())));
            }
            stack.push(fraction);
        }

        String result;
        if(stack.size() == 1)
            result = stack.pop().getString();
        else
            throw logger.throwing(new TaskSolutionException("Неверное выражение: %s".formatted(condition.getExpression())));
        return logger.traceExit(new RationalArithmeticTaskSolution(String.join("\n", steps), result));
    }

    private static Fraction calculateNewValue(Fraction fstOperand, Fraction sndOperand, char operator)
            throws IllegalArgumentException
    {
        return switch (operator) {
            case '+' -> fstOperand.add(sndOperand);
            case '-' -> fstOperand.sub(sndOperand);
            case '*' -> fstOperand.multiplication(sndOperand);
            case '^' -> fstOperand.pow(sndOperand);
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
