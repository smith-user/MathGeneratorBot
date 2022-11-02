package handler;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class QueryHandlerTest {

    static QueryHandler queryHandler;

    @BeforeAll
    public static void init() {
        queryHandler = new QueryHandler();
    }

    @Test
    void testResponse() {
        String query = "/tasks invalidType 6";
        String type = "invalidType";
        assertEquals("Неверный тип задачи: \"%s\"".formatted(type), queryHandler.getResponse(query,-10));
    }
}
