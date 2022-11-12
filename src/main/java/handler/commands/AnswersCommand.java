package handler.commands;

import handler.Command;
import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import handler.DefaultResponse;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;
import tasksGenerator.TasksGenerator;

import java.util.ArrayList;

public class AnswersCommand extends Command {
    public AnswersCommand(ArrayList<TaskCondition> tasks, ArrayList<TaskSolution> tasksSolution) {
        super(null, null, tasks, tasksSolution);
    }

    @Override
    public String execute(int userId, String arguments) {
        if (tasksSolution.isEmpty())
            return DefaultResponse.NO_TASKS_GENERATED;
        StringBuilder tmpResponse = new StringBuilder();
        for (int i = 0; i < tasksSolution.size(); i++) {
            tmpResponse.append(i+1)
                    .append(") ")
                    .append(tasks.get(i).getExpression())
                    .append("\n")
                    .append("ответ: ")
                    .append(tasksSolution.get(i).getResult())
                    .append("\n")
                    .append(tasksSolution.get(i).getSolutionSteps())
                    .append("\n")
                    .append("\n");
        }
        return tmpResponse.toString();
    }
}
