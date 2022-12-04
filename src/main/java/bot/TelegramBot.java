package bot;

import handler.HandlerState;
import handler.QueryHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class TelegramBot extends TelegramLongPollingBot {
    private QueryHandler handler = new QueryHandler();
    private final String BOT_NAME = "bot.name";
    private final String BOT_TOKEN = "bot.token";
    Properties properties = new Properties();

    public TelegramBot() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException ignored) {}
    }

    public void run() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getProperty(BOT_NAME);
    }

    @Override
    public String getBotToken() {
        return properties.getProperty(BOT_TOKEN);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.enableMarkdown(true);
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(handler.getResponse(update.getMessage().getText(),
                            Math.toIntExact(update.getMessage().getChatId())));
            message.setReplyMarkup(keyboardInit(handler.getState(Integer.parseInt(String.valueOf(update.getMessage().getChatId())))));
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private ReplyKeyboardMarkup keyboardInit(HandlerState state) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        if (state == HandlerState.COMMAND_WAITING) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("/help"));
            keyboardRow.add(new KeyboardButton("/tasks"));
            keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("/answers"));
            keyboardRow.add(new KeyboardButton("/stat"));
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
        }

        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }
}
