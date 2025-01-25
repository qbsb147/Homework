package Chess.view;

import java.util.*;

public class ChessBoard {
    public void showBoard(){
        int size = 8;
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        String horizontalLine = "  +" + "-----+".repeat(size);

        System.out.println(horizontalLine);

        String[][] board = setPieces();

        for (int row = 0; row < board.length; row++) {
            System.out.print(8-row + " |");

            for (int col = 0; col < board[row].length; col++) {
                if(board[row][col] != null){
                    System.out.print(board[row][col]);
                }else {
                    if ((row + col) % 2 == 0) {
                        System.out.print("     |"); // 빈 칸 (5칸)
                    } else {
                        System.out.print("#####|"); // 검은 칸 (5칸)
                    }
                }
            }

            System.out.println();
            System.out.println(horizontalLine);
        }

        // 아래 알파벳 좌표 출력
        System.out.print("    ");
        for (char column : columns) {
            System.out.print(column + "     ");
        }
        System.out.println();
    }

    //보드 초기 상태 해시맵 반환
    public HashMap<String, int[]> PiecesInitPosition(){

        HashMap<String, int[]> pieces = new HashMap<>();

        pieces.put("w1L1w|", new int[]{0,0});
        pieces.put("w1N1w|", new int[]{0,1});
        pieces.put("w1B1w|", new int[]{0,2});
        pieces.put("w<K>w|", new int[]{0,3});
        pieces.put("w<Q>w|", new int[]{0,4});
        pieces.put("w2B2w|", new int[]{0,5});
        pieces.put("w2N2w|", new int[]{0,6});
        pieces.put("w2L2w|", new int[]{0,7});

        pieces.put("w1P1w|", new int[]{1,0});
        pieces.put("w2P2w|", new int[]{1,1});
        pieces.put("w3P3w|", new int[]{1,2});
        pieces.put("w4P4w|", new int[]{1,3});
        pieces.put("w5P5w|", new int[]{1,4});
        pieces.put("w6P6w|", new int[]{1,5});
        pieces.put("w7P7w|", new int[]{1,6});
        pieces.put("w8P8w|", new int[]{1,7});

        pieces.put("b1L1d|", new int[]{7,0});
        pieces.put("b1N1d|", new int[]{7,1});
        pieces.put("b1B1d|", new int[]{7,2});
        pieces.put("b<K>d|", new int[]{7,3});
        pieces.put("b<Q>d|", new int[]{7,4});
        pieces.put("b2B2d|", new int[]{7,5});
        pieces.put("b2N2d|", new int[]{7,6});
        pieces.put("b2L2d|", new int[]{7,7});

        pieces.put("b1P1d|", new int[]{6,0});
        pieces.put("b2P2d|", new int[]{6,1});
        pieces.put("b3P3d|", new int[]{6,2});
        pieces.put("b4P4d|", new int[]{6,3});
        pieces.put("b5P5d|", new int[]{6,4});
        pieces.put("b6P6d|", new int[]{6,5});
        pieces.put("b7P7d|", new int[]{6,6});
        pieces.put("b8P8d|", new int[]{6,7});

        return pieces;
    }

    public String[][] setPieces(){
        HashMap<String, int[]> pieceMap = PiecesInitPosition();
        String[][] board = new String[8][8];
        Set<Map.Entry<String, int[]>> allPiece = pieceMap.entrySet();
        for(Map.Entry<String, int[]> entry : allPiece){
            board[entry.getValue()[0]][entry.getValue()[1]] = entry.getKey();
        }
        return board;
    }
}
