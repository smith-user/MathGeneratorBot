package handler;

import handler.CommandHandlerException.NoGeneratedTasksException;
import handler.CommandHandlerException.StorageErrorException;
import handler.CommandHandlerException.UnknownCommandException;
import org.junit.jupiter.api.*;
import tasksGenerator.TasksGenerator;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

public class CommandHandlerTest {
    static CommandHandler commandHandler;

    @BeforeEach
    public void init() {
        commandHandler = new CommandHandler();
    }

    @Test
    void testUnknownCommand() {
        String data = "/unknownCommand";
        Throwable thrown = assertThrows(UnknownCommandException.class, () -> {
            commandHandler.processCommand(-10, data, null);
        });
        assertEquals(DefaultResponse.UNKNOWN_COMMAND + ' ' + data, thrown.getMessage());
    }
    @Test
    void testNoGeneratedTasks() {
        Throwable thrown = assertThrows(NoGeneratedTasksException.class, () -> {
            commandHandler.processCommand(-10, "/answers", null);
        });
        assertEquals(DefaultResponse.NO_TASKS_GENERATED, thrown.getMessage());
    }
    @Test
    void testInvalidTaskType() {
        String type = "INVALID_TYPE";
        Throwable thrown = assertThrows(InvalidParameterException.class, () -> {
            commandHandler.processCommand(-10, "/tasks", type);
        });
        assertEquals("Неверный тип задачи: \"%s\"".formatted(type), thrown.getMessage());
    }

    @Test
    void testGenerateTasksByType() {
        String str = null;
        try{
            str = commandHandler.processCommand(-10, "/tasks", "LINEAR_EQUATION 5");
        } catch ( UnknownCommandException | NoGeneratedTasksException |
                InvalidParameterException | StorageErrorException ignored) {}
        String[] arrayStr = str.split("\n");

        for (int i = 0; i < 5; i++) {
            assertEquals("Найдите все корни или убедитесь, что их нет", arrayStr[i].split(":")[0]);
        }
    }

    @Test
    void testAnswers() {
        String str = null;
        try {
            commandHandler.processCommand(-10, "/tasks", "LINEAR_EQUATION 5");
            str = commandHandler.processCommand(-10, "/answers", null);
        } catch (UnknownCommandException | NoGeneratedTasksException |
                 InvalidParameterException | StorageErrorException ignored) {}
        String[] arrayStr = str.split("\n\n");
        for (int i = 0; i < 5; i++) {
            assertEquals(i+1 + ")", arrayStr[i].substring(0, 2));
        }
    }

    @Test
    void testIllegalNumberOfTasks() {
        String illegalNumber = " F";
        Throwable thrown = assertThrows(InvalidParameterException.class, () -> {
            commandHandler.processCommand(-10, "/tasks", "RATIONAL_ARITHMETIC" + illegalNumber);
        });
        assertEquals(DefaultResponse.ILLEGAL_NUMBER_OF_TASKS, thrown.getMessage());
    }

    @Test
    void testGenerateTasks() {
        String str = null;
        try{
            str = commandHandler.processCommand(-10, "/tasks", null);
        } catch ( UnknownCommandException | NoGeneratedTasksException |
                  InvalidParameterException | StorageErrorException ignored) {}
        String[] arrayStr = str.split("\n");
        assertEquals(5, arrayStr.length);
    }
}
