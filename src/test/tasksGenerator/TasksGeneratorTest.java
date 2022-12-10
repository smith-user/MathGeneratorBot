package test.tasksGenerator;

import static MathGeneratorBot.tasksGenerator.TasksGenerator.MathTaskTypes.*;

import MathGeneratorBot.tasksGenerator.TasksGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TasksGeneratorTest {

    @Test
    void instance() {
        try {
            TasksGenerator generator = TasksGenerator.instance();
            generator.createTaskByType(RATIONAL_ARITHMETIC);
        } catch(Exception e) {
            fail();
        }

    }
}