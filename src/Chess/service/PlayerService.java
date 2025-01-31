package Chess.service;

import Chess.model.dao.ChessDao;
import Chess.model.dao.PlayerDao;
import Chess.model.vo.Player;
import Chess.model.vo.Record;

import java.sql.Connection;
import java.util.ArrayList;

import static Chess.common.JDBCTemplate.*;

public class PlayerService {

    private PlayerService() {
    }

    private static class PlayerServiceHolder{
        private static final PlayerService PLAYER_SERVICE = new PlayerService();
    }

    public static PlayerService getInstance(){
        return PlayerServiceHolder.PLAYER_SERVICE;
    }

    public int playerJoin(Player p) {
        Connection conn = getConnection();
        int result = PlayerDao.getInstance().playerJoin(p, conn);
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
        Player player = PlayerDao.getInstance().playerLogin(id, pwd, conn);
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
        int result = PlayerDao.getInstance().deletePlayer(p, conn);

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
        int result = PlayerDao.getInstance().updatePlayer(p, conn);

        if (result > 0) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return result;
    }

    public Player checkId(String id){
        Connection conn = getConnection();
        Player player = PlayerDao.getInstance().checkId(conn, id);

        if (player!=null) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return player;
    }

    public ArrayList<Chess.model.vo.Record> selectRecord(Long userno, int page){
        Connection conn = getConnection();
        ArrayList<Record> records = ChessDao.getInstance().selectRecord(conn, userno, page);

        if (records!=null) {
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return records;
    }

    public ArrayList<Integer> myScore(Long userno) {
        Connection conn = getConnection();
        ArrayList<Integer> score = PlayerDao.getInstance().myScore(conn, userno);
        if(!score.isEmpty()){
            commit(conn);
        }else{
            rollback(conn);
        }
        close(conn);
        return score;

    }

}
