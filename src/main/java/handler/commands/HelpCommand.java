package handler.commands;

import handler.Command;
import storage.JsonStorage;
import tasksGenerator.TasksGenerator;

public class HelpCommand extends Command {
    private static StringBuilder help_text = new StringBuilder("""
            Бот может генерировать математические задачи по заданной теме.
            /tasks <type> <number> - генерация задач типа <type> в количестве
            <number> штук.
            /answers - выводит ответы к последним сгенерированным задачам
            
            Возможные типы задач: """);

    public HelpCommand(TasksGenerator generator) {
        super(generator, null, null, null);
    }


    @Override
    public String execute(int userId, String arguments) {
        StringBuilder outputHelpText = new StringBuilder(help_text);
        for (String i : generator.getNamesOfTaskTypes()) {
            outputHelpText.append(" ").append(i).append(",");
        }
        return outputHelpText.toString();
    }
}
