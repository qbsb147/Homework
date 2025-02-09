package Chess.config.connection;

import Chess.view.OfflineChessMenu;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChessClient {
    private JSONParser parser = new JSONParser();
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);
    private JSONObject responseJson = null;

    public ChessClient() {
    }

    public ChessClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            new OfflineChessMenu(out, in, sc).mainMenu();

            new Thread(new IncomingReader()).start();

        } catch (IOException e) {
            System.out.println("서버 연결 실패: " + e.getMessage());
            e.printStackTrace();
            // 연결 실패 시 자원 해제
            closeResources();

        }finally{
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void closeResources() {
        try {
            if (socket != null) socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    if(serverMessage.startsWith("{")) {
                        responseJson = (JSONObject) parser.parse(serverMessage);
                    }/*else if(serverMessage.startsWith("FIND")){
                        String message = serverMessage.substring(4);
                        System.out.println(message);
                    }else if(serverMessage.startsWith("JOINMASTER↯")){
                        String userName = serverMessage.split("↯",2)[1];

                    }else{
                        System.out.println(serverMessage);
                    }*/
                }
            } catch (IOException | ParseException e) {
                System.out.println("서버 연결 종료.");
            }
        }
    }
    protected void out(JSONObject json) {
        if (out != null) {
            out.println(json.toJSONString());
            json.clear();

        } else {
            System.out.println("서버와 연결이 되어있지 않습니다.");
        }
    }

    protected void out(String content) {
        if (out != null) {
            out.println(content);
        } else {
            System.out.println("서버와 연결이 되어있지 않습니다.");
        }
    }

    public void nextTurn(){
        System.out.println("========= 다음으로 넘어갈려면 아무거나 입력 =========");
        String next = sc.nextLine();
    }

}