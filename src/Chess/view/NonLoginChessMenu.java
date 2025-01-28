package Chess.view;

import Chess.controller.PlayerController;
import Chess.model.vo.Player;

import java.util.Scanner;

public class NonLoginChessMenu extends ChessMenu {
    public void mainMenu() {
        while (true) {
            System.out.println("========== 체스 게임 시작하기 ==========");
            System.out.println("******* 메인 메뉴 *******");
            System.out.println("1. Player 로그인");
            System.out.println("2. Player 등록");
            System.out.println("3. 오프라인 게임하기");
            System.out.println("4. Player 기록");
            System.out.println("9. 종료");
            System.out.print("메뉴 번호 입력 : ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1: playerLogin();
                    break;
                case 2: playerJoin();
                    break;
                case 3: soloPlay();
                    break;
                case 4: playerRecord();
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

        player = playerController.playerLogin(id, pwd);
        if(player!=null) new LoginChessMenu(player);
        else System.out.println("로그인에 실패했습니다.");
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
        if(agree=='N'){
            System.out.println("회원가입이 취소되었습니다.");
            return;
        }
        playerController.playerJoin(id, pwd, name, age, gender, email, phone);
    }

}