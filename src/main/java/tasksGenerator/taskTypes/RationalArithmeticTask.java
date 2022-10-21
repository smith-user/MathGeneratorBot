package tasksGenerator.taskTypes;

import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import net.java.jsience.

public class RationalArithmeticTask extends TaskType {

    public RationalArithmeticTask() {

    }

    @Override
    protected void generate() {
        String expression;


        condition = new TaskCondition("Вычислить:", expression);
    }

    @Override
    protected void solve() {

    }

    private int intRandom() {
        double maxNumber = 100;
        return (int) (Math.random() * maxNumber);
    }
}


