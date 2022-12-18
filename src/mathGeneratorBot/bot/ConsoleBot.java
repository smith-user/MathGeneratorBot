package mathGeneratorBot.bot;

import mathGeneratorBot.handler.QueryHandler;

import java.util.Scanner;

public class ConsoleBot {
    private static final int CONSOLE_USER_ID = -10;
    private static final Scanner in = new Scanner(System.in);

    private QueryHandler handler;
    /**
     * Метод получает данные от пользователя, отправляет их обработчику {@code Handler}
     * и отправляет ответ пользователю {@code response}
     */
    ConsoleBot(QueryHandler handler) {
        this.handler = handler;
    }
    public void run() {
        String response;

        while (true) {
            String userInput = in.nextLine();
            response = handler.getResponse(userInput, CONSOLE_USER_ID);
            System.out.println(response);
        }
    }
}
