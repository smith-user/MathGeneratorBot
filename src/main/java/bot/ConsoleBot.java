package bot;

import handler.QueryHandler;

import java.util.Scanner;

public class ConsoleBot {

    private static final Scanner in = new Scanner(System.in);

    public void run() {
        String response;
        QueryHandler handler = new QueryHandler();
        while (true) {
            String userInput = in.nextLine();
            response = handler.getResponse(userInput);
            System.out.println(response);
        }
    }
}
