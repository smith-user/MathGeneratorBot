package handler.commands;

/**
 * Команада, возвращающая {@code help_text}
 */
public class HelpCommand extends Command {
    private static String help_text = """
            Бот может генерировать математические задачи по заданной теме.
            `/tasks <type> <number>` - генерация задач типа `<type>` в количестве
            `<number>` штук.
            `/answers` - выводит ответы к последним сгенерированным задачам
                        
            Возможные типы задач: `арифметика`, `уравения`""";

    public HelpCommand() {
        super(null);
    }


    @Override
    public String execute(int userId, String arguments) {
        return help_text;
    }
}
