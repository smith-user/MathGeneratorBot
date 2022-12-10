package MathGeneratorBot.tasksGenerator.MathAPI.wolframAlphaAPI;

import MathGeneratorBot.appContext.AppContext;
import MathGeneratorBot.appContext.AppProperties;
import MathGeneratorBot.tasksGenerator.MathAPI.APIQueryException;
import MathGeneratorBot.tasksGenerator.MathAPI.MathAPI;
import MathGeneratorBot.tasksGenerator.TasksGenerator;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;

public class WolframAlphaAPI implements MathAPI {
    private static WolframAlphaAPI alphaAPI;
    private final WAEngine engine;
    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());
    private WolframAlphaAPI() {
        engine = new WAEngine();
        engine.setAppID(getAppId());
        engine.addFormat("plaintext");
        engine.addPodState("Result__Step-by-step solution");
    }

    private static String getAppId() {
        ApplicationContext ctx = AppContext.getApplicationContext();;
        AppProperties properties = ctx.getBean(AppProperties.class);
        return properties.getProperty(AppProperties.PropertyNames.WOLFRAM_API_ID);
    }

    public static WolframAlphaAPI instance() {
        if (alphaAPI == null)
            alphaAPI = new WolframAlphaAPI();
        return alphaAPI;
    }

    @Override
    public HashMap<String, ArrayList<String>> performQuery(String request) throws APIQueryException {
        logger.traceEntry("input={}", request);
        WAQuery query = engine.createQuery();
        query.setInput("solve " + request);
        try {
            WAQueryResult queryResult = engine.performQuery(query);
            if (queryResult.isError()) {
                throw logger.throwing(new APIQueryException(("""
                        WolframAlphaAPI Query Exception
                          error code: %d
                          error message:  %s""").formatted(
                                  queryResult.getErrorCode(),
                                    queryResult.getErrorMessage())
                ));
            } else if (!queryResult.isSuccess()) {
                throw logger.throwing(new APIQueryException("Query was not understood; no results available."));
            } else {
                // Got a result.
                return logger.traceExit(getContent(queryResult));
            }
        } catch (WAException e) {
            throw logger.throwing(new APIQueryException(e));
        }
    }

    /**
     * Парсинг api-запроса.
     * @param queryResult результат api-запроса.
     * @return словарь, где ключ - название фрагмента ответа, значение - содержимое ответа.
     */
    private HashMap<String, ArrayList<String>> getContent(WAQueryResult queryResult) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        for (WAPod pod : queryResult.getPods()) {
            if (!pod.isError()) {
                String podTitle = pod.getTitle();
                ArrayList<String> subpods = getSubpods(pod);
                result.put(podTitle, subpods);
            }
        }
        return result;
    }

    /**
     * Получение содержимого фрагмента ответа на api-запрос.
     * @param pod фрагмент ответа на api-запрос.
     * @return массив строк - сожержимое фрагмента.
     */
    private ArrayList<String> getSubpods(WAPod pod) {
        ArrayList<String> subpods = new ArrayList<>();
        for (WASubpod subpod : pod.getSubpods()) {
            for (Object element : subpod.getContents()) {
                if (element instanceof WAPlainText) {
                    subpods.add(((WAPlainText) element).getText());
                }
            }
        }
        return subpods;
    }
}
