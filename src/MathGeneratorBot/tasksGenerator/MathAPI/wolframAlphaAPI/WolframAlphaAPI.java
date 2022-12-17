package MathGeneratorBot.tasksGenerator.MathAPI.wolframAlphaAPI;

import MathGeneratorBot.appContext.AppContext;
import MathGeneratorBot.appContext.AppProperties;
import MathGeneratorBot.logMessage.MathAPIMessage;
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static MathGeneratorBot.logMessage.MathAPIMessage.MessageType.*;

final public class WolframAlphaAPI implements MathAPI {
    private static final Logger logger = LogManager.getLogger(TasksGenerator.class.getName());
    private static final WolframAlphaAPI alphaAPI = new WolframAlphaAPI();
    private final WAEngine engine;

    private WolframAlphaAPI() {
        engine = new WAEngine();
        engine.setAppID(getAppId());
        engine.addFormat("plaintext");
        engine.addPodState("Result__Step-by-step solution");
    }

    private static String getAppId() {
        ApplicationContext ctx = AppContext.getApplicationContext();
        AppProperties properties = ctx.getBean(AppProperties.class);
        return properties.getProperty(AppProperties.PropertyNames.WOLFRAM_API_ID);
    }

    public static WolframAlphaAPI instance() {
        return alphaAPI;
    }

    @Override
    public HashMap<String, ArrayList<String>> performQuery(String request) throws APIQueryException {
        logger.traceEntry("input={}", request);
        WAQuery query = engine.createQuery();
        query.setInput("solve " + request);
        String queryInput = query.getInput();
        try {
            //TODO CaptureStream
//            PrintStream out = new PrintStream(new FileOutputStream("target/file.txt"));
//            PrintStream err = new PrintStream(new FileOutputStream("target/file.txt"));
//            System.setOut(out);
//            System.setErr(err);
            logger.info(new MathAPIMessage(PERFORM_QUERY, Map.of("query", queryInput)).getFormattedMessage());
            WAQueryResult queryResult = engine.performQuery(query);
            if (queryResult.isError()) {
                throw logger.throwing(
                        Level.WARN,
                        new MathAPIMessage(QUERY_EXCEPTION, Map.of(
                                "query", queryInput,
                                "error code", String.valueOf(queryResult.getErrorCode()),
                                "error message", queryResult.getErrorMessage())
                        ).getThrowable()
                );
            } else if (!queryResult.isSuccess()) {
                throw logger.throwing(
                        Level.WARN,
                        new MathAPIMessage(QUERY_EXCEPTION, Map.of(
                                "query", queryInput,
                                "message", "Query was not understood; no results available.")
                        ).getThrowable()
                );
            } else {
                logger.info(new MathAPIMessage(RESPONSE_RECEIVED, Map.of("query", query.getInput())).getFormattedMessage());
                return logger.traceExit(getContent(queryResult));
            }
        } catch (WAException e) {
            throw logger.throwing(
                    Level.WARN,
                    new MathAPIMessage(QUERY_EXCEPTION, Map.of("query", queryInput)).getThrowable(e)
            );
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
