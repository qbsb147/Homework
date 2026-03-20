package Chess.server.strategy;

import Chess.controller.MultiController;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;

public class MultiStrategy implements Strategy {
    private static MultiStrategy multiStrategy;

    private Map<InetAddress, PrintWriter> clients;
    private Map<String, PrintWriter> standByRooms;
    private Map<String, PrintWriter> allRooms;
    private PrintWriter out;
    private BufferedReader in;

    private MultiStrategy(Map<InetAddress, PrintWriter> clients,
                            Map<String, PrintWriter> standByRooms,
                            Map<String, PrintWriter> allRooms,
                            PrintWriter out,
                            BufferedReader in) {
        this.clients = clients;
        this.standByRooms = standByRooms;
        this.allRooms = allRooms;
        this.out = out;
        this.in = in;
    }

    public static synchronized MultiStrategy getInstance(Map<InetAddress, PrintWriter> clients,
                                                           Map<String, PrintWriter> standByRooms,
                                                           Map<String, PrintWriter> allRooms,
                                                           PrintWriter out,
                                                           BufferedReader in){
        if (multiStrategy == null) {
            multiStrategy = new MultiStrategy(clients, standByRooms, allRooms, out, in);
        }
        return multiStrategy;
    }

    private MultiController multiController = MultiController.getInstance(clients,standByRooms,allRooms,out,in);

    @Override
    public String getKey() {
        return "multi";
    }

    public JSONObject processClientMessage(JSONObject json) {
        String type = (String) json.get("type");

        switch (type) {
            case "NEW" ->{
                JSONObject response = multiController.newRoom(json);
                return response;
            }

            case "EXIT" ->{
                JSONObject response = multiController.exitRoom(json);
                return response;
            }

            case "FIND" ->{
                JSONObject response = multiController.findRoom(json);
                return response;
            }

            case "JOIN" ->{
                JSONObject response = multiController.joinRoom(json);
                return response;
            }

            case "PLAY" ->{
                JSONObject response = multiController.playGame(json);
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
