package Chess.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardWithImages extends JFrame {
    private static final int BOARD_SIZE = 8;
    private static final String[][] position = {
            {"wRw", "wNw", "wBw", "wQw", "wKw", "wBw", "wNw", "wRw"},
            {"wPw", "wPw", "wPw", "wPw", "wPw", "wPw", "wPw", "wPw"},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {"bPd", "bPd", "bPd", "bPd", "bPd", "bPd", "bPd", "bPd"},
            {"bRd", "bNd", "bBd", "bQd", "bKd", "bBd", "bNd", "bRd"}
    };

    private final Map<String, ImageIcon> pieceImages = new HashMap<>();

    public ChessBoardWithImages() {
        setTitle("Chess Board with Images");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadPieceImages(); // 체스 기물 이미지 로드

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE + 1, BOARD_SIZE + 1));

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
        String[] pieces = {"wPw", "wRw", "wNw", "wKw", "wQw", "wBw", "bPd", "bNd", "bBd", "bQd", "bKd", "bRd"};
        for (String piece : pieces) {
            pieceImages.put(piece, new ImageIcon("images/" + piece + ".png"));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessBoardWithImages::new);
    }
}
