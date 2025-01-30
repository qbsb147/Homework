package Chess.controller;

import Chess.model.vo.Player;
import Chess.model.vo.Record;
import Chess.model.vo.builder.PlayerBuilder;
import Chess.service.PlayerService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlayerController {

    private PlayerController() {
    }

    private static class PlayerControllerHolder{
        private static final PlayerController PLAYER_CONTROLLER = new PlayerController();
    }

    public static PlayerController getInstance(){
        return PlayerControllerHolder.PLAYER_CONTROLLER;
    }

    private PlayerService playerService = PlayerService.getInstance();

    public JSONObject playerLogin(JSONObject json){
        String id = (String) json.get("id");
        String pwd = (String) json.get("pwd");
        Player player;
        player = playerService.playerLogin(id, pwd);
        json.clear();

        if (player != null) {
            json.put("status", "success");
            json.put("message", "로그인 성공");
            json.put("userNo", player.getUserNo());
            json.put("id", player.getId());
            json.put("pwd", player.getPwd());
            json.put("name", player.getName());
            json.put("age", player.getAge());
            json.put("gender", player.getGender());
            json.put("email", player.getEmail());
            json.put("phone", player.getPhone());
        } else {
            json.put("status", "fail");
            json.put("message", "로그인 실패");
        }
        return json;
    }

    public JSONObject checkId(JSONObject json){
        String id = (String) json.get("id");
        Player player;
        player = playerService.checkId(id);
        json.clear();

        if (player!= null) {
            json.put("status", "success");
            json.put("message", "이미 존재하는 아이디");
            json.put("id", player.getId());
        }else {
            json.put("status", "fail");
            json.put("message", "중복되는 아이디 없음");
        }
        return json;
    }

    public JSONObject playerJoin(JSONObject json){
        String id = (String) json.get("id");
        String pwd = (String) json.get("pwd");
        String name = (String) json.get("name");
        Integer age = ((Long) json.get("age")).intValue();
        String gender = (String) json.get("gender");
        String email = (String) json.get("email");
        String phone = (String) json.get("phone");
        Player player = new PlayerBuilder()
                        .id(id)
                        .pwd(pwd)
                        .name(name)
                        .age(age)
                        .gender(gender)
                        .email(email)
                        .phone(phone)
                        .build();
        json.clear();

        int result = playerService.playerJoin(player);
        if (result > 0) {
            json.put("status", "success");
            json.put("message", "회원 가입 성공");
        }else{
            json.put("status", "fail");
            json.put("message", "회원 가입 실패");
        }

        return json;
    }

    public JSONObject inquiry(JSONObject json){
        Long userno = (Long)(json.get("userNo"));
        Integer page = ((Long) (json.get("page"))).intValue();
        json.clear();

        ArrayList<Record> records = playerService.selectRecord(userno, page);
        ArrayList<JSONObject> jsonList = records.stream().map(record -> {
            JSONObject jsOn = new JSONObject();
            jsOn.put("gameNo", record.getGameNo());
            jsOn.put("id", record.getId());
            jsOn.put("victory", record.getVictory());
            jsOn.put("position", record.getPosition());
            jsOn.put("record", record.getRecord());
            return jsOn;
        }).collect(Collectors.toCollection(ArrayList::new));

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(jsonList);
        JSONObject finalJson = new JSONObject();
        if(!records.isEmpty()){
            finalJson.put("message", "리스트 조회 성공");
            finalJson.put("status", "success");
            finalJson.put("data", jsonArray);
        }else{
            finalJson.put("message", "리스트 조회 실패");
            finalJson.put("status", "fail");
            finalJson.put("date", jsonArray);
        }
        return finalJson;
    }

    public JSONObject myScore(JSONObject json){
        Long userno = (Long)(json.get("userNo"));
        ArrayList<Integer> result = playerService.myScore(userno);
        json.clear();

        if (!result.isEmpty()){
            Integer total;
            Float whiteRatio = 0f;
            Float blackRatio = 0f;
            Integer white = result.get(0);
            Integer black = result.get(1);
            String ratio = white +" : "+black;
            total = white + black;
            if(total!=0){
                int gcd = gcd(white, black);
                whiteRatio = (float)(white/total*100);
                blackRatio = (float)(black/total*100);
                ratio = white/gcd + " : " + black/gcd;
            }else total = 0;

            json.put("status", "success");
            json.put("message", "점수 조회 성공");
            json.put("total", total);
            json.put("white", result.get(0));
            json.put("black", result.get(1));
            json.put("whiteRatio", whiteRatio);
            json.put("blackRatio", blackRatio);
            json.put("ratio", ratio);
        }else{
            json.put("status", "fail");
            json.put("message", "점수 조회 실패");
        }
        return json;
    }
    public JSONObject updatePlayer(JSONObject json){
        Long userNo = (Long) json.get("userNo");
        String id = (String) json.get("id");
        String pwd = (String) json.get("pwd");
        String name = (String) json.get("name");
        Integer age = ((Long) json.get("age")).intValue();
        String gender = (String) json.get("gender");
        String email = (String) json.get("email");
        String phone = (String) json.get("phone");
        Player player = new Player(userNo, id, pwd, name, age, gender, email, phone);
        json.clear();

        int result = playerService.updatePlayer(player);
        if (result > 0) {
            json.put("status", "success");
            json.put("message", "회원 수정 성공");
        }else{
            json.put("status", "fail");
            json.put("message", "회원 수정 실패");
        }
        return json;
    }
    public JSONObject deletePlayer(JSONObject json){
        Long userNo = (Long) json.get("userNo");
        String id = (String) json.get("id");
        String pwd = (String) json.get("pwd");
        String name = (String) json.get("name");
        Integer age = ((Long) json.get("age")).intValue();
        String gender = (String) json.get("gender");
        String email = (String) json.get("email");
        String phone = (String) json.get("phone");
        Player player = new Player(userNo, id, pwd, name, age, gender, email, phone);
        json.clear();
        int result = playerService.deletePlayer(player);
        if (result > 0) {
            json.put("status", "success");
            json.put("message", "회원 삭제 성공");
        }else{
            json.put("status", "fail");
            json.put("message", "회원 삭제 실패");
        }
        return json;
    }

    public int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
