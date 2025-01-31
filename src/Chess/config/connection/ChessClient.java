package Chess.config.connection;

import Chess.controller.ChessController;
import Chess.view.ChessBoard;
import Chess.view.LoginChessClient;
import org.json.simple.JSONArray;
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
    protected ChessController chessController;
    protected JSONObject requestJson = new JSONObject();
    protected JSONParser parser = new JSONParser();
    protected PrintWriter out;
    protected BufferedReader in;
    private Socket socket;
    protected Scanner sc = new Scanner(System.in);
    protected JSONObject responseJson = null;
    protected JSONObject jsonMap = null;
    protected JSONObject jsonLogin = null;
    protected JSONArray jsonArray = null;
    protected String message;

    public ChessClient() {
    }

    public ChessClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new IncomingReader()).start();

        } catch (IOException e) {
            System.out.println("서버 연결 실패: " + e.getMessage());
            e.printStackTrace();
            // 연결 실패 시 자원 해제
            closeResources();

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
                    }else if(serverMessage.startsWith("FIND")){
                        String message = serverMessage.substring(4);
                        System.out.println(message);
                    }else if(serverMessage.startsWith("JOINMASTER↯")){
                        String userName = serverMessage.split("↯",2)[1];

                    }else{
                        System.out.println(serverMessage);
                    }
                }
            } catch (IOException | ParseException e) {
                System.out.println("서버 연결 종료.");
            }
        }
    }

    protected void resultLogin() {
        while(responseJson==null){
            System.out.println();
        }
        if(responseJson.get("status").equals("success")) {
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
        }else{
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
        }
        jsonLogin = responseJson;

        responseJson=null;
    }

    protected void resultPrint() {
        while(responseJson==null){System.out.println();}
        if(responseJson.get("status").equals("success")) {
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
        }else{
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
        }
        jsonMap = responseJson;

        responseJson=null;
    }

    protected void resultList() {
        while(responseJson==null){System.out.println();}
        if(responseJson.get("status").equals("success")) {
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
        }else{
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
        }
        jsonArray = (JSONArray) responseJson.get("data");

        responseJson=null;
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

    public void soloPlay() {
        new ChessBoard().display(jsonLogin);
    }

    public void nextTurn(){
        System.out.println("========= 다음으로 넘어갈려면 아무거나 입력 =========");
        String next = sc.nextLine();
    }

}