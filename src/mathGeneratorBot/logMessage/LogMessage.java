package mathGeneratorBot.logMessage;

import org.apache.logging.log4j.message.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

abstract public class LogMessage implements Message {
    protected final Map<String, String> parameters;

    protected LogMessage(Map<String, String> parameters) {
        this.parameters = (parameters == null) ? new HashMap<>() : parameters;
    }

    protected String parametersToString() {
        LinkedList<String> params = new LinkedList<>();
        for(var parameter : parameters.keySet()) {
            params.add("%s: \"%s\"".formatted(parameter, parameters.get(parameter)));
        }
        return String.join("; ", params);
    }

    @Override
    public String getFormat() {
        return "%s %s";
    }
    @Override
    public Object[] getParameters() {
        return parameters.values().toArray(new String[0]);
    }
    abstract public Throwable getThrowable(Throwable e);

}
