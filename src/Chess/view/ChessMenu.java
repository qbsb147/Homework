package Chess.view;

import Chess.controller.ChessController;
import Chess.controller.PlayerController;
import Chess.model.vo.Player;
import Chess.model.vo.Record;

import java.util.ArrayList;
import java.util.Scanner;

public class ChessMenu {
    protected ChessController chessController;
    protected Scanner sc = new Scanner(System.in);
    protected Player player = null;
    protected PlayerController playerController = PlayerController.getInstance();
    public void displaySucccess(String message) {
        System.out.println("\n서비스 요청 성공 : "+message);
    }
    public void displayFail(String message) {
        System.out.println("\n서비스 요청 실패 : "+message);
    }
    public void soloPlay() {
        new ChessBoard().display(player);
    }

    public void displayNoData(String message){
        System.out.println("\n"+message);
    }

    public void nextTurn(){
        System.out.println("========= 다음으로 넘어갈려면 아무거나 입력 =========");
        String next = sc.nextLine();
    }
}