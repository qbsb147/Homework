package Chess.service;

import java.util.HashMap;
import java.util.Map;

public class ChessService {
    private HashMap<String, int[]> position = new HashMap<>();

    public boolean piecesCheck(String input){
        boolean check;
        check = true;
        return check;
    }

    public boolean moveCheck(String input){
        boolean check;
        check = true;
        return check;
    }

    private HashMap<String, int[]> initPosition() {
        position.put("w1L1w|", new int[]{0,0});
        position.put("w1N1w|", new int[]{0,1});
        position.put("w1B1w|", new int[]{0,2});
        position.put("w<K>w|", new int[]{0,3});
        position.put("w<Q>w|", new int[]{0,4});
        position.put("w2B2w|", new int[]{0,5});
        position.put("w2N2w|", new int[]{0,6});
        position.put("w2L2w|", new int[]{0,7});

        position.put("w1P1w|", new int[]{1,0});
        position.put("w2P2w|", new int[]{1,1});
        position.put("w3P3w|", new int[]{1,2});
        position.put("w4P4w|", new int[]{1,3});
        position.put("w5P5w|", new int[]{1,4});
        position.put("w6P6w|", new int[]{1,5});
        position.put("w7P7w|", new int[]{1,6});
        position.put("w8P8w|", new int[]{1,7});

        position.put("b1L1d|", new int[]{7,0});
        position.put("b1N1d|", new int[]{7,1});
        position.put("b1B1d|", new int[]{7,2});
        position.put("b<K>d|", new int[]{7,3});
        position.put("b<Q>d|", new int[]{7,4});
        position.put("b2B2d|", new int[]{7,5});
        position.put("b2N2d|", new int[]{7,6});
        position.put("b2L2d|", new int[]{7,7});

        position.put("b1P1d|", new int[]{6,0});
        position.put("b2P2d|", new int[]{6,1});
        position.put("b3P3d|", new int[]{6,2});
        position.put("b4P4d|", new int[]{6,3});
        position.put("b5P5d|", new int[]{6,4});
        position.put("b6P6d|", new int[]{6,5});
        position.put("b7P7d|", new int[]{6,6});
        position.put("b8P8d|", new int[]{6,7});

        return position;
    }

    public String[][] setPieces(HashMap<String, int[]> position){
        String[][] board = new String[8][8];
        HashMap<String, int[]> allPiece = position;
        for(Map.Entry<String, int[]> entry : allPiece.entrySet()){
            board[entry.getValue()[0]][entry.getValue()[1]] = entry.getKey();
        }
        return board;
    }
}
