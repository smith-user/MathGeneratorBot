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
import java.util.LinkedHashMap;

public class AnswersCommand extends Command {
    public AnswersCommand(LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                          LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(null, null, tasks, tasksSolution);
    }

    @Override
    public String execute(int userId, String arguments) {
        if (tasksSolution.get(userId) == null || tasksSolution.get(userId).isEmpty())
            return DefaultResponse.NO_TASKS_GENERATED;
        StringBuilder tmpResponse = new StringBuilder();

        tmpResponse.append("Решены %s из %d задач\n".formatted(rightAnswers(userId, arguments),
                                                           tasksSolution.get(userId).size()));


        for (int i = 0; i < tasksSolution.get(userId).size(); i++) {
            tmpResponse.append("*")
                    .append(i+1)
                    .append(") ")
                    .append("*")
                    .append("`")
                    .append(tasks.get(userId).get(i).getExpression())
                    .append("`")
                    .append("\n")
                    .append("*")
                    .append("ответ: ")
                    .append("*")
                    .append("`")
                    .append(tasksSolution.get(userId).get(i).getResult())
                    .append("`")
                    .append("\n")
                    .append("`")
                    .append(tasksSolution.get(userId).get(i).getSolutionSteps())
                    .append("` ")
                    .append("\n")
                    .append("\n");
        }
        return tmpResponse.toString();
    }

    private int rightAnswers(int userId, String answers) {
        if (answers == null)
            return 0;
        String[] answersArray = answers.split(" ");
        int answersNumber = 0;
        for (int i = 0; i < answersArray.length; i++) {
            if (answersArray[i].equals(tasksSolution.get(userId).get(i).getResult()))
                answersNumber++;
        }
        return answersNumber;
    }
}
