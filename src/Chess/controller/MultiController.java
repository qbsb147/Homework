package Chess.controller;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;

public class MultiController {

    private static MultiController multiController;

    private Map<InetAddress, PrintWriter> clients;
    private Map<String, PrintWriter> standByRooms;
    private Map<String, PrintWriter> allRooms;
    private PrintWriter out;
    private BufferedReader in;

    private MultiController(Map<InetAddress, PrintWriter> clients,
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

    public static synchronized MultiController getInstance(Map<InetAddress, PrintWriter> clients,
                                              Map<String, PrintWriter> standByRooms,
                                              Map<String, PrintWriter> allRooms,
                                              PrintWriter out,
                                              BufferedReader in){
        if (multiController == null) {
            multiController = new MultiController(clients, standByRooms, allRooms, out, in);
        }
        return multiController;
    }

    public JSONObject newRoom(JSONObject json) {
        String nameOfRoom = (String)json.get("nameOfRoom");
        standByRooms.put(nameOfRoom, out);
        allRooms.put(nameOfRoom, out);
        json.clear();
        return json;
    }

    public JSONObject exitRoom(JSONObject json) {
        String nameOfRoom = (String)json.get("nameOfRoom");
        standByRooms.remove(nameOfRoom);
        String id = nameOfRoom.split(" : ")[0];
        allRooms.remove(id);
        json.clear();
        return json;
    }

    public JSONObject findRoom(JSONObject json) {
        if(!standByRooms.isEmpty()) {
            for (String name : standByRooms.keySet()) {
                out.println(name);
            }
            out.println("입력 = ");
        }else{
            out.println("방을 찾을 수가 없음");
        }

        json.clear();
        return json;
    }

    public JSONObject joinRoom(JSONObject json) {
        String nameOfRoom = (String)json.get("nameOfRoom");
        if(standByRooms.containsKey(nameOfRoom)){
            int turn = (int)(Math.random()*2);
            String participant = (String)json.get("participant");
            standByRooms.get(nameOfRoom).println("ENEMY↯"+(1-turn)+"↯"+participant);
            standByRooms.get(nameOfRoom).println("READY↯"+participant + "님이 도전하였습니다.");
            standByRooms.get(nameOfRoom).println("READY↯게임을 시작하기 위해선 ready를 입력해 대기해주세요.");
            out.println("ENEMY↯"+turn+"↯"+nameOfRoom.split(" : ")[0]);
            out.println("게임을 시작하기 위해선 ready를 입력해 대기해주세요.");
            standByRooms.remove(nameOfRoom);
        }else{
            out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
        }
        json.clear();
        return json;
    }

    public JSONObject playGame(JSONObject json) {
        String enemyId = (String)json.get("enemyId");
        allRooms.get(enemyId).println(json);
        json.clear();
        return json;
    }
}
