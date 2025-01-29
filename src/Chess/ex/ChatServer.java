package Chess.ex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ChatServer {
    private static Map<String, PrintWriter> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("서버 실행 중...");

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();
        }
    }
    public static void broadcast(String message) {
        for (PrintWriter out : clients.values()) {
            out.println(message);
        }
    }
    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("사용자명을 입력하세요:");
                username = in.readLine();
                clients.put(username, out);

                System.out.println(username + " 접속 완료!");

                String input;
                while ((input = in.readLine()) != null) {
                    System.out.println("받은 메시지: " + input);
                    broadcast(input);

                    // 메시지 형식: "@상대방ID 메시지"
                    if (input.startsWith("@")) {
                        String[] parts = input.split(" ", 2);
                        String receiver = parts[0].substring(1);
                        String message = parts[1];

                        if (clients.containsKey(receiver)) {
                            clients.get(receiver).println(username + " (귓속말): " + message);
                        } else {
                            out.println("❌ " + receiver + " 님을 찾을 수 없습니다.");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username != null) {
                    clients.remove(username);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
