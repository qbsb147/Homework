package Chess.controller;

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

    public JSONObject updateRecord(Long userNo, String victory){
        ArrayList recordArray = chessService.updateRecord(userNo, victory);

        JSONObject recordJson = new JSONObject();
        recordJson.put("strategy", "chess");
        recordJson.put("type", "insertLastRow");
        recordJson.put("userNo", (Long)recordArray.get(0));
        recordJson.put("victory", (String)recordArray.get(1));
        recordJson.put("position", (String)recordArray.get(2));
        recordJson.put("record", (String)recordArray.get(3));
        return recordJson;
    }

    public JSONObject insertRecord(JSONObject json){

        int result = chessService.insertRecord(
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
