package Chess.view;

import Chess.controller.ChessController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OfflineChessMenu {
    private ChessController chessController = null;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner sc;
    private JSONObject jsonLogin;
    private JSONObject requestJson = new JSONObject();
    private JSONObject responseJson = null;
    private JSONParser parser = new JSONParser();
    private JSONObject jsonMap = null;
    private JSONArray jsonArray = null;


    public OfflineChessMenu() {
    }

    public OfflineChessMenu(PrintWriter out, BufferedReader in, Scanner sc) {
        this.out = out;
        this.in = in;
        this.sc = sc;
    }

    public void mainMenu(){
        while (true) {
            System.out.println("========== 체스 게임 시작하기 ==========");
            System.out.println("******* 메인 메뉴 *******");
            System.out.println("1. "+ ((jsonLogin==null||((String)(jsonLogin.get("id")))==null) ? "Player 로그인" : "회원 페이지로 이동"));
            System.out.println("2. Player 회원 등록");
            System.out.println("3. 오프라인 게임하기");
            System.out.println("4. Player 기록");
            System.out.println((jsonLogin!=null&&((String)(jsonLogin.get("id")))!=null ? "5. Player 로그아웃" : ""));
            System.out.println("9. 종료");
            System.out.print("메뉴 번호 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    if (jsonLogin == null||((String)(jsonLogin.get("id")))==null) playerLogin();
                    else new OnlineChessMenu(jsonLogin, out, in, sc).playerLoginMenu();
                    break;
                case 2: playerJoin();
                    break;
                case 3: soloPlay();
                    break;
                case 4: playerRecord();
                    break;
                case 5: if(jsonLogin != null&&((String)(jsonLogin.get("id")))!=null){
                    jsonLogin=null;
                }else{
                    System.out.println("잘 못 입력하였습니다. 다시 입력해주세요");
                }
                    break;
                case 9:
                    System.out.println("프로그램 종료.");
                    return;
                default:
                    System.out.println("잘 못 입력하였습니다. 다시 입력해주세요");
            }
        }
    }

    public void playerLogin(){
        System.out.println("========== 로그인 화면 ==========");
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String pwd = sc.nextLine();

        requestJson.put("strategy", "player");
        requestJson.put("type", "login");
        requestJson.put("id", id);
        requestJson.put("pwd", pwd);
        out(requestJson);
        resultLogin();
    }

    public void playerJoin(){
        System.out.println("========== 회원가입 ==========");
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();
        requestJson.put("strategy", "player");
        requestJson.put("type","checkId");
        requestJson.put("id",id);
        out(requestJson);
        resultPrint();

        if((jsonMap.get("status")).equals("success")){
            playerJoin();
            return;
        }
        String pwd;
        while (true) {
            String pwdCheck;
            System.out.print("비밀번호를 입력하세요 : ");
            pwd = sc.nextLine();
            System.out.print("비밀번호를 다시 입력하세요 : ");
            pwdCheck = sc.nextLine();
            if(pwd.equals(pwdCheck))break;
            System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요!");
        }

        System.out.print("이름을 입력하세요 : ");
        String name = sc.nextLine();
        System.out.print("나이를 입력하세요 : ");
        int age = sc.nextInt();
        sc.nextLine();
        String gender;
        while (true) {
            System.out.print("성별을 입력하세요(M/F) : ");
            gender = sc.nextLine().toUpperCase().substring(0,1);
            if(gender.equals("M")||gender.equals("F"))break;
            System.out.println("성별을 잘 못 입력하셨습니다.");
        }

        System.out.print("이메일을 입력하세요 : ");
        String email = sc.nextLine();
        System.out.print("휴대전화를 입력하세요 : ");
        String phone = sc.nextLine();
        System.out.println("개인정보 수집에 동의하시겠습니까?(y/n) : ");
        char agree = sc.nextLine().toUpperCase().charAt(0);
        while (true){
            if(agree == 'Y') {
                System.out.println("개인정보 수집에 동의하셨습니다.");
                break;
            }else if(agree == 'N'){
                System.out.println("회원가입이 취소되었습니다.");
                return;
            }
            System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
        }
        requestJson.put("strategy", "player");
        requestJson.put("type","playerJoin");
        requestJson.put("id",id);
        requestJson.put("pwd",pwd);
        requestJson.put("name",name);
        requestJson.put("age",age);
        requestJson.put("gender",gender);
        requestJson.put("email",email);
        requestJson.put("phone",phone);
        out(requestJson);
        resultPrint();
    }

    public void playerRecord(){
        while (true) {
            System.out.println("========= 나의 기록 보기 =========");
            System.out.println("1. 나의 게임 기록 조회");
            System.out.println("2. 나의 승률 확인하기");
            System.out.println("3. 나가기");
            System.out.print("메뉴 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 :
                    recordlist(0);
                    break;
                case 2 :
                    myScore();
                    return;
                case 3 :
                    return;
                default:
                    System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요");
            }
        }
    }

    public void recordlist(int page) {
        while(true){
            Long userno = (jsonLogin != null&&((Long)(jsonLogin.get("userNo")))!=null) ? (Long)(jsonLogin.get("userNo")): null;
            requestJson.put("strategy", "player");
            requestJson.put("type", "inquiry");
            requestJson.put("userNo", userno);
            requestJson.put("page", page);

            out(requestJson);
            resultList();
            displayRecordList();
            if(jsonArray==null&&page>0){
                recordlist(page - 1);
                return;
            }

            System.out.println("확인하고 싶은 나의 지난 게임 번호를 입력하세요.(현재 페이지 : "+(page+1)+")");
            System.out.println("이전으로 돌아가고 싶으면 exit, 다음 페이지는(없으면 현재 페이지 유지) next, 이전 페이지는 prev");
            System.out.print("입력 : ");
            String choice = sc.nextLine();
            if(choice.equals("exit")){
                return;
            } else if (choice.equals("next")) {
                    recordlist(page + 1);
                    return;
            } else if (choice.equals("prev")) {
                if(page>0){
                    recordlist(page - 1);
                    return;
                }else{
                    System.out.println("이전 페이지가 없습니다.");
                }
            } else {
                String numbers = "0" + choice.replaceAll("[^0-9]", "");
                int num = Integer.parseInt(numbers);
                if(num>=1&& num <= jsonArray.size()){
                    confirmRecord(num-1);
                    chessController = null;
                }else{
                    System.out.println("잘 못 입력하셨습니다.");
                }
            }
        }
    }

    public void displayRecordList() {
        System.out.println("========= 나의 플레이 목록 =========");
        if(jsonArray!=null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                System.out.println("순번 : [ " + (i + 1) + " ]" +
                        ", 플레이어 아이디 : < " + (
                        ((String) ((JSONObject) (jsonArray.get(i))).get("id")) != null
                                ? ((String) ((JSONObject) (jsonArray.get(i))).get("id"))
                                : "오프라인") + " >" +
                        ", 이긴 팀 : " + (((String) ((JSONObject) (jsonArray.get(i))).get("victory")).equals("W")
                        ? "백팀"
                        : "흑팀"));
            }
        }
    }

    public void confirmRecord(int choice){
        System.out.println("\n========= 마지막 장면 =========");
        JSONObject json = (JSONObject) jsonArray.get(choice);
        String position = (String)(json.get("position"));
        chessController = new ChessController();
        chessController.comfirmRecord(position);

        System.out.print("처음부터 확인해보시겠습니까?(y/n) : ");

        char check = sc.nextLine().toLowerCase().charAt(0);
        switch (check){
            case 'y' :
                chessController.turn((String)(((JSONObject)(jsonArray.get(choice))).get("record")));
                break;
            case 'n' :
                return;
            default:
                System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
        }
    }

    public void myScore() {
        Long userno = jsonLogin != null ? (Long)(jsonLogin.get("userNo")) : null;
        requestJson.put("strategy", "player");
        requestJson.put("type", "myScore");
        requestJson.put("userNo", userno);
        out(requestJson);
        resultPrint();
        displayMyScoreSuccess(
                ((Long)(jsonMap.get("total"))).intValue(),
                ((Long)(jsonMap.get("white"))).intValue(),
                ((Long)(jsonMap.get("black"))).intValue(),
                ((Double)(jsonMap.get("whiteRatio"))).floatValue(),
                ((Double)(jsonMap.get("blackRatio"))).floatValue(),
                (String)(jsonMap.get("ratio"))
        );
    }

    public void displayMyScoreSuccess(int total, Integer white, Integer black, Float whiteRatio, Float blackRatio, String ratio) {
        System.out.println("========= " + ((jsonLogin!=null&&jsonLogin.get("name")!=null) ? jsonLogin.get("name") : "플레이어") +"님의 플레이 목록 =========");
        System.out.println("진행한 게임 횟수 : " + total);
        System.out.println("흰색이 이긴 횟수 : " + white);
        System.out.println("블랙이 이긴 횟수 : " + black);
        System.out.printf("흰색이 이긴 비율 : %.2f\n", whiteRatio);
        System.out.printf("블랙이 이긴 비율 : %.2f\n", blackRatio);
        System.out.printf("비율[화이트 : 블랙] = %s\n", ratio);
    }

    public void soloPlay() {
        new ChessBoard(out, in, sc).display(jsonLogin);
    }


    private void out(JSONObject json) {
        if (out != null) {
            out.println(json.toJSONString());
            json.clear();

        } else {
            System.out.println("서버와 연결이 되어있지 않습니다.");
        }
    }

    private void resultLogin() {
        String serverMessage= null;
        try {
            serverMessage = in.readLine();
            responseJson = (JSONObject) parser.parse(serverMessage);
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            jsonLogin = responseJson;

            responseJson=null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void resultPrint() {
        String serverMessage= null;
        try {
            serverMessage = in.readLine();
            responseJson = (JSONObject) parser.parse(serverMessage);
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            jsonMap = responseJson;

            responseJson=null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void resultList() {
        String serverMessage= null;
        try {
            serverMessage = in.readLine();
            responseJson = (JSONObject) parser.parse(serverMessage);
            System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            jsonArray = (JSONArray) responseJson.get("data");

            responseJson=null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
