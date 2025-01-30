package Chess.model.dao;

import Chess.model.vo.Record;
import Chess.model.vo.builder.RecordBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static Chess.common.JDBCTemplate.close;

public class ChessDao {
    private Properties prop = new Properties();

    private ChessDao() {
    }
    private static class ChessDaoHolder{
        private static final ChessDao CHESS_DAO = new ChessDao();
    }

    public static ChessDao getInstance(){
        return ChessDaoHolder.CHESS_DAO;
    }

    public int insertRecord(Connection conn, Long userNo, String victory, String allRecord, String finalPosition){
        int result = 0;
        PreparedStatement pstmt = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql = prop.getProperty("insertRecord");
        try {
            pstmt = conn.prepareStatement(sql);
            if(userNo!=null){
                pstmt.setLong(1, userNo);
            }else{
                pstmt.setNull(1, java.sql.Types.BIGINT);
            }
            pstmt.setString(2, victory);
            pstmt.setString(3, finalPosition);
            pstmt.setString(4, allRecord);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }
        return result;
    }

    public ArrayList<Record> selectRecord(Connection conn, Long userno, int page){
        ArrayList<Record> records = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rset = null;

        try {
            prop.loadFromXML(new FileInputStream("resources/query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sql;

        int pageTop = 10*page+10;
        int pageBottom = 10*page;

        if (userno != null) {
            sql =   "SELECT GAMENO, USERNO, VICTORY, POSITION, RECORD, ID "+
                    "FROM (" +
                    "    SELECT A.*, ROWNUM AS RNUM " +
                    "    FROM SOLO_CHESS_RECORD A " +
                    "    WHERE ROWNUM <= ? " +
                    ") " +
                    "LEFT JOIN PLAYER USING (USERNO) " +
                    "WHERE USERNO= ? AND RNUM > ?";
        }else{
            sql = "SELECT GAMENO, USERNO, VICTORY, POSITION, RECORD, ID "+
                    "FROM (" +
                    "    SELECT A.*, ROWNUM AS RNUM " +
                    "    FROM SOLO_CHESS_RECORD A " +
                    "    WHERE ROWNUM <= ?" +
                    ") " +
                    "LEFT JOIN PLAYER USING (USERNO) " +
                    "WHERE USERNO IS NULL AND RNUM > ?";
        }

        try {
            preparedStatement = conn.prepareStatement(sql);
            if (userno != null) {
                preparedStatement.setLong(1, pageTop);
                preparedStatement.setLong(2, userno);
                preparedStatement.setLong(3, pageBottom);
            }else{
                preparedStatement.setLong(1, pageTop);
                preparedStatement.setLong(2, pageBottom);
            }
            rset = preparedStatement.executeQuery();

            while (rset.next()){
                Record record = new RecordBuilder()
                        .gameNo(rset.getLong("GAMENO"))
                        .id(rset.getString("ID"))
                        .victory(rset.getString("VICTORY"))
                        .position(rset.getString("POSITION"))
                        .record(rset.getString("RECORD"))
                        .build();
                records.add(record);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(preparedStatement);
        }
        return records;
    }

}
