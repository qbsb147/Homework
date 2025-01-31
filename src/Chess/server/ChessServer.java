package Chess.server;

import Chess.server.strategy.ChessStrategy;
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
        ServerSocket serverSocket = new ServerSocket(5000);
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
                        System.out.println(input);
                        if (input.startsWith("{")) {
                            JSONParser parser = new JSONParser();
                            JSONObject inputJson = (JSONObject) parser.parse(input);
                            Strategy strategy = null;
                            switch ((String) (inputJson.get("strategy"))) {
                                case "player" -> {
                                    strategy = new PlayerStrategy();
                                }
                                case "chess" -> {
                                    strategy = new ChessStrategy();
                                }
                            }
                            JSONObject response = strategy.processClientMessage(inputJson);

                            if (response != null) {
                                out.println(response);
                                out.flush();
                            }
                        }

                        if (input.startsWith("NEW")){
                            input = input.substring(3);
                            standByRooms.put(input, out);
                            allRooms.put(input, out);
                        }

                        if (input.startsWith("FIND")){
                            if(!standByRooms.isEmpty()) {
                                for (String name : standByRooms.keySet()) {
                                    System.out.println(name);
                                    out.println(name);
                                }
                            }else{
                                out.println("방을 찾을 수가 없음");
                            }
                        }

                        if (input.startsWith("JOIN")){
                            String nameOfRoom = input.split("↯",3)[2];

                            if (standByRooms.containsKey(nameOfRoom)){
                                if (allRooms.containsKey(nameOfRoom)) {
                                    allRooms.get(nameOfRoom).println(input.split("↯",3)[1] + "님이 접속했습니다.");
                                }
                                out.println("연결 완료!");
                                standByRooms.remove(nameOfRoom);
                            }else{
                                out.println("잘 못 입력하셨습니다.");
                            }
                        }

                        if (input.startsWith("CONNECT")){
                            input = input.substring(7);
                            allRooms.get(input).println(input);
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
    }
}
