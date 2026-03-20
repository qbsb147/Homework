package Chess.view;

import Chess.config.connection.ChessClient;
import Chess.controller.ChessController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public class ChessBoard{
    private PrintWriter out;
    private BufferedReader in;
    private Scanner sc;
    private JSONObject jsonLogin = null;
    private JSONObject responseJson = null;
    private JSONObject jsonMap = null;
    private ChessController chessController = new ChessController();
    private JSONParser parser = new JSONParser();

    public ChessBoard(PrintWriter out, BufferedReader in, Scanner sc) {
        this.out = out;
        this.in = in;
        this.sc = sc;
    }

    public void display(JSONObject jsonLogin) {
        this.jsonLogin = jsonLogin;
        chessController.start();
        explain();
        progress();
    }

    public void explain(){
        System.out.println();
        System.out.println("============체스 게임에 온것을 환영합니다============");
        System.out.println();
        System.out.println("먼저 움직일 기물의 위치를 입력한 다음 (ex. 1A)");
        System.out.println();
        System.out.println("내가 이동할 위치를 입력해주세요 (ex. 3A)");
        System.out.println();
        System.out.println("흑 기물 먼저 시작하겠습니다.");
        System.out.println();
    }

    public void progress() {
        String tmp = command("b");
        while (true) {
            tmp = command(tmp);
            switch (tmp){
                case "W":whiteWin();
                    return;
                case "B":blackWin();
                    return;
            }
        }
    }

    public String command(String tmp) {
        System.out.println("============" + (tmp.equals("b") ? "흑" : "백") + " 기물의 차례입니다============");
        System.out.print("당신이 움직일 기물을 입력하세요. (ex. 1A) : ");
        String piece = sc.nextLine();
        piece += "-";
        piece = piece.substring(0, 2).toUpperCase();
        boolean inputCheck = chessController.inputCheck(piece,tmp,"get");

        while (!inputCheck) {
            System.out.println("잘 못 입력하셨습니다.");
            System.out.print("당신이 움직일 기물을 입력하세요. (ex. 1A) : ");
            piece = sc.nextLine();
            piece += "-";
            piece = piece.substring(0, 2).toUpperCase();
            inputCheck = chessController.inputCheck(piece,tmp,"get");
        }

        System.out.print("당신이 이동할 위치를 지정해주세요. (ex. 1A) : ");
        String move = sc.nextLine();
        move += "-";
        move = move.substring(0, 2).toUpperCase();
        if(piece.charAt(0)==move.charAt(0)&&piece.charAt(1)==move.charAt(1))
            move = "--";
        inputCheck = chessController.inputCheck(move,tmp,"set");

        while (!inputCheck) {
            System.out.println("잘 못 입력하셨습니다.");
            System.out.print("당신이 이동할 위치를 지정해주세요. (ex. 1A) : ");
            move = sc.nextLine();
            move += "-";
            move = move.substring(0, 2).toUpperCase();
            if(piece.charAt(0)==move.charAt(0)&&piece.charAt(1)==move.charAt(1))
                move = "--";
            inputCheck = chessController.inputCheck(move,tmp,"set");
        }

        String result = chessController.move(piece, move);
        if(result.equals("")){
            System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
            return command(tmp);
        }

        if (!result.equals("M")){
            Long userNo = jsonLogin!=null&&jsonLogin.get("name")!=null
                    ? (Long)(jsonLogin.get("userNo"))
                    : null;
            JSONObject recordJson = chessController.updateRecord(userNo, result);
            out(recordJson);
            resultPrint();
            return result;
        }

        return tmp.equals("b") ? "w" : "b";
    }

    public void whiteWin(){
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃      🏆 백 팀의 승리! 🏆      ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }

    public void blackWin(){
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃      🏆 흑 팀의 승리! 🏆      ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }

    private void out(JSONObject json) {
        if (out != null) {
            out.println(json.toJSONString());
            json.clear();

        } else {
            System.out.println("서버와 연결이 되어있지 않습니다.");
        }
    }

    private void resultPrint() {
        String serverMessage= null;
        try {
            serverMessage = in.readLine();
            responseJson = (JSONObject) parser.parse(serverMessage);
            if(responseJson.get("status").equals("success")) {
                System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            }else{
                System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            }
            jsonMap = responseJson;

            responseJson=null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
