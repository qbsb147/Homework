package Chess.server;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChessServer {
    private static Map<String, PrintWriter> clients = new HashMap<>();

    public static synchronized void broadcast(String message) {
        for (PrintWriter out : clients.values()) {
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

                String input;
                while ((input = in.readLine()) != null) {
                    try {
                        MessageProcessor messageProcessor = new MessageProcessor();
                        JSONObject response = messageProcessor.processClientMessage(input, out);

                        if (response != null) {
                            out.println(response);
                            out.flush();
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
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("서버 실행 중...");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("새로운 클라이언트 연결: " + socket.getInetAddress());
                new ClientHandler(socket).start();
            } catch (IOException e) {
                System.out.println("클라이언트 연결 실패: " + e.getMessage());
            }
        }
    }
}
