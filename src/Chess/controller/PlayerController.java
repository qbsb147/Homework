package Chess.controller;

import Chess.model.vo.Player;
import Chess.service.PlayerService;

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

    public int playerJoin(String id,String pwd,String name,int age,String gender,String email,String phone){
        int result =0;
        Player p = new Player(id, pwd, name, age, gender, email, phone);

        result = playerService.playerJoin(p);
        return result;
    }
    public Player playerLogin(String id, String pwd){
        Player player;
        player = playerService.playerLogin(id, pwd);

        return player;
    }
    public int updatePlayer(Player p){
        int result = 0;

        result = playerService.updatePlayer(p);
        return result;
    }
    public int deletePlayer(Player p){
        int result = 0;
        result = playerService.deletePlayer(p);
        return result;
    }

}
