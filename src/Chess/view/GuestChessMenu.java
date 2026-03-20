package Chess.view;

import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GuestChessMenu {
    private static GuestChessMenu guestChessMenu;

    private String enemyId;
    private JSONObject jsonLogin;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner sc;
    private int turn;

    private JSONObject requestJson = new JSONObject();
    private MultiChessBoard multiChessBoard;

    private GuestChessMenu(JSONObject jsonLogin, PrintWriter out, BufferedReader in, Scanner sc) {
        this.jsonLogin = jsonLogin;
        this.out = out;
        this.in = in;
        this.sc = sc;
    }

    public static synchronized GuestChessMenu getInstance(JSONObject jsonLogin, PrintWriter out, BufferedReader in, Scanner sc){
        if (guestChessMenu == null) {
            guestChessMenu = new GuestChessMenu(jsonLogin,out,in,sc);
        }
        return guestChessMenu;
    }

    public void findGame() {
        while (true){
            System.out.println("들어갈 방의 [유저 이름 : 방 이름]을 입력하세요.");
            System.out.println("이전으로 돌아갈려면 exit");
            System.out.println("========= 열린 방 목록(유저 이름 : 방 이름) =========");

            requestJson.put("type", "FIND");
            requestJson.put("strategy", "multi");
            out(requestJson);
            resultAllRead();
            String nameOfRoom = sc.nextLine();
            if(nameOfRoom.equals("exit")){
                return;
            }else {
                requestJson.put("type","JOIN");
                requestJson.put("strategy", "multi");
                requestJson.put("participant",(String)(jsonLogin.get("id")));
                requestJson.put("nameOfRoom",nameOfRoom);
                out(requestJson);
                String result = resultAllRead();
                if(!result.equals("잘 못 입력하셨습니다. 다시 입력해주세요.")){
                    while (true){
                        System.out.println("게임을 시작할려면 start를 입력 나가실려면 exit를 입력해주세요.");
                        System.out.print("입력 = ");
                        String start = sc.nextLine().toLowerCase();
                        switch (start){
                            case "start" : joinGame(nameOfRoom);
                            case "exit" : return;
                            default:
                                System.out.println("잘 못 입력하셨습니다. 나가실려면 exit, 시작은 start");
                        }
                    }
                }
            }
        }
    }

    public void joinGame(String nameOfRoom) {
        String nameRoom = nameOfRoom.split(" : ")[1];
        System.out.println("상대방 아이디 : "+ enemyId);
        System.out.println("방이름 : "+ nameRoom);

        String start = sc.nextLine();
        multiChessBoard = new MultiChessBoard(out, in, sc, turn, enemyId, jsonLogin);
        multiChessBoard.display();
    }

    private void out(JSONObject json) {
        if (out != null) {
            out.println(json.toJSONString());
            json.clear();
        } else {
            System.out.println("서버와 연결이 되어있지 않습니다.");
        }
    }

    private String resultAllRead() {
        String serverMessage = null;

        try {
            while (true) {

                serverMessage = in.readLine();

                if (serverMessage == null || serverMessage.isEmpty()) {
                    break;
                } else if (serverMessage.startsWith("FIND")) {
                    serverMessage = serverMessage.substring(4);
                } else if (serverMessage.startsWith("ENEMY↯")) {
                    String turn = serverMessage.split("↯", 3)[1];
                    enemyId = serverMessage.split("↯", 3)[2];
                    this.turn = Integer.parseInt(turn);
                } else {
                    System.out.println(serverMessage);
                }

                // ★ 입력이 준비되지 않았으면 반복문 탈출 ★
                if (!in.ready()) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serverMessage;
    }

}