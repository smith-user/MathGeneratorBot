package user;

public class User {

    private final int id;
    private int solvedTasks;
    private int generatedTasks;

    public User(int userId) {
        this.id = userId;
        solvedTasks = 0;
        generatedTasks = 0;
    }
    public void addSolvedTasks(int solvedTasksNumber) {
        this.solvedTasks += solvedTasksNumber;
    }

    public void addGeneratedTasks(int generatedTasksNumber) {
        this.generatedTasks += generatedTasksNumber;
    }

    public int getSolvedTasks() {
        return this.solvedTasks;
    }

    public int getGeneratedTasks() {
        return this.generatedTasks;
    }
}
