package Chess.service;

import Chess.model.dao.ChessDao;
import Chess.model.vo.Piece;
import Chess.model.vo.piece.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static Chess.common.JDBCTemplate.*;

public class ChessService extends JFrame {
    private static final int BOARD_SIZE = 8;
    private final Map<String, ImageIcon> pieceImages = new HashMap<>();
    private String[][] position;
    private JPanel boardPanel;
    private Piece piece;
    private ArrayList<String> record = new ArrayList<>();
    private ChessService currentInstance;

    public ChessService() {
        currentInstance = this;
        this.position = new String[][] {
                { "wRw", "wNw", "wBw", "wQw", "wKk", "wBw", "wNw", "wRw" },
                { "wPw", "wPw", "wPw", "wPw", "wPw", "wPw", "wPw", "wPw" },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { "bPd", "bPd", "bPd", "bPd", "bPd", "bPd", "bPd", "bPd" },
                { "bRd", "bNd", "bBd", "bQd", "bKk", "bBd", "bNd", "bRd" } };

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
        String[] pieces = { "wPw", "wRw", "wNw", "wKk", "wQw", "wBw", "bPd", "bNd", "bBd", "bQd", "bKk", "bRd" };
        for (String piece : pieces) {
            pieceImages.put(piece, new ImageIcon("images/" + piece + ".png"));
        }
    }

    public boolean inputCheck(String input, String tmp, String method) {
        if ((input.charAt(0) >= '1'
                && input.charAt(0) <= '8'
                && input.charAt(1) >= 'A'
                && input.charAt(1) <= 'H')) {
        } else return false;
        String item = position[7 - (input.charAt(0) - '1')][input.charAt(1) - 'A'];
        if (method.equals("get")) {
            if (item==null||(item != null && !item.startsWith(tmp)))
                return false;
        } else {
            if (item != null && item.startsWith(tmp))
                return false;
        }
        return true;
    }

    public boolean movable(String piece, String move) {
        String item = position[7 - (piece.charAt(0) - '1')][piece.charAt(1) - 'A'];
        boolean movable = false;
        if (item == null)
            return false;
        switch (item.charAt(1)) {
            case 'P':
                this.piece = new Pawn();
                movable = this.piece.movable(piece, move, position);
                break;
            case 'R':
                this.piece = new Rook();
                movable = this.piece.movable(piece, move, position);
                break;
            case 'K':
                this.piece = new King();
                movable = this.piece.movable(piece, move, position);
                break;
            case 'Q':
                this.piece = new Queen();
                movable = this.piece.movable(piece, move, position);
                break;
            case 'N':
                this.piece = new Knight();
                movable = this.piece.movable(piece, move, position);
                break;
            case 'B':
                this.piece = new Bishop();
                movable = this.piece.movable(piece, move, position);
                break;
        }
        return movable;
    }

    public String move(String piece, String move) {
        String prePosition = position[7 - (piece.charAt(0) - '1')][piece.charAt(1) - 'A'];
        String target = position[7 - (move.charAt(0) - '1')][move.charAt(1) - 'A'];

        record.add(piece + ":" + move);

        if (target!=null&&target.equals("wKk")) {
            JOptionPane.showMessageDialog(null, "게임 종료! 블랙팀 승리!", "체스 게임", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // 현재 ChessService JFrame만 닫기
            return "B";
        }

        else if (target!=null&&target.equals("bKk")){
            JOptionPane.showMessageDialog(null, "게임 종료! 화이트팀 승리!", "체스 게임", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // 현재 ChessService JFrame만 닫기
            return "W";
        }

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

    public ArrayList updateRecord(Long userNo, String victory){
        String allRecord = String.join(",", record);
        String finalPosition = Arrays.deepToString(position);
        ArrayList reordArray = new ArrayList<>();
        reordArray.add(userNo);
        reordArray.add(victory);
        reordArray.add(finalPosition);
        reordArray.add(allRecord);

        return reordArray;
    }

    public int insertRecord(
                             Long userNo,
                             String victory,
                             String position,
                             String record){
        Connection conn = getConnection();
        int result = ChessDao.getInstance().insertRecord(conn, userNo, victory, record, position);
        if (result > 0) {
            commit(conn);
        }else{
            rollback(conn);
        }
        this.dispose();
        return result;
    }

    private void updateBoard() {
        // 체스판의 모든 칸을 업데이트
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = (JPanel) boardPanel.getComponent((row + 1) * (BOARD_SIZE + 1) + col + 1);
                square.removeAll(); // 기존 기물 제거

                String piece = this.position[row][col];
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(pieceImages.get(piece));
                    square.add(pieceLabel, BorderLayout.CENTER);
                }

                square.revalidate();
                square.repaint();
            }
        }
    }

    public void updateBoard(String position) {
        String refine = position.replaceAll("[\\[\\]]", "");
        String[] refine2 = refine.split(", ");
        String[][] refine3 = IntStream.range(0,8)
                .mapToObj(i-> Arrays.copyOfRange(refine2, i*8,(i+1)*8))
                .toArray(String[][]::new);
        // 체스판의 모든 칸을 업데이트
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = (JPanel) boardPanel.getComponent((row + 1) * (BOARD_SIZE + 1) + col + 1);
                square.removeAll(); // 기존 기물 제거

                String piece = refine3[row][col];
                if (piece != null) {
                    JLabel pieceLabel = new JLabel(pieceImages.get(piece));
                    square.add(pieceLabel, BorderLayout.CENTER);
                }

                square.revalidate();
                square.repaint();
            }
        }
    }

    public void closeCurrentBoard() {
        if (currentInstance != null) {
            currentInstance.dispose();
            currentInstance = null;
        }
    }
}
