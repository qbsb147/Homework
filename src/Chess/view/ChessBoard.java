package Chess.view;

import Chess.controller.ChessController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChessBoard {
    private Scanner sc = new Scanner(System.in);
    private ChessController chessController = new ChessController();

    public void display(){
        explain();
    }

    public void explain(){
        System.out.println();
        System.out.println("============체스 게임에 온것을 환영합니다============");
        System.out.println();
        System.out.println("먼저 움직일 기물의 위치를 입력한 다음 (ex. A1)");
        System.out.println();
        System.out.println("내가 이동할 위치를 입력해주세요 (ex. A3)");
        System.out.println();
        System.out.println("흑 기물 먼저 시작하겠습니다.");
        System.out.println();
        System.out.print("아무거나 입력해서 계속 진행 : ");
        String next = sc.nextLine();

        String tmp = command("b");
        while (true) {
            tmp = command(tmp);
        }
    }

    public String command(String tmp) {

        System.out.println("============" + (tmp.equals("b") ? "흑" : "백") + " 기물의 차례입니다.============");
        System.out.print("당신이 움직일 기물을 입력하세요(" + (tmp.equals("b") ? "ex) b1R1d" : "ex) w1R1w") + " : ");
        String piece = sc.next();
        sc.nextLine();
        boolean inputCheck = chessController.inputCheck(piece,tmp);

        while (!inputCheck) {
            System.out.println("잘 못 입력하셨습니다.");
            System.out.print("당신이 움직일 기물을 입력하세요(" + (tmp.equals("b") ? "ex) b1R1d" : "ex) w1R1w") + " : ");
            piece = sc.next();
            sc.nextLine();
            inputCheck = chessController.inputCheck(piece,tmp);
        }

        System.out.print("당신이 이동할 위치를 지정해주세요(ex. 1A) : ");
        String move = sc.next().toUpperCase();
        sc.nextLine();
        inputCheck = chessController.inputCheck(move,tmp);

        while (!inputCheck) {
            System.out.println("잘 못 입력하셨습니다.");
            System.out.print("당신이 이동할 위치를 지정해주세요(ex. 1A) : ");
            move = sc.next();
            sc.nextLine();
            if(piece.charAt(0)==move.charAt(0)&&piece.charAt(1)==move.charAt(1))
                move = "--";
            inputCheck = chessController.inputCheck(move,tmp);
        }

        chessController.movePosition(piece, move);

        return tmp.equals('b') ? "w" : "b";
    }
}
