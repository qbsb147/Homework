package Chess.server;

import Chess.server.strategy.ChessStrategy;
import Chess.server.strategy.MultiStrategy;
import Chess.server.strategy.PlayerStrategy;
import Chess.server.strategy.Strategy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChessServer {
    private static Map<InetAddress, PrintWriter> clients = new HashMap<>();
    private static Map<String, PrintWriter> standByRooms = new HashMap<>();
    private static Map<String, PrintWriter> allRooms = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3600);
        System.out.println("서버 실행 중...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("새로운 클라이언트 연결: " + socket.getInetAddress());
            new ClientHandler(socket).start();
        }
    }

    public static synchronized void broadcastRoom(String message) {
        for (PrintWriter out : standByRooms.values()) {
            out.println(message);
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                clients.put(socket.getInetAddress(), out);

                String input;
                while ((input = in.readLine()) != null) {
                    try {
                        if (input.startsWith("{")) {
                            JSONParser parser = new JSONParser();
                            JSONObject inputJson = (JSONObject) parser.parse(input);
                            Strategy strategy = null;
                            JSONObject response = null;
                                    switch ((String) (inputJson.get("strategy"))) {
                                case "player" -> {
                                    strategy = PlayerStrategy.getInstance();
                                    response = strategy.processClientMessage(inputJson);
                                }
                                case "chess" -> {
                                    strategy = ChessStrategy.getInstance();
                                    response = strategy.processClientMessage(inputJson);
                                }
                                case "multi" ->{
                                    inputProcess(inputJson);
                                }
                            }

                            if (response != null) {
                                out.println(response);
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("메시지 처리 오류: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("클라이언트 연결 종료: " + e.getMessage());
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void inputProcess(JSONObject inputJson){
            String type = (String) inputJson.get("type");

            switch (type){
                case "NEW" ->{
                    String nameOfRoom = ((String)inputJson.get("nameOfRoom"));
                    standByRooms.put(nameOfRoom, out);
                    String id = nameOfRoom.split(" : ")[0];
                    allRooms.put(nameOfRoom, out);
                    allRooms.put(id, out);
                }

                case "EXIT" ->{
                    String nameOfRoom = ((String)inputJson.get("nameOfRoom"));
                    standByRooms.remove(nameOfRoom);
                    String id = nameOfRoom.split(" : ")[0];
                    allRooms.remove(id);
                }

                case "FIND" ->{
                    if(!standByRooms.isEmpty()) {
                        for (String name : standByRooms.keySet()) {
                            out.println(name);
                        }
                        out.println("입력 = ");
                    }else{
                        out.println("방을 찾을 수가 없음");
                    }
                }

                case "JOIN" ->{
                    String nameOfRoom = ((String)inputJson.get("nameOfRoom"));
                    if(standByRooms.containsKey(nameOfRoom)){
                        int turn = (int)(Math.random()*2);
                        String participant = ((String)inputJson.get("participant"));
                        standByRooms.get(nameOfRoom).println("ENEMY↯"+(1-turn)+"↯"+participant);
                        standByRooms.get(nameOfRoom).println("READY↯"+participant + "님이 도전하였습니다.");
                        standByRooms.get(nameOfRoom).println("READY↯게임을 시작하기 위해선 ready를 입력해 대기해주세요.");
                        out.println("ENEMY↯"+turn+"↯"+nameOfRoom.split(" : ")[0]);
                        out.println("게임을 시작하기 위해선 ready를 입력해 대기해주세요.");
                        standByRooms.remove(nameOfRoom);
                    }else{
                        out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
                    }
                }

                case "PLAY" ->{
                    String enemyId = ((String)inputJson.get("enemyId"));
                    allRooms.get(enemyId).println(inputJson);
                }
            }
        }
    }
}
