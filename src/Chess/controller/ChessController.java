package Chess.controller;

import Chess.model.vo.Record;
import Chess.service.ChessService;
import Chess.view.ChessMenu;

import java.util.*;
import java.util.stream.IntStream;

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

    public void comfirmRecord(String position){
        chessService.updateBoard(position);
    }

    public void turn(String record){
        String[] list = record.split(",");
        Queue<String> Qlist = new LinkedList<>();
        Qlist.addAll(List.of(list));
        while(!Qlist.isEmpty()){
            String[] move = Qlist.poll().split(":");
            chessService.move(move[0],move[1]);
            new ChessMenu().nextTurn();
        }
    }

    public void close(){
        chessService.closeCurrentBoard();
    }
}
