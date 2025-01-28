package Chess.controller;

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
        }
        return result;
    }

    public void updateRecord(Long userNo, String victory){
        int result = chessService.updateRecord(userNo, victory);
        if (result > 0) {
            new ChessMenu().displaySucccess("최신 기록 업데이트");
        }else{
            new ChessMenu().displayFail("업데이트 실패");
        }
    }
}
