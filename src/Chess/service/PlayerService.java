package Chess.service;

import Chess.model.dao.PlayerDao;
import Chess.model.vo.Player;

import java.sql.Connection;

import static Chess.common.JDBCTemplate.*;

public class PlayerService {
    public int playerJoin(Player p) {
        Connection conn = getConnection();
        int result = new PlayerDao().playerJoin(p, conn);
        if (result > 0) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public Player playerLogin(String id, String pwd) {
        Connection conn = getConnection();
        Player player = new PlayerDao().playerLogin(id, pwd, conn);
        if (player!=null) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return player;
    }

    public int deletePlayer(Player p){
        Connection conn = getConnection();
        int result = new PlayerDao().deletePlayer(p, conn);

        if (result > 0) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public int updatePlayer(Player p){
        Connection conn = getConnection();
        int result = new PlayerDao().updatePlayer(p, conn);

        if (result > 0) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }

}
