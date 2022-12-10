package MathGeneratorBot.bot;

import MathGeneratorBot.appContext.AppContext;
import MathGeneratorBot.handler.QueryHandler;
import MathGeneratorBot.appContext.AppProperties;
import MathGeneratorBot.handler.HandlerState;

import org.springframework.context.ApplicationContext;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.ArrayList;

public class TelegramBot extends TelegramLongPollingBot {
    private QueryHandler handler = new QueryHandler();
    private final AppProperties properties;

    public TelegramBot() {
        ApplicationContext ctx = AppContext.getApplicationContext();
        properties = ctx.getBean(AppProperties.class);
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
        return properties.getProperty(AppProperties.PropertyNames.BOT_NAME);
    }

    @Override
    public String getBotToken() {
        return properties.getProperty(AppProperties.PropertyNames.BOT_TOKEN);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();

            message.enableMarkdown(true);
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(handler.getResponse(update.getMessage().getText(),
                            Math.toIntExact(update.getMessage().getChatId())));
            message.setReplyMarkup(keyboardInit(handler.getState(Math.toIntExact(update.getMessage().getChatId()))));
            try {
                execute(message);
                if (handler.getState(Math.toIntExact(update.getMessage().getChatId())) == HandlerState.GIVE_ANSWER_FILE) {
                    SendDocument document = new SendDocument();
                    document.setChatId(update.getMessage().getChatId().toString());
                    document.setDocument(new InputFile().setMedia(new File(handler.getAnswersFile().getPath())));
                    execute(document);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * формирует клавиатуру в чате с tg ботом
     * @param state состояние обработчика {@code QueryHandler}
     * @return объект клавиатуры
     */
    private ReplyKeyboardMarkup keyboardInit(HandlerState state) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        if (state == HandlerState.COMMAND_WAITING || state == HandlerState.GIVE_ANSWER_FILE) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("/help"));
            keyboardRow.add(new KeyboardButton("/tasks"));
            keyboardRow = new KeyboardRow();
            keyboardRows.add(keyboardRow);
            keyboardRow.add(new KeyboardButton("/answers"));
            keyboardRow.add(new KeyboardButton("/stat"));
            keyboardRow.add(new KeyboardButton("/solve"));
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
