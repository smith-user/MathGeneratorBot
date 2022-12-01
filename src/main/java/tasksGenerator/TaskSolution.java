package tasksGenerator;

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
}
