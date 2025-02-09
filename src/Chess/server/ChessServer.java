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
                            switch ((String) (inputJson.get("strategy"))) {
                                case "player" -> {
                                    strategy = PlayerStrategy.getInstance();
                                }
                                case "chess" -> {
                                    strategy = ChessStrategy.getInstance();
                                }
                            }
                            JSONObject response = strategy.processClientMessage(inputJson);

                            if (response != null) {
                                out.println(response);
                            }
                        }

                        if (input.startsWith("NEW")){
                            input = input.substring(3);
                            standByRooms.put(input, out);
                            String id = input.split(" : ")[0];
                            allRooms.put(input, out);
                            allRooms.put(id, out);
                        }

                        if(input.startsWith("EXIT")){
                            input = input.substring(4);
                            standByRooms.remove(input);
                            String id = input.split(" : ")[0];
                            allRooms.remove(id);
                        }

                        if (input.startsWith("FIND")){
                            if(!standByRooms.isEmpty()) {
                                for (String name : standByRooms.keySet()) {
                                    out.println(name);
                                }
                                out.println("입력 = ");
                            }else{
                                out.println("방을 찾을 수가 없음");
                            }
                        }

                        if (input.startsWith("JOIN")){
                            String userName = input.split("↯",3)[1];
                            String nameOfRoom = input.split("↯",3)[2];
                            standByRooms.get(nameOfRoom).println(userName + "님이 도전하였습니다.");
                            standByRooms.get(nameOfRoom).println("게임을 시작할려면 start를 입력해주세요.");
                            out.println("게임을 시작할려면 start 입력");

                            standByRooms.remove(nameOfRoom);

                        }

                        if (input.startsWith("ENTRY")){
                            if(!standByRooms.isEmpty()) {
                                for (String name : standByRooms.keySet()) {
                                    out.println("ENTRY");
                                    out.println("같이 플레이할려면 start 입력 : ");
                                }
                            }else{
                                out.println("방을 찾을 수가 없음");
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
    }
}
