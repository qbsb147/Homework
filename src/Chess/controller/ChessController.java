package Chess.controller;

import Chess.service.ChessService;

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
}
