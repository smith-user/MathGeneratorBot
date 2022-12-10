package MathGeneratorBot.tasksGenerator;

/**
 * Класс, представлющий собой решение математической задачи,
 * которое состоит из ответа и пошагового решения или пояснения.
 */
abstract public class TaskSolution {
    private final String solutionSteps;
    private final String result;
    private final static String EmptySolutionSteps = "";

    /**
     *
     * @param solutionSteps строка пошагового решения или пояснения решения задачи
     * @param result строка ответа к задаче
     */
    public TaskSolution(String solutionSteps, String result) {
        this.solutionSteps = solutionSteps;
        this.result = result;
    }

    /**
     *
     * @param result строка ответа к задаче
     */
    public TaskSolution(String result) {
        this(EmptySolutionSteps, result);
    }

    /**
     *
     * @return строку пошагового решения или пояснения решения задачи
     */
    public String getSolutionSteps() {
        return solutionSteps;
    }
    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return getReducedString(result, 10);
    }

    private String getReducedString(String input, int length) {
        if (input.length() > length) {
            return "%s <...>".formatted(input.substring(0, length));
        } else
            return input;
    }
}
