package handler.commands;

import handler.CommandType;
import handler.DefaultResponse;
import handler.HandlerState;
import storage.JsonStorage;
import tasksGenerator.TaskCondition;
import tasksGenerator.TaskSolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AnswersCommand extends TasksCommand {
    public AnswersCommand(JsonStorage storage, HandlerState state,
                          LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                          LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(storage,state, tasks, tasksSolution);
    }

    @Override
    public String execute(int userId, String arguments) {

        if (state == HandlerState.COMMAND_WAITING) {
            state = state.nextState(CommandType.ANSWERS);
            return DefaultResponse.GET_ANSWERS;
        }
        if (state == HandlerState.ANSWER_WAITING) {
            if (tasksSolution.get(userId) == null || tasksSolution.get(userId).isEmpty())
                return DefaultResponse.NO_TASKS_GENERATED;
            StringBuilder tmpResponse = new StringBuilder();

            tmpResponse.append("Решены %s из %d задач\n".formatted(rightAnswers(userId, arguments),
                    tasksSolution.get(userId).size()));

            try {
                storage.updateUsersSolvedTasks(userId, rightAnswers(userId, arguments));
            } catch (IOException ignored) {
            }

            for (int i = 0; i < getSolutionNumber(userId); i++) {
                tmpResponse.append("*%d)* `%s`\n".formatted(i + 1, tasks.get(userId).get(i).getExpression()))
                        .append("*ответ: *`%s`\n".formatted(tasksSolution.get(userId).get(i).getResult()))
                        .append("`%s`\n".formatted(tasksSolution.get(userId).get(i).getSolutionSteps()));
            }
            return tmpResponse.toString();
        }
        return null;
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

    private int getSolutionNumber(int userId) {
        return tasksSolution.get(userId).size();
    }
}
