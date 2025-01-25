package Chess.controller;

import Chess.service.ChessService;

public class ChessController {
    private ChessService cs  = new ChessService();
    public boolean check(String input, int order){
        boolean check;
        if(order==0) check = cs.piecesCheck(input);
        else if(order==1) check = cs.moveCheck(input);
        else check=false;
        return  check;
    }
}
