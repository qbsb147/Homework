package Chess.server.strategy;

import org.json.simple.JSONObject;

public interface Strategy {
    String getKey();
    JSONObject processClientMessage(JSONObject json);
}
