package bot;

import handler.QueryHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
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
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
