package Chess.run;

import Chess.view.NonLoginChessClient;

public class Run {
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try {
            NonLoginChessClient menu = new NonLoginChessClient(SERVER_ADDRESS, SERVER_PORT);
            menu.mainMenu();
        } catch (Exception e) {
            System.out.println("서버 연결 실패");
            e.printStackTrace();
        }
    }
}
