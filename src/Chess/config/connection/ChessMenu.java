package Chess.config.connection;

import Chess.client.ChessClient;
import Chess.controller.ChessController;
import Chess.controller.PlayerController;
import Chess.model.vo.Player;
import Chess.view.ChessBoard;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChessMenu {
    private ChessClient client;
    JSONParser parser = new JSONParser();
    JSONObject json = new JSONObject();
    private String input;
    protected PrintWriter out;
    protected BufferedReader in;
    private Socket socket;
    protected Scanner sc = new Scanner(System.in);
    protected ChessController chessController;
    protected Player player = null;
    protected PlayerController playerController = PlayerController.getInstance();

    public ChessMenu() {
    }

    public ChessMenu(String serverAddress, int port) {
        try {
            // 소켓 연결
            socket = new Socket(serverAddress, port);

            // 입출력 스트림 설정
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // 서버 메시지 수신을 위한 스레드 시작
            new Thread(new IncomingReader()).start();

            // 연결 확인
            if (socket == null || !socket.isConnected()) {
                throw new IOException("서버 연결 실패");
            }

        } catch (IOException e) {
            System.out.println("서버 연결 실패: " + e.getMessage());
            e.printStackTrace();
            // 연결 실패 시 자원 해제
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    // 서버로부터 받은 메시지 처리
                    if (!serverMessage.startsWith("{")) {
                        System.out.println(serverMessage);
                    }
                }
            } catch (IOException e) {
                System.out.println("서버 연결이 종료되었습니다.");
            }
        }
    }

    protected void out(JSONObject json) {
        if (out != null) {
            out.println(json.toJSONString());
            out.flush();
            json.clear();
        } else {
            System.out.println("서버와 연결이 되어있지 않습니다.");
        }
    }

    public void displaySucccess(String message) {
        System.out.println("\n서비스 요청 성공 : "+message);
    }

    public void displayFail(String message) {
        System.out.println("\n서비스 요청 실패 : "+message);
    }

    public void soloPlay() {
        new ChessBoard().display(player);
    }

    public void displayNoData(String message){
        System.out.println("\n"+message);
    }

    public void nextTurn(){
        System.out.println("========= 다음으로 넘어갈려면 아무거나 입력 =========");
        String next = sc.nextLine();
    }

    protected JSONObject in(BufferedReader in) {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (message != null && message.startsWith("{")) {
                    JSONObject json = (JSONObject) parser.parse(message);
                    String status = (String) json.get("status");
                    if ("success".equals(status)) {
                        return json;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("메시지 처리 오류: " + e.getMessage());
        }
        return null;
    }
}