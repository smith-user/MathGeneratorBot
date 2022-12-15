package MathGeneratorBot.handler;

import MathGeneratorBot.bot.TelegramKeyboard;

public enum CommandType {
    TASKS,
    HELP,
    START,
    ANSWERS,
    STAT,
    SOLVE;

    public static CommandType valueByQuery(String query) {
        if (query.equals("/help") || query.equals(TelegramKeyboard.getCommandButtonName(CommandType.HELP))) {
            return HELP;
        }
        if (query.equals("/tasks") || query.equals(TelegramKeyboard.getCommandButtonName(CommandType.TASKS))) {
            return TASKS;
        }
        if (query.equals("/stat") || query.equals(TelegramKeyboard.getCommandButtonName(CommandType.STAT))) {
            return STAT;
        }
        if (query.equals("/answers") || query.equals(TelegramKeyboard.getCommandButtonName(CommandType.ANSWERS))) {
            return ANSWERS;
        }
        if (query.equals("/solve") || query.equals(TelegramKeyboard.getCommandButtonName(CommandType.SOLVE))) {
            return SOLVE;
        }
        if (query.equals("/start")) {
            return START;
        }
        return null;
    }
}
