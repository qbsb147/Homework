package Chess.view;

import Chess.controller.ChessController;

import java.util.HashMap;
import java.util.Scanner;

public class ChessBoard {
    private Scanner sc = new Scanner(System.in);
    private ChessController cc = new ChessController();

    public void display(){
        explain();
    }

    public void showBoard(String[][] position){
        int size = 8;
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        String horizontalLine = "  +" + "-----+".repeat(size);

        System.out.println(horizontalLine);

        for (int row = 0; row < position.length; row++) {
            System.out.print(8-row + " |");

            for (int col = 0; col < position[row].length; col++) {
                if(position[row][col] != null){
                    System.out.print(position[row][col]);
                }else {
                    if ((row + col) % 2 == 0) {
                        System.out.print("     |"); // 빈 칸 (5칸)
                    } else {
                        System.out.print("#####|"); // 검은 칸 (5칸)
                    }
                }
            }

            System.out.println();
            System.out.println(horizontalLine);
        }

        // 아래 알파벳 좌표 출력
        System.out.print("    ");
        for (char column : columns) {
            System.out.print(column + "     ");
        }
        System.out.println();
    }

    public void explain(){
        System.out.println();
        System.out.println();
        System.out.println("============체스 게임에 온것을 환영합니다============");
        System.out.println();
        System.out.println();
        System.out.println("글자가 써져있는 것은 기물이며 '백은 w[]w, 흑은 d[]b'로 감싸여져 있습니다.");
        System.out.println();
        System.out.println();
        System.out.print("설명을 이어서 들을려면 아무거나 입력 : ");
        String next = sc.nextLine();
        System.out.println();
        System.out.println();
        System.out.println("============체스 게임에 온것을 환영합니다============");
        System.out.println();
        System.out.println("'1L1'와 같이 숫자가 적혀있는 것은 각 기물들을 구분하기 위한 것이며");
        System.out.println();
        System.out.println("'P = 폰', 'R = 룩', 'N = 나이트', 'B = 비숍', 'K = 킹', 'Q = 퀸'을 뜻합니다.");
        System.out.println();
        System.out.print("설명을 이어서 들을려면 아무거나 입력 : ");
        String next1 = sc.nextLine();
        System.out.println();
        System.out.println();
        System.out.println("============체스 게임에 온것을 환영합니다============");
        System.out.println();
        System.out.println("먼저 움직일 기물을 입력한 다음 (ex. w1R1w)");
        System.out.println();
        System.out.println("내가 이동할 위치를 입력해주세요 (ex. A3)");
        System.out.println();
        System.out.print("설명을 이어서 들을려면 아무거나 입력 : ");
        String next2 = sc.nextLine();
        System.out.println();
        System.out.println();
        System.out.println("============체스 게임에 온것을 환영합니다============");
        System.out.println();
        System.out.println("이제 흑부터 게임을 시작하겠습니다. 즐겁게 플레이해주세요.");
        System.out.println();
        System.out.print("게임을 시작할려면 아무거나 입력 : ");
        String next3 = sc.nextLine();
    }

    public String command(String tmp, String[][] position) {
        showBoard(position);
        System.out.println("============" + (tmp.equals("b") ? "흑" : "백") + " 기물의 차례입니다.============");
        System.out.print("당신이 움직일 기물을 입력하세요(" + (tmp.equals("b") ? "ex) b1R1d" : "ex) w1R1w") + " : ");
        String input = sc.next();
        sc.nextLine();
        boolean check = cc.check(input,0,tmp);

        while (!check) {
            showBoard(position);
            System.out.println("잘 못 입력하셨습니다.");
            System.out.print("당신이 움직일 기물을 입력하세요(" + (tmp.equals("b") ? "ex) b1R1d" : "ex) w1R1w") + " : ");
            input = sc.next();
            sc.nextLine();
            check = cc.check(input,0,tmp);
        }

        showBoard(position);
        System.out.print("당신이 이동할 위치를 지정해주세요(ex. A1) : ");
        input = sc.next().toUpperCase();
        sc.nextLine();
        check = cc.check(input,1,tmp);

        while (!check) {
            showBoard(position);
            System.out.println("잘 못 입력하셨습니다.");
            System.out.print("당신이 이동할 위치를 지정해주세요(ex. A1) : ");
            input = sc.next();
            sc.nextLine();
            check = cc.check(input,1,tmp);
        }

        return tmp;
    }
}
