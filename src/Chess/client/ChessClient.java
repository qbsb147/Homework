package Chess.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChessClient {
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 5000;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner sc;
    private String receivedMessage;

    public PrintWriter getOut() {
        return out;
    }
    
    public BufferedReader getIn() {
        return in;
    }
    
    public ChessClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            sc = new Scanner(System.in);

            new Thread(new IncomingReader()).start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    if (serverMessage.startsWith("{")) {
                        receivedMessage = serverMessage;
                    } else {
                        System.out.println(serverMessage);
                    }
                }
            } catch (IOException e) {
                System.out.println("서버 연결 종료.");
            }
        }
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void closeResources() {
        try {
            if (socket != null) socket.close();
            if (sc != null) sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
