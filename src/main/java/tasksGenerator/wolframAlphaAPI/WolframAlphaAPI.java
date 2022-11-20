package tasksGenerator.wolframAlphaAPI;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import java.util.ArrayList;
import java.util.HashMap;

public class WolframAlphaAPI {
    private static WolframAlphaAPI alphaAPI;
    private final WAEngine engine;
    private WolframAlphaAPI() {
        String appId = "22QVAA-J89WPHAPXU"; // TODO get from file
        engine = new WAEngine();
        engine.setAppID(appId);
        engine.addFormat("plaintext");
        engine.addPodState("Result__Step-by-step solution");
    }

    public static WolframAlphaAPI instance() {
        if (alphaAPI == null)
            alphaAPI = new WolframAlphaAPI();
        return alphaAPI;
    }

    public HashMap<String, ArrayList<String>> performQuery(String request) throws APIQueryException{
        WAQuery query = engine.createQuery();

        // Set properties of the query.
        query.setInput("solve " + request);
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        try {
            WAQueryResult queryResult = engine.performQuery(query);
            if (queryResult.isError()) {
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
                for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()) {
                        String podTitle = pod.getTitle();
                        ArrayList<String> subpods = new ArrayList<>();
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    subpods.add(((WAPlainText) element).getText());
                                }
                            }
                        }
                        result.put(podTitle, subpods);
                    }
                }
            }
        } catch (WAException e) {
            e.printStackTrace();
        }
        return result;
    }
}
