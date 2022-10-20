package bot;

import handler.Handler;

import java.util.Scanner;

public class ConsoleBot {

    private static final Scanner in = new Scanner(System.in);
    public void processCommands() {

        String response;
        Handler handler = new Handler();
        while (true) {
            String userInput = in.nextLine();
            System.out.println(userInput);
        }
    }
}
