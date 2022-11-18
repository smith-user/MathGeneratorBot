import bot.ConsoleBot;
import bot.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        //ConsoleBot bot = new ConsoleBot();
        TelegramBot bot = new TelegramBot();
        bot.run();
    }
}