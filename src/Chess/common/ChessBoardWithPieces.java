package Chess.common;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardWithPieces extends JFrame {
    private static final int BOARD_SIZE = 8;
    private static final String IMAGE_PATH = "pieces/"; // 체스 기물 이미지 폴더

    public ChessBoardWithPieces() {
        setTitle("Java Swing Chess with Pieces");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE + 1, BOARD_SIZE + 1));

        // 체스 기물 초기 배치 (기물 이름: 이미지 파일명)
        Map<String, String> pieceMap = new HashMap<>();
        pieceMap.put("r", "rook.png");    // 룩
        pieceMap.put("n", "knight.png");  // 나이트
        pieceMap.put("b", "bishop.png");  // 비숍
        pieceMap.put("q", "queen.png");   // 퀸
        pieceMap.put("k", "king.png");    // 킹
        pieceMap.put("p", "pawn.png");    // 폰

        // 첫 번째 빈칸 (좌측 상단)
        boardPanel.add(new JLabel(""));

        // 가로(열) 라벨 (A~H)
        for (char col = 'A'; col <= 'H'; col++) {
            JLabel label = new JLabel(String.valueOf(col), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            boardPanel.add(label);
        }

        // 체스 보드 + 세로(행) 라벨 (1~8)
        for (int row = 0; row < BOARD_SIZE; row++) {
            // 세로(행) 라벨 (1~8)
            JLabel rowLabel = new JLabel(String.valueOf(8 - row), SwingConstants.CENTER);
            rowLabel.setFont(new Font("Arial", Font.BOLD, 16));
            boardPanel.add(rowLabel);

            // 체스판 + 기물 추가
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel(new BorderLayout());
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                // 체스 기물 배치
                String piece = getInitialPiece(row, col);
                if (piece != null) {
                    String imagePath = IMAGE_PATH + pieceMap.get(piece.toLowerCase());
                    ImageIcon icon = new ImageIcon(imagePath);
                    JLabel pieceLabel = new JLabel(icon);
                    square.add(pieceLabel, BorderLayout.CENTER);
                }

                boardPanel.add(square);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // 체스 기물 초기 위치 설정
    private String getInitialPiece(int row, int col) {
        String[] backRow = {"r", "n", "b", "q", "k", "b", "n", "r"};
        if (row == 0) return "B" + backRow[col]; // 흑색 기물
        if (row == 1) return "Bp";               // 흑색 폰
        if (row == 6) return "Wp";               // 백색 폰
        if (row == 7) return "W" + backRow[col]; // 백색 기물
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessBoardWithPieces::new);
    }
}
