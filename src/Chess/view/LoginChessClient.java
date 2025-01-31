package Chess.view;

import Chess.config.connection.ChessClient;
import Chess.controller.ChessController;
import org.json.simple.JSONObject;

import static Chess.run.Run.SERVER_ADDRESS;
import static Chess.run.Run.SERVER_PORT;

public class LoginChessClient extends ChessClient {
    private String starter=null;

    public LoginChessClient(JSONObject jsonLogin) {
        super(SERVER_ADDRESS, SERVER_PORT);
        this.jsonLogin = jsonLogin;
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
                    new NonLoginChessClient().mainMenu();
                default:
                    System.out.println("잘 못 입력하였습니다. 다시 입력해주세요");
            }
        }
    }
    public void updatePlayer(){
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

    public void deletePlayer(){
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
        new NonLoginChessClient(SERVER_ADDRESS, SERVER_PORT).mainMenu();
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

    public void newGame(){
        System.out.print("개설할 방 이름 : ");
        String nameOfRoom = sc.nextLine();
        out("NEW"+((String)jsonLogin.get("id"))+" : "+nameOfRoom);
        System.out.println("새로운 방을 만들었습니다!");
        System.out.println("참여자 기다리는 중...");
        String response = message;

    }

    public void findGame() {
        while (true){
            out("FIND");
            while(message==null){
                System.out.println();
            }
            System.out.println("들어갈 방의 유저 이름 : 방 이름을 입력하세요.");
            System.out.println("이전으로 돌아갈려면 exit");
            System.out.println("========= 열린 방 목록(유저 이름 : 방 이름) =========");
            System.out.println(message);
            System.out.print("입력 = ");
            String nameOfRoom = sc.nextLine();
            if(nameOfRoom.equals("exit")){
                return;
            }else {
                message=null;
                out("JOIN↯"+(String)(jsonLogin.get("id"))+"↯"+nameOfRoom);
                while(message==null){
                    System.out.println();
                }
                String response = message;

                if(response.equals("연결 완료!")){
                    joinGame(nameOfRoom);
                    return;
                }
            }
        }
    }
//↯

    public void joinGame(String nameOfRoom){
        String userId = nameOfRoom.split(" : ")[0];
        String nameRoom = nameOfRoom.split(" : ")[1];
        System.out.println("방 이름 : "+ nameRoom);
        System.out.println("대결 상대 : "+ userId);

        int num = (int)(Math.random()*2);
        if(num==0)starter = (String)(jsonLogin.get("Id"));
        else starter = userId;

        new MultiChessBoard(nameOfRoom, starter).display(jsonLogin);
        out("CONNECT"+nameOfRoom);
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

    public void recordlist(int page){
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

    public void myScore(){
        Long userno = jsonMap != null ? (Long)(jsonMap.get("userNo")) : null;
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
        System.out.println("========= " + ((jsonMap!=null&&jsonMap.get("name")!=null) ? jsonMap.get("name") : "플레이어") +"님의 플레이 목록 =========");
        System.out.println("진행한 게임 횟수 : " + total);
        System.out.println("흰색이 이긴 횟수 : " + white);
        System.out.println("블랙이 이긴 횟수 : " + black);
        System.out.printf("흰색이 이긴 비율 : %.2f\n", whiteRatio);
        System.out.printf("블랙이 이긴 비율 : %.2f\n", blackRatio);
        System.out.printf("비율[화이트 : 블랙] = %s\n", ratio);
    }

}