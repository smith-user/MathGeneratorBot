package MathGeneratorBot;


import MathGeneratorBot.bot.TelegramBot;

public class Main {
    public static void main(String[] args) {
        //ConsoleBot bot = new ConsoleBot();
        TelegramBot bot = new TelegramBot();
        bot.run();
    }
}