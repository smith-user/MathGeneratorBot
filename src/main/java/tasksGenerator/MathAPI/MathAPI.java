package tasksGenerator.MathAPI;

import java.util.ArrayList;
import java.util.HashMap;

public interface MathAPI {
    public HashMap<String, ArrayList<String>> performQuery(String request) throws APIQueryException;
}
