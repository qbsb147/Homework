package Chess.view;

import Chess.controller.PlayerController;
import Chess.model.vo.Player;

import java.util.Scanner;

public class LoginChessMenu extends ChessMenu {
    public LoginChessMenu(Player player) {
        this.player = player;
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
                case 9 :
                    System.out.println("메인화면으로 이동합니다.");
                    new NonLoginChessMenu().mainMenu();
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
        updateMenu(player);
        playerController.updatePlayer(player);
    }

    public void updateMenu(Player player){
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
                        player.setPwd(pwd);
                        System.out.println("비밀번호를 수정했습니다.");
                    }
                    break;
                case 2 :
                    System.out.print("수정할 이름 : ");
                    String name = sc.nextLine();
                    player.setName(name);
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
                    player.setGender(gender);
                    System.out.println("성별이 수정되었습니다.");
                    break;
                case 4 :
                    System.out.print("수정할 나이 : ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    player.setAge(age);
                    System.out.println("나이가 수정되었습니다.");
                    break;
                case 5 :
                    System.out.print("수정할 이메일 : ");
                    String email = sc.nextLine();
                    player.setEmail(email);
                    System.out.println("이메일이 수정되었습니다.");
                    break;
                case 6 :
                    System.out.print("수정할 핸드폰 번호 : ");
                    String phone = sc.nextLine();
                    player.setPhone(phone);
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

        if(!player.getPwd().equals(pwd)){
            System.out.println("비밀번호가 틀립니다.");
            return;
        }
        playerController.deletePlayer(player);
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

}