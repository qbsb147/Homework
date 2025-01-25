package Chess.controller;

import Chess.service.ChessService;

public class ChessController {
    private ChessService cs  = new ChessService();
    public boolean check(String input, int order, String tmp){
        boolean check = false;
        switch (order){
            case 0:
                check = cs.piecesCheck(input, tmp);
                break;
            case 1:
                check = cs.moveCheck(input);
                break;
        }
        return check;
    }
}
