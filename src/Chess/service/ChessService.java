package Chess.service;

import java.util.HashMap;
import java.util.Map;

public class ChessService {
    private HashMap<String, int[]> position;
    String piece;
    String pos;

    public ChessService() {
        this.position = new HashMap<>();
        this.position.put("w1L1w|", new int[]{0,0});
        this.position.put("w1N1w|", new int[]{0,1});
        this.position.put("w1B1w|", new int[]{0,2});
        this.position.put("w<K>w|", new int[]{0,3});
        this.position.put("w<Q>w|", new int[]{0,4});
        this.position.put("w2B2w|", new int[]{0,5});
        this.position.put("w2N2w|", new int[]{0,6});
        this.position.put("w2L2w|", new int[]{0,7});

        this.position.put("w1P1w|", new int[]{1,0});
        this.position.put("w2P2w|", new int[]{1,1});
        this.position.put("w3P3w|", new int[]{1,2});
        this.position.put("w4P4w|", new int[]{1,3});
        this.position.put("w5P5w|", new int[]{1,4});
        this.position.put("w6P6w|", new int[]{1,5});
        this.position.put("w7P7w|", new int[]{1,6});
        this.position.put("w8P8w|", new int[]{1,7});

        this.position.put("b1L1d|", new int[]{7,0});
        this.position.put("b1N1d|", new int[]{7,1});
        this.position.put("b1B1d|", new int[]{7,2});
        this.position.put("b<K>d|", new int[]{7,3});
        this.position.put("b<Q>d|", new int[]{7,4});
        this.position.put("b2B2d|", new int[]{7,5});
        this.position.put("b2N2d|", new int[]{7,6});
        this.position.put("b2L2d|", new int[]{7,7});

        this.position.put("b1P1d|", new int[]{6,0});
        this.position.put("b2P2d|", new int[]{6,1});
        this.position.put("b3P3d|", new int[]{6,2});
        this.position.put("b4P4d|", new int[]{6,3});
        this.position.put("b5P5d|", new int[]{6,4});
        this.position.put("b6P6d|", new int[]{6,5});
        this.position.put("b7P7d|", new int[]{6,6});
        this.position.put("b8P8d|", new int[]{6,7});
    }

    public boolean piecesCheck(String input, String tmp){
        if(input.startsWith(tmp)&&position.containsKey(input+"|")){
            this.piece = input;
            return true;
        }
        return false;
    }

    public boolean moveCheck(String input){
        if(input.charAt(0)>='A'
                &&input.charAt(0)<='H'
                &&input.charAt(1)>='1'
                &&input.charAt(1)<='8'){
            this.pos = input;
            return true;
        }
        return false;
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
