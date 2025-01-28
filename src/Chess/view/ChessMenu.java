package Chess.view;

import Chess.controller.PlayerController;
import Chess.model.vo.Player;

import java.util.Scanner;

public class ChessMenu {
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

    public void playerRecord() {
    }
}