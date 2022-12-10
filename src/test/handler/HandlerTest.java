package test.handler;

import MathGeneratorBot.handler.DefaultResponse;
import MathGeneratorBot.handler.QueryHandler;
import MathGeneratorBot.handler.commands.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import MathGeneratorBot.storage.JsonStorage;
import MathGeneratorBot.user.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandlerTest {
    QueryHandler handler;
    @BeforeEach
    void init() {
        handler = new QueryHandler();
        handler.getResponse("/start", -1);
    }

    @Test
    void testHelpCommand() {
        HelpCommand helpCommand = new HelpCommand();
        assertEquals(handler.getResponse("/help", -1), helpCommand.execute(-1, null));
    }

    @Test
    void testStatCommand() throws IOException {
        JsonStorage storage = new JsonStorage();
        User user = storage.getUserById(-1);
        assertEquals(handler.getResponse("/stat", -1),
                "Всего вы сгенерировали *%d* задач и решили из них *%d* задач".formatted(
                user.getGeneratedTasks(), user.getSolvedTasks()));
    }

    @Test
    void testTasksCommand() {
        assertEquals(handler.getResponse("/tasks", -1),
                     DefaultResponse.GET_TASK_TYPE);
        assertEquals(handler.getResponse("уравнения 1", -1).split(":")[0],
                "Найдите все корни или убедитесь, что их нет");
    }

    @Test
    void testWrongTasksCommand() {
        assertEquals(handler.getResponse("/tasks", -1),
                DefaultResponse.GET_TASK_TYPE);
        assertEquals(handler.getResponse("wrong input", -1),
                DefaultResponse.ILLEGAL_TYPE_OF_TASKS);
    }

    @Test
    void testAnswersCommand() {
        handler.getResponse("/tasks", -1);
        handler.getResponse("уравнения 1", -1);
        assertEquals(handler.getResponse("/answers", -1),
                DefaultResponse.GET_ANSWERS);
        assertEquals(handler.getResponse("-", -1).split("\n")[0],
                "Решены 0 из 1 задач");
    }

    @Test
    void testWrongAnswersCommand() {
        assertEquals(handler.getResponse("/answers", -1),
                DefaultResponse.NO_TASKS_GENERATED);
    }
}
