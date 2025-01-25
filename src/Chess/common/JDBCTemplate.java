package Chess.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//공통 템플릿(매번 반복적으로 작성될 코드를 메서드로 정의)
public class JDBCTemplate {
    //모든 메서드를 전부 static메서드로 만듬
    //싱글톤 패턴 : 메모리영역에 단 한번만 생성해서 매번 재사용하는 개념

    //1. Connection객체 생성 후 해당 Connection객체 반환
    public static Connection getConnection(){
        Connection conn = null;
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("resources/driver.properties"));
            Class.forName(prop.getProperty("driver"));

            conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    //2. commit처리해주는 메서드(Connection 객체 전달받아 사용)
    public static void commit(Connection conn){
        try {
            if(conn != null && !conn.isClosed())conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //3. rollback처리해주는 메서드(Connection 객체 전달받아 사용)
    public static void rollback(Connection conn){
        try {
            if(conn != null && !conn.isClosed())conn.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //4. Statement관련 객체를 전달받아서 반납시켜주는 메서드
    public static void close(Statement stmt) {
        try {
            if(stmt!=null && !stmt.isClosed()) stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //5. Connection 객체를 전달받아서 반납시켜주는 메서드
    public static void close(Connection conn) {
        try {
            if(conn!=null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //5. ResultSet 객체를 전달받아서 반납시켜주는 메서드
    public static void close(ResultSet rset) {
        try {
            if(rset!=null && !rset.isClosed()) rset.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
