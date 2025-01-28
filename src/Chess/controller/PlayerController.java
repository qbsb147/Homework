package Chess.controller;

import Chess.model.vo.Player;
import Chess.model.vo.Record;
import Chess.service.PlayerService;
import Chess.view.ChessMenu;
import Chess.view.NonLoginChessMenu;

import java.util.ArrayList;

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

    public void playerJoin(String id,String pwd,String name,int age,String gender,String email,String phone){
        Player p = new Player(id, pwd, name, age, gender, email, phone);
        int result = playerService.playerJoin(p);
        if (result > 0) {
            new ChessMenu().displaySucccess("회원 가입 성공");
        }else{
            new ChessMenu().displayFail("회원 가입 실패");
        }
    }
    public Player playerLogin(String id, String pwd){
        Player player;
        player = playerService.playerLogin(id, pwd);
        if(player!=null){
            new ChessMenu().displaySucccess("로그인 성공");
        }else{
            new ChessMenu().displayFail("로그인 실패");
        }
        return player;
    }
    public void updatePlayer(Player p){
        int result = playerService.updatePlayer(p);
        if(result>0){
            new ChessMenu().displaySucccess("회원 수정 성공");
        }else{
            new ChessMenu().displayFail("회원 수정 실패");
        }
    }
    public void deletePlayer(Player p){
        int result = playerService.deletePlayer(p);
        if (result > 0) {
            new ChessMenu().displaySucccess("회원 삭제 성공");
        }else{
            new ChessMenu().displayFail("회원 삭제 실패");
        }
    }

    public Player checkId(String id){
        Player player = playerService.checkId(id);
            if (player!= null) {
                return player;
            }
        return null;
    }

    public ArrayList<Record> inquiry(Long userno, int page){
        ArrayList<Record> records = playerService.selectRecord(userno, page);
        if(records.isEmpty()){
            new ChessMenu().displayNoData("게임 기록을 찾지 못했습니다.");
        }else{
            new NonLoginChessMenu().displayRecordList(records);
        }
        return records;
    }

    public void myScore(Long userno){
        ArrayList<Integer> result = playerService.myScore(userno);

        if (!result.isEmpty()){
            Integer total;
            Float whiteRatio = 0f;
            Float blackRatio = 0f;
            Integer white = result.get(0);
            Integer black = result.get(1);
            if(white!=0&&black!=0){
                total = white + black;
                whiteRatio = (float)(white/total*100);
                blackRatio = (float)(black/total*100);
            }else if(white!=null)total=white;
            else if(black!=null)total=black;
            else total = 0;

            new NonLoginChessMenu().displayMyScoreSuccess(total, white, black, whiteRatio, blackRatio);
        }
    }
}
