package Chess.model.dao;

import Chess.model.vo.Player;
import Chess.model.vo.builder.PlayerBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static Chess.common.JDBCTemplate.close;

public class PlayerDao {
    private PlayerDao() {
    }
    private static class PlayerDaoHolder{
        private static final PlayerDao PLAYER_DAO = new PlayerDao();
    }

    public static PlayerDao getInstance(){
        return PlayerDaoHolder.PLAYER_DAO;
    }

    private Properties prop = new Properties();

    public int playerJoin(Player p, Connection conn){
        int result = 0;
        PreparedStatement pstmt = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql = prop.getProperty("playerJoin");
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getId());
            pstmt.setString(2, p.getPwd());
            pstmt.setString(3, p.getName());
            pstmt.setString(4, p.getGender());
            pstmt.setInt(5, p.getAge());
            pstmt.setString(6, p.getEmail());
            pstmt.setString(7, p.getPhone());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }
        return result;
    }

    public Player playerLogin(String id, String pwd, Connection conn){
        ResultSet rset = null;
        Player player = null;
        PreparedStatement pstmt = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql = prop.getProperty("playerLogin");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);

            rset = pstmt.executeQuery();

            while (rset.next()){

                player = new Player(
                        rset.getLong("USERNO")
                        ,rset.getString("ID")
                        ,rset.getString("PWD")
                        ,rset.getString("NAME")
                        ,rset.getInt("AGE")
                        ,rset.getString("GENDER")
                        ,rset.getString("EMAIL")
                        ,rset.getString("PHONE")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);
        }
        return player;
    }

    public int deletePlayer(Player m,Connection conn){
        int result = 0;
        PreparedStatement pstmt = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql = prop.getProperty("deletePlayer");
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, m.getUserNo());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }

        return result;
    }

    public int updatePlayer(Player m, Connection conn){
        int result =0;
        PreparedStatement pstmt = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql = prop.getProperty("updatePlayer");
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, m.getPwd());
            pstmt.setString(2, m.getName());
            pstmt.setString(3, m.getGender());
            pstmt.setInt(4, m.getAge());
            pstmt.setString(5, m.getEmail());
            pstmt.setString(6, m.getPhone());
            pstmt.setLong(7, m.getUserNo());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }
        return result;
    }
    public Player checkId(Connection conn, String id){
        ResultSet rset = null;
        Player player = null;
        PreparedStatement pstmt = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql = prop.getProperty("checkId");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            rset = pstmt.executeQuery();

            while (rset.next()){
                player = new PlayerBuilder()
                        .id(rset.getString("ID"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);
        }
        return player;
    }

    public ArrayList<Integer> myScore(Connection conn, Long userno){
        ResultSet rset = null;
        PreparedStatement pstmt = null;
        ArrayList<Integer> list = new ArrayList<>();

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String sql = prop.getProperty("myScore");

        try {
            pstmt = conn.prepareStatement(sql);
            if(userno != null){
                pstmt.setLong(1, userno);
                pstmt.setLong(2, userno);
            }else{
                pstmt.setNull(1, Types.BIGINT);
                pstmt.setNull(2, Types.BIGINT);
            }

            rset = pstmt.executeQuery();
            rset.next();
            list.add(rset.getInt("WHITE"));
            list.add(rset.getInt("BLACK"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }
}
