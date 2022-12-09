package tasksGenerator.MathAPI.wolframAlphaAPI;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import tasksGenerator.MathAPI.APIQueryException;
import tasksGenerator.MathAPI.MathAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class WolframAlphaAPI implements MathAPI {
    private static WolframAlphaAPI alphaAPI;
    private final WAEngine engine;
    private static final String WOLFRAM_API_PROPERTY = "wolframAPI.token";
    private WolframAlphaAPI() {
        engine = new WAEngine();
        engine.setAppID(getAppId());
        engine.addFormat("plaintext");
        engine.addPodState("Result__Step-by-step solution");
    }

    private static String getAppId() {
        // TODO create class with properties
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException ignored) {}
        return properties.getProperty(WOLFRAM_API_PROPERTY);
    }

    public static WolframAlphaAPI instance() {
        if (alphaAPI == null)
            alphaAPI = new WolframAlphaAPI();
        return alphaAPI;
    }

    @Override
    public HashMap<String, ArrayList<String>> performQuery(String request) throws APIQueryException {
        WAQuery query = engine.createQuery();
        // Set properties of the query.
        query.setInput("solve " + request);
        try {
            WAQueryResult queryResult = engine.performQuery(query);
            if (queryResult.isError()) {
                // TODO Correct creating exceptions
                throw new APIQueryException(("""
                        WolframAlphaAPI Query Exception
                          error code: %d
                          error message:  %s""").formatted(
                                  queryResult.getErrorCode(),
                                    queryResult.getErrorMessage())
                );
            } else if (!queryResult.isSuccess()) {
                throw new APIQueryException("Query was not understood; no results available.");
            } else {
                // Got a result.
                return getContent(queryResult);
            }
        } catch (WAException e) {
            e.printStackTrace();
            throw new APIQueryException();
        }

    }

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
