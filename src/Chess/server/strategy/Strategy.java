package Chess.server.strategy;

import org.json.simple.JSONObject;

public interface Strategy {
    JSONObject processClientMessage(JSONObject json);
}
