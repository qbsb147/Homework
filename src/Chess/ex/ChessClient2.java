package Chess.ex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChessClient2 {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;

    public ChessClient2(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);

            new Thread(new IncomingReader()).start();

            
            String username = scanner.nextLine();
            out.println(username);

            while (true) {
                String message = scanner.nextLine();
                out.println(message);
                out.flush();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("연결 종료...");
                    break;
                }
            }

            // 종료 처리
            closeResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeResources() {
        try {
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {

            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                System.out.println("서버 연결 종료.");
            }
        }
    }

    public static void main(String[] args) {
        new ChessClient2("localhost", 5000);
    }
}
