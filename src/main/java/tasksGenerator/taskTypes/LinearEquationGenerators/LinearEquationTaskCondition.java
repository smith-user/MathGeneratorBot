package tasksGenerator.taskTypes.LinearEquationGenerators;

import tasksGenerator.TaskCondition;
import tasksGenerator.mathClasses.Fraction;

/**
 * Данный класс расширяет и определяет {@code TaskCondition} для задач с линейными уравнениями.
 * Добавлены новые поля: коэффициент при неизвестном и свободный коэффициент в уравнении.
 */
public class LinearEquationTaskCondition extends TaskCondition {

    private final Fraction coefficientAtX;
    private final Fraction freeCoefficient;

    public LinearEquationTaskCondition(Fraction coefficientAtX, Fraction freeCoefficient) {
        super("Найдите все корни или убедитесь, что их нет: ",
                "%sx + %s = 0".formatted(coefficientAtX, freeCoefficient)
        );
        this.coefficientAtX = coefficientAtX;
        this.freeCoefficient = freeCoefficient;
    }

    public Fraction getCoefficientAtX() {
        return coefficientAtX;
    }

    public Fraction getFreeCoefficient() {
        return freeCoefficient;
    }
}
