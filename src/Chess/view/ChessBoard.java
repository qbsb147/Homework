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
        progress();
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
    }

    public void progress(){
        String tmp = command("b");
        while (true) {
            tmp = command(tmp);
            switch (tmp){
                case "W":whiteWin();
                break;
                case "B":blackWin();
                break;
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
            command(tmp);
        }
        if (!result.equals("M"))return result;

        return tmp.equals("b") ? "w" : "b";
    }

    public void whiteWin(){
        System.out.println("============백팀의 승리입니다!============");
    }

    public void blackWin(){
        System.out.println("============흑팀의 승리입니다!============");
    }
}
