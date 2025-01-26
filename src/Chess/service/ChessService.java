package Chess.service;

import Chess.service.piece.Rook;
import Chess.service.piece.Pawn;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessService extends JFrame {
    private static final int BOARD_SIZE = 8;
    private final Map<String, ImageIcon> pieceImages = new HashMap<>();
    private String[][] position;
    private String pos;
    private JPanel boardPanel;

    public ChessService() {

        this.position = new String[][] {
                { "wRw", "wNw", "wBw", "wQw", "wKw", "wBw", "wNw", "wRw" },
                { "wPw", "wPw", "wPw", "wPw", "wPw", "wPw", "wPw", "wPw" },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { "bPd", "bPd", "bPd", "bPd", "bPd", "bPd", "bPd", "bPd" },
                { "bRd", "bNd", "bBd", "bQd", "bKd", "bBd", "bNd", "bRd" } };

        setTitle("Chess Board with Images");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadPieceImages();// 체스 기물 이미지 로드

        boardPanel = new JPanel(new GridLayout(BOARD_SIZE + 1, BOARD_SIZE + 1));

        // 좌측 상단 빈 칸
        boardPanel.add(new JLabel(""));

        // 가로(A~H) 라벨
        for (char col = 'A'; col <= 'H'; col++) {
            JLabel label = new JLabel(String.valueOf(col), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            boardPanel.add(label);
        }

        // 체스판 생성
        for (int row = 0; row < BOARD_SIZE; row++) {
            // 세로(1~8) 라벨
            JLabel rowLabel = new JLabel(String.valueOf(8 - row), SwingConstants.CENTER);
            rowLabel.setFont(new Font("Arial", Font.BOLD, 16));
            boardPanel.add(rowLabel);

            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel(new BorderLayout());

                // 체스판 색상 설정
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                // 체스 기물 배치
                String piece = position[row][col];
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(pieceImages.get(piece));
                    square.add(pieceLabel, BorderLayout.CENTER);
                }

                boardPanel.add(square);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadPieceImages() {
        String[] pieces = { "wPw", "wRw", "wNw", "wKw", "wQw", "wBw", "bPd", "bNd", "bBd", "bQd", "bKd", "bRd" };
        for (String piece : pieces) {
            pieceImages.put(piece, new ImageIcon("images/" + piece + ".png"));
        }
    }

    public boolean inputCheck(String input, String tmp, String method) {
        if ((input.charAt(0) >= '1'
                && input.charAt(0) <= '8'
                && input.charAt(1) >= 'A'
                && input.charAt(1) <= 'H')) {
            this.pos = input;
        } else
            return false;
        String item = position[7 - (input.charAt(0) - '1')][input.charAt(1) - 'A'];
        if (method.equals("get")) {
            if ((item != null && !item.startsWith(tmp)))
                return false;
        } else {
            if ((item != null && item.startsWith(tmp)))
                return false;
        }
        return true;
    }

    public String[][] setPieces(HashMap<String, int[]> position) {
        String[][] board = new String[8][8];
        HashMap<String, int[]> allPiece = position;
        for (Map.Entry<String, int[]> entry : allPiece.entrySet()) {
            board[entry.getValue()[0]][entry.getValue()[1]] = entry.getKey();
        }
        return board;
    }

    public boolean movable(String piece, String move) {
        String item = position[7 - (piece.charAt(0) - '1')][piece.charAt(1) - 'A'];
        boolean movable = false;
        if (item == null)
            return false;
        switch (item.charAt(1)) {
            case 'P':
                movable = new Pawn().movable(piece, move, position);
                break;
            case 'R':
                movable = new Rook().movable(piece, move, position);
                break;
        }

        return movable;
    }

    public String move(String piece, String move) {
        String prePosition = position[7 - (piece.charAt(0) - '1')][piece.charAt(1) - 'A'];
        String target = position[7 - (move.charAt(0) - '1')][move.charAt(1) - 'A'];
        if (target == "wKw")
            return "B";
        else if (target == "bKd")
            return "W";
        else {
            position[7 - (piece.charAt(0) - '1')][piece.charAt(1) - 'A'] = null;
            position[7 - (move.charAt(0) - '1')][move.charAt(1) - 'A'] = prePosition;

            // GUI 업데이트를 위한 코드 추가
            SwingUtilities.invokeLater(() -> {
                updateBoard();
            });
        }
        return "M";
    }

    private void updateBoard() {
        // 체스판의 모든 칸을 업데이트
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = (JPanel) boardPanel.getComponent((row + 1) * (BOARD_SIZE + 1) + col + 1);
                square.removeAll(); // 기존 기물 제거

                String piece = position[row][col];
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(pieceImages.get(piece));
                    square.add(pieceLabel, BorderLayout.CENTER);
                }

                square.revalidate(); // 레이아웃 재검증
                square.repaint(); // 다시 그리기
            }
        }
    }

}
