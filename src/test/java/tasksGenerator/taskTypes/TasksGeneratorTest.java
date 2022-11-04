package tasksGenerator.taskTypes;

import org.junit.jupiter.api.*;
import tasksGenerator.TasksGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class TasksGeneratorTest {
    static TasksGenerator generator;

    @BeforeAll
    static void init() {
        generator = TasksGenerator.Instance();
    }

    @Test
    void testCreateTaskConditionOfCorrectClass() {
        assertEquals(generator, TasksGenerator.Instance());
    }
}
