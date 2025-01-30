package Chess.view;

import Chess.config.connection.ChessClient;
import org.json.simple.JSONObject;

import static Chess.run.Run.SERVER_ADDRESS;
import static Chess.run.Run.SERVER_PORT;

public class LoginChessClient extends ChessClient {
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
            System.out.println("4. 나의 전적");
            System.out.println("5. 회원 정보 수정");
            System.out.println("6. 회원 탈퇴하기");
            System.out.println("7. 회원 정보 조회");
            System.out.println("8. 이전 페이지로 이동");
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
                case 4 : playerRecord();
                    break;
                case 5 : updatePlayer();
                    break;
                case 6 : deletePlayer();
                    break;
                case 7 : myInfo();
                    break;
                case 8 : return;
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

        if(!player.getPwd().equals(pwd)){
            System.out.println("비밀번호가 틀립니다.");
            return;
        }
        requestJson.put("strategy", "player");
        requestJson.put("type", "updatePlayer");
        updateMenu();
        out(requestJson);
        resultLogin();
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
                        requestJson.put("pwd",pwd);
                        System.out.println("비밀번호를 수정했습니다.");
                    }
                    break;
                case 2 :
                    System.out.print("수정할 이름 : ");
                    String name = sc.nextLine();
                    requestJson.put("name",name);
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
                    requestJson.put("gender",gender);
                    System.out.println("성별이 수정되었습니다.");
                    break;
                case 4 :
                    System.out.print("수정할 나이 : ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    requestJson.put("age",age);
                    System.out.println("나이가 수정되었습니다.");
                    break;
                case 5 :
                    System.out.print("수정할 이메일 : ");
                    String email = sc.nextLine();
                    requestJson.put("email",email);
                    System.out.println("이메일이 수정되었습니다.");
                    break;
                case 6 :
                    System.out.print("수정할 핸드폰 번호 : ");
                    String phone = sc.nextLine();
                    requestJson.put("phone",phone);
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

        System.out.print("비밀번호를 입력해주세요.");
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
        requestJson.put("age",((Long)jsonLogin.get("age")).intValue());
        requestJson.put("gender",(String)jsonLogin.get("gender"));
        requestJson.put("email",(String)jsonLogin.get("email"));
        requestJson.put("phone",(String)jsonLogin.get("phone"));
        out(requestJson);
        resultPrint();
    }

    public void myInfo(){
        System.out.print("회원 아이디 : ");
        System.out.println(player.getId());
        System.out.print("회원 이름 : ");
        System.out.println(player.getName());
        System.out.print("회원 성별 : ");
        System.out.println(player.getGender());
        System.out.print("회원 나이 : ");
        System.out.println(player.getAge());
        System.out.print("회원 이메일 : ");
        System.out.println(player.getEmail());
        System.out.print("회원 전화번호 : ");
        System.out.println(player.getPhone());
    }

    public void multiPlay() {

    }

    public void playerRecord(){
        while (true) {
            System.out.println("========= 나의 기록 보기 =========");
            System.out.println("1. 유저의 게임 기록 조회");
            System.out.println("2. 승률 확인하기");
            System.out.print("메뉴 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 :
                    break;
                case 2 :
                    break;
            }
        }
    }

}