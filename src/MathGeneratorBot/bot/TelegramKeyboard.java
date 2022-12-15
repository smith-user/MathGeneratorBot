package MathGeneratorBot.bot;

import MathGeneratorBot.handler.CommandType;
import MathGeneratorBot.handler.HandlerState;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Map;

public class TelegramKeyboard {
    ReplyKeyboardMarkup keyboard;

    public TelegramKeyboard() {
        keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
    }

    static private final Map<CommandType, String> commandText = Map.of(
            CommandType.HELP, "Нужна помощь",
            CommandType.TASKS, "Хочу задачи",
            CommandType.ANSWERS, "Проверить задачи",
            CommandType.STAT, "Хочу статистику",
            CommandType.SOLVE, "Решить задачу"
    );
    public static String getCommandButtonName(CommandType type) {
        return commandText.get(type);
    }
    public ReplyKeyboardMarkup setButtons(HandlerState state) {
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        if (state == HandlerState.COMMAND_WAITING || state == HandlerState.GIVE_ANSWER_FILE) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton(commandText.get(CommandType.HELP)));
            keyboardRow.add(new KeyboardButton(commandText.get(CommandType.TASKS)));
            keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton(commandText.get(CommandType.ANSWERS)));
            keyboardRow.add(new KeyboardButton(commandText.get(CommandType.STAT)));
            keyboardRow.add(new KeyboardButton(commandText.get(CommandType.SOLVE)));
        } else if (state == HandlerState.TASK_TYPE_WAITING) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("арифметика 2"));
            keyboardRow.add(new KeyboardButton("арифметика 4"));
            keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("уравнения 2"));
            keyboardRow.add(new KeyboardButton("уравнения 4"));
        } else if (state == HandlerState.ANSWER_WAITING) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("введите ответы"));
        } else if (state == HandlerState.USERS_TASK_WAITING) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("введите задачу"));
        }
        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }
}