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

public class OnlineChessMenu {
    private String enemyId;
    private JSONObject jsonLogin;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner sc;
    private int turn;

    private JSONParser parser = new JSONParser();
    private JSONObject requestJson = new JSONObject();
    private JSONObject responseJson = null;
    private JSONObject jsonMap = null;
    private JSONArray jsonArray = null;
    private ChessController chessController = null;
    private MultiChessBoard multiChessBoard;



    public OnlineChessMenu(JSONObject jsonLogin, PrintWriter out, BufferedReader in, Scanner sc) {
        this.jsonLogin = jsonLogin;
        this.out = out;
        this.in = in;
        this.sc = sc;
    }

    public void playerLoginMenu(){
        while (true) {
            System.out.println("========== 체스 게임 시작하기 ==========");
            System.out.println("******* 메인 메뉴 *******");
            System.out.println("1. 오프라인 게임하기");
            System.out.println("2. 온라인 대결");
            System.out.println("3. Player 기록");
            System.out.println("4. 회원 정보 수정");
            System.out.println("5. 회원 탈퇴하기");
            System.out.println("6. 회원 정보 조회");
            System.out.println("7. 이전 페이지로 이동");
            System.out.println("9. Player 로그아웃");
            System.out.print("메뉴 번호 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 : soloPlay();
                    break;
                case 2 : multiPlay();
                    break;
                case 3 : playerRecord();
                    break;
                case 4 : updatePlayer();
                    break;
                case 5 : deletePlayer();
                    break;
                case 6 : myInfo();
                    break;
                case 7 : return;
                case 9 :
                    System.out.println("메인화면으로 이동합니다.");
                    new OfflineChessMenu().mainMenu();
                default:
                    System.out.println("잘 못 입력하였습니다. 다시 입력해주세요");
            }
        }
    }
    public void updatePlayer() {
        System.out.println("========== 회원 수정 ==========");

        System.out.print("비밀번호를 입력해주세요 : ");
        String pwd = sc.nextLine();

        if(!((String)jsonLogin.get("pwd")).equals(pwd)){
            System.out.println("비밀번호가 틀립니다.");
            return;
        }
        updateMenu();
        JSONObject loginClone = (JSONObject) jsonLogin.clone();
        requestJson = loginClone;
        requestJson.put("strategy", "player");
        requestJson.put("type", "updatePlayer");
        out(requestJson);
        resultPrint();
    }

