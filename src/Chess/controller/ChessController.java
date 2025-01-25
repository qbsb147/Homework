package Chess.controller;

import Chess.service.ChessService;

public class ChessController {
    private ChessService chessService = new ChessService();

    public boolean inputCheck(String input, String tmp){
        return chessService.inputCheck(input, tmp);
    }

    public void movePosition(String piece, String move){
        chessService.movePosition(piece, move);
    }

}
