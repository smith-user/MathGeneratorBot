package tasksGenerator.taskTypes;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.mathClasses.Fraction;

/**
 * Класс представляющий тип математической задачи
 * на нахождение корней линейного уравнения.
 */
public class LinearEquationTask extends TaskType {

    protected static final int maxNumber = 5;
    protected Fraction coefficientAtX;
    protected Fraction freeCoefficient;

    public LinearEquationTask() {
        coefficientAtX = Fraction.randomSignFraction(maxNumber);
        freeCoefficient = Fraction.randomSignFraction(maxNumber);
        condition = generate();
        solution = solve();
    }

    @Override
    protected TaskCondition generate() {
        return new TaskCondition("Найдите все корни или убедитесь, что их нет: ", "%sx + %s = 0".formatted(coefficientAtX, freeCoefficient));
    }

    @Override
    protected TaskSolution solve() {
        String steps = "";
        freeCoefficient = freeCoefficient.mult(new Fraction(-1));
        steps += "%sx = %s".formatted(coefficientAtX, freeCoefficient);
        if (Math.abs(coefficientAtX.toDouble()) < 10e-6) {
            if (Math.abs(freeCoefficient.toDouble()) < 10e-6)
                return new TaskSolution(steps, "x - любое число");
            else
                return new TaskSolution(steps, "Корней нет");
        }
        Fraction result = freeCoefficient.div(coefficientAtX);
        steps += "\n%s / %s = %s".formatted(freeCoefficient, coefficientAtX, result);
        return new TaskSolution(steps, result.getString());
    }
}
