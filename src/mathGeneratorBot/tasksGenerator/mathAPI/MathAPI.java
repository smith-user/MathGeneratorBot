package mathGeneratorBot.tasksGenerator.mathAPI;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Интерфейс для взаоимодействия с API для решения математических задач.
 */
public interface MathAPI {
    /**
     * Выполнить запрос.
     * @param request запрос
     * @return ответ на запрос, где ключ - название фрагмента ответа, значение - содержимое этого фрагмента.
     * @throws APIQueryException если при выполнении запроса возникла ошибка.
     */
    public HashMap<String, ArrayList<String>> performQuery(String request) throws APIQueryException;
}
