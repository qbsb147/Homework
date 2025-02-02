package Chess.run;

import Chess.config.connection.ChessClient;

public class Run {
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 3600;

    public static void main(String[] args) {
        try {
            new ChessClient(SERVER_ADDRESS, SERVER_PORT);
        } catch (Exception e) {
            System.out.println("서버 연결 실패");
            e.printStackTrace();
        }
    }
}
