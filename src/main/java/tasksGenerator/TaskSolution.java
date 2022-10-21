package tasksGenerator;

public class TaskSolution {
    private final String solutionSteps;
    private final String result;

    public TaskSolution(String solutionSteps, String result) {
        this.solutionSteps = solutionSteps;
        this.result = result;
    }

    public TaskSolution(String result) {
        this("", result);
    }

    public String getSolutionSteps() {
        return solutionSteps;
    }
    public String getResult() {
        return result;
    }
}
