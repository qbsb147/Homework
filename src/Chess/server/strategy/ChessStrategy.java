package Chess.server.strategy;

import Chess.controller.ChessController;
import org.json.simple.JSONObject;

public class ChessStrategy implements Strategy {
    private ChessController chessController = new ChessController();

    private ChessStrategy() {
    }

    private static class ChessStrategyHolder{
        private static final ChessStrategy CHESS_STRATEGY = new ChessStrategy();
    }

    public static ChessStrategy getInstance(){
        return ChessStrategyHolder.CHESS_STRATEGY;
    }

    public JSONObject processClientMessage(JSONObject json) {
        String type = (String) json.get("type");

        switch (type) {
            case "insertLastRow" -> {
                JSONObject response = chessController.insertRecord(json);
                return response;
            }

            default -> {
                JSONObject response = new JSONObject();
                response.put("status", "fail");
                response.put("message", "알 수 없는 요청 타입");
                return response;
            }

        }

    }

}
