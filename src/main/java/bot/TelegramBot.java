package bot;

import handler.QueryHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TelegramBot extends TelegramLongPollingBot {
    private QueryHandler handler = new QueryHandler();
    Properties properties = new Properties();

    public TelegramBot() {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException ignored) {}
    }

    @Override
    public String getBotUsername() {
        return properties.getProperty("bot.name");
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("bot.token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(handler.getResponse(update.getMessage().getText(),
                            Math.toIntExact(update.getMessage().getChatId())));
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
