package Chess.model.dao;

import Chess.model.vo.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static Chess.common.JDBCTemplate.close;

public class ChessDao {
    private ChessDao() {
    }
    private static class ChessDaoHolder{
        private static final ChessDao CHESS_DAO = new ChessDao();
    }

    public static ChessDao getInstance(){
        return ChessDaoHolder.CHESS_DAO;
    }


    private Properties prop = new Properties();

    public int insertRecord(Connection conn, Player player, String victory, String allRecord, String finalPosition){
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
        Player p = null;
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
                p = new Player();
                p.setUserNo(rset.getLong("USERNO"));
                p.setId(rset.getString("ID"));
                p.setPwd(rset.getString("PWD"));
                p.setName(rset.getString("NAME"));
                p.setGender(rset.getString("GENDER"));
                p.setAge(rset.getInt("AGE"));
                p.setEmail(rset.getString("EMAIL"));
                p.setPhone(rset.getString("PHONE"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);
        }
        return p;
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

}
