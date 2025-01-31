package Chess.server.strategy;

import Chess.controller.PlayerController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlayerStrategy implements Strategy {
    private PlayerController playerController = PlayerController.getInstance();

    public JSONObject processClientMessage(JSONObject json) {
        String type = (String) json.get("type");

        switch (type) {

            case "login" -> {
                JSONObject response = playerController.playerLogin(json);
                return response;
            }

            case "checkId"->{
                JSONObject response = playerController.checkId(json);
                return response;
            }

            case "playerJoin"->{
                JSONObject response = playerController.playerJoin(json);
                return response;
            }

            case "inquiry"->{
                JSONObject response = playerController.inquiry(json);
                return response;
            }

            case "myScore"->{
                JSONObject response = playerController.myScore(json);
                return response;
            }

            case "updatePlayer"->{
                JSONObject response = playerController.updatePlayer(json);
                return response;
            }

            case "deletePlayer"->{
                JSONObject response = playerController.deletePlayer(json);
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