    public void updateMenu(){
        while (true) {
            System.out.println("========= 수정할 정보를 선택해주세요 =========");
            System.out.println("1. 비밀번호");
            System.out.println("2. 이름");
            System.out.println("3. 성별");
            System.out.println("4. 나이");
            System.out.println("5. 이메일");
            System.out.println("6. 핸드폰 번호");
            System.out.println("9. 정보 수정 끝내기");
            System.out.print("메뉴 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("수정할 비밀번호 : ");
                    String pwd = sc.nextLine();
                    System.out.print("수정할 비밀번호 확인 : ");
                    String pwdCheck = sc.nextLine();
                    if (!pwd.equals(pwdCheck)) {
                        System.out.println("비밀번호가 다릅니다.");
                    }else{
                        jsonLogin.put("pwd",pwd);
                        System.out.println("비밀번호를 수정했습니다.");
                    }
                    break;
                case 2 :
                    System.out.print("수정할 이름 : ");
                    String name = sc.nextLine();
                    jsonLogin.put("name",name);
                    System.out.println("이름이 수정되었습니다.");
                    break;
                case 3 :
                    String gender;
                    while (true) {
                        System.out.print("수정할 성별(M/F) : ");
                        gender = sc.nextLine().toUpperCase().substring(0,1);
                        if(gender.equals("M")||gender.equals("F"))break;
                        System.out.println("성별을 잘 못 입력하셨습니다.");
                    }
                    jsonLogin.put("gender",gender);
                    System.out.println("성별이 수정되었습니다.");
                    break;
                case 4 :
                    System.out.print("수정할 나이 : ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    jsonLogin.put("age",age);
                    System.out.println("나이가 수정되었습니다.");
                    break;
                case 5 :
                    System.out.print("수정할 이메일 : ");
                    String email = sc.nextLine();
                    jsonLogin.put("email",email);
                    System.out.println("이메일이 수정되었습니다.");
                    break;
                case 6 :
                    System.out.print("수정할 핸드폰 번호 : ");
                    String phone = sc.nextLine();
                    jsonLogin.put("phone",phone);
                    System.out.println("핸드폰 번호가 수정되었습니다.");
                    break;

                case 9 :
                    return;
                default:
                    System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    public void deletePlayer() {
        System.out.println("========== 회원 탈퇴 ==========");

        System.out.print("비밀번호를 입력해주세요 : ");
        String pwd = sc.nextLine();

        if(!((jsonLogin.get("pwd"))).equals(pwd)){
            System.out.println("비밀번호가 틀립니다.");
            return;
        }
        requestJson.put("strategy", "player");
        requestJson.put("type", "deletePlayer");
        requestJson.put("userNo",(Long)jsonLogin.get("userNo"));
        requestJson.put("id",(String)jsonLogin.get("id"));
        requestJson.put("pwd",(String)jsonLogin.get("pwd"));
        requestJson.put("name",(String)jsonLogin.get("name"));
        requestJson.put("age",((Long)jsonLogin.get("age")));
        requestJson.put("gender",(String)jsonLogin.get("gender"));
        requestJson.put("email",(String)jsonLogin.get("email"));
        requestJson.put("phone",(String)jsonLogin.get("phone"));
        out(requestJson);
        resultPrint();
        new OfflineChessMenu().mainMenu();
    }

    public void myInfo(){
        System.out.print("회원 아이디 : ");
        System.out.println(jsonLogin.get("id"));
        System.out.print("회원 이름 : ");
        System.out.println(jsonLogin.get("name"));
        System.out.print("회원 성별 : ");
        System.out.println(jsonLogin.get("gender"));
        System.out.print("회원 나이 : ");
        System.out.println(jsonLogin.get("age"));
        System.out.print("회원 이메일 : ");
        System.out.println(jsonLogin.get("email"));
        System.out.print("회원 전화번호 : ");
        System.out.println(jsonLogin.get("phone"));
    }

    public void multiPlay() {
        while (true){
            System.out.println("========= 상대방 찾기 =========");
            System.out.println();
            System.out.println("1. 새로운 방 만들기");
            System.out.println("2. 방 찾기");
            System.out.println("3. 이전으로 돌아가기");
            System.out.print("입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();
            switch (choice){
                case 1 :
                    newGame();
                    break;
                case 2 :
                    findGame();
                    break;
                case 3:
                    System.out.println("이전으로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    public void newGame() {
        System.out.print("개설할 방 이름 : ");
        String room = sc.nextLine();
        requestJson.put("type", "NEW");
        requestJson.put("nameOfRoom", ((String)jsonLogin.get("id"))+" : "+room);
        requestJson.put("strategy", "multi");
        out(requestJson);
        System.out.println("새로운 방을 만들었습니다!");
        System.out.println("나갈려면 exit를 입력");
        System.out.println("참여자 기다리는 중...");
        String input = sc.nextLine().toLowerCase();
        if(input.equals("exit")){
            requestJson.put("type", "EXIT");
            requestJson.put("nameOfRoom", ((String)jsonLogin.get("id"))+" : "+room);
            requestJson.put("strategy", "multi");
            out(requestJson);
            return;
        }
        String res = resultAllRead();
        if(input.equals("ready")){
            joinGame(((String)jsonLogin.get("id"))+" : "+room);
        }
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
        multiChessBoard = new MultiChessBoard(out, in, sc, turn, enemyId,jsonLogin);
        multiChessBoard.display();
    }

//--------------------------------------------------------------------------------------------------

    public void playerRecord() {
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
            Long userno = (jsonLogin != null&&((String)(jsonLogin.get("id")))!=null) ? (Long)(jsonLogin.get("userNo")): null;
            requestJson.put("strategy", "player");
            requestJson.put("type", "inquiry");
            requestJson.put("userNo", userno);
            requestJson.put("page", page);

            out(requestJson);
            resultList();
            displayRecordList();
            if(jsonArray==null){
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

    private void out(String content) {
        if (out != null) {
            out.println(content);
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

    private void resultList() {
        String serverMessage= null;
        try {
            serverMessage = in.readLine();
            responseJson = (JSONObject) parser.parse(serverMessage);
            if(responseJson.get("status").equals("success")) {
                System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            }else{
                System.out.println("\n서비스 요청 결과 : "+responseJson.get("message"));
            }
            jsonArray = (JSONArray) responseJson.get("data");

            responseJson=null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private String resultRead() {
        String serverMessage= null;
        try {
            serverMessage = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serverMessage;
    }

    private String resultAllRead() {
        String serverMessage= null;
        try {
            while (true) {

                serverMessage = in.readLine();

                if (serverMessage == null || serverMessage.isEmpty()) {
                    break;
                } else if (serverMessage.startsWith("FIND")) {
                    serverMessage = serverMessage.substring(4);
                } else if (serverMessage.startsWith("ENEMY↯")) {
                    String turn = serverMessage.split("↯",3)[1];
                    enemyId = serverMessage.split("↯",3)[2];
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