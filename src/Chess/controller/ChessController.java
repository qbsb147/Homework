package Chess.controller;

import Chess.model.vo.Record;
import Chess.service.ChessService;
import Chess.config.connection.ChessClient;
import org.json.simple.JSONObject;

import java.util.*;

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
            new ChessClient().displaySucccess("최신 기록 업데이트");
        }else{
            new ChessClient().displayFail("업데이트 실패");
        }
    }

    public JSONObject insertLastRow(JSONObject json){

        int result = chessService.insertLastRow(
                (Long)(json.get("userNo")),
                (String)(json.get("victory")),
                (String)(json.get("position")),
                (String)(json.get("record"))
        );
        json.clear();
        if (result > 0) {
            json.put("status", "success");
            json.put("message", "서버에 최신 경기 등록 성공");
        }else{
            json.put("status", "fail");
            json.put("message", "서버에 최신 경기 등록 실패");
        }
        return json;
    }

    public JSONObject selectByLast(){
        Record record = chessService.selectByLast();
        if (record!=null) {
            new ChessClient().displaySucccess("서버에 등록할 최신 기록 가져옴");
        }else{
            new ChessClient().displayFail("서버 업데이트 실패");
        }
        JSONObject recordJson = new JSONObject();
        recordJson.put("strategy", "chess");
        recordJson.put("type", "insertLastRow");
        recordJson.put("userNo", record.getUserNo());
        recordJson.put("victory", record.getVictory());
        recordJson.put("position", record.getPosition());
        recordJson.put("record", record.getRecord());
        return recordJson;
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
            new ChessClient().nextTurn();
        }
    }

    public void close(){
        chessService.closeCurrentBoard();
    }
}
