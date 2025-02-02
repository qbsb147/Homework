package Chess.server.strategy;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;

public class MultiStrategy implements Strategy {
    private Map<InetAddress, PrintWriter> clients;
    private Map<String, PrintWriter> standByRooms;
    private Map<String, PrintWriter> allRooms;
    private PrintWriter out;
    private BufferedReader in;

    public MultiStrategy(Map<InetAddress, PrintWriter> clients, Map<String, PrintWriter> standByRooms, Map<String, PrintWriter> allRooms, PrintWriter out, BufferedReader in) {
        this.clients = clients;
        this.standByRooms = standByRooms;
        this.allRooms = allRooms;
        this.out = out;
        this.in = in;
    }

    public JSONObject processClientMessage(JSONObject json) {
        String type = (String) json.get("type");

        switch (type) {
            case "newGame" -> {
                out.println("새로운 방을 만들었습니다!");
                out.println("나갈려면 exit를 입력");
                out.println("참여자 기다리는 중...");
            }

            default -> {
                JSONObject response = new JSONObject();
                response.put("status", "fail");
                response.put("message", "알 수 없는 요청 타입");
                return response;
            }

        }
        return null;
    }

}
