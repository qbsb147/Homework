package Chess.controller;

import Chess.model.vo.Player;
import Chess.service.PlayerService;

public class PlayerController {
    private PlayerService cs = new PlayerService();

    public int playerJoin(String id,String pwd,String name,int age,String gender,String email,String phone){
        int result =0;
        Player p = new Player(id, pwd, name, age, gender, email, phone);

        result = cs.playerJoin(p);
        return result;
    }
    public Player playerLogin(String id, String pwd){
        Player player;
        player = cs.playerLogin(id, pwd);
        return player;
    }
    public int updatePlayer(Player p){
        int result = 0;

        result = cs.updatePlayer(p);
        return result;
    }
    public int deletePlayer(Player p){
        int result = 0;
        result = cs.deletePlayer(p);
        return result;
    }

}
