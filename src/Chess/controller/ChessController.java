package Chess.controller;

import Chess.model.vo.Player;
import Chess.service.ChessService;
import Chess.view.ChessMenu;

public class ChessController {

    private ChessService chessService = new ChessService();

    public boolean inputCheck(String input, String tmp, String method) {
        return chessService.inputCheck(input, tmp, method);
    }

    public String move(String piece, String move) {
        String result = "";
        boolean movable = chessService.movable(piece, move);
        if (movable) {
            result = chessService.move(piece, move);
            chessService.record();
        }
        return result;
    }

    public void updateRecord(Player player, String victory){
        int result = chessService.updateRecord(player, victory);
        if (result > 0) {
            new ChessMenu().displaySucccess("최신 기록 업데이트");
        }else{
            new ChessMenu().displayFail("업데이트 실패");
        }
    }
}
