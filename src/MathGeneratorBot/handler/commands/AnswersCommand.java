package MathGeneratorBot.handler.commands;

import MathGeneratorBot.handler.CommandType;
import MathGeneratorBot.handler.DefaultResponse;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.tasksGenerator.TaskCondition;
import MathGeneratorBot.tasksGenerator.TaskSolution;
import MathGeneratorBot.PDF.PDFAnswersFile;
import com.itextpdf.text.DocumentException;
import MathGeneratorBot.handler.HandlerState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Команда, проверяущая ответы пользователя на задачи и выводящая ответы к этим задачам.
 */
public class AnswersCommand extends TasksCommand {

    private PDFAnswersFile answersFile;
    private final String ANSWERS_FILE_PATH = "target/answers.pdf";
    private static final Logger logger = LogManager.getLogger(AnswersCommand.class.getName());
    public AnswersCommand(JsonStorage storage, HandlerState state, PDFAnswersFile answersFile,
                          LinkedHashMap<Integer, ArrayList<TaskCondition>> tasks,
                          LinkedHashMap<Integer, ArrayList<TaskSolution>> tasksSolution) {
        super(storage,state, tasks, tasksSolution);
        this.answersFile = answersFile;
    }

    @Override
    public String execute(int userId, String arguments) {
        logger.traceEntry("arguments={}, userId={}", arguments, userId);
        if (tasksSolution.get(userId) == null || tasksSolution.get(userId).isEmpty())
            return logger.traceExit(DefaultResponse.NO_TASKS_GENERATED);

        if (state == HandlerState.COMMAND_WAITING) {
            state = state.nextState(CommandType.ANSWERS);
            return logger.traceExit(DefaultResponse.GET_ANSWERS);
        }
        if (state == HandlerState.ANSWER_WAITING) {
            state = state.nextState(CommandType.ANSWERS);
            StringBuilder tmpResponse = new StringBuilder();
            tmpResponse.append("Решены %s из %d задач\n".formatted(rightAnswers(userId, arguments),
                    tasksSolution.get(userId).size()));

            try {
                storage.updateUsersSolvedTasks(userId, rightAnswers(userId, arguments));
            } catch (IOException e) {
                logger.catching(e);
            }

            for (int i = 0; i < getSolutionNumber(userId); i++) {
                tmpResponse.append("*%d)* `%s`\n".formatted(i + 1, tasks.get(userId).get(i).getExpression()))
                        .append("*ответ: *`%s`\n".formatted(tasksSolution.get(userId).get(i).getResult()));
            }
            generateAnswersFile(userId);
            return tmpResponse.toString();
        }
        return null;
    }

    private void generateAnswersFile(int userId) {
        logger.traceEntry("userId={}", userId);
        answersFile.setFilePath(ANSWERS_FILE_PATH);
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < getSolutionNumber(userId); i++) {
            answers.add("%d) %s \n %s".formatted(i+1,
                        tasks.get(userId).get(i).getExpression(),
                        tasksSolution.get(userId).get(i).getSolutionSteps()));
        }
        try {
            answersFile.generate(answers);
        } catch (IOException | DocumentException e) {
            logger.catching(e);
        }
        logger.traceExit("Файл сгенерирован");
    }

    /**
     * @param userId id пользователя
     * @param userAnswers ответы пользователя на сгенерированные задачи
     * @return количество правильных ответов пользователя
     */
    private int rightAnswers(int userId, String userAnswers) {
        if (userAnswers == null)
            return 0;
        String[] answersArray = userAnswers.split(" ");
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
