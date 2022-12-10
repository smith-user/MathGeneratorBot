package MathGeneratorBot.bot;

import MathGeneratorBot.handler.QueryHandler;

import java.util.Scanner;

public class ConsoleBot {
    private static final int CONSOLE_USER_ID = -10;
    private static final Scanner in = new Scanner(System.in);
    /**
     * Метод получает данные от пользователя, отправляет их обработчику {@code Handler}
     * и отправляет ответ пользователю {@code response}
     */
    public void run() {
        String response;
        QueryHandler handler = new QueryHandler();
        while (true) {
            String userInput = in.nextLine();
            response = handler.getResponse(userInput, CONSOLE_USER_ID);
            System.out.println(response);
        }
    }
}
