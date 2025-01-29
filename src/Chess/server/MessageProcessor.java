package Chess.server;

import Chess.controller.PlayerController;
import Chess.model.vo.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.PrintWriter;

public class MessageProcessor {
    private Player player = null;
    private PlayerController playerController = PlayerController.getInstance();

    private void out(PrintWriter out, JSONObject json) {
        out.println(json.toJSONString());
        out.flush();
        json.clear();
    }

    public JSONObject processClientMessage(String message, PrintWriter out) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);
            String type = (String) json.get("type");

            switch (type) {
                case "login" -> {
                    String id = (String) json.get("id");
                    String pwd = (String) json.get("pwd");
                    player = playerController.playerLogin(id, pwd);
                    System.out.println(id+ pwd);

                    if (player != null) {
                        json.put("status", "success");
                        json.put("userNo", player.getUserNo());
                        json.put("id", player.getId());
                        json.put("pwd", player.getPwd());
                        json.put("name", player.getName());
                        json.put("age", player.getAge());
                        json.put("gender", player.getGender());
                        json.put("email", player.getEmail());
                        json.put("phone", player.getPhone());

                        return json;  // JSON 문자열 반환
                    } else {
                        json.clear();
                        json.put("status", "fail");
                        json.put("message", "로그인 실패");
                        return json;
                    }
                }
                default -> {
                    JSONObject response = new JSONObject();
                    response.put("status", "fail");
                    response.put("message", "알 수 없는 요청 타입");
                    return response;
                }
            }

        } catch (ParseException e) {
            JSONObject error = new JSONObject();
            error.put("status", "fail");
            error.put("message", "잘못된 JSON 형식");
            return error.toJSONString();
        }
    }
}
