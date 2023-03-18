package mathGeneratorBot.bot;

import mathGeneratorBot.appContext.AppContext;
import mathGeneratorBot.appContext.AppProperties;
import mathGeneratorBot.handler.HandlerState;
import mathGeneratorBot.handler.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class TelegramBot extends TelegramLongPollingBot {
    private QueryHandler handler;
    private TelegramKeyboard keyboard;
    private final AppProperties properties;

    public TelegramBot(QueryHandler handler) {
        this.handler = handler;
        ApplicationContext ctx = AppContext.getApplicationContext();
        properties = ctx.getBean(AppProperties.class);
        keyboard = new TelegramKeyboard();
    }

    public void run() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot(this.handler));
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
            message.setReplyMarkup(
                    keyboard.setButtons(handler.getState(Math.toIntExact(update.getMessage().getChatId())))
            );
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
}
