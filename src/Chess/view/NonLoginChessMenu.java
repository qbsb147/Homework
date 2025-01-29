package Chess.view;

import Chess.client.ChessClient;
import Chess.controller.ChessController;
import Chess.model.vo.Player;
import Chess.model.vo.Record;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static Chess.run.Run.SERVER_ADDRESS;
import static Chess.run.Run.SERVER_PORT;

public class NonLoginChessMenu extends ChessMenu {
    public NonLoginChessMenu() {
        super(SERVER_ADDRESS, SERVER_PORT);  // 기본 생성자에서 서버 연결 정보 전달
    }

    public NonLoginChessMenu(String serverAddress, int port) {
        super(serverAddress, port);  // 서버 주소와 포트를 전달받는 생성자
    }

    public void mainMenu() {
        while (true) {
            System.out.println("========== 체스 게임 시작하기 ==========");
            System.out.println("******* 메인 메뉴 *******");
            System.out.println("1. "+ (player==null ? "Player 로그인" : "회원 페이지로 이동"));
            System.out.println("2. Player 회원 등록");
            System.out.println("3. 오프라인 게임하기");
            System.out.println("4. Player 기록");
            System.out.println((player!=null ? "5. Player 로그아웃" : ""));
            System.out.println("9. 종료");
            System.out.print("메뉴 번호 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    if (player == null) playerLogin();
                    break;
                case 2: playerJoin();
                    break;
                case 3: soloPlay();
                    break;
                case 4: playerRecord();
                    break;
                case 5: if(player != null){
                    player=null;
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

        json.put("type", "login");
        json.put("id", id);
        json.put("pwd", pwd);
        JSONObject out1 = out(json);

        if(json!=null) {
            player = new Player((Long) json.get("userNo"),
                    (String) json.get("id"),
                    (String) json.get("pwd"),
                    (String) json.get("name"),
                    (Integer) json.get("age"),
                    (String) json.get("gender"),
                    (String) json.get("email"),
                    (String) json.get("phone"));
        }
        if(player==null) System.out.println("로그인에 실패했습니다.");
    }

    public void playerJoin(){
        System.out.println("========== 회원가입 ==========");
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine().trim();
        Player playerCk = playerController.checkId(id);
        if(playerCk!=null){
            System.out.println("이미 존재하는 아이디입니다. 다시 입력해주세요.");
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
        playerController.playerJoin(id, pwd, name, age, gender, email, phone);
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
                case 1:
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
            Long userno = player != null ? player.getUserNo() : null;
            ArrayList<Record> records = playerController.inquiry(userno, page);
            System.out.println("확인하고 싶은 나의 지난 게임 번호를 입력하세요.(현재 페이지 : "+(page+1)+")");
            System.out.println("이전으로 돌아가고 싶으면 exit, 다음 페이지는 next, 이전 페이지는 prev");
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
                if(num>=1&& num <= records.size()){
                    confirmRecord(num-1, records);
                    chessController = null;
                }else{
                    System.out.println("잘 못 입력하셨습니다.");
                }
            }
        }
    }

    public void displayRecordList(ArrayList<Record> records) {
        System.out.println("========= 나의 플레이 목록 =========");
        for(int i =0; i< records.size(); i++){
            System.out.println("순번 : [ "+ (i+1) +" ]" +
                    ", 플레이어 아이디 : < "+ (records.get(i).getId()!= null ? records.get(i).getId() : "오프라인")+" >" +
                    ", 이긴 팀 : " + (records.get(i).getVictory().equals("W") ? "백팀" : "흑팀"));
        }
    }

    public void confirmRecord(int choice, ArrayList<Record> records){
        System.out.println("\n========= 마지막 장면 =========");
        String position = records.get(choice).getPosition();
        chessController = new ChessController();
        chessController.comfirmRecord(position);

        System.out.print("처음부터 확인해보시겠습니까?(y/n) : ");

        char check = sc.nextLine().toLowerCase().charAt(0);
        switch (check){
            case 'y' :
                chessController.turn(records.get(choice).getRecord());
                break;
            case 'n' :
                return;
            default:
                System.out.println("잘 못 입력하셨습니다. 다시 입력해주세요.");
        }
    }

    public void myScore(){
        Long userno = player != null ? player.getUserNo() : null;
        playerController.myScore(userno);
    }


    public void displayMyScoreSuccess(int total, Integer white, Integer black, Float whiteRatio, Float blackRatio, String gcd) {
        System.out.println("========= " + (player!=null ? player.getName() : "플레이어") +"님의 플레이 목록 =========");
        System.out.println("진행한 게임 횟수 : " + total);
        System.out.println("흰색이 이긴 횟수 : " + white);
        System.out.println("블랙이 이긴 횟수 : " + black);
        System.out.printf("흰색이 이긴 비율 : %.2f\n", whiteRatio);
        System.out.printf("블랙이 이긴 비율 : %.2f\n", blackRatio);
        System.out.printf("비율[화이트 : 블랙] = %s\n", gcd);
    }
}
